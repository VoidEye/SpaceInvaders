/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders2;

import java.awt.Graphics2D;
import java.util.LinkedList;

/**
 *
 * @author Michał
 */
public class AliensList
{    
    public enum Direction {RIGHT, LEFT, DOWN};
    
    private LinkedList<Alien> mAliens = new LinkedList<Alien>();
    private Direction mAliensDirection=Direction.RIGHT;
    private boolean mMoveDown=false;
    
    public void addAlien(Alien pAlien)
    {
        mAliens.add(pAlien);
    }
    
    public void changeDirection()
    {
        mAliensDirection=mAliensDirection==Direction.LEFT?Direction.RIGHT:Direction.LEFT;
        mMoveDown=true;
    }
    
    public void drawAllAliens(Graphics2D g2d)
    {
        Alien AlienToDraw;
        for(int i = 0; i < mAliens.size(); ++i)
        {
            AlienToDraw = mAliens.get(i);
            AlienToDraw.paint(g2d);
        }
    }
    
    public void moveAllAliens()
    {
        Alien AlienToMove;
        for(int i = 0; i < mAliens.size(); ++i)
        {
            AlienToMove = mAliens.get(i);
            AlienToMove.move(mAliensDirection);
            
            if(mMoveDown)
            {
                AlienToMove.move(Direction.DOWN);
            }
        }
        mMoveDown=false;
    }
    
    public boolean checkVerticalCollision(int endValue, Direction dir)
    {
        Alien AlienToMove;
        
        switch(dir)
        {
            case RIGHT:
            {
                AlienToMove=mAliens.getLast();
                if(AlienToMove.getXposition()+AlienToMove.getWidth() + AlienToMove.getAcceleration() > endValue)
                {
                    return true;
                }
                break;
            }
            case LEFT:
            {
                AlienToMove=mAliens.getFirst();
                if(AlienToMove.getXposition() - AlienToMove.getAcceleration() <endValue)
                {
                    return true;
                }
                break;
            }
        }
        
        return false;
    }
}
