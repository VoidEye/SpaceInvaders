package spaceinvaders2;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.JPanel;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Board extends JPanel
{
    //To paint a ship, Move it and generate projectile from it's position.
    Ship playerShip = new Ship(this);
    
    //To paint a projectile, Move it and set's it's position when shot.
    Projectile projectile = new Projectile();
    
    //For handling all Alliens, moving them, painting we use list.
    private LinkedList<Alien> Aliens = new LinkedList<Alien>();
    
    //Alien bombs
    private LinkedList<Bomb> AlienBombs = new LinkedList<Bomb>();
    
    //a variable to manage holding "space" because we don't want to shoot, when key "space" is hold
    private boolean spaceReleased = true;
    
    private long timeStart;
    private int alienCount;
    private int playerLifes = 3;
    private int alienAcceleration = 20;
    private final int alienPositionChangePace = 1000;
    private boolean reachedR = true;
    
    //In this constructor we use anonymous class to handle key events
    //TODO: why in constructor?
    //Because we want to make it only once, and ...?
    public Board(long time)
    {
        this.timeStart = time;
        addKeyListener(new KeyListener()                //keyboard handling.
        {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) 
            {
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                    playerShip.SetXacceleration(-2);
                
                if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    playerShip.SetXacceleration(2);
                
                if(e.getKeyCode() == KeyEvent.VK_SPACE)
                    if(spaceReleased)
                    {
                        if(!projectile.getProjectileCollision())
                        {
                            projectile.setPosY(playerShip.GetYposition());
                            projectile.setPosX(playerShip.GetXposition());
                            projectile.setProjectileCollision(true);
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
                    playerShip.SetXacceleration(0);
                }
            }
        });
	setFocusable(true);
    }
    
    //Function creates starting Aliens to linked list, they are painted later.
    public void CreateAliens()
    {
        int alienPosX;
        int alienPosY = 60;
        
        for(int i = 0; i < 4; ++i)
        {
            alienPosX = 10;
            for(int j = 0; j < 9; ++j)
            {
                alienPosX += 20;
                Alien alienToMake = new Alien(alienPosX, alienPosY);
                Aliens.add(alienToMake);
            }
            alienPosY += 20;
        }
        this.alienCount = 4*9;
    }
    
    public void ThrowBomb(int alienPositionX, int alienPositionY)
    {
        Bomb bombToAdd = new Bomb(alienPositionX, alienPositionY);
        AlienBombs.add(bombToAdd);
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
        for(int i = 0; i < alienCount; ++i)
        {
            AlienToDraw = Aliens.get(i);
            AlienToDraw.paint(g2d);
        }
        Bomb BombToDraw;
        for(int i = 0; i < AlienBombs.size(); ++i)
        {
            BombToDraw = AlienBombs.get(i);
            BombToDraw.paint(g2d);
        }
        playerShip.paint(g2d);
        projectile.paint(g2d);
        
        for(int i = 0; i < playerLifes; ++i)
        {
            Image Life = playerShip.GetImage();
            g.drawImage(Life, i*20, 20, null);
        }
    }

    public void Move()
    {
        //Moving palayerShip and it's projectile
        this.PlayerCollision();
        playerShip.Move();
        projectile.move();
        
        //Moving Bombs
        for(int i = 0; i < AlienBombs.size(); ++i)
        {
            AlienBombs.get(i).move();
        }
        
        //Moving Aliens
        this.AlienCollision();
        
        long tEnd = System.currentTimeMillis();
        boolean reached = false;

        if((tEnd - timeStart) >= alienPositionChangePace)
        {
            this.timeStart = System.currentTimeMillis();
            

            Alien alienToMove = Aliens.getLast();
            
            if(alienToMove.getXposition() + alienToMove.getWidth() + Math.abs(alienAcceleration) > this.getWidth() && reachedR)//reached right end
            {
                alienAcceleration *= -1;
                reached = true;
                reachedR = false;
            }

            alienToMove = Aliens.getFirst();
            
            if(alienToMove.getXposition() - Math.abs(alienAcceleration) <0 && !reachedR)//reached left end
            {
                alienAcceleration *= -1;
                reached = true;
                reachedR = true;
            }
            
            for (int i = 0; i < alienCount; ++i) 
            {
                alienToMove = Aliens.get(i);
                if(reached)
                    alienToMove.setYPosition(alienToMove.getYposition() + 20);
                else
                    alienToMove.setXPosition(alienToMove.getXposition() + alienAcceleration);
                if(alienToMove.getYposition() >= playerShip.GetYposition())
                {
                    JOptionPane.showMessageDialog(null, "Game Over");
                    System.exit(-1);
                }
            }
            //Bomb Throwing mechanism
            for(int i = 0; i < 2; ++i)
            {
                Random rand = new Random();
                if(Aliens.size() > 1)
                {
                    int randomNum = rand.nextInt((Aliens.size() - 2) + 1) + 1;
                    alienToMove = Aliens.get(randomNum);
                    ThrowBomb(alienToMove.getXposition(), alienToMove.getYposition());
                }
            }
        }
    }
    
    public void AlienCollision()
    {
        Alien alienToCheck;
        for(int i = 0; i < alienCount; ++i)
        {
            alienToCheck = Aliens.get(i);
            if(alienToCheck.getBounds().intersects(projectile.getBounds()))
            {
               this.alienCount--;
               this.Aliens.remove(i);
               projectile.setProjectileCollision(false);
               projectile.remove();
               if(alienCount <= 0)
               {
                   JOptionPane.showMessageDialog(null, "You won!");
                   System.exit(-1);
               }
            }
        }
        if(projectile.getPosY() <= 0)
            projectile.setProjectileCollision(false);
    }
    public void PlayerCollision()
    {
        Bomb bombToCheck;
        for(int i = 0; i < AlienBombs.size(); ++i)
        {
            bombToCheck = AlienBombs.get(i);
            if(playerShip.GetBounds().intersects(bombToCheck.getBounds()))
            {
                playerShip.Destroyed();
                this.AlienBombs.clear();
                --playerLifes;
                if(playerLifes == -1)
                {
                    JOptionPane.showMessageDialog(null, "Game Over");
                    System.exit(-1);
                }
            }
            if(bombToCheck.getPosY() > 400)
                this.AlienBombs.remove(i);
        }
    }
}


/* TODO: some refactoring, netwrok communication, score, proper documentation comments*/