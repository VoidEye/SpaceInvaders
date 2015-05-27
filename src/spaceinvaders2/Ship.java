package spaceinvaders2;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 * @brief class responsible for placing a player ship in board space
 * There are functions like get position, get bounds, get width, move and paint
 */
public class Ship 
{
    ImageIcon skin = new ImageIcon(this.getClass().getResource("player.png"));
    private Image shipSkin = skin.getImage();
    
    private Board boardH;       //board hook
    
    private int positionX = 140;        //Ship position     
    private int positionY = 350;
    private int xAcceleration = 0;      //Ship movment speed
    
    private boolean spaceReleased = true;
    
    public Ship(Board b)
    {
        this.boardH = b;        //Need for comunication between classes
    }
    
    public Ship(int posX, int posY, Board b)
    {
        this.positionX = posX;
        this.positionY = posY;
        this.boardH = b;        //Need for comunication between classes
    }
    
    public void paint(Graphics2D g)
    {
        g.drawImage(shipSkin, positionX, positionY, null);
    }
    
    public void Move()
    {
        if((positionX + xAcceleration > 0) && 
        (positionX + xAcceleration < boardH.getWidth() - this.GetWidth()))
            positionX += xAcceleration;
    }

    public void Destroyed()
    {
        this.positionX = 140;
        this.positionY = 350;
        try
        {
            Thread.sleep((long) 500);
        }
        catch(InterruptedException ex){}
    }
    
    public int GetXposition()
    {
        return positionX;
    }
    public int GetYposition()
    {
        return positionY;
    }
    public void SetXposition(int posX)
    {
        this.positionX = posX;
    }
    public void SetYposition(int posY)
    {
        this.positionY = posY;
    }
    
    public void SetXacceleration(int n)
    {
         this.xAcceleration = n;
    }
        public int GetWidth()
    {
        return shipSkin.getWidth(null);
    }
    
    public int GetHeight()
    {
        return shipSkin.getHeight(null);
    }
    
    public Rectangle GetBounds()
    {
        return new Rectangle(positionX, positionY, this.GetWidth(), this.GetHeight());
    }
    
    public Image GetImage()
    {
        return skin.getImage();
    }
}
