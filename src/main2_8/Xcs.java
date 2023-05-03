package main2_8;

import javax.swing.*;
import java.awt.*;

public class Xcs extends JPanel {
    private double t = 0;
    private double st = 1d;

    Xcs() {
        this.setBackground(Color.BLACK);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        int num = 10;
        double dt = Math.PI / num;


        for(int i = 0; i < num; i++) {
            dt += 2 * Math.PI / num;
            draw(g, dt);
        }

        g.drawString(System.currentTimeMillis()+ "", 10, 20);
        sleep();
    }
    private void draw(Graphics g, double dt) {
        int x, y;
        int lRad = 100;
        x = (int) (Math.cos(t + dt) * lRad *2 * st) + getWidth()/2 - lRad;
        y = (int) (Math.sin(t + dt) * lRad *2 * st)  +getHeight()/2 - lRad;
        int rad = 50;
        g.fillOval(x,y, rad *2, rad *2);
        g.drawLine(x+ rad,y+ rad,getWidth()/2,getHeight()/2);
    }
    private void sleep() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t += 0.02;
        st += 0.001;
        repaint();
    }
}
