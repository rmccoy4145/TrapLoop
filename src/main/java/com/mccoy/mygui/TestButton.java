/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.mygui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/**
 *
 * @author rmccoy
 */
public class TestButton extends JButton implements ActionListener {
    
    TestButton() {
        super("Move it");
        addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        setText("Moving it!");
    }
    
}
