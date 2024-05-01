/* @file EnvPresenter.java
 * @brief Class for EnvPresenter
 * @autor Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.tool.env;

import ija.ija2023.project.common.AbstractRobot;
import ija.ija2023.project.common.RobotFactory;
import ija.ija2023.project.room.AutonomousRobot;
import ija.ija2023.project.room.ControlledRobot;
import ija.ija2023.project.room.Room;
import ija.ija2023.project.tool.common.Position;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Objects;

public class EnvPresenter {
    public static void start(Room room, Stage beforeStage) {
        // Based on the room create Stage with controls
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        int width = room.cols();
        int height = room.rows();
        Stage primaryStage = new Stage();
        BorderPane root = new BorderPane();
        Group controlledRobotGroup = null;

        primaryStage.setTitle("Room Creator");

        int controlPanelWidth = 500;
        int ControlPanelHeight = 150;


        int scale = 100;
        int sceneWidth = Math.max(scale * width, controlPanelWidth);
        int sceneHeight = scale * height + ControlPanelHeight;

        // Create buttons
        GridPane createButtonsGridPane = new GridPane();
        createButtonsGridPane.setPrefSize(sceneWidth, ControlPanelHeight);
        createButtonsGridPane.setHgap(10);
        Button leftButton = new Button("<-");
        Button forwardButton = new Button("FORWARD");
        forwardButton.setPrefWidth(100);
        Button rightButton = new Button("->");

        createButtonsGridPane.add(leftButton, 0, 0);
        createButtonsGridPane.add(forwardButton, 1, 0);
        createButtonsGridPane.add(rightButton, 2, 0);

        // align content to center of a pane
        createButtonsGridPane.setAlignment(Pos.CENTER);

        root.setCenter(createButtonsGridPane);

        Button createButton = new Button("End live simulation");
        createButton.setOnAction(e -> {
            primaryStage.close();
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            primaryStage.close();
            beforeStage.show();
        });
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox buttonBox = new HBox(cancelButton, spacer, createButton);
        buttonBox.setPadding(new Insets(10, 10, 10, 10)); // Padding

        root.setBottom(buttonBox);

        GridPane roomGridPane = new GridPane();
        roomGridPane.setAlignment(Pos.CENTER);
        root.setTop(roomGridPane);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                // Draw floor
                Rectangle floor = new Rectangle(scale, scale);
                floor.setFill(javafx.scene.paint.Color.GRAY);
                // Add floor to the scene
                roomGridPane.add(floor, x, y);

                // Check if there is an obstacle
                if (room.obstacleAt(y, x)) {
                    // Draw obstacle
                    Rectangle obstacle = new Rectangle(scale, scale);
                    obstacle.setFill(javafx.scene.paint.Color.BLACK);
                    // Add obstacle to the scene
                    roomGridPane.add(obstacle, x, y);
                    continue;
                }

                // Check if there is a robot
                if (room.robotAt(new Position(y, x))) {
                    AbstractRobot robot = room.getRobotFromPosition(new Position(y, x));
                    if (robot instanceof ControlledRobot) {
                        // Draw robot
                        Group robotModel = EnvCreator.drawRobotModel(scale, true);
                        // Add robot to the scene
                        roomGridPane.add(robotModel, x, y);
                        final Group finalControlledRobotGroup = robotModel;
                        rightButton.setOnAction(e -> {
                            finalControlledRobotGroup.setRotate(finalControlledRobotGroup.getRotate() + 5);
                        });
                        leftButton.setOnAction(e -> {
                            finalControlledRobotGroup.setRotate(finalControlledRobotGroup.getRotate() - 5);
                        });
                        forwardButton.setOnAction(e -> {
                            if (Objects.equals(forwardButton.getText(), "FORWARD")) {
                                forwardButton.setText("PAUSE");
                            } else {
                                forwardButton.setText("FORWARD");
                            }
                        });
                        continue;
                    }
                    if (robot instanceof AutonomousRobot) {
                        // Draw robot
                        Group robotModel = EnvCreator.drawRobotModel(scale);
                        // Add robot to the scene
                        roomGridPane.add(robotModel, x, y);
                        continue;
                    }
                }
            }
        }

        Scene scene = new Scene(root, sceneWidth, sceneHeight);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

