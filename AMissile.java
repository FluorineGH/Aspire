/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aspire;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class AMissile {
    private int x,y;
    private Image image;
    boolean visible;
    private int width,height;
    private final int BOARD_HEIGHT = 0;
    private final int MISSILE_SPEED = 20;
    
    public AMissile(int x, int y){
        ImageIcon ii = new ImageIcon(this.getClass().getResource("img/missile.png"));
        image = ii.getImage();
        visible = true;
        width = image.getWidth(null);
        height = image.getHeight(null);
        this.x = x;
        this.y = y;
    }
    
    public Image getImage(){
        return image;
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
        y -= MISSILE_SPEED;
        if (y < BOARD_HEIGHT) visible = false;
    }
}
