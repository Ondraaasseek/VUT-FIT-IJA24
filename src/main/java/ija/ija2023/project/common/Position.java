/* @file Position.java
 * @brief Class for Position
 * @autor Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.common;

/**
 * Class for Position
 *
 * @version 1.0
 * @since 2024-05-02
 */
public class Position {
    int row;
    int col;

    /**
     * Constructor for Position
     * @param row Row of the position
     * @param col Column of the position
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Get row of the position
     * @return Row of the position
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Get column of the position
     * @return Column of the position
     */
    public int getCol() {
        return this.col;
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
        if (!(o instanceof Position)) {
            return false;
        }
        Position p = (Position) o;
        return this.row == p.row && this.col == p.col;
    }

    @Override
    public int hashCode() {
        return this.row * 31 + this.col;
    }

    @Override
    public String toString() {
        return "Position[" + this.row + ", " + this.col + "]";
    }
}
