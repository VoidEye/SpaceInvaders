package spaceinvaders2;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Bomb 
{
    private int positionX = 0;
    private int positionY = 0;
    private boolean collision = false;
    
    ImageIcon skin = new ImageIcon(this.getClass().getResource("bomb.png"));
    private Image bombSkin = skin.getImage();
    
    public Bomb(int PosX, int PosY)
    {
        this.positionX = PosX;
        this.positionY = PosY;
    }
    
    public void paint(Graphics2D g)
    {
        g.drawImage(bombSkin, positionX+3, positionY, null);
    }
    
    public void move()
    {
        positionY += 2;
    }
    
    public int getWidth()
    {
        return bombSkin.getWidth(null);
    }
    
    public int getHeight()
    {
        return bombSkin.getHeight(null);
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(positionX, positionY, this.getWidth(), this.getHeight());
    }
    
    public int getPosY()
    {
        return this.positionY;
    }
}
