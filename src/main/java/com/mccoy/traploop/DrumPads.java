/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.traploop;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/**
 *
 * @author rmccoy
 */
public class DrumPads extends JPanel {
    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;       
    private static final Color PAD_COLOR = Color.DARK_GRAY;
    private static final Font padFont = new Font("Futura", Font.PLAIN, 18);
    
    JButton kickPad = new KickPad();
    JButton hihatPad = new HiHatPad();
    JButton snarePad = new SnarePad();
    JButton loopPad = new LoopPad();
    
    DrumPads() {
        this.setLayout(new GridLayout(4, 0));
        this.add(kickPad);
        this.add(hihatPad);
        this.add(snarePad);
        this.add(loopPad);
        
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
            this.setFont(padFont);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SoundWorker.loopSample(StockSounds.LOOP_SOUND);
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
            this.setFont(padFont);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SoundWorker.playSample(StockSounds.HH_SOUND);
            
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
            this.setFont(padFont);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SoundWorker.playSample(StockSounds.BD_SOUND);
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
            this.setFont(padFont);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SoundWorker.playSample(StockSounds.SNR_SOUND);
        }
        
    } 
}
