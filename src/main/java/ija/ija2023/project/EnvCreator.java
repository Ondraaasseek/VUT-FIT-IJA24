package ija.ija2023.project;

import ija.ija2023.project.room.Room;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class EnvCreator {
    public static void start(int width, int height) {

        // Create the environment
        Room room = Room.create(width, height);
        Stage primaryStage = new Stage();

        primaryStage.setTitle("Room Creator");

        int controlPanelWidth = 800;
        int ControlPanelHeight = 300;


        int scale = 100;
        int sceneWidth = Math.max(scale * width, controlPanelWidth);
        int sceneHeight = scale * height + ControlPanelHeight;

        int roomWidthOffset = (sceneWidth - width * scale) / 2;
        int controlPanelOffset = (sceneWidth - controlPanelWidth) / 2;

        // Create control panel
        Rectangle controlPanel = new Rectangle(controlPanelOffset, height * scale, controlPanelWidth, ControlPanelHeight);
        controlPanel.setFill(javafx.scene.paint.Color.LIGHTGRAY);
        // Inside the control panel create text fields for placing Obstacles and Robots
        TextField obstacleXField = new TextField();
        TextField obstacleYField = new TextField();
        TextField robotXField = new TextField();
        TextField robotYField = new TextField();
        Button placeObstacleButton = new Button("Place Obstacle");
        Button placeRobotButton = new Button("Place Robot");



        Group roomGroup = new Group();
        roomGroup.getChildren().add(controlPanel);

        // Draw the room for each cell create 100x100 rectangle
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // Draw rectangle
                Rectangle rectangle = new Rectangle(i * scale + roomWidthOffset, j * scale, scale, scale);
                rectangle.setFill(javafx.scene.paint.Color.GRAY);
                // Add rectangle to the scene
                roomGroup.getChildren().add(rectangle);
            }
        }

        Scene scene = new Scene(roomGroup, sceneWidth, sceneHeight);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
