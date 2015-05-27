package spaceinvaders2;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import java.io.Serializable;

/**
 * @brief class responsible for placing a Alien in board space
 * There are functions like get position, get bounds, get width and paint
 */
public class Alien implements Serializable
{
    private int positionX = 60;
    private int positionY = 200;
    private transient final ImageIcon skin = new ImageIcon(this.getClass().getResource("alien.png"));
    private transient final Image alienSkin = skin.getImage();
    
    public Alien(int PosX, int PosY)
    {
        this.positionX = PosX;
        this.positionY = PosY;
    }
    
    public void paint(Graphics2D g)
    {
        g.drawImage(alienSkin, positionX, positionY, null);
    }
    
    public int getXposition()
    {
        return positionX;
    }
    public int getYposition()
    {
        return positionY;
    }
    
    public void setXPosition(int position)
    {
        this.positionX = position;
    }
    
    public void setYPosition(int position)
    {
        this.positionY = position;
    }
    
    public int getWidth()
    {
        return alienSkin.getWidth(null);
    }
    
    public int getHeight()
    {
        return alienSkin.getHeight(null);
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(positionX, positionY, this.getWidth(), this.getHeight());
    }
}
