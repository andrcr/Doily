import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class DrawPanel extends JPanel {
    public Graphics2D g2;
    public int sectorNumber; // Number of sectors
    public Color colorForDraw;
    public int sizeForDraw;
    public boolean reflect;
    public boolean showSectors;
    private ArrayList<ArrayList<CoordinatePoint>> masterPoints; // holds
    // ArrayLists of
    // drawn points
    private Graphics g;
    private int previousPointX; // to connect with lines
    private int previousPointY; // to connect with lines
    private GalleryPanel galleryPanel;

    public DrawPanel(GalleryPanel galleryPanel) { // reference link
        this.galleryPanel = galleryPanel;
    }

    public void init() { // code practice + style stuff

        Dimension d = new Dimension(800, 700);
        setPreferredSize(d);
        masterPoints = new ArrayList<ArrayList<CoordinatePoint>>();
        MyAdapter adapter = new MyAdapter();
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
        showSectors = true;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        previousPointX = 0;
        previousPointY = 0;

        if (showSectors && sectorNumber >= 2) { // checks showSectors (if true
            // or not) and value of
            // sectorNumber whether to draw
            // any sectors or not
            drawSectors(g2);
        }

        for (int i = 0; i <= sectorNumber; i++) { // for the number of sectors

            if (reflect) {
                drawingByUserReflected(g2); // calls a specific method for
                // reflection

                g2.rotate(Math.toRadians(360 / (double) sectorNumber), getWidth() / 2, getHeight() / 2);
                drawingByUser(g2);
            } else {
                drawingByUser(g2); // without reflection
                g2.rotate(Math.toRadians(360 / (double) sectorNumber), getWidth() / 2, getHeight() / 2);

            }

        }

    }

    private void drawSectors(Graphics2D g2) {
		/*
		 * Draw lines from center point for sectorNumber the user specifies.
		 * sectorNumber is number of sectors
		 */
        for (int i = 1; i <= sectorNumber; i++) {

            g2.setStroke(new BasicStroke(3)); // avoid overlapping colors and
            // size
            g2.setColor(Color.black);
            g2.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, 0);
            g2.rotate(Math.toRadians(360 / (double) sectorNumber), getWidth() / 2, getHeight() / 2);
        }
    }

    private void drawingByUser(Graphics2D g2) {

        for (ArrayList<CoordinatePoint> x : masterPoints) {
			/*
			 * fix bug to disconnect different drags with ArrayList of
			 * ArrayLists of points. Smoothing the lines with BasicStroke
			 * 
			 */

            for (CoordinatePoint p : x) {

                if (p == x.get(0)) { // avoiding OutOfBoundsE

                    g2.setColor(p.getColor());
                    g2.setStroke(new BasicStroke(p.getSize(), BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND));
                    g2.drawLine((int) p.getX(), (int) p.getY(), (int) p.getX(), (int) p.getY());
                    previousPointX = (int) p.getX();
                    previousPointY = (int) p.getY();
                    g2.setColor(Color.black); // avoid overlap of colors

                } else {
					/* Connect with lines to avoid disconnection of points */
                    g2.setColor(p.getColor());
                    g2.setStroke(new BasicStroke(p.getSize(), BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND));
                    g2.drawLine((int) p.getX(), (int) p.getY(), previousPointX, previousPointY);
                    previousPointX = (int) p.getX();
                    previousPointY = (int) p.getY();
                    g2.setColor(Color.black); // avoid overlap of colors

                }

            }

        }

    }

    private void drawingByUserReflected(Graphics2D g2) {
		/*
		 * I change coordinate X for reflection and smooth the lines with
		 * BasicStroke. Same method as drawingByUser but set up with conditions
		 * depending on when the reflect button is on and when not to keep
		 * changes to individual drags
		 */
        for (ArrayList<CoordinatePoint> x : masterPoints) {
            for (CoordinatePoint p : x) {

                if (p == x.get(0)) {

                    if (p.getReflect()) {

                        g2.setColor(p.getColor());
                        g2.setStroke(new BasicStroke(p.getSize(), BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND));
                        g2.drawLine(getWidth() - (int) p.getX(), (int) p.getY(), getWidth() - (int) p.getX(),
                                (int) p.getY());
                        previousPointX = (int) p.getX();
                        previousPointY = (int) p.getY();
                        g2.setColor(Color.black);

                    } else {
                        g2.setColor(p.getColor());
                        g2.setStroke(new BasicStroke(p.getSize(), BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND));
                        g2.drawLine((int) p.getX(), (int) p.getY(), (int) p.getX(), (int) p.getY());
                        previousPointX = (int) p.getX();
                        previousPointY = (int) p.getY();
                        g2.setColor(Color.black);

                    }

                } else {

                    if (p.getReflect()) {

                        g2.setColor(p.getColor());
                        g2.setStroke(new BasicStroke(p.getSize(), BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND));
                        g2.drawLine(getWidth() - (int) p.getX(), (int) p.getY(), getWidth() - previousPointX,
                                previousPointY);
                        previousPointX = (int) p.getX();
                        previousPointY = (int) p.getY();
                        g2.setColor(Color.black);

                    } else {

                        g2.setColor(p.getColor());
                        g2.setStroke(new BasicStroke(p.getSize(), BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND));
                        g2.drawLine((int) p.getX(), (int) p.getY(), previousPointX, previousPointY);
                        previousPointX = (int) p.getX();
                        previousPointY = (int) p.getY();
                        g2.setColor(Color.black);

                    }

                }

            }

        }

    }

    public void clear() {
		/* Clear the canvas by clearing the contents of master ArrayList */
        masterPoints.clear();
        repaint();
    }

    public void undo() {
		/* remove last drawn by removing last ArrayList */
        if (masterPoints.size() >= 1)
            masterPoints.remove(masterPoints.size() - 1);
        repaint();

    }

    public void makePanelImage(DrawPanel panel) {
		/*
		 * When called by Save button in controlPanel this method makes a
		 * buffered image with the sizes of the panel, creates graphics and
		 * paints on it whatever there is on the panel. Then, I get rid of the
		 * graphics to not waste resources. I want to create a scaled image out
		 * of it, like a thumbnail or something similar so I rescale it to a
		 * smaller size.
		 * 
		 * Then I create a ToggleButton because I think it is a good way of
		 * highlighting visually the selection (which one of the gallery images
		 * to remove).
		 * 
		 * The image is set as an icon on the button
		 * 
		 * Every button has its own listener so that when it is hit it will be
		 * highlighted. Every button is also added to a button group in order to
		 * remove a button at a time (spec says to remove one) and in order to
		 * fix a bug.
		 */

        // check if the user has reached the limit
        if (galleryPanel.getComponentCount() <= 11) {

            Dimension size = panel.getSize();
            BufferedImage picture = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = picture.createGraphics();
            panel.paint(g2);
            g2.dispose();

            Image img = picture.getScaledInstance(90, 90, Image.SCALE_SMOOTH);

            JToggleButton frameForImage = new JToggleButton();

            ImageIcon img2 = new ImageIcon(img);

            frameForImage.setIcon(img2);

            galleryPanel.buttonGroup.add(frameForImage);

            frameForImage.setToolTipText("Click in order to select image for removal.");
            frameForImage.setBackground(Color.black);
            frameForImage.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    galleryPanel.forRemoval = (JToggleButton) e.getSource();

                }
            });
            galleryPanel.add(frameForImage);

			/*
			 * This is something extra in order for the user to have a copy in
			 * the source code folder a picture file with an original size
			 * screenshot of his/hers doily.
			 * 
			 * galleryPanel.counter is to prevent from the files overwriting
			 * each other
			 */
            try {
                File file = new File("doily" + galleryPanel.counter + ".png");
                ImageIO.write(picture, "png", file);

            } catch (Exception e) {
                e.printStackTrace();
            }

            galleryPanel.counter++;

        } else
            JOptionPane.showMessageDialog(null, "You cannot add more than 12 images.");

    }

    private class MyAdapter extends MouseAdapter {

        // Implementing needed methods
        ArrayList<CoordinatePoint> points; // one drag

        public void mousePressed(MouseEvent e) {
            points = new ArrayList<CoordinatePoint>();
            // keeps adding to masterPoints
            masterPoints.add(points);
            points.add(new CoordinatePoint(e.getX(), e.getY(), sizeForDraw, colorForDraw, reflect));
            repaint();

        }

        public void mouseDragged(MouseEvent e) {

            points.add(new CoordinatePoint(e.getX(), e.getY(), sizeForDraw, colorForDraw, reflect));
            repaint();

        }

    }
}