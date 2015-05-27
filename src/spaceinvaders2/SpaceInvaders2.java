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
            while(true) 
            { 
                board.Move();
                board.repaint();
                objectOutput.writeObject(board.GetAlienList());
                objectOutput.flush();
                objectOutput.reset();

                /*Data to send/recive(P1/P2): Ship position, Projectile position,
                  Lifes left, Bombs position, Bombs count
                  Aliens position, Aliens count */

                Thread.sleep((long) 10);
            }
        }
        else if(Client)
        {
            Socket socket = new Socket("127.0.0.1",1500);
            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
            while (true) 
            {
                Object object = objectInput.readObject();
                
                LinkedList<Alien> Aliens = (LinkedList<Alien>) object;
                board.UpdateAliens(Aliens);
                
                board.ClientMove();
                board.repaint();

                Thread.sleep((long) 10);
            }
        }
    }
}
