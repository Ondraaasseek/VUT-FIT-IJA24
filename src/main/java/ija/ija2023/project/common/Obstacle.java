/** 
 * @file Obstacle.java
 * @brief Class for Obstacle
 * @author Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.common;

/**
 * Class for Obstacle
 *
 * @version 1.0
 * @since 2024-05-02
 *
 * @see Environment
 * @see Position
 */
public class Obstacle {
    Environment env;
    Position pos;

    /**
     * Constructor for Obstacle
     * @param env Environment where the obstacle is
     * @param pos Position of the obstacle
     */
    public Obstacle(Environment env, Position pos) {
        this.env = env;
        this.pos = pos;
    }

    /**
     * Get the position of the obstacle
     * @return Position of the obstacle
     */
    public Position getPosition() {
        return this.pos;
    }
}
