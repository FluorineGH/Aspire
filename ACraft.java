/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aspire;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class ACraft {
    private int dx;
    private int dy;
    private int x;
    private int y;
    private int width;
    private int height;
    private int weapon, beams;
    private boolean visible, missile;
    private Image image1, image2, image3, image4;
    private ArrayList bullets, missiles, lasers;
    private int anim = 0;
    
    public ACraft(){
        image1 = new ImageIcon(this.getClass().getResource("img/craft1.png")).getImage();
        image2 = new ImageIcon(this.getClass().getResource("img/craft2.png")).getImage();
        image3 = new ImageIcon(this.getClass().getResource("img/craft3.png")).getImage();
        image4 = new ImageIcon(this.getClass().getResource("img/craft4.png")).getImage();
        width = image1.getWidth(null);
        height = image1.getHeight(null);
        weapon = 0;
        bullets = new ArrayList();
        missiles = new ArrayList();
        lasers = new ArrayList();
        visible = true;
        x = 360;
        y = 700;
        beams = 0;
    }
    
    public void move(){
        x += dx;
        y += dy;
        // Boundaries
        if(x<0) x = 0;
        if(x>675) x = 675;
        if(y<600) y = 600;
        if(y>720) y = 720;
        // Animate Thrusters
        anim++;
        if(anim >3) anim = 0;
    }
    
    public int getX(){
        return x;
    }   
    
    public int getY(){
        return y;
    }
    
    public void setX(){
        x = 360;
    }   
    
    public void setY(){
        y = 700;
    }
    
    public int getBeams(){
        return beams;
    }
    
    public void setBeams(){
        beams = 0;
    }
    
    public void addBeams(){
        beams += 100;
    }
    
    public Image getImage(){
        switch(anim){
            case 0: return image1;
            case 1: return image2;
            case 2: return image3;
            default: return image4;
        }               
    }
    
    public void setWeapon(int w){
        if(weapon > w) return;
        if(w == 1) missile = true;       
        weapon = w;
    }
    
    public ArrayList getBullets(){
        return bullets;
    }
    
    public ArrayList getMissiles(){
        return missiles;
    }
    
    public ArrayList getLasers(){
        return lasers;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x+5, y+5, width-10, height-10);
    }
    
    public void boom(){
        weapon = 0;
        bullets.clear();
        missiles.clear();
//        lasers.clear();
    }
    
    public void keyPressed(KeyEvent e){
        
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_Z) {
            if(ABoard.PAUSE == false) ABoard.PAUSE = true;
            else ABoard.PAUSE = false;
         }
        
        if (key == KeyEvent.VK_ESCAPE) {
            if(ABoard.SPLASH == true){
                ABoard.SPLASH = false;
                ABoard.INGAME = true;
            }else {
                if(ABoard.PAUSE == false) ABoard.PAUSE = true;
                else ABoard.PAUSE = false;
            }
        }
        
        if (key == KeyEvent.VK_SPACE) {           
            fire();           
            if(ABoard.END == true && ABoard.INIT.length()<3){
               ABoard.INIT+=ABoard.letters[ABoard.init];
               ABoard.count++;
            }           
        }
        
        if (key == KeyEvent.VK_BACK_SPACE) {                                  
            if(ABoard.INGAME == false && ABoard.INIT.length()<3 && ABoard.INIT.length() > 0){
               ABoard.INIT = ABoard.INIT.substring(0,ABoard.INIT.length()-1);
               ABoard.count--;
            }    
            if(ABoard.BOSSTRIG == true) {
                ABoard.BOSS = 500;
                ABoard.INGAME = false;
            }
        }
        
        if (key == KeyEvent.VK_LEFT) {
            if(ABoard.INGAME == true){ 
                dx = -8;
            }
        }
        
        if (key == KeyEvent.VK_RIGHT) {
            if(ABoard.INGAME == true){
                dx = 8;
            }
        }
        
        if (key == KeyEvent.VK_UP) {
            if(ABoard.INGAME == true){
                dy = -8;    
            }
            if(ABoard.INGAME == false && ABoard.init < 26) ABoard.init++;
            if(ABoard.INGAME == false && ABoard.init == 26) ABoard.init = 0;
        }
        
        if (key == KeyEvent.VK_DOWN) {
            if(ABoard.INGAME == true){
                dy = 8;
            }
            if(ABoard.INGAME == false && ABoard.init > 0) ABoard.init--;
            if(ABoard.INGAME == false && ABoard.init == 0) ABoard.init = 25;
        }        
    }
    
    public void fire() {
        switch (weapon){
            case 0: bullets.add(new ABullet(x + width/2-2, y+5));
                    ABoard.playSound(ABoard.bullet);
                    break;
            case 1: if(missiles.size()<ABoard.missileMax){
                    ABoard.playSound(ABoard.missile);
                    missiles.add(new AMissile(x + 22, y+10)); 
                    missiles.add(new AMissile(x - 1, y+10));
                    }
                    break;
            case 2: if(lasers.size() < 5){                       
                        ABoard.playSound(ABoard.laser);
                        lasers.add(new ALaser(x,y));
                        beams--;
                        if(beams == 0){
                            weapon = 0;
                            if(missile == true) weapon = 1;
                        }                       
                    }
                        
                    break;
    }
  //     
    }
    
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}
