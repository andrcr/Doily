import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

public class ControlPanel extends JPanel {
    private JButton clearButton; // clearing everything that is drawn
    private JButton undoButton; // clearing the previously drawn array of
    // points(connected with lines)

    private String[] colours; // store the available colors
    private DrawPanel drawPanel; // reference linking

    private JComboBox<String> chooseColour; // combo box for displaying the
    // available colors and choosing one

    private JToggleButton hideSectors; // toggle between showing the sectors or
    // not

    private JSpinner sizeOfPen; // change the size of pen
    private JSpinner numberOfSectors; // change number of sectors
    private SpinnerNumberModel model; // for JSpinner

    private JButton saveButton; // save what is currently drawn in drawPanel to
    // galleryPanel

    private JToggleButton reflectionButton; // turn reflection on

    private GalleryPanel galleryPanel; // reference linking
    private JButton removeButton; // remove the button that is selected

    public ControlPanel(DrawPanel drawPanel, GalleryPanel galleryPanel) { // reference
        // linking
        this.drawPanel = drawPanel;

        this.galleryPanel = galleryPanel;

    }

    public void init() // same code practice principle + design stuff

    {

        Dimension d = new Dimension(1000, 100);

        setPreferredSize(d);

        model = new SpinnerNumberModel();
        model.setMinimum(0);

        setLayout(new GridLayout(1, 9));

        setBackground(Color.lightGray);

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 3)), "MENU",
                TitledBorder.CENTER, TitledBorder.TOP));

        clearButton = new JButton("CLEAR IMAGE");
        clearButton.addActionListener(new ActionListener() {

                                          @Override
                                          public void actionPerformed(ActionEvent e) {

                                              if (e.getSource() == clearButton) {
                                                  drawPanel.clear(); // calls clear in drawPanel when buttons
                                                  // is pressed
                                              }

                                          }

                                      }

        );
        add(clearButton);

        undoButton = new JButton("UNDO LAST DRAWN");
        undoButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() == undoButton) {
                    drawPanel.undo(); // calls undo when button is pressed
                }
            }

        });
        add(undoButton);
        hideSectors = new JToggleButton("HIDE SECTORS");
        hideSectors.addActionListener(new ActionListener() {
            /*
             * when this button is pressed it sets showSectors to the opposite
             * of the value of isSelected. this is somewhat confusing because I
             * have named my variable showSectors (which lets drawSectors() in
             * drawPanel to make them visible) but if the Hide button is pressed
             * it wont give permission for the method to draw them that is why I
             * had to use the ! operator
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.showSectors = !hideSectors.isSelected();
                drawPanel.repaint();

            }

        });
        add(hideSectors);

        reflectionButton = new JToggleButton("REFLECT");
        reflectionButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
				/*
				 * sets the value of reflect to true/false and the reflect
				 * variable has permission to do something in drawPanel if true
				 */
                drawPanel.reflect = reflectionButton.isSelected();
                drawPanel.repaint();
            }

        });

        add(reflectionButton);

        colours = new String[8]; // array to hold the colors

        colours[0] = "BLACK";
        colours[1] = "YELLOW";
        colours[2] = "RED";
        colours[3] = "BLUE";
        colours[4] = "GREEN";
        colours[5] = "PINK";
        colours[6] = "ORANGE";
        colours[7] = "MAGENTA";

        chooseColour = new JComboBox<String>(colours);

        chooseColour.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 3)), "COLOUR",
                TitledBorder.CENTER, TitledBorder.TOP));

        chooseColour.setSelectedIndex(0); // default at start
        chooseColour.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String selected = (String) chooseColour.getSelectedItem(); // store
                // selected
                // String
                // Object

                // check which String Object it is and sets the color
                // accordingly

                if (selected == colours[1]) {
                    drawPanel.colorForDraw = Color.yellow;
                } else if (selected == colours[2]) {
                    drawPanel.colorForDraw = Color.red;
                } else if (selected == colours[3]) {
                    drawPanel.colorForDraw = Color.blue;
                } else if (selected == colours[4]) {
                    drawPanel.colorForDraw = Color.green;
                } else if (selected == colours[5]) {
                    drawPanel.colorForDraw = Color.pink;
                } else if (selected == colours[6]) {
                    drawPanel.colorForDraw = Color.orange;
                } else if (selected == colours[7]) {
                    drawPanel.colorForDraw = Color.magenta;
                } else
                    drawPanel.colorForDraw = Color.black;

            }
        });
        add(chooseColour);

        sizeOfPen = new JSpinner(); // JSpinner because I want the user to
        // have freedom

        sizeOfPen.setFont(new Font("For Number size", Font.BOLD, 30)); // style

        sizeOfPen.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 3)), "PEN SIZE",
                TitledBorder.CENTER, TitledBorder.TOP));

        JFormattedTextField txt = ((JSpinner.NumberEditor) sizeOfPen.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
		/*
		 * restricting the JSpinner to not accept non-numerical arguments
		 * because it needs to be an integer and it is just ugly if the user is
		 * able to spam chars in the text box
		 */

        drawPanel.sizeForDraw = 10; // some default values
        sizeOfPen.setValue(10);

        sizeOfPen.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                int selectedSize = (int) sizeOfPen.getValue();
                if (selectedSize < 1) {
                    JOptionPane.showMessageDialog(null, "The size cannot be less than 1");
					/*
					 * tell the user that the penSize can't be less than 1. It
					 * can be zero actually but in most programs of this kind
					 * the minimum of 1 makes the most sense.
					 */

                    sizeOfPen.setValue(1); // sets the number showing on
                    // JSpinner to 1
                } else
                    drawPanel.sizeForDraw = selectedSize; // otherwise just set
                // it to whatever
                // the user wants

            }
        });
        add(sizeOfPen);

        numberOfSectors = new JSpinner(model);

        JFormattedTextField txt2 = ((JSpinner.NumberEditor) numberOfSectors.getEditor()).getTextField();
        ((NumberFormatter) txt2.getFormatter()).setAllowsInvalid(false);

		/*
		 * same thing goes for this JSpinner since the sector number can be a
		 * positive number only
		 */

        numberOfSectors.setFont(new Font("For Number Of Sectors", Font.BOLD, 30)); // style

        numberOfSectors.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 3)), "SECTORS",
                TitledBorder.CENTER, TitledBorder.TOP));

        drawPanel.sectorNumber = 5; // some on start-up values

        numberOfSectors.setValue(5);

        model.setMinimum(1);

        numberOfSectors.addChangeListener(new ChangeListener() {
            @Override

            public void stateChanged(ChangeEvent e) {
                int sectorNumber = (int) numberOfSectors.getValue();
				/*
				 * keeping sectorNumber == 0 because the user might enter it
				 * manually because although char is an invalid input, 0 still
				 * is.
				 */
                if (sectorNumber == 1 || sectorNumber == 0) {
					/*
					 * setting to zero because if the sector is only one sector
					 * no need for rotation according to value of sectorNumber
					 */
                    drawPanel.sectorNumber = 1; // in order to have reflection
                    // when it is just one sector
                    // free-drawing

                    numberOfSectors.setValue(1);
                    drawPanel.repaint();

					/*
					 * model.setStepSize(2); this is another way of fixing the
					 * bug of 1 = 0 in the JSpinner by different logic but I
					 * decided that 1 sector is the minimum because 1 sector
					 * would represent one whole canvas for drawing like Paint
					 * program without rotation
					 */

                } else {
                    // model.setStepSize(1); see above ^

                    drawPanel.sectorNumber = sectorNumber; // otherwise just set
                    // it to
                    // whatever the use wants
                    numberOfSectors.setValue(sectorNumber);
                    drawPanel.repaint();
                }

            }
        });
        add(numberOfSectors);

        saveButton = new JButton("SAVE");
        saveButton.addActionListener(new ActionListener() {

			/*
			 * when pressed calls the special method makePanelImage that is
			 * explained in drawPanel (it takes an image out of drawPanel, since
			 * it is passed here as an argument, an already existing panel when
			 * the button is pressed)
			 */

            @Override
            public void actionPerformed(ActionEvent e) {

                drawPanel.makePanelImage(drawPanel);
                galleryPanel.revalidate();
                galleryPanel.repaint();

            }

        });

        add(saveButton);

        removeButton = new JButton("REMOVE SELECTED");
        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

				/*
				 * first a check if the Object is null because if the user
				 * clicks the remove button on startup before adding at least
				 * one image the programs throws a NullPointerException; the
				 * other check notifies the user that he/she cannot remove
				 * something when, apart from not selecting anything, there is
				 * not even something to select; this also fixed a bug I
				 * encountered earlier
				 */
                if (galleryPanel.forRemoval == null || galleryPanel.getComponentCount() == 0) {
                    JOptionPane.showMessageDialog(null, "You have not selected anything for removal");
                } else {
					/*
					 * other wise it removes the specified toggled button
					 */
                    galleryPanel.remove(galleryPanel.forRemoval);
                    galleryPanel.revalidate();
                    galleryPanel.repaint();

                }

            }

        });

        add(removeButton);
    }

}
