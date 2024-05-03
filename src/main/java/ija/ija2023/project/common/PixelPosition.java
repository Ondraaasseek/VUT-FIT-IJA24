/** 
 * @file PixelPosition.java
 * @brief Class for PixelPosition
 * @author Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.common;

/**
 * Class for PixelPosition
 *
 * @version 1.0
 * @since 2024-05-02
 */
public class PixelPosition {
    double y;
    double x;

    /**
     * Constructor for PixelPosition
     * @param y y pixel coordinate of the position
     * @param x x pixel coordinate of the position
     */
    public PixelPosition(double y, double x) {
        this.y = y;
        this.x = x;
    }

    /**
     * Get y of the position
     * @return y of the position
     */
    public double getY() {
        return this.y;
    }

    /**
     * Get x of the position
     * @return x of the position
     */
    public double getX() {
        return this.x;
    }

    /**
     * Compare two positions if they are equal
     * @param o Object to compare with this position
     * @return True if the positions are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PixelPosition)) {
            return false;
        }
        PixelPosition p = (PixelPosition) o;
        return this.y == p.y && this.x == p.x;
    }

    @Override
    public String toString() {
        return "PixelPosition[" + this.y + ", " + this.x + "]";
    }
}
