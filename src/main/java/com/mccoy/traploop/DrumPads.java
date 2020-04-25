/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.traploop;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
/**
 *
 * @author rmccoy
 */
public class DrumPads extends JPanel {
    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;       
    private static final String BD_SOUND = "audiofiles/bd_sound.wav";
    private static final String SNR_SOUND = "audiofiles/snr_sound.wav";
    private static final String HH_SOUND = "audiofiles/hh_sound.wav";
    private static final String LOOP_SOUND = "audiofiles/loop1_16.wav";
    private static final int KICK_AMPLITUDE = 60;
    private static final int HIHAT_AMPLITUDE = 25;
    private static final int SNARE_AMPLITUDE = 50;
    private static final Color PAD_COLOR = Color.DARK_GRAY; 
    
    JButton kickPad = new KickPad();
    JButton hihatPad = new HiHatPad();
    JButton snarePad = new SnarePad();
    JButton loopPad = new LoopPad();
    MovingCircle circle;
    
    DrumPads(MovingCircle circle) {
        this.setLayout(new GridLayout(4, 0));
        this.add(kickPad);
        this.add(hihatPad);
        this.add(snarePad);
        this.add(loopPad);
        this.circle = circle;
    }

    private class ClickAction extends AbstractAction {
        JButton button;    
        ClickAction(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            button.doClick();
        }
    }
    
    private class LoopPad extends JButton implements ActionListener{ 
        LoopPad() {
            super("Loop 16");
            addActionListener(this);
            this.setBackground(PAD_COLOR);
            this.setOpaque(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SoundWorker.loopSample(LOOP_SOUND);
        }
    }       
    
    private class HiHatPad extends JButton implements ActionListener { 
        HiHatPad() {
            super("HiHat");
            addActionListener(this);
            getInputMap(IFW).put(KeyStroke.getKeyStroke("H"), "HiHat");
            getActionMap().put("HiHat", new ClickAction(this));
            this.setBackground(PAD_COLOR);
            this.setOpaque(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SoundWorker.playSample(HH_SOUND);
            circle.y = circle.y - HIHAT_AMPLITUDE;
            
        }       
    }      
    
    private class KickPad extends JButton implements ActionListener { 
        KickPad() {
            super("Kick");
            addActionListener(this);
            getInputMap(IFW).put(KeyStroke.getKeyStroke("K"), "Kick");
            getActionMap().put("Kick", new ClickAction(this));
            this.setBackground(PAD_COLOR); 
            this.setOpaque(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SoundWorker.playSample(BD_SOUND);
            circle.y = circle.y - KICK_AMPLITUDE;
        }
    }  
    
    private class SnarePad extends JButton implements ActionListener { 
        SnarePad() {
            super("Snare");
            addActionListener(this);
            getInputMap(IFW).put(KeyStroke.getKeyStroke("S"), "Snare");
            getActionMap().put("Snare", new ClickAction(this));
            this.setBackground(PAD_COLOR);
            this.setOpaque(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SoundWorker.playSample(SNR_SOUND);
            circle.y = circle.y - SNARE_AMPLITUDE;
        }
        
    } 
}
