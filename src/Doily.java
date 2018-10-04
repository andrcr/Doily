import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

public class Doily extends JFrame {

    private Container pane;
    private ControlPanel controlPanel;
    private DrawPanel drawPanel;
    private GalleryPanel galleryPanel;

    public Doily() {
        super("Digital Doily GUI"); // invoking super constructor and setting
        // the header for the program

    }

    public void init() { // good code practice to put the specification and
        // initialization of the GUI in a method

        pane = getContentPane();

        pane.setLayout(new BorderLayout()); // style specifications

        galleryPanel = new GalleryPanel();
        galleryPanel.init();

        pane.add(galleryPanel, BorderLayout.EAST);

        drawPanel = new DrawPanel(galleryPanel); // reference linking for
        // drawPanel images to be
        // transferred to
        // galleryPanel
        drawPanel.init();
        pane.add(drawPanel, BorderLayout.CENTER);

        controlPanel = new ControlPanel(drawPanel, galleryPanel);
		/*
		 * reference linking between the controlPanel and the other 2 because it
		 * is a menu for functions inside the 2 other classes.
		 */
        controlPanel.init();
        pane.add(controlPanel, BorderLayout.SOUTH);

    }

}
