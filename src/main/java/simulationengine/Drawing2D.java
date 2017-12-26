package simulationengine;

import airspaceengine.AirspaceEngine;
import airspaceengine.routesegment.RouteSegment;
import airspaceengine.waypoint.Waypoint;
import uav.UAV;
import uav.UAVEngine;

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

                g.setColor(Color.RED);    // set the drawing color
                for (Waypoint wp : AirspaceEngine.getInstance().getAirMap().getNodes().getWaypointList()) {
                    Double x = wp.getX();
                    Double y = wp.getY();
                    g.drawOval(x.intValue(), y.intValue(), 20, 20);
                    g.fillOval(x.intValue(), y.intValue(), 20, 20);
                }

                g.setColor(Color.YELLOW);
                for (RouteSegment rs : AirspaceEngine.getInstance().getAirMap().getEdges().getRouteSegList()) {
                    Double x1 = rs.getFrom().getX();
                    Double x2 = rs.getTo().getX();
                    Double y1 = rs.getFrom().getY();
                    Double y2 = rs.getTo().getY();
                    g.drawLine(x1.intValue(), y1.intValue(), x2.intValue(), y2.intValue());
                }

                g.setColor(Color.GREEN);
                for (UAV uav : UAVEngine.getInstance().getUAVs()) {
                    uav.setOrigin();
                    Double x = uav.getOperation().getCurrentX();
                    Double y = uav.getOperation().getCurrentY();
                    g.drawRect(x.intValue(), y.intValue(), 15, 15);
                    g.fillRect(x.intValue(), y.intValue(), 15, 15);
                }

                // Printing texts
                g.setColor(Color.WHITE);
                g.setFont(new Font("Monospaced", Font.PLAIN, 12));
                g.drawString("Airspace Simulation ...", 10, 20);
            }
        }

    }
}
