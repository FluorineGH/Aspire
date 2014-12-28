/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aspire;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class ABang {
    private int x,y,anim;
    private Image bang0, bang1, bang2;
    boolean visible;
    private int width,height;
    private final int BOARD_HEIGHT = 800;
    private final int BOMB_SPEED = 10;
    
    public ABang(int x, int y){
        bang0 = new ImageIcon(this.getClass().getResource("img/bang0.png")).getImage();
        bang1 = new ImageIcon(this.getClass().getResource("img/bang1.png")).getImage();
        bang2 = new ImageIcon(this.getClass().getResource("img/bang2.png")).getImage();
        visible = true;
        width = bang1.getWidth(null);
        height = bang1.getHeight(null);
        this.x = x;
        this.y = y;
        anim = 0;
    }
    
    public Image getImage(){
        switch(anim){
                case 0: return bang0;
                case 1: return bang1;
                case 2: return bang2;
                default: return bang1;
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
        anim++;
        if(anim >2) visible = false;
    }
    
// END    
}
