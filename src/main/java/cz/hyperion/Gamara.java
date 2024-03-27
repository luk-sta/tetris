package cz.hyperion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gamara extends JPanel implements ActionListener, KeyListener {
    Timer tm = new Timer(20, this);

    int x = 40, y = 0, velX = 0, velY = 1;
    private Color color=Color.red;
    public Gamara(int velY) {
        this.velY = velY;
        x += new Random().nextInt(100);
        if (velY > 0) {
            tm.start(); //starts timer
        }
        addKeyListener(this); //this referring to KeyListener
        setFocusable(true); //enable KeyListener
        setFocusTraversalKeysEnabled(false); //shift or tab is not use so F
    }

    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(x, y, 20, 20);
        g.fillRect(x + 20, y + 20, 20, 20);
    }

    public void actionPerformed(ActionEvent e) {
        color =Color.white;
        repaint();
        if (x < 0) {
            //            velX = 0;
            x = 0;
        }

        if (x > 1500) {
            //            velX = 0;
            x = 00;
        }

        if (y < 0) {
            //velY = 0;
            y = 0;
        }

        if (y > 1300) {
            //velY = 0;
            y = 00;
        }
        x = x + velX;
        y = y + velY;
        color =Color.red;
                repaint();
    }

    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_LEFT) // VK_Left is left arrow
        {
            velX = -3;
            velY = 0;
        }
        if (c == KeyEvent.VK_UP) // VK_UP is up arrow
        {
            velX = 0;
            velY = -3; // means up
        }
        if (c == KeyEvent.VK_RIGHT) {
            velX = 3;
            velY = 0;
        }

        if (c == KeyEvent.VK_DOWN) {
            velX = 0;
            velY = 3;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        velX = 0;
        velY = 0;


    }
/*public static void main(String [] args)
{

  JFrame f=new JFrame("vivek");
  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  Gamara a =new Gamara ();
  f.add(a);
  f.setSize(600,400);
  f.setVisible(true);
  }
}*/

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        Gamara g = new Gamara(1);
        JFrame jf = new JFrame();
        jf.setTitle("Tutorial");
        jf.setSize(1600, 1400);
        jf.setVisible(true);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(g,0);
        Gamara g1 = new Gamara(1);
        jf.add(g1,1);
        jf.add(new Gamara(1),2);
        jf.getContentPane().setBackground(Color.white);
        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            jf.revalidate();
        }
        //        jf.setBackground(Color.white);
    }


}
