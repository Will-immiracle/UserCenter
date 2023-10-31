package tankGame;

import javax.swing.*;
import java.io.IOException;

public class TankGame01 extends JFrame {
    MyPanel mp = null;
    public static void main(String[] args) throws IOException {
        TankGame01 tankGame01 = new TankGame01();
    }

    public TankGame01() throws IOException {
        mp = new MyPanel();
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);
        this.setSize(1000,800);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
