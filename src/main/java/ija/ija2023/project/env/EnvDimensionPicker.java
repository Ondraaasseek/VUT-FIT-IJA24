package ija.ija2023.project.env;

import ija.ija2023.project.room.Room;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Class for the window for selecting the dimensions of the room
 *
 * @version 1.0
 * @since 2024-05-02
 *
 * @see Stage
 * @see Room
 * @see EnvCreator
 */
public class EnvDimensionPicker {
    public static void start(Stage beforeStage) {
        // Title of the window
        Stage dimensionStage = new Stage();
        dimensionStage.setTitle("Create new environment");

        // Title above the buttons
        Text textEnv = new Text("Create new environment");
        textEnv.setFont(Font.font("null", FontWeight.BOLD, 20));

        // Layout of the form
        GridPane formGridPane = new GridPane();
        formGridPane.setHgap(10);
        formGridPane.setVgap(10);
        formGridPane.setPadding(new Insets(10));

        // Width field
        Label widthLabel = new Label("Width:");
        TextField widthField = new TextField();
        widthField.focusedProperty().addListener(fieldFocusListener(widthField));

        // Height field
        Label heightLabel = new Label("Height:");
        TextField heightField = new TextField();
        heightField.focusedProperty().addListener(fieldFocusListener(heightField));

        // Add the fields to the form
        formGridPane.add(widthLabel, 0, 0);
        formGridPane.add(widthField, 1, 0);
        formGridPane.add(heightLabel, 0, 1);
        formGridPane.add(heightField, 1, 1);

        // Button for going back
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(cancelButtonHandler(dimensionStage, beforeStage));

        // Button for creating the environment
        Button createButton = new Button("Create");
        createButton.setOnAction(createButtonHandler(dimensionStage, widthField, heightField));
        
        // Layout of the navigation buttons
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox buttonBox = new HBox(cancelButton, spacer, createButton);
        buttonBox.setPadding(new Insets(10));

        // Layout of the window
        VBox vbox = new VBox(textEnv, formGridPane, buttonBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        Scene scene = new Scene(vbox, 300, 200);

        // Show the window
        dimensionStage.setScene(scene);
        dimensionStage.show();   
    }

    private static EventHandler<ActionEvent> cancelButtonHandler(Stage primaryStage, Stage beforeStage) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Move to the previous window
                primaryStage.close();
                beforeStage.show();
            }
        };
    }

    private static EventHandler<ActionEvent> createButtonHandler(Stage primaryStage, TextField widthField, TextField heightField) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Check if the input fields are valid
                int width = 0;
                int height = 0;
                try {
                    width = Integer.parseInt(widthField.getText());
                    if (width < 1) {
                        widthField.setText("Invalid input.");
                        throw new Exception("Width out of range.");
                    }
                    height = Integer.parseInt(heightField.getText());
                    if (height < 1) {
                        heightField.setText("Invalid input.");
                        throw new Exception("Height out of range.");
                    }
                } catch (Exception ex) {
                    System.out.println("ERROR " + ex.getMessage());
                    return;
                }

                // Create empty room
                Room room = null;
                try {
                    room = Room.create(height, width);
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

    private static ChangeListener<Boolean> fieldFocusListener(TextField field) {
        return new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // Clear the field upon focus if it contains warning text
                if (newValue && field.getText().equals("Invalid input.")) {
                    field.setText("");
                }
            }
        };
    }
}
