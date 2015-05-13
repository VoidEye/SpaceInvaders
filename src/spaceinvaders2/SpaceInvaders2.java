package spaceinvaders2;

import java.awt.Color;
import javax.swing.JFrame;

public class SpaceInvaders2 extends JFrame
{

    public static void main(String[] args) 
    {
       JFrame window = new JFrame("Space Invaders");
       Board board = new Board();
       
       window.setContentPane(board);
       window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       window.setSize(600,800);
       window.setVisible(true);
       window.setResizable(false);
       window.getContentPane().setBackground(Color.black);
       
       while(true)
       {
           board.move();
           board.repaint();
           try
            {
                Thread.sleep((long) 10);
            }
            catch(InterruptedException ex){}
       }
    }
    
}
