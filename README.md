# Doily

This is a coursework I had in the first year. It consisted in creating a GUI for drawing Digital Doilies using the Swing API. A digital doily is a repeated pattern of mouse drawn graphics intended to resemble a lace doily. To achieve this, the drawing area is divided in to sectors radiating out from a central point. Any point drawn in any sector is also drawn at the same relative position in every other sector. In addition to this the points are reflected across each sector.

Features:
- Ability to clear the doily display
- Ability to change the colour and size of the pen for the doily to be drawn in
- Ability to change the number of sectors that the display is divided into
- Ability to toggle between showing the sector lines, and whether or not to reflect the drawn points
- Ability to undo the previously drawn point
- Ability to save the current doily image in a gallery of images

Known bugs: 
- It starts to lag after a lot has been drawn 
- The image changes if you resize the window
- Some line are drawn below old ones instead of over
