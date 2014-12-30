/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aspire;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public class playSound {
   
    public playSound(){
        
    }
    
    public void play(URL u) throws Exception {   
        AudioInputStream ais = AudioSystem.getAudioInputStream(u);
        DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(ais);       
        clip.start();
    }  
    
}
