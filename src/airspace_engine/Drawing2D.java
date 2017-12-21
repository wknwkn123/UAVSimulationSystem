package airspace_engine;

import airspace_engine.route_segment.RouteSegment;
import airspace_engine.waypoint.Waypoint;

import java.awt.*;       // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import javax.swing.*;    // Using Swing's components and containers

public class Drawing2D {
    private static Drawing2D ourInstance = new Drawing2D();

    public static Drawing2D getInstance() {
        return ourInstance;
    }

    private Drawing2D() {}

    public void draw2D() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Drawing2D.CGTemplate(); // Let the constructor do the job
            }
        });
    }

    public static class CGTemplate extends JFrame {
        // Define constants
        public static final int CANVAS_WIDTH  = 640;
        public static final int CANVAS_HEIGHT = 640;

        // Declare an instance of the drawing canvas,
        // which is an inner class called DrawCanvas extending javax.swing.JPanel.
        private DrawCanvas canvas;

        // Constructor to set up the GUI components and event handlers
        public CGTemplate() {
            canvas = new DrawCanvas();    // Construct the drawing canvas
            canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

            // Set the Drawing JPanel as the JFrame's content-pane
            Container cp = getContentPane();
            cp.add(canvas);
            // or "setContentPane(canvas);"

            setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
            pack();              // Either pack() the components; or setSize()
            setTitle("......");  // "super" JFrame sets the title
            setVisible(true);    // "super" JFrame show
        }

        /**
         * Define inner class DrawCanvas, which is a JPanel used for custom drawing.
         */
        private class DrawCanvas extends JPanel {
            // Override paintComponent to perform your own painting
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);     // paint parent's background
                setBackground(Color.BLACK);  // set background color for this JPanel

                // Your custom painting codes. For example,
                // Drawing primitive shapes
                g.setColor(Color.RED);    // set the drawing color
                for (Waypoint wp : AirspaceEngine.getInstance().getAirMap().getNodes()) {
                    Double x = wp.getX();
                    Double y = wp.getY();
                    g.drawOval(x.intValue(), y.intValue(), 10, 10);
                }

                g.setColor(Color.YELLOW);
                for (RouteSegment rs : AirspaceEngine.getInstance().getAirMap().getEdges()) {
                    Double x1 = rs.getFrom().getX();
                    Double x2 = rs.getTo().getX();
                    Double y1 = rs.getFrom().getY();
                    Double y2 = rs.getTo().getY();
                    g.drawLine(x1.intValue(), y1.intValue(), x2.intValue(), y2.intValue());
                }

//                g.drawOval(150, 180, 10, 10);
//                g.drawRect(200, 210, 20, 30);
//                g.setColor(Color.RED);       // change the drawing color
//                g.fillOval(300, 310, 30, 50);
//                g.fillRect(400, 350, 60, 50);
                // Printing texts
                g.setColor(Color.GREEN);
                g.setFont(new Font("Monospaced", Font.PLAIN, 12));
                g.drawString("Airspace Simulation ...", 10, 20);
            }
        }

    }
}
