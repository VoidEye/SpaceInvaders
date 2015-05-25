package spaceinvaders2;

import java.awt.Color;
import javax.swing.JFrame;

public class SpaceInvaders2 extends JFrame
{

    public static void main(String[] args) 
    {
       JFrame window = new JFrame("Space Invaders");
       long tStart = System.currentTimeMillis();
       Board board = new Board(tStart);
       
       window.setContentPane(board);
       window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       window.setSize(340,400);
       window.setVisible(true);
       window.setResizable(false);
       window.getContentPane().setBackground(Color.black);
       
       board.CreateAliens();
       while(true)
       {
           board.Move();
           board.repaint();
           try
            {
                Thread.sleep((long) 10);
            }
            catch(InterruptedException ex){}
       }
    }
    
}
