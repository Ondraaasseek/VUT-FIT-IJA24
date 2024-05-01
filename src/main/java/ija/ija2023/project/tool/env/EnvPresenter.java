/* @file EnvPresenter.java
 * @brief Class for EnvPresenter
 * @autor Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.tool.env;

import ija.ija2023.project.common.AbstractRobot;
import ija.ija2023.project.room.AutonomousRobot;
import ija.ija2023.project.room.ControlledRobot;
import ija.ija2023.project.room.Room;
import ija.ija2023.project.tool.common.Position;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

        Group roomGroup = new Group();
        root.setTop(roomGroup);
        int roomOffsetX = (sceneWidth - scale * width) / 2;
        // Draw floor
        Rectangle floor = new Rectangle(roomOffsetX, 0, scale * width, scale * height);
        floor.setFill(javafx.scene.paint.Color.GRAY);
        roomGroup.getChildren().add(floor);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Check if there is an obstacle
                if (room.obstacleAt(y, x)) {
                    // Draw obstacle
                    Rectangle obstacle = new Rectangle(roomOffsetX+scale*x, scale*y, scale, scale);
                    obstacle.setFill(javafx.scene.paint.Color.BLACK);
                    // Add obstacle to the scene
                    roomGroup.getChildren().add(obstacle);
                    continue;
                }

                // Check if there is a robot
                if (room.robotAt(new Position(y, x))) {
                    AbstractRobot robot = room.getRobotFromPosition(new Position(y, x));
                    if (robot instanceof ControlledRobot) {
                        // Draw robot
                        Group robotModel = EnvCreator.drawRobotModel(scale, true);
                        // Add robot to the scene on a specific position
                        robotModel.setLayoutX(roomOffsetX+scale*x+scale/2);
                        robotModel.setLayoutY(scale*y+scale/2);
                        // get angle from robot in room
                        robotModel.setRotate(robot.angle());
                        roomGroup.getChildren().add(robotModel);

                        // rotate right button
                        AnimationTimer rightButtonHoldTimer = new AnimationTimer() {
                            @Override
                            public void handle(long now) {
                                robotModel.setRotate(robotModel.getRotate() + 2);
                            }
                        };
                        rightButton.setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                rightButtonHoldTimer.start();
                            }
                        });
                        rightButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                rightButtonHoldTimer.stop();
                            }
                        });

                        // rotate left button
                        AnimationTimer leftButtonHoldTimer = new AnimationTimer() {
                            @Override
                            public void handle(long now) {
                                robotModel.setRotate(robotModel.getRotate() - 2);
                            }
                        };
                        leftButton.setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                leftButtonHoldTimer.start();
                            }
                        });
                        leftButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                leftButtonHoldTimer.stop();
                            }
                        });
                        
                        // forward button
                        AnimationTimer forwardButtonTimer = new AnimationTimer() {
                            @Override
                            public void handle(long now) {
                                double angle = robotModel.getRotate();
                                // Calculate new position
                                double newX = robotModel.getLayoutX() + Math.cos(Math.toRadians(angle)) * 2;
                                double newY = robotModel.getLayoutY() + Math.sin(Math.toRadians(angle)) * 2;

                                // Check if new position is within floor boundaries
                                boolean collisionDetected = !(newX - (double) scale / 2 >= roomOffsetX && newX + (double) scale / 2 <= roomOffsetX + scale * width && newY - (double) scale / 2 >= 0 && newY + (double) scale / 2 <= scale * height);

                                // Check if new position is not colliding with obstacle
                                for (int x = 0; x < width; x++) {
                                    for (int y = 0; y < height; y++) {
                                        if (room.obstacleAt(y, x)) {
                                            // Check if new position is colliding with obstacle
                                            if (newX + (double) scale / 2 >= roomOffsetX + scale * x && newX - (double) scale / 2 <= roomOffsetX + scale * x + scale && newY + (double) scale / 2 >= scale * y && newY - (double) scale / 2 <= scale * y + scale) {
                                                // System log
                                                System.out.println("INFO Controlled robot collided with an obstacle");
                                                collisionDetected = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (collisionDetected) {
                                        break;
                                    }
                                }

                                // If no collision detected, update position
                                if (!collisionDetected) {
                                    robotModel.setLayoutX(newX);
                                    robotModel.setLayoutY(newY);
                                }
                            }
                        };
                        forwardButton.setOnAction(e -> {
                            if (Objects.equals(forwardButton.getText(), "FORWARD")) {
                                forwardButton.setText("PAUSE");
                                forwardButtonTimer.start();
                                // System log
                                System.out.println("INFO Controlled robot started moving forward");
                            } else {
                                forwardButton.setText("FORWARD");
                                forwardButtonTimer.stop();
                                // System log
                                System.out.println("INFO Controlled robot paused");
                            }
                        });
                        continue;
                    }
                    if (robot instanceof AutonomousRobot) {
                        // Draw robot
                        Group robotModel = EnvCreator.drawRobotModel(scale);
                        // Add robot to the scene on a specific position
                        robotModel.setLayoutX(roomOffsetX+scale*x+scale/2);
                        robotModel.setLayoutY(scale*y+scale/2);
                        robotModel.setRotate(robot.angle());
                        roomGroup.getChildren().add(robotModel);
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

