package spaceinvaders2;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;


public class Alien 
{
    private int positionX = 0;
    private int positionY = 0;
    ImageIcon skin = new ImageIcon(this.getClass().getResource("alien.png"));
    private Image alienSkin = skin.getImage();
    
    public Alien()
    {
        
    }
    
    public void paint(Graphics2D g)
    {
        g.drawImage(alienSkin, positionX, positionY, null);
    }
    
    public void move()
    {
        //Prouszanie się z czasem rzeczywistym, czy z ilością instrkucji?
        //Poruszanie sięz czasem nie będzie freezowało gry? Zrobć to na innym wątku?
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
}
