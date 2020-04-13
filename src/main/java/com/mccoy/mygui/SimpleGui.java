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
public class SimpleGui {
    JFrame frame;
    JLabel label;
    TestButton button = new TestButton();
    
    public static void main (String[] args) {
        SimpleGui gui = new SimpleGui();
        gui.go();
    }

    public void go() {
        frame = new JFrame();       
        label = new JLabel("Move it, Move it!");
        
        MovingCircle drawPanel = new MovingCircle();
        frame.getContentPane().add(BorderLayout.NORTH, label);
        frame.getContentPane().add(BorderLayout.SOUTH, button);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
       
        drawPanel.animateCircle();
        
    }
    

}
