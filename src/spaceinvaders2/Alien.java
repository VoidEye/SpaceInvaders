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
    private int mAcceleration=20;
    
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
        
    public void move(AliensList.Direction dir)
    {
        /* Potrzebuję zmiennej difference obliczanej przy każdej iteracji. Jeżeli difference == 0 to przesuń
           czyli potrzebujemy zmiennej odliczajacej sekundy. */
        
        if(dir==AliensList.Direction.DOWN)
        {
            setYPosition(getYposition()+getWidth());
        }
        else
        {
            setXPosition(getXposition() + mAcceleration*(dir==AliensList.Direction.LEFT?-1:1));
        }
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
    
    public int getWidth()
    {
        return alienSkin.getWidth(null);
    }
    
    public int getHeight()
    {
        return alienSkin.getHeight(null);
    }
    
    public int getAcceleration()
    {
        return mAcceleration;
    }
}
