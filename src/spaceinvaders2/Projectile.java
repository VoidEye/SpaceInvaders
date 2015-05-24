package spaceinvaders2;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Projectile 
{
    private int positionX = 0;
    private int positionY = 0;
    private boolean collision = false;
    
    ImageIcon skin = new ImageIcon(this.getClass().getResource("shot.png"));
    private Image projectileSkin = skin.getImage();
    
    private Board boardH;       //board hook
    Ship PlayerShip = new Ship(this);
    
    public Projectile(Board b)
    {
        this.boardH = b;
    }
    public Projectile()
    {
        this.positionY = PlayerShip.getYposition()+ 6;
        this.positionX = PlayerShip.getXposition();
    }

    public Projectile(int positionX, int positionY)
    {
        this.positionX = positionX; 
        this.positionY = positionY;
    }

    public void paint(Graphics2D g)
    {
        g.drawImage(projectileSkin, positionX+7, positionY, null);
    }
    
    public void move()
    {
        --positionY;
    }
    
    public int getPosX()
    {
        return this.positionX;
    }
    
    public void setPosY(int position)
    {
        this.positionY = position;
    }
    public void setPosX(int position)
    {
        this.positionX = position;
    }
    public boolean getProjectileCollision()
    {
        return this.collision;
    }
    public Rectangle getBounds()
    {
        return new Rectangle();
    }
}
