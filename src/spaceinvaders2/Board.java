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
    //To paint a ship, move it and generate projectile from it's position.
    Ship PlayerShip = new Ship(this);
    
    //To paint a projectile, move it and set's it's position when shot.
    Projectile projectile = new Projectile(this);
    
    //For handling all Alliens, moving them, painting we use list.
    private LinkedList<Alien> Aliens = new LinkedList<Alien>();
    
    //Alien bombs
    private LinkedList<Bomb> AlienBombs = new LinkedList<Bomb>();
    
    //a variable to manage holding "space" because we don't want to shoot, when key "space" is hold
    private boolean spaceReleased = true;
    
    private long time;
    private int AlienCount;
    private int PlayerLifes = 3;
    
    //In this constructor we use anonymous class to handle key events
    //TODO: why in constructor?
    //Because we want to make it only once, and ...?
    public Board(long time)
    {
        this.time = time;
        addKeyListener(new KeyListener()                //keyboard handling.
        {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) 
            {
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                    PlayerShip.setXacceleration(-2);
                
                if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    PlayerShip.setXacceleration(2);
                
                if(e.getKeyCode() == KeyEvent.VK_SPACE)
                    if(spaceReleased)
                    {
                        if(!projectile.getProjectileCollision())
                        {
                            projectile.setPosY(PlayerShip.getYposition());
                            projectile.setPosX(PlayerShip.getXposition());
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
        
        for(int i = 0; i < 4; ++i)
        {
            AlienPosX = 10;
            for(int j = 0; j < 9; ++j)
            {
                AlienPosX += 20;
                Alien BadAlien = new Alien(AlienPosX, AlienPosY);
                Aliens.add(BadAlien);
            }
            AlienPosY += 20;
        }
        this.AlienCount = 4*9;
    }
    
    public void ThrowBomb(int AlienPositionX, int AlienPositionY)
    {
        Bomb BombToAdd = new Bomb(AlienPositionX, AlienPositionY);
        AlienBombs.add(BombToAdd);
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
        for(int i = 0; i < AlienCount; ++i)
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
        PlayerShip.paint(g2d);
        projectile.paint(g2d);
        
        for(int i = 0; i < PlayerLifes; ++i)
        {
            Image Life = PlayerShip.getImage();
            g.drawImage(Life, i*20, 20, null);
        }
    }

    private int AlienAcceleration = 20;
    private boolean reachedR = true;
    
    public void move()
    {
        //Moving palayerShip and it's projectile
        this.PlayerCollision();
        PlayerShip.move();
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

        if((tEnd - time) >= 1000)
        {
            this.time = System.currentTimeMillis();
            

            Alien AlienToMove = Aliens.getLast();
            
            if(AlienToMove.getXposition() + AlienToMove.getWidth() + Math.abs(AlienAcceleration) > this.getWidth() && reachedR)//reached right end
            {
                AlienAcceleration *= -1;
                reached = true;
                reachedR = false;
            }

            AlienToMove = Aliens.getFirst();
            
            if(AlienToMove.getXposition() - Math.abs(AlienAcceleration) <0 && !reachedR)//reached left end
            {
                AlienAcceleration *= -1;
                reached = true;
                reachedR = true;
            }
            
            for (int i = 0; i < AlienCount; ++i) 
            {
                AlienToMove = Aliens.get(i);
                if(reached)
                    AlienToMove.setYPosition(AlienToMove.getYposition() + 20);
                else
                    AlienToMove.setXPosition(AlienToMove.getXposition() + AlienAcceleration);
            }
            //Bomb Throwing mechanism
            for(int i = 0; i < 2; ++i)
            {
                Random rand = new Random();
                int randomNum = rand.nextInt((Aliens.size() - 2) + 1) + 1;
                AlienToMove = Aliens.get(randomNum);
                ThrowBomb(AlienToMove.getXposition(), AlienToMove.getYposition());
            }
        }
    }
    
    public void AlienCollision()
    {
        Alien AlienToCheck;
        for(int i = 0; i < AlienCount; ++i)
        {
            AlienToCheck = Aliens.get(i);
            if(AlienToCheck.getBounds().intersects(projectile.getBounds()))
            {
               this.AlienCount--;
               this.Aliens.remove(i);
               projectile.setProjectileCollision(false);
               projectile.remove();
            }
        }
        if(projectile.getPosY() <= 0)
            projectile.setProjectileCollision(false);
    }
    public void PlayerCollision()
    {
        Bomb BombToCheck;
        for(int i = 0; i < AlienBombs.size(); ++i)
        {
            BombToCheck = AlienBombs.get(i);
            if(PlayerShip.getBounds().intersects(BombToCheck.getBounds()))
            {
                PlayerShip.destroyed();
                this.AlienBombs.clear();
                --PlayerLifes;
                if(PlayerLifes == -1)
                {
                    JOptionPane.showMessageDialog(null, "Game Over");
                    System.exit(-1);
                }
            }
            if(BombToCheck.getPosY() > 400)
                this.AlienBombs.remove(i);
        }
    }
}
