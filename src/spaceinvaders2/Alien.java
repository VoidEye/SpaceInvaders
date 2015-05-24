package spaceinvaders2;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;


public class Alien 
{
    private int positionX = 60;
    private int positionY = 200;
    ImageIcon skin = new ImageIcon(this.getClass().getResource("alien.png"));
    private Image alienSkin = skin.getImage();
    private Board boardH; 
    
    public Alien(int PosX, int PosY, Board b)
    {
        this.boardH = b;
        this.positionX = PosX;
        this.positionY = PosY;
    }
    
    public void paint(Graphics2D g)
    {
        g.drawImage(alienSkin, positionX, positionY, null);
    }
    
    public void shot(int positionX, int positionY)
    {
        //co pół skeundy + rand, tandomowy alien szczela.
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
