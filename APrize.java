/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aspire;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;

public class APrize {
    private int x;
    private int y;
    private int type;
    private int width;
    private int height;
    private boolean visible;
    private Image prize00, prize01, prize02, prize03, prize10, prize11, prize12, prize13, prize20, prize21, prize22, prize23;
    private int anim = 0;
    Random r = new Random();
    
    public APrize(int x, int y, int t){
        type = t;
        this.x = x;
        this.y = y;
        
        prize00 = new ImageIcon(this.getClass().getResource("img/prize00.png")).getImage();
        prize01 = new ImageIcon(this.getClass().getResource("img/prize01.png")).getImage();
        prize02 = new ImageIcon(this.getClass().getResource("img/prize02.png")).getImage();
        prize03 = new ImageIcon(this.getClass().getResource("img/prize03.png")).getImage();               
        prize10 = new ImageIcon(this.getClass().getResource("img/prize10.png")).getImage();
        prize11 = new ImageIcon(this.getClass().getResource("img/prize11.png")).getImage();
        prize12 = new ImageIcon(this.getClass().getResource("img/prize12.png")).getImage();
        prize13 = new ImageIcon(this.getClass().getResource("img/prize13.png")).getImage(); 
        prize20 = new ImageIcon(this.getClass().getResource("img/prize20.png")).getImage();
        prize21 = new ImageIcon(this.getClass().getResource("img/prize21.png")).getImage();
        prize22 = new ImageIcon(this.getClass().getResource("img/prize22.png")).getImage();
        prize23 = new ImageIcon(this.getClass().getResource("img/prize23.png")).getImage();
        
        visible = true;
        width = prize00.getWidth(null);
        height = prize00.getHeight(null); 
    }
    
    public void pMove(){
        y+=5;
        anim++;
        if(anim >3) anim = 0;
    }
    
    public Image getImage(){
        switch(type){
            case 0: switch(anim){
                            case 0: return prize00;
                            case 1: return prize01;
                            case 2: return prize02;
                            default: return prize03;
                    }
            case 1: switch(anim){
                            case 0: return prize10;
                            case 1: return prize11;
                            case 2: return prize12;
                            default: return prize13;
                    }
            case 2: switch(anim){
                            case 0: return prize20;
                            case 1: return prize21;
                            case 2: return prize22;
                            default: return prize23;
                  }
        default: switch(anim){
                            case 0: return prize10;
                            case 1: return prize11;
                            case 2: return prize12;
                            default: return prize13;
                    }
        
        }   
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getT() {
        return type;
    }
    
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
