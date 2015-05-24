package spaceinvaders2;

import java.awt.Graphics2D;
import java.awt.Image;
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
    
    public void move()
    {
        /* Potrzebuję zmiennej difference obliczanej przy każdej iteracji. Jeżeli difference == 0 to przesuń
           czyli potrzebujemy zmiennej odliczajacej sekundy. */
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
    
    public void setXPosition(int position)
    {
        this.positionX = position;
    }
    
    public void setYPosition(int position)
    {
        this.positionY = position;
    }
}
