package spaceinvaders2;

import java.awt.Color;
import javax.swing.JFrame;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 * @brief Main class with main function, where window of 'ServerCheck' is created, and later, main game window.
 * Basic network communication is handled there.
 */
public class SpaceInvaders2 extends JFrame 
{

    public static void main(String[] args) throws Exception
    {
        boolean Server = false;
        boolean SinglePlayer = false;
        boolean Client = false;
        
        ServerCheck sCheck = new ServerCheck();
        sCheck.setVisible(true);
        sCheck.setAlwaysOnTop(true);
        
        while(sCheck.isActive()){}
        
        Server = sCheck.getServerBool();
        Client = sCheck.getClientBool();
        SinglePlayer = sCheck.getSingleBool();
        
        JFrame window = new JFrame("Space Invaders");
        long tStart = System.currentTimeMillis();
        Board board = new Board(tStart);

        window.setContentPane(board);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(340, 400);
        window.setVisible(true);
        window.setResizable(false);
        window.getContentPane().setBackground(Color.black);

        board.CreateAliens();

        Socket serverSocket;
        
        if(SinglePlayer)
        {
            while(true) 
            {
                board.Move();
                board.repaint();
 
                Thread.sleep((long) 10);
            }
        }
        
        else if(Server) 
        {
            ServerSocket server = new ServerSocket(1500);
            serverSocket = server.accept();
            ObjectOutputStream objectOutput = new ObjectOutputStream(serverSocket.getOutputStream());
            ObjectInputStream objectInput = new ObjectInputStream(serverSocket.getInputStream());
            while(true) 
            { 
                board.Move();
                board.repaint();
                objectOutput.writeObject(board.GetAlienList());
                objectOutput.flush();
                objectOutput.reset();
                
                objectOutput.writeObject(board.GetBombList());
                objectOutput.flush();
                objectOutput.reset();
                
                objectOutput.writeObject(board.GetPlayerCoords());
                objectOutput.flush();
                objectOutput.reset();
                
                Object object = objectInput.readObject();
                
                LinkedList<Alien> Aliens = (LinkedList<Alien>) object;
                board.UpdateAliens(Aliens);
                
                object = objectInput.readObject();
                LinkedList<Bomb> Bombs = (LinkedList<Bomb>) object;
                board.UpdateBombs(Bombs);
                
                object = objectInput.readObject();
                int[] P2Coords = (int[]) object;
                board.Player2Update(P2Coords[0], P2Coords[1], P2Coords[2], P2Coords[3]);
                
                /*Data to send/recive(P1/P2): Ship position, Projectile position */

                Thread.sleep((long) 10);
            }
        }
        else if(Client)
        {
            Socket socket = new Socket("127.0.0.1",1500);
            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            while (true) 
            {
                Object object = objectInput.readObject();
                
                LinkedList<Alien> Aliens = (LinkedList<Alien>) object;
                board.UpdateAliens(Aliens);
                
                object = objectInput.readObject();
                LinkedList<Bomb> Bombs = (LinkedList<Bomb>) object;
                board.UpdateBombs(Bombs);
                
                object = objectInput.readObject();
                int[] P2Coords = (int[]) object;
                board.Player2Update(P2Coords[0], P2Coords[1], P2Coords[2], P2Coords[3]);
                
                board.ClientMove();
                
                objectOutput.writeObject(board.GetAlienList());
                objectOutput.flush();
                objectOutput.reset();
                
                objectOutput.writeObject(board.GetBombList());
                objectOutput.flush();
                objectOutput.reset();
                
                objectOutput.writeObject(board.GetPlayerCoords());
                objectOutput.flush();
                objectOutput.reset();
                
                
                board.repaint();

                Thread.sleep((long) 10);
            }
        }
    }
}
