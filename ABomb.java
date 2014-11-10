/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aspire;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class ABomb {
    private int x,y,anim;
    private Image bomb0, bomb1, bomb2;
    boolean visible;
    private int width,height;
    private final int BOARD_HEIGHT = 800;
    private final int BOMB_SPEED = 10;
    
    public ABomb(int x, int y){
        bomb0 = new ImageIcon(this.getClass().getResource("img/bomb0.png")).getImage();
        bomb1 = new ImageIcon(this.getClass().getResource("img/bomb1.png")).getImage();
        bomb2 = new ImageIcon(this.getClass().getResource("img/bomb2.png")).getImage();
        visible = true;
        width = bomb1.getWidth(null);
        height = bomb1.getHeight(null);
        this.x = x;
        this.y = y;
        anim = 0;
    }
    
    public Image getImage(){
        switch(anim){
                case 0: return bomb0;
                case 1: return bomb1;
                case 2: return bomb2;
                default: return bomb1;
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
        return new Rectangle(x, y, width, height);
    }
    
    public void move(){
        y += BOMB_SPEED;
        if (y > BOARD_HEIGHT) visible = false;
        anim++;
        if(anim >2) anim = 0;
    }
    
// END    
}
