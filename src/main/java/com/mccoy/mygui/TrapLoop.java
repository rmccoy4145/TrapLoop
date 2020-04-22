/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.mygui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author rmccoy
 */
public class TrapLoop {
    JFrame frame;
    JLabel label;
    DrumPads pads = new DrumPads();
    
    public static void main (String[] args) {
        
        TrapLoop gui = new TrapLoop();
        gui.go();
    }

    public void go() {
        frame = new JFrame();       
        label = new JLabel("TrapLoop version 1.0");
        
        MovingCircle drawPanel = new MovingCircle();
        frame.getContentPane().add(BorderLayout.NORTH, label);
        frame.getContentPane().add(BorderLayout.SOUTH, pads);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 380);
        frame.setVisible(true);
       
        drawPanel.animateCircle();
        
    }
    

}
