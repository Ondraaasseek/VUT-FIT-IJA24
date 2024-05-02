package ija.ija2023.project;

import ija.ija2023.project.env.EnvInput;

// Application entry point class initialization of the main window. and calling everything else.

/**
 * Entry point to the application
 * @see EnvInput
 */
public class Main {
    public static void main(String[] args) {
        // Call the main window
        EnvInput.launch(EnvInput.class, args);
    }
}
