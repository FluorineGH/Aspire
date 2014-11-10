/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aspire;

import java.io.*;
import javax.sound.sampled.*;

public class playSound {
    
    
    public playSound(){
        
    }
    
    public void play(String s) throws Exception {      
    // specify the sound to play
    // (assuming the sound can be played by the audio system)
    File soundFile = new File(s);
    AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);

    // load the sound into memory (a Clip)
    DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
    Clip clip = (Clip) AudioSystem.getLine(info);
    clip.open(sound);

    // due to bug in Java Sound, explicitly exit the VM when
    // the sound has stopped.
    clip.addLineListener(new LineListener() {
      public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
          event.getLine().close();
          //System.exit(0);
        }
      }
    });

    // play the sound clip
    clip.start();
  }
    
}
