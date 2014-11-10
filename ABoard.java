/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aspire;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.ArrayList;
import javax.sound.sampled.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ABoard extends JPanel implements ActionListener {
    private Timer timer;
    private ACraft craft;
    static int LEVEL = 1;
    static int SCORE, ONEUP = 0;
    static int LIVES = 2;
    static boolean PAUSE = false;
    private boolean INGAME, LEVEL2, LEVEL3, LEVEL4= false;
    private int stars = 1;
    Random r = new Random();
    private ArrayList starz, aliens, prizes, bombs;
    private boolean drawstars = true;
    private int prizeRand = 0;
    static int missileMax = 10;
    private Image[] img = new Image[7];
    private int frames = 0;
    private int frameStep = 0;
    
    static String boom, prize, bullet, missile, laser, level2, level3, level4;
    
    public ABoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);       
        setSize(720,800);
        loadImg();
        loadSound();        
        INGAME = true;
        starz = new ArrayList();
        craft = new ACraft();        
        aliens = new ArrayList();
        prizes = new ArrayList();
        bombs = new ArrayList();       
        timer = new Timer(52-LEVEL*2,this);
        timer.start();
    }
    
     public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        
        // Draw Stars
        if(drawstars == true) {
            for (int i = 0; i < starz.size(); i++) {
                AStar a = (AStar)starz.get(i);
                if (a.isVisible())
                    g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
            }
        }        
        
        if(INGAME == true) {
            
        // Draw prizes
            for (int i = 0; i < prizes.size(); i++) {
                    APrize p = (APrize)prizes.get(i);
                    if (p.isVisible())
                        g2d.drawImage(p.getImage(), p.getX(), p.getY(), this);
                }
        
        // Draw Craft
        if (craft.isVisible()) g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);
        
        // Draw Aliens
        for (int i = 0; i < aliens.size(); i++) {
                AAlien a = (AAlien)aliens.get(i);
                g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
        }
        
        // Draw Bullets
        ArrayList bs = craft.getBullets();       
        for (int i = 0; i < bs.size(); i++) {
                ABullet b = (ABullet)bs.get(i);
                g2d.drawImage(b.getImage(), b.getX(), b.getY(), this);
        }
        
        // Draw MIssiles
        ArrayList ms = craft.getMissiles();       
        for (int i = 0; i < ms.size(); i++) {
                AMissile m = (AMissile)ms.get(i);
                g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
        }      
        
        // Draw Lasers
        ArrayList ls = craft.getLasers();       
        for (int i = 0; i < ls.size(); i++) {
                ALaser l = (ALaser)ls.get(i);
                g2d.setColor(l.getColour());
                g2d.fill(l.getBounds());
        }
        
         // Draw Bombs               
        for (int i = 0; i < bombs.size(); i++) {
                ABomb b = (ABomb)bombs.get(i);
                g2d.drawImage(b.getImage(), b.getX(), b.getY(), this);
        }
        
        } else {
        // GAME OVER STUFF              
            aliens.clear();
            prizes.clear();
            craft.boom();
            craft.setVisible(false);
            
            if(frameStep<14) {
                g2d.drawImage(img[frames], craft.getX(), craft.getY(),this); 
                frames = frameStep/2;
                if(frames >6) frames = 0;                                                      
                frameStep++;
                return;
            }

            if(LIVES < 1) {
                INGAME = false;
                g2d.setFont(new Font("Helvetica", Font.BOLD, 70));
                g2d.setColor(Color.red);
                g2d.drawString("GAME OVER", 220, 250);
                g2d.drawString("SCORE: " + SCORE, 240, 350);              
            } else {
                INGAME = true;
                craft.setVisible(true);
                missileMax = 10;
                LIVES-=1;
                frameStep = 0;
                craft.resetBeams();
            }
            
        }
        
        // Display Scores, etc
        g2d.setFont(new Font("Helvetica", Font.BOLD, 20));
        g2d.setColor(Color.blue);
        g2d.drawString("SCORE: " + SCORE, 5, 20);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Level: " + LEVEL, 135, 20);
        g2d.drawString("Lives: " + LIVES, 240, 20);
        g2d.drawString("Max Missiles: " + missileMax, 340, 20);
        g2d.drawString("Laserbolts left: " + craft.getBeams(), 520, 20);
        
        
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
     }
        
    public void actionPerformed(ActionEvent e) {
        // Pause on Z key
        if(PAUSE == true) timer.stop();             
        
        // Set Level
        if(SCORE > 1000) {
            if(LEVEL2 == false) playSound(level2);
            LEVEL2 = true;
            LEVEL = 2;
        }
        if(SCORE > 3000) {
            if(LEVEL3 == false) playSound(level3);
            LEVEL3 = true;
            LEVEL = 3;
        }
        if(SCORE > 7500) {
            setBackground(Color.DARK_GRAY);
            if(LEVEL4 == false) playSound(level4);
            LEVEL4 = true;
            LEVEL = 4;
        }
        
        if(ONEUP>5000){
            LIVES++;
            ONEUP-=5000;
        }       
        
        // Set Stars
        initStars();
        for (int i = 0; i < starz.size(); i++) {
                AStar a = (AStar)starz.get(i);
                if (a.isVisible())
                    a.sMove();
            }
        // Set Aliens
        initAliens();
        
        // Set Bombs
        initBombs();
        
        // Bullet move
        ArrayList bs = craft.getBullets();       
        for(int i = 0; i < bs.size(); i++) {
            ABullet b = (ABullet) bs.get(i);
            if (b.isVisible())
                b.move();
            else bs.remove(i);
        }
        
        // missle move
        ArrayList ms = craft.getMissiles();       
        for(int i = 0; i < ms.size(); i++) {
            AMissile m = (AMissile) ms.get(i);
            if (m.isVisible())
                m.move();
            else ms.remove(i);
        }
        
        // Laser move
        ArrayList ls = craft.getLasers();       
        for(int i = 0; i < ls.size(); i++) {
            ALaser l = (ALaser) ls.get(i);
            if (l.isVisible())
                l.move();
            else ls.remove(i);
        }

        
        // alien move             
        for(int i = 0; i < aliens.size(); i++) {
            AAlien a = (AAlien) aliens.get(i);
            if (a.isVisible())
                a.aMove();
            else aliens.remove(i);
        }
        // Move Prizes
        for(int i = 0; i < prizes.size(); i++) {
            APrize p = (APrize) prizes.get(i);
            if (p.isVisible())
                p.pMove();
            else prizes.remove(i);
        }
        // Move Bombs
        for(int i = 0; i < bombs.size(); i++) {
            ABomb b = (ABomb) bombs.get(i);
            if (b.isVisible())
                b.move();
            else bombs.remove(i);
        }
        
        craft.move();
        checkCollisions();
        repaint();
    }
    
    public void checkCollisions() {
        // Remove Stars
        for(int i = 0;i<starz.size();i++){
            AStar a = (AStar)starz.get(i);
            if(a.getY()>800) a.setVisible(false);
        }
        
        // Check Craft - Alien collision
        Rectangle r3 = craft.getBounds();       
        for (int j = 0; j<aliens.size(); j++) {
            AAlien a = (AAlien) aliens.get(j);
            Rectangle r2 = a.getBounds();

            if (r3.intersects(r2)) {
                playSound(boom);
                INGAME = false;
                a.setVisible(false);
            }
        }
        
        // Check Bullet - Alien collision
        ArrayList bs = craft.getBullets();
        for (int i = 0; i < bs.size(); i++) {
            ABullet b = (ABullet) bs.get(i);

            Rectangle r4 = b.getBounds();
            for (int j = 0; j<aliens.size(); j++) {
                AAlien a = (AAlien) aliens.get(j);
                Rectangle r2 = a.getBounds();

                if (r4.intersects(r2)) {
                    b.setVisible(false);
                    a.setVisible(false);
                    SCORE+=10*LEVEL;
                    ONEUP+=10*LEVEL;
                    prizeRand = r.nextInt(100);
                    if(prizeRand < 10) prizes.add(new APrize(a.getX(),a.getY(),0));
                    if(prizeRand > 10 && prizeRand < 16) prizes.add(new APrize(a.getX(),a.getY(),1));
                    if(prizeRand == 50) prizes.add(new APrize(a.getX(),a.getY(),2));
                }
            }
        }
        
        // Check Missile - Alien collision
        ArrayList ms = craft.getMissiles();
        for (int i = 0; i < ms.size(); i++) {
            AMissile m = (AMissile) ms.get(i);

            Rectangle r1 = m.getBounds();

            for (int j = 0; j<aliens.size(); j++) {
                AAlien a = (AAlien) aliens.get(j);
                Rectangle r2 = a.getBounds();

                if (r1.intersects(r2)) {
                    m.setVisible(false);
                    a.setVisible(false);
                    SCORE+=10*LEVEL;
                    ONEUP+=10*LEVEL;
                    prizeRand = r.nextInt(100);
                    if(prizeRand < 10) prizes.add(new APrize(a.getX(),a.getY(),0));
                    if(prizeRand > 10 && prizeRand <16) prizes.add(new APrize(a.getX(),a.getY(),1));
                    if(prizeRand == 50) prizes.add(new APrize(a.getX(),a.getY(),2));
                }
            }
        }
        
        // Check Laser - Alien collision
        ArrayList ls = craft.getLasers();
        for (int i = 0; i < ls.size(); i++) {
            ALaser l = (ALaser) ls.get(i);

            Rectangle r6 = l.getBounds();

            for (int j = 0; j<aliens.size(); j++) {
                AAlien a = (AAlien) aliens.get(j);
                Rectangle r2 = a.getBounds();

                if (r6.intersects(r2)) {
                    a.setVisible(false);
                    SCORE+=10*LEVEL;
                    ONEUP+=10*LEVEL;
                    prizeRand = r.nextInt(100);
                    if(prizeRand < 10) prizes.add(new APrize(a.getX(),a.getY(),0));
                    if(prizeRand > 10 && prizeRand <16) prizes.add(new APrize(a.getX(),a.getY(),1));
                    if(prizeRand == 50) prizes.add(new APrize(a.getX(),a.getY(),2));
                }
            }
        }
        
        // Award Prizes
        for(int i = 0;i<prizes.size();i++){
            APrize p = (APrize) prizes.get(i);
            Rectangle r5 = p.getBounds();
            
            if(r5.intersects(r3)) {
                playSound(prize);
                craft.setWeapon(p.getT());
                p.setVisible(false);
                SCORE+=50;
                ONEUP+=50;
                if(p.getT()==1) missileMax++;
                if(p.getT()==2) craft.resetBeams();                
            }
        }
         // Check Bomb - Craft collision
        for(int i = 0;i<bombs.size();i++){
            ABomb b = (ABomb) bombs.get(i);
            Rectangle r6 = b.getBounds();
            
            if(r6.intersects(r3)) {
                playSound(boom);
                INGAME = false;
                b.setVisible(false);                
            }
        }
    }    
    
    private void initStars(){
        // Set Stars      
        stars = r.nextInt(3);
        if(LEVEL == 4) stars+=5;
        for(int i=0;i<stars;i++){
            starz.add(new AStar());
        }
    }
    
    private void initAliens(){
        // Set Aliens        
        if(aliens.size()< 10)
            stars = r.nextInt(650)+30;
            for(int i=0;i<(10-aliens.size());i++){
            aliens.add(new AAlien(stars,-10,LEVEL));
        }
    }
    
    private void initBombs(){             
        if(LEVEL > 3)            
            for(int i=0;i<(aliens.size());i++){
                stars = r.nextInt(1000-LEVEL*100);
                if(stars < LEVEL) {
                    AAlien a = (AAlien)aliens.get(i);
                    bombs.add(new ABomb(a.getX(),a.getY()));               
                }
        }
    }
    
    private void loadImg(){        
        img[0] = new ImageIcon(this.getClass().getResource("img/boom0.png")).getImage();
        img[1] = new ImageIcon(this.getClass().getResource("img/boom1.png")).getImage();
        img[2] = new ImageIcon(this.getClass().getResource("img/boom2.png")).getImage();
        img[3] = new ImageIcon(this.getClass().getResource("img/boom3.png")).getImage();
        img[4] = new ImageIcon(this.getClass().getResource("img/boom4.png")).getImage();
        img[5] = new ImageIcon(this.getClass().getResource("img/boom5.png")).getImage();
        img[6] = new ImageIcon(this.getClass().getResource("img/boom6.png")).getImage();
    }
    
    private void loadSound(){           
        boom = "C:\\Users\\jcalvert\\Documents\\NetBeansProjects\\Aspire\\src\\aspire\\sound\\boom.wav";
        prize = "C:\\Users\\jcalvert\\Documents\\NetBeansProjects\\Aspire\\src\\aspire\\sound\\prize.wav";
        bullet = "C:\\Users\\jcalvert\\Documents\\NetBeansProjects\\Aspire\\src\\aspire\\sound\\bullet.wav";
        missile = "C:\\Users\\jcalvert\\Documents\\NetBeansProjects\\Aspire\\src\\aspire\\sound\\missile.wav";
        laser = "C:\\Users\\jcalvert\\Documents\\NetBeansProjects\\Aspire\\src\\aspire\\sound\\laser.wav";
        level2 = "C:\\Users\\jcalvert\\Documents\\NetBeansProjects\\Aspire\\src\\aspire\\sound\\level2.wav";
        level3 = "C:\\Users\\jcalvert\\Documents\\NetBeansProjects\\Aspire\\src\\aspire\\sound\\level3.wav";
        level4 = "C:\\Users\\jcalvert\\Documents\\NetBeansProjects\\Aspire\\src\\aspire\\sound\\level4.wav";
        
    }
    
   static void playSound(String s){
        playSound Play = new playSound();
        try{
        Play.play(s);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private void quit(){
        timer.stop();        
        System.exit(0);
    }
    
    private class TAdapter extends KeyAdapter {       
        public void keyReleased(KeyEvent e){
            craft.keyReleased(e);
        }        
        public void keyPressed(KeyEvent e){
            craft.keyPressed(e);
            if(timer.isRunning() == false && PAUSE == false) timer.restart();
            if(e.getKeyCode() == KeyEvent.VK_Q) quit();
        }
    }
// Aspire END    
}