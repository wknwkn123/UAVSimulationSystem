package mapbuilder;

import javax.swing.*;
import java.awt.*;

public class Visualizer extends JFrame{
    public Visualizer() {
        setSize(1920,1080);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(new DrawArea());
        setVisible(true);
    }

    public static void main(String[] args){

    }
    private class DrawArea extends JPanel {
        public DrawArea(){

        }
    }
}
