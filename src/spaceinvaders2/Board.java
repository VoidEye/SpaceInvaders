package spaceinvaders2;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.JPanel;

public class Board extends JPanel
{
    //To paint a ship, move it and generate projectile from it's position.
    Ship PlayerShip = new Ship(this);
    
    //To paint a projectile, move it and set's it's position when shot.
    Projectile projectile = new Projectile(this);
    
    //For handling all Alliens, moving them, painting we use list.
    private LinkedList<Alien> Aliens = new LinkedList<Alien>();
    
    //a variable to manage holding "space" becaouse we don't want to shoot, when key "space" is hold
    private boolean spaceReleased = true;
    
    private long time;
    
    //In this constructor we use anonymous class to handle key events
    //TODO: why in constructor?
    //Because we want to make it only once, and ...?
    public Board(long time)
    {
        this.time = time;
        addKeyListener(new KeyListener()                //keyboard handling.
        {
            
            //TODO: Do I need this?
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) 
            {
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                    PlayerShip.setXacceleration(-1);
                
                if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    PlayerShip.setXacceleration(1);
                
                if(e.getKeyCode() == KeyEvent.VK_SPACE)
                    if(spaceReleased)
                    {
                        if(!projectile.getProjectileCollision())
                        {
                            projectile.setPosY(PlayerShip.getYposition());
                            projectile.setPosX(PlayerShip.getXposition());
                            spaceReleased = false;
                        }
                    }
            }
            @Override
            public void keyReleased(KeyEvent e) 
            {
		if(!spaceReleased)
                    spaceReleased = true;

                if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    PlayerShip.setXacceleration(0);
                }
            }
        });
        // TODO: What is this?
	setFocusable(true);
    }
    
    //Function creates starting Aliens to linked list, they are painted later.
    public void CreateAliens()
    {
        int AlienPosX;
        int AlienPosY = 60;
        
        for(int i = 0; i < 5; ++i)
        {
            AlienPosX = 10;
            for(int j = 0; j < 11; ++j)
            {
                AlienPosX += 20;
                Alien BadAlien = new Alien(AlienPosX, AlienPosY, this);
                Aliens.add(BadAlien);
            }
            AlienPosY += 20;
        }
    }
            
    @Override
    public void paint(Graphics g)
    {
        //TODO: why like this.
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        Alien AlienToDraw;
        for(int i = 0; i < 55; ++i)
        {
            AlienToDraw = Aliens.get(i);
            AlienToDraw.paint(g2d);
        }
        PlayerShip.paint(g2d);
        projectile.paint(g2d);
    }

     private boolean reachedREnd = false;
     private boolean reachedLEnd = true;
     
    public void move()
    {
        PlayerShip.move();
        projectile.move();
        long tEnd = System.currentTimeMillis();
        
        if((tEnd - time) >= 200)
        {
            this.time = System.currentTimeMillis();
            
            int AlienAcceleration = 20;

            Alien AlienToMove = Aliens.getLast();
            
            if(AlienToMove.getXposition() > this.getWidth())
            {
                reachedREnd = true;
                reachedLEnd = false;
                AlienToMove = Aliens.getFirst();
            }
            
            if(AlienToMove.getXposition() < 0)
            {
                reachedREnd = false;
                reachedLEnd = true;
                AlienToMove = Aliens.getLast();
            }
            if(reachedREnd && !reachedLEnd)
                AlienAcceleration = -20;
             
            for (int i = 0; i < 55; ++i) 
            {
                AlienToMove = Aliens.get(i);
                AlienToMove.setXPosition(AlienToMove.getXposition() + AlienAcceleration);
            }
        }
    }
}
