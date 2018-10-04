import java.awt.Color;
import java.awt.Point;

public class CoordinatePoint extends Point {
    /*
     * this class is for making custom Point objects for drawing with special
     * and individual characteristics like size, color, reflected/not reflected
     */
    private int sizeForDraw;
    private Color colorForDraw;
    private boolean reflect;

    public CoordinatePoint(int x, int y, int sizeForDraw, Color colorForDraw, boolean reflect) {
        super(x, y);

        this.sizeForDraw = sizeForDraw;
        this.colorForDraw = colorForDraw;
        this.reflect = reflect;
    }

    // some getters
    public int getSize() {
        return sizeForDraw;
    }

    public Color getColor() {
        return colorForDraw;
    }

    public boolean getReflect() {
        return reflect;
    }

}