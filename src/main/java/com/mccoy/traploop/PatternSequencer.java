/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.traploop;

import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 *
 * @author rmccoy
 */
public class PatternSequencer extends JPanel{
    Image backgroundImage;
    private static final int BEATS_PER_PATTERN = 16;
    private static HashMap<String, LinkedList> patternMatrix = new HashMap<>();
    private static final String[] INSTRUMENTS = {"KD", "Snr", "HH"}; //change to Enum
    private static final String[] MATRIX_LABELS = {
        "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"
    };
    private static final Font labelFont = new Font("Futura", Font.PLAIN, 18);
    private static GridLayout gridLayout = new GridLayout(INSTRUMENTS.length + 1, BEATS_PER_PATTERN + 1, 10, 10);

    public PatternSequencer() {
        loadBackground();
        this.setLayout(gridLayout);
        setupPatternMatrix();
    }
    public static String[] getInstruments() {
        return INSTRUMENTS;
    }
    
    public void paintComponent(Graphics g) {
            g.drawImage(backgroundImage, 0, 0, this);          
    }
    
    private void loadBackground() {
        try {
        backgroundImage = ImageIO.read(SoundWorker.class.getClassLoader().getResourceAsStream("images/default_bgA.jpg"));
        } catch (Exception e) {
            System.out.println("Error reading file" + e);
        }
    }
    
    private void setupPatternMatrix() {
        for (String instrument : this.INSTRUMENTS) {
            patternMatrix.put(instrument, createInstrumentSequence());
        }
        for (String colLabel : MATRIX_LABELS) {
            JLabel label = new JLabel(colLabel);
            label.setForeground(Color.WHITE);
            label.setBackground(Color.DARK_GRAY);
            label.setFont(labelFont);
            this.add(label);
        }
        for (Map.Entry<String, LinkedList> entry : patternMatrix.entrySet()) {
            String instrumentLabel = entry.getKey();
            LinkedList<Beat> instrumentBeats = entry.getValue();
            JLabel instLabel = new JLabel(instrumentLabel);
            instLabel.setFont(labelFont);
            instLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            this.add(instLabel);
            for (Beat beat : instrumentBeats) {
                this.add(beat);
            }        
        }
    }
    
    private LinkedList<Beat> createInstrumentSequence() {
       LinkedList<Beat> instrumentPattern = new LinkedList<>();
        for (int i = 0; i < BEATS_PER_PATTERN; i++) {
            instrumentPattern.add(new Beat());
        }
        return instrumentPattern;
    }
    
    public static LinkedList <Boolean> getInstrumentNoteSequence(String instrumentName) {
        LinkedList <Beat> instrumentSequence = patternMatrix.get(instrumentName);
        LinkedList<Boolean> instrumentNoteSequence = new LinkedList<>();
        instrumentSequence.forEach(beat -> instrumentNoteSequence.add(beat.isSelected()));        
        return instrumentNoteSequence;
        
    }
    
    private class Beat extends JCheckBox {
        public Beat() {
            this.setSelected(false);
            this.setBorderPainted(false);
            this.setBackground(Color.LIGHT_GRAY);
            this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            this.setHorizontalAlignment(CENTER);
        }
        
    }
   
}
