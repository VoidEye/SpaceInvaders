package spaceinvaders2;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * @brief class responsible for placing a player projectile in board space
 * There are functions like get position, get bounds, get width, move and paint
 */
public class Projectile 
{
    private int positionX = 0;
    private int positionY = 0;
    private boolean collision = false;
    
    ImageIcon skin = new ImageIcon(this.getClass().getResource("shot.png"));
    private Image projectileSkin = skin.getImage();

    public Projectile(){}
    public Projectile(int posX, int posY)
    {
        this.positionX = posX;
        this.positionY = posY;
    }

    public void paint(Graphics2D g)
    {
        g.drawImage(projectileSkin, positionX+7, positionY, null);
    }
    
    public void Move()
    {
        positionY -= 3;
    }
    
    public void remove()
    {
        this.positionX = 800; 
        this.positionY = 800;
    }
    
    public int getPosX()
    {
        return this.positionX;
    }
    
    public int getPosY()
    {
        return this.positionY;
    }
    
    public void setPosY(int position)
    {
        this.positionY = position;
    }
    public void setPosX(int position)
    {
        this.positionX = position;
    }
    
    public int getWidth()
    {
        return projectileSkin.getWidth(null);
    }
    
    public int getHeight()
    {
        return projectileSkin.getHeight(null);
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(positionX, positionY, this.getWidth()+10, this.getHeight()+10);
    }
    
    public boolean getProjectileCollision()
    {
        return this.collision;
    }
    
    public void setProjectileCollision(boolean x)
    {
        this.collision = x;
    }
}
