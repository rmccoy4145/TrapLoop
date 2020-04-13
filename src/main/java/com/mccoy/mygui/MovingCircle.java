/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.mygui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author rmccoy
 */
public class MovingCircle extends JPanel {
    int x;
    int y;
    int circleWidth = 100;
    int circleHeight = 100;
    boolean move = false;
        public void paintComponent(Graphics g) {
            drawCircle(g);
        }
        
        private void drawCircle (Graphics g) {
            g.fillRect(0,0,getWidth(),getHeight());
            Color redColor = new Color( 200, 0, 0);
            g.setColor(redColor);
            g.fillOval(x,y,circleWidth,circleHeight);    
        }
        
        public void animateCircle() {
            move = true;
            int increment = 1;
            while (true) {
            if (x == getWidth() - circleWidth || y == getWidth() - circleHeight ) {
                increment = -1;
            }
            if (x == 0 || y == 0) {
                increment = 1;
            }
            x = x + increment;
            y = y + increment;
            
            this.repaint();
            
                try {
                    Thread.sleep(10);                
                } catch(Exception ex) { }
            }
        }
        
}
