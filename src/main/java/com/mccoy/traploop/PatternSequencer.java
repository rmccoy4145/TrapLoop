/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.traploop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;

/**
 *
 * @author rmccoy
 */
public class PatternSequencer extends JPanel{
    Image backgroundImage;
    private static final int BEATS_PER_PATTERN = 16;
    private HashMap<JLabel, ArrayList> patternMatrix = new HashMap<>();
    private static final String[] INSTRUMENTS= {"Kickdrum", "Snare", "HiHat"};

    public PatternSequencer() {
        loadBackground();
        this.setLayout(new GridLayout(INSTRUMENTS.length, BEATS_PER_PATTERN + 1));
        setupPatternMatrix();

    }
    
    public void paintComponent(Graphics g) {
            g.drawImage(backgroundImage, 0, 0, this);          
    }
    
    private void loadBackground() {
        try {
        backgroundImage = ImageIO.read(SoundWorker.class.getClassLoader().getResourceAsStream("images/background.jpg"));
        } catch (Exception e) {
            System.out.println("Error reading file" + e);
        }
    }
    
    private void setupPatternMatrix() {
        for (String instrument : this.INSTRUMENTS) {
            patternMatrix.put(new JLabel(instrument), createInstrumentSequence());
        }
        for (Map.Entry<JLabel, ArrayList> entry : patternMatrix.entrySet()) {
            JLabel instrumentLabel = entry.getKey();
            ArrayList<Beat> instrumentBeats = entry.getValue();
            
            this.add(instrumentLabel);
            for (Beat beat : instrumentBeats) {
                this.add(beat);
            }        
        }
    }
    
    private ArrayList<Beat> createInstrumentSequence() {
       ArrayList<Beat> instrumentPattern = new ArrayList<>();
        for (int i = 0; i < BEATS_PER_PATTERN; i++) {
            instrumentPattern.add(new Beat());
        }
        return instrumentPattern;
    }
    
    
    private class Beat extends JCheckBox {
        public Beat() {
            this.setSelected(false);
        }
        
    }
   
}
