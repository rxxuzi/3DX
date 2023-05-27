package main2_9;

import javax.swing.*;
import java.awt.*;
//import  io.KeyHandler;

public class Menu extends JPanel {
    String path = "./rsc/BlueFlame.png";
    Font font;
    Image img = Toolkit.getDefaultToolkit().getImage(path);

    public static double t = 0;

    public Menu() {
        this.setFocusable(true);
        this.requestFocus();
        this.setBackground(Color.BLACK);
        setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        g.drawImage(img, 0, 0, this);
        font = new Font("Arial", Font.BOLD, 20);
        g.setFont(font);

        g.drawString(System.currentTimeMillis() +"ms" , 10,20);
        font = new Font("Arial", Font.BOLD, 50);
        g.setFont(font);
        g.setColor(Color.GREEN);
        int l = 100;
        int x = (int) (Math.cos(t) * 2 * l) + getWidth()/2 - l;
        int y = (int) (Math.sin(t) * 2 * l) + getHeight()/2 - l;

        g.fillOval(x, y,200,200);




        g.setColor(Color.RED);
        g.drawString("Test", getWidth()/2 - 50, getHeight()/2);

        sleep();

    }

    private void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t += 0.02;
        repaint();
    }
}