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

/**
 * 
 * @brief most important class of program, where all functions responsible for painting, moving, sending and receiving data through network are stored.
 * 
 */

public class Board extends JPanel
{
    //To paint a ship, Move it and generate projectile from it's position.
    Ship playerShip = new Ship(this);
    //Connected player ship
    Ship player2Ship = new Ship(800, 800, this);
    
    //To paint a projectile, Move it and set's it's position when shot.
    Projectile projectile = new Projectile();
    //Connected player projectile
    Projectile projectile2 = new Projectile(800, 800);
    
    //For handling all Alliens, moving them, painting we use list.
    private LinkedList<Alien> Aliens = new LinkedList<Alien>();
    
    //Alien bombs
    private LinkedList<Bomb> AlienBombs = new LinkedList<Bomb>();
    
    //a variable to manage holding "space" because we don't want to shoot, when key "space" is hold
    private boolean spaceReleased = true;
    
    //Player lifes
    private int playerLifes = 3;
    
    //Variable necessary for moving aliens in the right pace
    private long timeStart;
    //Variable determines a pace on which aliens move(1000 = 1s)
    private final int alienPositionChangePace = 1000;
    //Number of aliens appering on the screen
    private int alienCount;

    //Variable determines how many pixels single alien moves every time it moves
    private int alienAcceleration = 20;
    
    //Variable necessary for moving aliens downwards.
    private boolean reachedR = true;
    
    
    /**
     * @brief Default constructor.
     * 
     * Constructor takes argument 'time' which is result of function 'System.currentTimeMillis();'
     * it's is necessary for proper Alien movement.
     * In this constructor we make anonymous class that is required for keyboard handling.
     * 
     */
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
    
    /**@brief Function creates starting Aliens to linked list, they are painted later.
     * 
     */
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
    
    
    /** @brief Function adds bomb at the specified location
     */
    public void ThrowBomb(int alienPositionX, int alienPositionY)
    {
        Bomb bombToAdd = new Bomb(alienPositionX, alienPositionY);
        AlienBombs.add(bombToAdd);
    }
           
    /** @brief Painting Aliens, SHips, Bombs and Projectiles
     * 
     */
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
        player2Ship.paint(g2d);
        projectile.paint(g2d);
        projectile2.paint(g2d);
        
        for(int i = 0; i < playerLifes; ++i)
        {
            Image Life = playerShip.GetImage();
            g.drawImage(Life, i*20, 20, null);
        }
    }

    
    /** @brief In this function player ship, projectile, bombs, and aliens are moved.
      * also we check there Collisions (bomb with player, projectile with alien)
      * We check there, if aliens got to the Players position, if yes, the game is over.*/
    public void Move()
    {
        //Checking PleyerShip Collision, moving it and it's projectile
        this.PlayerCollision();
        playerShip.Move();
        projectile.Move();
        if(projectile.getPosY() <= 0)
        {
            projectile.setProjectileCollision(false);
            projectile.remove();
        }
        
        //Moving Bombs
        for(int i = 0; i < AlienBombs.size(); ++i)
        {
            AlienBombs.get(i).move();
        }
        
        //Moving Aliens
        this.AlienCollision();
        
        /*Aliens are moved at the pace of one second, at each iteration we assign new value
         to tEnd, to check how much time has passed since we last moved aliens*/
        long tEnd = System.currentTimeMillis();
        
        //a variable necessary for moving Aliens downwards
        boolean reached = false;
        
        //There we check if enough time passed to move Aliens.
        if((tEnd - timeStart) >= alienPositionChangePace)
        {
            //Here is assigned new value to variable timeStart, so we can be sure, that Aliens are moving at the same pace
            this.timeStart = System.currentTimeMillis();
            
            //We take last Alien in list, and check if it has reached right border
            Alien alienToMove = Aliens.getLast();
            //If he did, we start moving to the left
            if(alienToMove.getXposition() + alienToMove.getWidth() + Math.abs(alienAcceleration) > this.getWidth() && reachedR)//reached right end
            {
                alienAcceleration *= -1;
                reached = true;
                reachedR = false;
            }
            //Here we take First alien from the list, to check if he reached left border
            alienToMove = Aliens.getFirst();
            //If he did, we start moving right
            if(alienToMove.getXposition() - Math.abs(alienAcceleration) <0 && !reachedR)//reached left end
            {
                alienAcceleration *= -1;
                reached = true;
                reachedR = true;
            }
            /*There we move Aliens in vertical or horizontal motion.
              Also, we check if any Alien reached Player position
            */
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
            /* A random alien is taken and a new bomb is created at his position*/
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
    
    /*
     Function for checking if any alien was hit by a projectile, 
     and if all aliens are dead player wins.
    */
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
            }
            if(alienCount <= 0)
            {
                JOptionPane.showMessageDialog(null, "You won!");
                System.exit(-1);
            }  
        }
    }
    
    /**
     * @brief Function for checking if Player was hit by a bomb, 
     * and if all PlayerLifes are used, player loose.
     * Also, if a bomb misses player we remove it to maintain List of Bombs.
    */
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
                projectile.setProjectileCollision(false);
                projectile.remove();
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
    
    /**
     *  @brief Updates connected player ship and projectile positions
    */
    public void Player2Update(int shipPositionX, int shipPositionY, int projPositionX, int projPositionY, int lifesLeft)
    {
        this.player2Ship.SetXposition(shipPositionX);
        this.player2Ship.SetYposition(shipPositionY);
        
        this.projectile2.setPosX(projPositionX);
        this.projectile2.setPosY(projPositionY);
    }
    
    /**
      * @brief Required for sending information to connected player
    */
    public LinkedList GetAlienList()
    {
        return this.Aliens;
    }
    
    /**
     * @brief  Required for sending information to connected player
    */
    public LinkedList GetBombList()
    {
        return this.AlienBombs;
    }
    
    /**
      * @brief Updating Alien information for connected player
    */
    public void UpdateAliens(LinkedList<Alien> A)
    {
        Alien alienRecived;
        
        this.Aliens.clear();
        
        for(int i = 0; i < A.size(); ++i)
        {
            alienRecived = A.get(i);
            Alien alienToMake = new Alien(alienRecived.getXposition(), alienRecived.getYposition());
            Aliens.add(alienToMake);
        }
        this.alienCount = A.size();
    }
    
    /**
     *   @brief Updating Bombs information for connected player
    */
    public void UpdateBombs(LinkedList<Bomb> B)
    {
        this.AlienBombs = B;
    }
    
    /**
      * @brief A smaller version of function Move, 
      * where we don't need to move aliens and bombs,
      * because they are updated through network.
    */
    public void ClientMove()
    {
        this.PlayerCollision();
        this.AlienCollision();
        playerShip.Move();
        projectile.Move();
    }
}