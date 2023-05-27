package main3_0;

public class LifeGame {
    public static void main(String[] args) {
        new LifeGameFrame();
    }
}

class LifeGameFrame extends javax.swing.JPanel{
    public LifeGameFrame(){
        javax.swing.JFrame frame = new javax.swing.JFrame("Life Game");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.getContentPane().add(this);
        frame.setVisible(true);
    }

    public void paintComponent(java.awt.Graphics g){
        super.paintComponent(g);
        g.drawString("Hello World!", 100, 100);
    }

    public void setBackground(java.awt.Color color){
        super.setBackground(color);
    }

    public void setForeground(java.awt.Color color){
        super.setForeground(color);
    }

    public void setSize(int width, int height){
        super.setSize(width, height);
    }

    public void setLocation(int x, int y){
        super.setLocation(x, y);
    }

    public void setBounds(int x, int y, int width, int height){

    }
}
