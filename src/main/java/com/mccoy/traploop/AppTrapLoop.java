/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.traploop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author rmccoy
 */
public class AppTrapLoop {
    JFrame frame;
    JLabel label;
    MovingCircle drawPanel = new MovingCircle();
    DrumPads pads = new DrumPads(drawPanel);
    PatternSequencer patternSequencer = new PatternSequencer();
    MidiPlayer midiPlayer = new MidiPlayer();
    
    public static void main (String[] args) {
        
        AppTrapLoop gui = new AppTrapLoop();
        gui.go();
    }

    public void go() {
        frame = new JFrame();
        frame.setResizable(false);
        
        label = new JLabel("TrapLoop version 1.0");
        
        
        frame.getContentPane().add(BorderLayout.NORTH, label);
        frame.getContentPane().add(BorderLayout.SOUTH, midiPlayer.transportUI);
        frame.getContentPane().add(BorderLayout.WEST, pads);
        frame.getContentPane().add(BorderLayout.CENTER, patternSequencer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(740, 400);
        frame.setVisible(true);
       
        //drawPanel.animateCircle();
        
    }
    

}
