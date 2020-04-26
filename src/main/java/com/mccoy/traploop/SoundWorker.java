/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.traploop;
import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import javax.sound.sampled.*;
import sun.audio.*;

/**
 *
 * @author rmccoy
 */
public class SoundWorker {
    static private final SoundWorker sw = new SoundWorker();
    
    static public void playSample(String audioFileName) {
        try {
            InputStream fxAudioFile = SoundWorker.class.getClassLoader().getResourceAsStream(audioFileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fxAudioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
            audioClip.start();

        } catch (Exception e) {
            System.out.println("Error trying to play sound" + e.getMessage());
        }
        
    }
    
        static public void loopSample(String audioFileName) {

        try {
            InputStream fxAudioFile = SoundWorker.class.getClassLoader().getResourceAsStream(audioFileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fxAudioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
            audioClip.loop(16);
        } catch (Exception e) {
            System.out.println("Error trying to play sound " + e.getMessage());
        }
        
    }
    
}
