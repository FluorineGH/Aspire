/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aspire;

import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;

public class AStar {
    private int x;
    private int y = -10;
    private int ySpeed = 0;
    private int size = 0;
    private int width;
    private int height;
    private boolean visible;
    private Image image;
    Random r = new Random();
    
    public AStar(){
        x = r.nextInt(720)+1;
        ySpeed = r.nextInt(50)+10;
        size = r.nextInt(3)+1;
        
        if(size == 1){
            image = new ImageIcon(this.getClass().getResource("img/star1.png")).getImage();       
        }else if(size == 2){
            image = new ImageIcon(this.getClass().getResource("img/star2.png")).getImage();
        }else{
            image = new ImageIcon(this.getClass().getResource("img/star3.png")).getImage();
        }
        visible = true;
        width = image.getWidth(null);
        height = image.getHeight(null);       
    }
    
    public void sMove(){
        y+=ySpeed;
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
    
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
