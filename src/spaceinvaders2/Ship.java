package spaceinvaders2;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Ship 
{
    ImageIcon skin = new ImageIcon(this.getClass().getResource("player.png"));
    private Image shipSkin = skin.getImage();
    
    private Board boardH;       //board hook
    private Projectile projectileH;
    
    private int positionX = 300;        //Ship position     
    private final int positionY = 700;
    private int Xacceleration = 0;      //Ship movment speed
    
    private final int WIDTH = 20;   //Width of a ship
    private final int HEIGHT = 20;   //Height of a ship
    
    private boolean spaceReleased = true;
    
    public Ship(Board b)
    {
        this.boardH = b;        //Need for comunication between classes
    }
    public Ship(Projectile p)
    {
        this.projectileH = p;
    }
    
    public void paint(Graphics2D g)
    {
        g.drawImage(shipSkin, positionX, positionY, null);
    }
    
//    public void keyReleased(KeyEvent e)
//    {
//        if(!spaceReleased)
//            spaceReleased = true;
//
//        if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT)
//        {
//            Xacceleration = 0;
//        }
//    }
//    public void keyPressed(KeyEvent e)              //Poruszanie i strzelanie gracza.
//    {
//        if(e.getKeyCode() == KeyEvent.VK_LEFT)
//                Xacceleration = -1;
//    
//        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
//                Xacceleration = 1;
//        
//        if(e.getKeyCode() == KeyEvent.VK_SPACE)
//            if(spaceReleased)
//            {
//                boardH.addNewShoot();
//                spaceReleased = false;
//            }
//    }
    
    public void move()
    {
        if(positionX + Xacceleration > 0 && positionX + Xacceleration < boardH.getWidth() - WIDTH)
            positionX += Xacceleration;
    }

    public void destroyed()
    {
        //TODO
    }
    
    public int getXposition()
    {
        return positionX;
    }
    public int getYposition()
    {
        return positionY;
    }
    
    public void setXacceleration(int n)
    {
         this.Xacceleration = n;
    }
    //TODO: COLLISION WITH ALLIEN PROJECTILE.
    
}
