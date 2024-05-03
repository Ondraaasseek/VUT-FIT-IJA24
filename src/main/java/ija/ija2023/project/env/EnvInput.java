/** 
 * @file EnvInput.java
 * @brief Class for the main window of the application
 * @author Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.env;

import ija.ija2023.project.room.Room;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

/**
 * Class for the main window of the application
 *
 * @version 1.0
 * @since 2024-04-28
 *
 * @see Stage
 * @see FileChooser
 * @see EnvDimensionPicker
 */
public class EnvInput extends Application {

    /**
     * Function for starting the window
     * @param primaryStage Stage of the window
     */
    @Override
    public void start(Stage primaryStage) {
        // Title of the window
        primaryStage.setTitle("Robot Simulation");

        // Title above the buttons
        Text title = new Text("Roboti a Kameny");
        title.setFont(Font.font("null", FontWeight.BOLD, 20));

        // Button for loading from file
        Button loadButton = new Button("Load from file");
        loadButton.setOnAction(loadButtonHandler(primaryStage));

        // Button for creating new environment
        Button createButton = new Button("Create new environment");
        createButton.setOnAction(createButtonHandler(primaryStage));

        // Layout of the window
        VBox vbox = new VBox(title, loadButton, createButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        Scene scene = new Scene(vbox, 300, 200);

        // Show the window
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Handler for the load button
     * @param primaryStage Stage of the window
     * @return EventHandler for the load button
     */
    private static EventHandler<ActionEvent> loadButtonHandler(Stage primaryStage) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Open file chooser
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");

                // Get the selected file
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                if (selectedFile == null) {
                    return;
                }
                String filePath = selectedFile.getAbsolutePath();

                // Get the file content
                String content = "";
                try{
                    content = new String(Files.readAllBytes(Paths.get(filePath)));
                } catch (IOException ex) {
                    System.out.println("ERROR " + ex.getMessage());
                }

                // Parse the file content
                Room room = null;
                try {
                    room = InputParser.parseInput(content);
                    if (room == null) {
                        throw new Exception("Room creation failed.");
                    }
                } catch (Exception ex) {
                    System.out.println("ERROR " + ex.getMessage());
                    return;
                }
                
                // Move to the next window
                primaryStage.close();
                EnvCreator.start(room, primaryStage);
            }
        };
    }

    /**
     * Handler for the create button
     * @param primaryStage Stage of the window
     * @return EventHandler for the create button
     */
    private static EventHandler<ActionEvent> createButtonHandler(Stage primaryStage) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Move to the next window
                primaryStage.close();
                EnvDimensionPicker.start(primaryStage);
            }
        };
    }
}
