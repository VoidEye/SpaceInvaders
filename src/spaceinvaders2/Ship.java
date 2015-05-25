package spaceinvaders2;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Ship 
{
    ImageIcon skin = new ImageIcon(this.getClass().getResource("player.png"));
    private Image shipSkin = skin.getImage();
    
    private Board boardH;       //board hook
    private Projectile projectileH;
    
    private int positionX = 140;        //Ship position     
    private int positionY = 350;
    private int Xacceleration = 0;      //Ship movment speed
    
    private final int WIDTH = 20;   //Width of a ship
    
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
    
    public void move()
    {
        if((positionX + Xacceleration > 0) && 
        (positionX + Xacceleration < boardH.getWidth() - this.getWidth()))
            positionX += Xacceleration;
    }

    public void destroyed()
    {
        this.positionX = 140;
        this.positionY = 350;
        try
        {
            Thread.sleep((long) 500);
        }
        catch(InterruptedException ex){}
        //add game over when reached position of ship.
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
        public int getWidth()
    {
        return shipSkin.getWidth(null);
    }
    
    public int getHeight()
    {
        return shipSkin.getHeight(null);
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(positionX, positionY, this.getWidth(), this.getHeight());
    }
    
    public Image getImage()
    {
        return skin.getImage();
    }
}
