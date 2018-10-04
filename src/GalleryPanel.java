import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

public class GalleryPanel extends JPanel {

    public int counter; // in order to have a variable that prevents the
    // screenshot files from overwriting each other

    public JToggleButton forRemoval; // the button containing the image that
    // should be removed

    public ButtonGroup buttonGroup; // linking the buttons to remove one at a
    // time and to fix a bug

    public void init() { // same code practice principle

        Dimension d = new Dimension(200, 600); // simple style specifications

        setPreferredSize(d);
        setLayout(new GridLayout(6, 2));


        setBackground(Color.LIGHT_GRAY);

        buttonGroup = new ButtonGroup();
        counter = 1;

    }

}
