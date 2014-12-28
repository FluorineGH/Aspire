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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ABoard extends JPanel implements ActionListener {
    private Timer timer;
    private ACraft craft;
    static int LEVEL = 1;
    static int BOSS = 300;
    static int levelup, SCORE, ONEUP = 0;
    static int LIVES = 2;
    static boolean PAUSE = false;
    static boolean INGAME = false;
    static boolean SPLASH = true;
    static boolean BOSSTRIG = false;
    static boolean END = false;
    private boolean LEVEL2, LEVEL3, LEVEL4, LEVEL5, LEVEL6, LEVEL7 = false;
    private int stars = 1;
    Random r = new Random();
    private ArrayList starz, aliens, prizes, bombs, bangs;
    private boolean drawstars = true;
    private int prizeRand = 0;
    static int missileMax = 10;
    private Image[] img = new Image[7];
    private Image[] bang = new Image[4];
    private int bangframes = 0;
    private int bangframeStep = 0;
    private int frames = 0;
    private int frameStep = 0;
   
    static String impact, boom, prize, bullet, missile, laser, level2, level3, level4;
    
    // Scores stuff
    static String [] letters = {
        "A","B","C","D","E","F",
        "G","H","I","J","K","L",
        "M","N","O","P","Q","R",
        "S","T","U","V","W","X",
        "Y","Z"
    };
    static int init = 0;
    static int count = 0;
    static String INIT = "";
    static boolean HIGH = false;
    File dirCheck, scorecard;
    List<AScore> scores;
    static int STEP = 0;
    
    public ABoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);       
        setSize(720,800);
        loadImg();
        loadSound();
        SPLASH = true;
        starz = new ArrayList();
        craft = new ACraft();        
        aliens = new ArrayList();
        prizes = new ArrayList();
        bombs = new ArrayList(); 
        bangs = new ArrayList();
        timer = new Timer(52-LEVEL*2,this);
        timer.start();
        
        // Score stuff
        scores = new ArrayList<>();
        File dirCheck = new File("K:\\Table Games\\Chipper\\java");
        if(dirCheck.exists()) scorecard = new File("K:\\Table Games\\Chipper\\java","scorecard.txt");
        else {
            System.out.println("DIRECTORY DOES NOT EXIST! Using current DIR.");
            scorecard = new File(System.getProperty("user.dir"),"scorecard.txt");
        }        
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
        
        if(SPLASH == true){
            g2d.setFont(new Font("Helvetica", Font.BOLD, 70));
            g2d.setColor(Color.red);
            g2d.drawString("Welcome to Aspire", 40, 120);
            g2d.setFont(new Font("Helvetica", Font.BOLD, 16));
            g2d.drawString(Aspire.VERSION, 300, 150);
            g2d.setColor(Color.blue);
            g2d.setFont(new Font("Helvetica", Font.BOLD, 50));
            g2d.drawString("Written by J~Cal", 150, 210);
            g2d.setColor(Color.cyan);
            g2d.setFont(new Font("Helvetica", Font.BOLD, 30));
            g2d.drawString("Arrow Keys to MOVE", 200, 300);
            g2d.drawString("Spacebar to FIRE", 225, 350);
            g2d.drawString("ESCAPE or Z to Pause", 195, 400);
            g2d.drawString("Q to QUIT", 280, 450);
            // Start Game
            g2d.setColor(Color.green);
            g2d.setFont(new Font("Helvetica", Font.BOLD, 50));
            g2d.drawString("Hit Esc to START", 150, 600);
            return;
        }      
        
        if(levelup > 0){
            switch(levelup){
                case 0: 
                case 1: break;
                case 2:
                    g2d.setColor(Color.green);
                    g2d.setFont(new Font("Helvetica", Font.BOLD, 50));
                    g2d.drawString("Level 2", 280, 550-STEP);
                    STEP++;                   
                    break;
                case 3:
                    g2d.setColor(Color.green);
                    g2d.setFont(new Font("Helvetica", Font.BOLD, 50));
                    g2d.drawString("Level 3", 280, 550-STEP);
                    STEP++;                   
                    break;
                case 4:
                    g2d.setColor(Color.green);
                    g2d.setFont(new Font("Helvetica", Font.BOLD, 50));
                    g2d.drawString("Level 4", 280, 550-STEP);
                    STEP++;                   
                    break;
                case 5:
                    g2d.setColor(Color.green);
                    g2d.setFont(new Font("Helvetica", Font.BOLD, 50));
                    g2d.drawString("Level 5", 280, 550-STEP);
                    STEP++;                   
                    break;
                case 6:
                    g2d.setColor(Color.green);
                    g2d.setFont(new Font("Helvetica", Font.BOLD, 50));
                    g2d.drawString("Level 6", 280, 550-STEP);
                    STEP++;                   
                    break;
                case 7:
                    if(STEP%2 == 0) g2d.setColor(Color.red);
                    if(STEP%2 == 1) g2d.setColor(Color.green);
                    g2d.setFont(new Font("Helvetica", Font.BOLD, 50));
                    g2d.drawString("BOSS LEVEL!!!", 280, 550-STEP);
                    STEP++;                   
                    break;
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
        
        // Draw Missiles
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
        // Draw Bangs
        for (int i = 0; i < bangs.size(); i++) {
                ABang b = (ABang)bangs.get(i);
                g2d.drawImage(b.getImage(), b.getX(), b.getY(), this);
        }
            
        } else {
            
             // WIN THE GAME
            if(BOSS < 1) {
                
        /*          for(int i = 0;i<10;i++){
                    int rand1 = r.nextInt(250);
                    int rand2 = r.nextInt(80);
                    AAlien a = (AAlien)aliens.get(0);
                    bangs.add(new ABang(a.getX()+rand1,a.getY()+rand2));
                }
        */
                g2d.setFont(new Font("Helvetica", Font.BOLD, 70));
                g2d.setColor(Color.red);
                g2d.drawString("YOU HAVE WON!!!", 40, 120);

                g2d.setColor(Color.blue);
                g2d.setFont(new Font("Helvetica", Font.BOLD, 50));
                g2d.drawString("You beat Aspire!", 150, 210);
                g2d.setColor(Color.cyan);
                g2d.setFont(new Font("Helvetica", Font.BOLD, 30));
                g2d.drawString("Congratulations!", 200, 300);
                g2d.drawString("Hope you had fun!", 225, 350);
                g2d.drawString("Try my other games!", 195, 400);
                g2d.setColor(Color.green);
                g2d.setFont(new Font("Helvetica", Font.BOLD, 30));
                g2d.drawString("Hit Backspace to enter your score!", 280, 450);
                return;
            }
            
                     
            if(LEVEL < 7) aliens.clear();
            prizes.clear();
            craft.boom();
            craft.setVisible(false);
            
            if(frameStep<14) {
                g2d.drawImage(img[frames], craft.getX(), craft.getY(),this); 
                frames = frameStep/2;
                if(frames >6) frames = 0;                                                      
                frameStep++;                
            }
                          
            if(LIVES < 1) {
                // GAME OVER STUFF
                if(bangframeStep<8) {
                    g2d.drawImage(bang[bangframes], craft.getX()-10, craft.getY()-10,this); 
                    g2d.drawImage(bang[bangframes], craft.getX()-5, craft.getY()+5,this);
                    g2d.drawImage(bang[bangframes], craft.getX()+10, craft.getY()-2,this);
                    g2d.drawImage(bang[bangframes], craft.getX()+15, craft.getY()+15,this);
                    g2d.drawImage(bang[bangframes], craft.getX(), craft.getY()+6,this);
                    bangframes = bangframeStep/2;
                    if(bangframes >4) bangframes = 0;                                                      
                    bangframeStep++;                
                return;
                }
                END = true;
                g2d.setFont(new Font("Helvetica", Font.BOLD, 70));
                g2d.setColor(Color.red);
                g2d.drawString("GAME OVER", 140, 120);
                g2d.drawString("SCORE: " + SCORE, 150, 200);
                
                scoreCard(g2d);
                
            } else {
                INGAME = true;
                craft.setVisible(true);
                missileMax = 10;
                LIVES-=1;
                frameStep = 0;
                craft.setBeams();
                craft.setX();
                craft.setY();
                bombs.clear();
                prizes.clear();
                
            }
            
        }
        
        // Display Scores, etc
        g2d.setFont(new Font("Helvetica", Font.BOLD, 20));
        g2d.setColor(Color.blue);
        g2d.drawString("SCORE: " + SCORE, 5, 20);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Level: " + LEVEL, 150, 20);
        g2d.drawString("Lives: " + LIVES, 240, 20);
        g2d.drawString("Max Missiles: " + missileMax, 340, 20);
        g2d.drawString("Laserbolts left: " + craft.getBeams(), 520, 20);
              
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
     }
        
    public void actionPerformed(ActionEvent e) {
        if(SPLASH == true){
            repaint();
            return;
        }
        
        // Pause on Z key
        if(PAUSE == true) timer.stop();             
        
        // Set Level
        if(SCORE > 1000) {
            levelup = 2;
            if(LEVEL2 == false) playSound(level2);
            if(SCORE > 1200) levelup = 0;
            LEVEL2 = true;
            LEVEL = 2;
        }
        if(SCORE > 3000) {
            levelup = 3;
            if(LEVEL3 == false) playSound(level3);
            if(SCORE > 3300) levelup = 0;
            LEVEL3 = true;
            LEVEL = 3;
        }
        if(SCORE > 7500) {
            levelup = 4;
            setBackground(Color.DARK_GRAY);
            if(SCORE > 7900) levelup = 0;
            if(LEVEL4 == false) playSound(level4);
            LEVEL4 = true;
            LEVEL = 4;
        }
        if(SCORE > 13000) {
            levelup = 5;
            setBackground(Color.DARK_GRAY);
            if(SCORE > 13500) levelup = 0;
            if(LEVEL5 == false) playSound(level2);
            LEVEL5 = true;
            LEVEL = 5;
        }
        if(SCORE > 20000) {
            levelup = 6;
            setBackground(Color.DARK_GRAY);
            if(SCORE > 20600) levelup = 0;
            if(LEVEL6 == false) playSound(level3);
            LEVEL6 = true;
            LEVEL = 6;
        }
        // Add Bosses
        if(SCORE > 30000) {
            levelup = 7;
            setBackground(Color.black);
            if(SCORE > 30350) levelup = 0;
            if(LEVEL7 == false) playSound(level4);
            LEVEL7 = true;
            LEVEL = 7;
        }
        
        // 1UP every 5K
        if(ONEUP > 5000){
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
        // Cycle Bangs
        for(int i = 0; i < bangs.size(); i++) {
            ABang b = (ABang) bangs.get(i);
            if (b.isVisible())
                b.move();
            else bangs.remove(i);
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
                for(int i = 0;i<7;i++){
                    int rand1 = r.nextInt(40)-20;
                    int rand2 = r.nextInt(40)-20;
                    bangs.add(new ABang(craft.getX()+rand1,craft.getY()+rand2));
                }
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

                if(LEVEL == 7){
                    if (r4.intersects(r2)) {
                        b.setVisible(false);
                        BOSS--;
                        if(BOSS < 1) {
                            a.setVisible(false);
                            INGAME = false;
                        }
                        SCORE+=10*LEVEL;
                        ONEUP+=10*LEVEL;
                        prizeRand = r.nextInt(100);
                        if(prizeRand < 10) prizes.add(new APrize(a.getX(),a.getY(),0));
                        if(prizeRand > 10 && prizeRand <16) prizes.add(new APrize(a.getX(),a.getY(),1));
                        if(prizeRand == 50) prizes.add(new APrize(a.getX(),a.getY(),2));
                    }
                }
                
                if (LEVEL < 7 && r4.intersects(r2)) {
                    playSound(impact);
                    bangs.add(new ABang(a.getX(),a.getY()));
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

                if(LEVEL == 7){
                    if (r1.intersects(r2)) {
                        m.setVisible(false);
                        BOSS-=2;
                        if(BOSS < 1){
                            a.setVisible(false);
                            INGAME = false;
                        }
                        SCORE+=10*LEVEL;
                        ONEUP+=10*LEVEL;
                        prizeRand = r.nextInt(100);
                        if(prizeRand < 10) prizes.add(new APrize(a.getX(),a.getY(),0));
                        if(prizeRand > 10 && prizeRand <16) prizes.add(new APrize(a.getX(),a.getY(),1));
                        if(prizeRand == 50) prizes.add(new APrize(a.getX(),a.getY(),2));
                    }
                }
                
                if (LEVEL < 7 && r1.intersects(r2)) {
                    playSound(impact);
                    bangs.add(new ABang(a.getX(),a.getY()));
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

                if(LEVEL == 7){
                    if (r6.intersects(r2)) {
                        l.setVisible(false);
                        BOSS-=3;
                        if(BOSS < 1){
                            a.setVisible(false);
                            INGAME = false;
                        }
                        SCORE+=10*LEVEL;
                        ONEUP+=10*LEVEL;
                        prizeRand = r.nextInt(100);
                        if(prizeRand < 10) prizes.add(new APrize(a.getX(),a.getY(),0));
                        if(prizeRand > 10 && prizeRand <16) prizes.add(new APrize(a.getX(),a.getY(),1));
                        if(prizeRand == 50) prizes.add(new APrize(a.getX(),a.getY(),2));
                    }
                }
                
                if (LEVEL < 7 && r6.intersects(r2)) {
                    playSound(impact);
                    bangs.add(new ABang(a.getX(),a.getY()));
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
                if(p.getT()==1) missileMax += 2;
                if(p.getT()==2) craft.addBeams();                
            }
        }
         // Check Bomb - Craft collision
        for(int i = 0;i<bombs.size();i++){
            ABomb b = (ABomb) bombs.get(i);
            Rectangle r6 = b.getBounds();
            
            if(r6.intersects(r3)) {
                playSound(boom);
                for(int j = 0;j<7;j++){
                    int rand1 = r.nextInt(40)-20;
                    int rand2 = r.nextInt(40)-20;
                    bangs.add(new ABang(craft.getX()+rand1,craft.getY()+rand2));
                }
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
        if(LEVEL < 7){
            if(aliens.size()< 10) stars = r.nextInt(650)+30;
            for(int i=0;i<(10-aliens.size());i++){
                aliens.add(new AAlien(stars,-10,LEVEL));
            }
        } else {
            if(BOSSTRIG == false){            
                aliens.add(new AAlien(300,50,7));
                BOSSTRIG = true;
            }
        }
    }
    
    private void initBombs(){             
        if(LEVEL > 3)            
            for(int i=0;i<(aliens.size());i++){
                stars = r.nextInt(1000-LEVEL*100);
                if(BOSSTRIG == true) stars -= 100;
                if(stars < LEVEL) {
                    AAlien a = (AAlien)aliens.get(i);
                    stars = r.nextInt(250);
                    bombs.add(new ABomb(a.getX()+stars,a.getY()));               
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
        bang[0] = new ImageIcon(this.getClass().getResource("img/bang0.png")).getImage();
        bang[1] = new ImageIcon(this.getClass().getResource("img/bang1.png")).getImage();
        bang[2] = new ImageIcon(this.getClass().getResource("img/bang2.png")).getImage();
        bang[3] = new ImageIcon(this.getClass().getResource("img/bang3.png")).getImage();
    }
    
    private void loadSound(){           
        impact = "C:\\Users\\jcalvert\\Documents\\NetBeansProjects\\Aspire\\src\\aspire\\sound\\impact.wav";
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
    
    private void scoreCard(Graphics2D g){
        // Grab the High Score list from file
        if(scores.size() == 0) readScores();
        // Print old High Score List       
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.red);
        g.drawString("HIGH SCORES", 50, 250);
        g.setColor(Color.orange);        

        for(int i = 0;i<scores.size();i++) {
            AScore as = (AScore)scores.get(i);
            if(as.getScore() == SCORE) g.setColor(Color.cyan);
            else g.setColor(Color.orange);
            g.drawString(as.getName(), 50, 300+i*30);
            g.drawString(Integer.toString(as.getScore()), 140, 300+i*30);
        }

        // Check if current score qualifies
        if(HIGH == false && SCORE > scores.get(scores.size()-1).getScore()){
            int place = 0;
            for(int i = 0;i < scores.size();i++){
                if(SCORE > scores.get(i).getScore()){
                    place = i;
                    break;
                }
            }
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.setColor(Color.green);
            g.drawString("You have a new high score!", 350, 250);
            //Get Initials
            g.drawString("Enter your initials:", 350,300);
            g.setColor(Color.blue);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString(letters[init], 550,305);
            g.drawString(INIT, 380,380);
            
            if(count == 3) {                    
                scores.add(place,new AScore(INIT,SCORE));
                scores.remove(scores.size()-1);
                // Repaint new High Scores
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.setColor(Color.orange);        
                for(int i = 0;i<scores.size();i++) {
                    AScore as = (AScore)scores.get(i);
                    if(as.getScore() == SCORE) g.setColor(Color.cyan);
                    else g.setColor(Color.orange);
                    g.drawString(as.getName(), 50, 300+i*30);
                    g.drawString(Integer.toString(as.getScore()), 140, 300+i*30);
                }
                setScores();
                HIGH = true;    
            }
             
        }
        
    }
   
    private void readScores(){       
        String name;
        String score;
        try{
            Scanner s = new Scanner(new BufferedReader(new FileReader(scorecard)));       
            for(int i=0;i<8;i++){
                name = s.next();
                score = s.next();
                scores.add(new AScore(name,Integer.parseInt(score)));
            }      
        } catch(FileNotFoundException e){
            System.err.println("IO Error: File not found. Generating new scores!");
            setScores();
        }
    }
   
    private void setScores(){
        String eol = System.getProperty("line.separator");
        String name;
        String score;
        try{
            Writer output = new BufferedWriter(new FileWriter(scorecard));
            if(scores.size() == 0){
                output.write("JCAL" + eol);
                output.write("8000" + eol);
                output.write("IS" + eol);
                output.write("7000" + eol);
                output.write("THE" + eol);
                output.write("6000" + eol);
                output.write("BEST" + eol);
                output.write("5000" + eol);
                output.write("OMG" + eol);
                output.write("4000" + eol);
                output.write("SO" + eol);
                output.write("3000" + eol);
                output.write("WOW" + eol);
                output.write("2000" + eol);
                output.write("YAY!" + eol);
                output.write("1000" + eol);
                output.close();
                readScores();
                return;
            }
            for(int i = 0;i<8;i++){
                name = scores.get(i).getName() + eol;
                score = Integer.toString(scores.get(i).getScore()) + eol;
                output.write(name);
                output.write(score);
            }
            output.close();
        } catch(IOException e){
            e.printStackTrace();            
            System.err.println("IO Error of some kind in setScores");
            return;
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
