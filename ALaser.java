/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aspire;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

public class ALaser {
    private int x,y,colour;
    boolean visible;
    private int width,height;
    private final int DECAY_SPEED = 20;
    
    public ALaser(int x,int y) {
        visible = true;
        height = y;
        width = 11;
        this.x = x;
        this.y = y;
        colour = 0;
    }
    
    public Color getColour(){
        switch(colour){
        case 0: return Color.white;
        case 1: return Color.lightGray;    
        case 2: return Color.CYAN;
        case 3: return Color.blue;    
        default: return Color.BLUE;
            
        }
    }
    
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible(){
        return visible;
    }
    
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    
    public Rectangle getBounds() {       
        return new Rectangle(x+8, 0, width, y+23);
    }
    
    public void move(){
        x+=1;
        width -= 2;
        y -= DECAY_SPEED;
        height -= DECAY_SPEED;
        if (width < -2) visible = false;
        colour++;
    }
       
    public int getW(){
        return width;
    }
// END    
}
