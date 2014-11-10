/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aspire;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

public class AAlien {
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean visible;
    private Image alien00, alien01, alien02, alien10, alien11, alien12, alien20, alien21, alien22;
    private int LEVEL = 0;
    private boolean flip = false;
    private int anim, fire = 0;
    Random r = new Random();
    
    public AAlien(int x, int y,int l){
        alien00 = new ImageIcon(this.getClass().getResource("img/alien00.png")).getImage();
        alien01 = new ImageIcon(this.getClass().getResource("img/alien01.png")).getImage();
        alien02 = new ImageIcon(this.getClass().getResource("img/alien02.png")).getImage();
        alien10 = new ImageIcon(this.getClass().getResource("img/alien10.png")).getImage();
        alien11 = new ImageIcon(this.getClass().getResource("img/alien11.png")).getImage();
        alien12 = new ImageIcon(this.getClass().getResource("img/alien12.png")).getImage();
        alien20 = new ImageIcon(this.getClass().getResource("img/alien20.png")).getImage();
        alien21 = new ImageIcon(this.getClass().getResource("img/alien21.png")).getImage();
        alien22 = new ImageIcon(this.getClass().getResource("img/alien22.png")).getImage();
        width = alien00.getWidth(null);
        height = alien00.getHeight(null);
        visible = true;
        this.x = x;
        this.y = y;
        LEVEL = l;
        
    }
    
    public void aMove() {
        switch(LEVEL){
            case 0: System.out.println("Level 0???");
                    break;
            case 1: if (y > 800 && ABoard.LEVEL < 2) visible = false;
                    else if (y > 800) y = -20;
                    y += 10;
                    break;
            case 2: if(y > 800) y = -20;
                        if(x>540){
                        flip = true;
                        x-=3;
                        y+=10;
                        break;
                    }else if(x<200){
                        flip = false;
                        x+=3;
                        y+=10;
                        break;
                    }else if(flip == true){
                        x-=3;
                        y+=10;
                        break;
                    } else if(flip == false){
                        x+=3;
                        y+=10;
                        break;
                    }    
             case 3: if(y > 800) y = -20;
                        if(x>680){
                        flip = true;
                        x-=5;
                        y+=15;
                        break;
                    }else if(x<40){
                        flip = false;
                        x+=5;
                        y+=15;
                        break;
                    }else if(flip == true){
                        x-=5;
                        y+=15;
                        break;
                    } else if(flip == false){
                        x+=5;
                        y+=15;
                        break;
                    }
             case 4: if(y > 800) y = -20;
                        if(x>540){
                        flip = true;
                        x-=3;
                        y+=10;
                        break;
                    }else if(x<200){
                        flip = false;
                        x+=3;
                        y+=10;
                        break;
                    }else if(flip == true){
                        x-=3;
                        y+=10;
                        break;
                    } else if(flip == false){
                        x+=3;
                        y+=10;
                        break;
                    }
             case 5: if(y > 800) y = -20;
                        if(x>680){
                        flip = true;
                        x-=5;
                        y+=15;
                        break;
                    }else if(x<40){
                        flip = false;
                        x+=5;
                        y+=15;
                        break;
                    }else if(flip == true){
                        x-=5;
                        y+=15;
                        break;
                    } else if(flip == false){
                        x+=5;
                        y+=15;
                        break;
                    }
             // Random Movement
             case 6: fire = r.nextInt(20);
                     if(fire < 10) flip = true;
                     else flip = false;
                     
                    if(y > 800) y = -20;
                        if(x>540){
                        flip = true;
                        x-=7;
                        y+=15;
                        break;
                    }else if(x<200){
                        flip = false;
                        x+=7;
                        y+=15;
                        break;
                    }else if(flip == true){
                        x-=7;
                        y+=15;
                        break;
                    } else if(flip == false){
                        x+=7;
                        y+=15;
                        break;
                    } 
        }
        // Step Animation
        anim++;
        if(anim >3) anim = 0;
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
    
    public Image getImage() {
          if(LEVEL == 1 || LEVEL ==4) {
              switch(anim){
                case 0: return alien00;
                case 1: return alien01;
                case 2: return alien02;
                default: return alien01;
            }              
             
          }
          if(LEVEL == 2 || LEVEL == 5) {
              switch(anim){
                case 0: return alien10;
                case 1: return alien11;
                case 2: return alien12;
                default: return alien11;
            }
          }
          
          if(LEVEL == 3 || LEVEL == 6) {
              switch(anim){
                case 0: return alien20;
                case 1: return alien21;
                case 2: return alien22;
                default: return alien21;
            }
          }
          
          else return alien00;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
}
