/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.com.mccoy.traploop;

import com.mccoy.traploop.SoundWorker;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author rmccoy
 */
public class MovingCircle extends JPanel {
    final int CIRCLE_START_Y = 100;
    int x;
    int y = CIRCLE_START_Y;
    int circleWidth = 75;
    int circleHeight = 75;
    boolean move = false;
    Image backgroundImage;
    
    MovingCircle() {
        try {
        backgroundImage = ImageIO.read(SoundWorker.class.getClassLoader().getResourceAsStream("images/background.jpg"));
        } catch (Exception e) {
            System.out.println("Error reading file" + e);
        }
    }
        public void paintComponent(Graphics g) {
            g.fillRect(0,0,getWidth(),getHeight());
            //g.drawImage(backgroundImage, 0, 0, this);
            drawCircle(g);
            
        }
        
        private void drawCircle (Graphics g) {

            Color redColor = new Color( 200, 0, 0);
            g.setColor(redColor);
            g.fillOval(x,y,circleWidth,circleHeight);    
        }
        
        public void animateCircle() {
            move = true;
            int increment = 1;
            while (true) {
            if (x == getWidth() - circleWidth) {
                increment = -1;
            }
            if (x == 0) {
                increment = 1;
            }
            x = x + increment;
            if (y < CIRCLE_START_Y) {
                y = y + 5;
            }
            this.repaint();
            
                try {
                    Thread.sleep(10);                
                } catch(Exception ex) { }
            }
        }
        
}
