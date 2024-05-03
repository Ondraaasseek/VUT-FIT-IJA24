/** 
 * @file Main.java
 * @brief Entry point to the application
 * @author Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project;

import ija.ija2023.project.env.EnvInput;

/**
 * Entry point to the application
 * @see EnvInput
 */
public class Main {
    /**
     * Main method
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Call the main window
        EnvInput.launch(EnvInput.class, args);
    }
}
