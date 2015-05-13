
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
    Ship PlayerShip = new Ship(this);
    private LinkedList<Projectile> ShootList = new LinkedList<Projectile>();    // Lista do przetrzymywania
                                                                                // wszystkich strzałów
    Projectile projectile = new Projectile(this);
    
    private boolean spaceReleased = true;
    public Board()
    {
        addKeyListener(new KeyListener()                //Obsługa klawiatury.
        {
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
                        //addNewShoot();
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
	setFocusable(true);
    }
//    public void addNewShoot()                           //Dodanie nowego strzału do listy
//    {
//        Projectile projectile = new Projectile(this);
//        ShootList.add(projectile);
//    }
            
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        //Projectile shootHook;
        PlayerShip.paint(g2d);
        projectile.paint(g2d);
//        for(int i = 0; i < ShootList.size(); ++i)
//        {
//            shootHook = ShootList.get(i); // wyprintować wartość Y z każdego kolejnego strzału.
//            
//            shootHook.paint(g2d);
//            g2d.drawString(String.valueOf(PlayerShip.getXposition()), 10+i*120, 30);
//        }
    }
    
    public void move()
    {
        PlayerShip.move();
        projectile.move();
//        for(int i = 0; i < ShootList.size(); ++i)
//        {
//            Projectile shootHook;
//            shootHook = ShootList.get(i);
//            shootHook.move();
//        }
    }
}
