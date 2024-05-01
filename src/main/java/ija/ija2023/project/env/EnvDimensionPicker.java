package ija.ija2023.project.env;

import ija.ija2023.project.room.Room;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class EnvDimensionPicker {
    public static void start(Stage beforeStage) {
        Stage NewEnvStage = new Stage();
        NewEnvStage.setTitle("Create new environment");

        Text textEnv = new Text("Create new environment");
        textEnv.setFont(Font.font("null", FontWeight.BOLD, 20));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10); // Horizontal gap
        gridPane.setVgap(10); // Vertical gap
        gridPane.setPadding(new Insets(10, 10, 10, 10)); // Padding

        Label widthLabel = new Label("Width:");
        TextField widthField = new TextField();
        gridPane.add(widthLabel, 0, 0); // Add to column 0, row 0
        gridPane.add(widthField, 1, 0); // Add to column 1, row 0

        Label heightLabel = new Label("Height:");
        TextField heightField = new TextField();
        gridPane.add(heightLabel, 0, 1); // Add to column 0, row 1
        gridPane.add(heightField, 1, 1); // Add to column 1, row 1

        Button create2Button = new Button("Create");
        Button cancelButton = new Button("Cancel");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox buttonBox = new HBox(cancelButton, spacer, create2Button);
        buttonBox.setPadding(new Insets(10, 10, 10, 10)); // Padding

        VBox newEnvVbox = new VBox(textEnv, gridPane, buttonBox);
        newEnvVbox.setAlignment(Pos.CENTER);
        newEnvVbox.setSpacing(10);
        Scene newEnvScene = new Scene(newEnvVbox, 300, 200);
        NewEnvStage.setScene(newEnvScene);
        NewEnvStage.show();

        cancelButton.setOnAction(e2 -> {
            NewEnvStage.close();
            beforeStage.show();
        });

        create2Button.setOnAction(e2 -> {
            // Check if the input fields are int
            boolean valid = true;
            int width = 0;
            int height = 0;
            try {
                width = Integer.parseInt(widthField.getText());
                if (width < 1) {
                    throw new Exception("Width out of range.");
                }
            } catch (Exception ex) {
                widthField.setText("Invalid input.");
                System.out.println("Exception " + ex.getMessage());
                valid = false;
            }

            try {
                height = Integer.parseInt(heightField.getText());
                if (height < 1) {
                    throw new Exception("Height out of range.");
                }
            } catch (Exception ex) {
                heightField.setText("Invalid input.");
                System.out.println("Exception " + ex.getMessage());
                valid = false;
            }

            if (!valid) {
                return;
            }
            // Call EnvCreator with the width and height
            NewEnvStage.close();
            Room room = Room.create(height, width);
            System.out.println("INFO Empty room created successfully.");
            for (int i = 0; i < room.rows(); i++) {
                for (int j = 0; j < room.cols(); j++) {
                    System.out.print(room.room[i][j] + " ");
                }
                System.out.println();
            }
            EnvCreator.start(room, NewEnvStage);
        });

        widthField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue && widthField.getText().equals("Invalid input.")) {
                    widthField.setText("");
                }
            }
        });

        heightField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue && heightField.getText().equals("Invalid input.")) {
                    heightField.setText("");
                }
            }
        });
    }
}
