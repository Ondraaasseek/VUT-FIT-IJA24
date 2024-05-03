/** 
 * @file EnvPresenter.java
 * @brief Class for the environment presenter here user can see the environment and control the robot and watch the autonomous robots move and collide with obstacles
 * @author Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.env;

import ija.ija2023.project.common.Position;
import ija.ija2023.project.common.robot.AbstractRobot;
import ija.ija2023.project.env.history.History;
import ija.ija2023.project.env.history.Memento;
import ija.ija2023.project.env.history.SceneSnapshot;
import ija.ija2023.project.room.AutonomousRobot;
import ija.ija2023.project.room.ControlledRobot;
import ija.ija2023.project.room.Room;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Class for the environment presenter here user can see the environment and control the robot
 * and watch the autonomous robots move and collide with obstacles
 *
 * @version 1.0
 * @since 2024-05-02
 * @see Stage
 * @see Room
 * @see Group
 * @see AnimationTimer
 * @see AbstractRobot
 */
public class EnvPresenter {
    static int scale = 100;
    static Room room;
    static Group controlledRobotModel;
    static List<Group> robotModels = new java.util.ArrayList<Group>();
    static History history;
    static int speed;
    static double animationFrameInterval = 0.01;
    static GridPane controlPanelGridPane;
    static VBox simulationControlPanel;
    static Slider scenePicker;
    static Timeline timeline;
    static Timeline forwardSimulationTimeline;
    static Timeline backwardSimulationTimeline;

    public static void start(Room roomInput, Stage beforeStage) {
        // Set the class variables
        room = roomInput;
        history = new History();
        speed = 2;

        // Title of the window
        Stage presenterStage = new Stage();
        presenterStage.setTitle("Room Creator");

        // Calculate window size
        int width = room.cols();
        int height = room.rows();
        int controlPanelWidth = 500;
        int ControlPanelHeight = 150;
        int sceneWidth = Math.max(scale * width, controlPanelWidth);
        int sceneHeight = scale * height + ControlPanelHeight;
        
        // Layout of the control panel
        controlPanelGridPane = new GridPane();
        controlPanelGridPane.setAlignment(Pos.CENTER);
        controlPanelGridPane.setPrefSize(sceneWidth, ControlPanelHeight);
        controlPanelGridPane.setHgap(10);

        // Button for turning left
        Button leftButton = new Button("<-");
        

        // Button for moving forward
        Button forwardButton = new Button("FORWARD");
        forwardButton.setPrefWidth(100);

        // Button for turning right
        Button rightButton = new Button("->");
        

        // Add buttons to the control panel
        controlPanelGridPane.add(leftButton, 0, 0);
        controlPanelGridPane.add(forwardButton, 1, 0);
        controlPanelGridPane.add(rightButton, 2, 0);


        // Create simulationControlPanel and hide it so it can be displayed when Live simulation is stopped
        GridPane simulationPanelGridPane = new GridPane();
        simulationPanelGridPane.setAlignment(Pos.CENTER);
        simulationPanelGridPane.setPrefSize(sceneWidth, ControlPanelHeight);
        simulationPanelGridPane.setHgap(10);

        // Slider for picking the scene from history
        scenePicker = new Slider(1, history.size(), history.size());
        scenePicker.setShowTickMarks(true);
        scenePicker.setOnMousePressed(e -> {
            forwardSimulationTimeline.stop();
            backwardSimulationTimeline.stop();
        });

        // Timelines for playing the simulation
        forwardSimulationTimeline = simulationTimeline(1);
        forwardSimulationTimeline.setCycleCount(Timeline.INDEFINITE);
        backwardSimulationTimeline = simulationTimeline(-1);
        backwardSimulationTimeline.setCycleCount(Timeline.INDEFINITE);

        // Button for playing backward
        Button backwardSimulationButton = new Button("<-");
        backwardSimulationButton.setOnAction(e -> {
            backwardSimulationTimeline.play();
            forwardSimulationTimeline.stop();
        });

        // Button for pausing the simulation
        Button pauseSimulationButton = new Button("Pause");
        pauseSimulationButton.setOnAction(e -> {
            forwardSimulationTimeline.stop();
            backwardSimulationTimeline.stop();
        });

        // Button for playing forward
        Button forwardSimulationButton = new Button("->");
        forwardSimulationButton.setOnAction(e -> {
            forwardSimulationTimeline.play();
            backwardSimulationTimeline.stop();
        });

        // Add slider to the simulation control panel
        simulationPanelGridPane.add(backwardSimulationButton, 0, 0);
        simulationPanelGridPane.add(pauseSimulationButton, 1, 0);
        simulationPanelGridPane.add(forwardSimulationButton, 2, 0);

        simulationControlPanel = new VBox(scenePicker,  simulationPanelGridPane);
        simulationControlPanel.setAlignment(Pos.CENTER);
        simulationControlPanel.setVisible(false);

        // Layout of the center
        StackPane centerPane = new StackPane();
        centerPane.getChildren().addAll(controlPanelGridPane, simulationControlPanel);
        centerPane.setPadding(new Insets(10));

        // Button for going back
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(cancelButtonHandler(presenterStage, beforeStage));

        // Button for ending the simulation
        Button stopResumeButton = new Button("Stop simulation");
        stopResumeButton.setOnAction(stopResumeButtonHandler(stopResumeButton));
        
        // Layout of the navigation buttons
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox buttonBox = new HBox(cancelButton, spacer, stopResumeButton);
        buttonBox.setPadding(new Insets(10));
        
        // Layout of the room
        Group roomGroup = new Group();
        int roomOffsetX = (sceneWidth - scale * width) / 2;

        // Draw floor
        Rectangle floor = new Rectangle(roomOffsetX, 0, scale * width, scale * height);
        floor.setFill(javafx.scene.paint.Color.GRAY);

        // Add floor to the scene
        roomGroup.getChildren().add(floor);

        scenePicker.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                int oldRounded = (int) Math.round(old_val.doubleValue());
                int newRounded = (int) Math.round(new_val.doubleValue());
                // if the text on the stopResumeButton is "Stop simulation" then the simulation is running
                // So just skip the restore
                if (Objects.equals("Stop simulation", stopResumeButton.getText())) {
                    return;
                }


                // Based on the value of the slider, restore the scene to the selected state
                if (newRounded > oldRounded) {
                    for (int i = oldRounded; i < newRounded; i++) {
                        history.redo();
                    }
                } else {
                    for (int i = oldRounded; i > newRounded; i--) {
                        history.undo();
                    }
                }
            }
        });

        // Draw obstacles and robots
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
                Position pos = new Position(y, x);
                if (room.robotAt(pos)) {
                    // Get the robot from room
                    AbstractRobot robot = room.getRobotFromPosition(pos);

                    // Draw robot
                    Group robotModel = EnvCreator.drawRobotModel(scale, robot instanceof ControlledRobot);
                    String robotId = pos.hashCode() + "";
                    robotModel.setId(robotId);
                    robotModel.setLayoutX(roomOffsetX+scale*x+scale/2);
                    robotModel.setLayoutY(scale*y+scale/2);
                    robotModel.setRotate(robot.getAngle());

                    // Update the robot position in the room
                    robot.setPixelPosition(robotModel.getLayoutX(), robotModel.getLayoutY());

                    // Add robot to the scene
                    roomGroup.getChildren().add(robotModel);

                    // insert robot into list
                    robotModels.add(robotModel);

                    // Check if the robot is controlled
                    if (robot instanceof ControlledRobot) {
                        // Set the controlled robot
                        controlledRobotModel = robotModel;

                        // left button init
                        AnimationTimer leftRotationTimer = rotateRobotAnimationTimer(controlledRobotModel, -1);
                        leftButton.setOnMousePressed(rotationButtonHandler(leftRotationTimer, true));
                        leftButton.setOnMouseReleased(rotationButtonHandler(leftRotationTimer, false));

                        // Forward button init
                        AnimationTimer forwardButtonTimer = forwardRobotAnimationTimer(controlledRobotModel, roomGroup, roomOffsetX, width, height);
                        forwardButton.setOnAction(forwardButtonHandler(forwardButton, forwardButtonTimer));
                        
                        // Right button init
                        AnimationTimer rightRotationTimer = rotateRobotAnimationTimer(controlledRobotModel, 1);
                        rightButton.setOnMousePressed(rotationButtonHandler(rightRotationTimer, true));
                        rightButton.setOnMouseReleased(rotationButtonHandler(rightRotationTimer, false));
                        continue;
                    }

                    // Check if the robot is autonomous
                    if (robot instanceof AutonomousRobot) {
                        // Rotation timer init
                        AnimationTimer rotationAutonomousTimer = rotateRobotAnimationTimer(robotModel, 1);

                        // Forward timer init
                        AnimationTimer forwardAutonomousTimer = forwardAutonomousRobotAnimationTimer(robotModel, roomGroup, roomOffsetX, width, height, rotationAutonomousTimer);

                        // Start the autonomous robot
                        forwardAutonomousTimer.start();
                        continue;
                    }
                }
            }
        }

        // Create a new Timeline
        timeline = new Timeline(new KeyFrame(Duration.seconds(animationFrameInterval), e -> {
            // get sys date
            Date date = new Date();
            // create new memento
            Memento memento = new Memento();
            // save the scene
            history.push(date, memento);
        }));

        // Set the cycle count for the Timeline
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Layout of the window
        BorderPane root = new BorderPane();
        root.setTop(roomGroup);
        root.setCenter(centerPane);
        root.setBottom(buttonBox);
        Scene scene = new Scene(root, sceneWidth, sceneHeight);

        // Show the window
        presenterStage.setScene(scene);
        presenterStage.show();
        timeline.play();
    }

    private static Timeline simulationTimeline(int direction) {
        return new Timeline(new KeyFrame(Duration.seconds(animationFrameInterval), e -> {
            int value = (int) scenePicker.getValue();
            scenePicker.setValue(value + direction);
            if (value + direction == 0 || value + direction == history.size()+1) {
                forwardSimulationTimeline.stop();
                backwardSimulationTimeline.stop();
            }
        }));
    }

    private static AnimationTimer rotateRobotAnimationTimer(Group robot, int direction) {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                // get robot from room by robotId
                AbstractRobot robotFromRoom = room.getRobotFromId(robot.getId());
                
                // rotate the robot in the room
                robotFromRoom.setAngle((int) robot.getRotate());

                // rotate the robot in the scene
                robot.setRotate(robot.getRotate() + speed*direction);
            }
        };
    }

    private static AnimationTimer forwardRobotAnimationTimer(Group robot, Group roomGroup, int roomOffsetX, int width, int height) {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                double angle = robot.getRotate();
                // Calculate new position
                double newX = robot.getLayoutX() + Math.cos(Math.toRadians(angle)) * speed;
                double newY = robot.getLayoutY() + Math.sin(Math.toRadians(angle)) * speed;

                // Check if new position is within floor boundaries
                boolean collisionDetected = !(newX - (double) scale / 2 + 6 >= roomOffsetX && newX + (double) scale / 2 - 6 <= roomOffsetX + scale * width && newY - (double) scale / 2 + 6 >= 0 && newY + (double) scale / 2 - 6 <= scale * height);

                // Check if new position is not colliding with obstacle
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        if (room.obstacleAt(y, x)) {
                            // Calculate the center of the obstacle
                            double obstacleCenterX = roomOffsetX + scale * x + (double) scale / 2;
                            double obstacleCenterY = scale * y + (double) scale / 2;

                            // Calculate the distance from the center of the circle to the closest point in the square
                            double dx = Math.max(Math.max(obstacleCenterX - (newX + (double) scale / 2), 0), (newX + (double) scale / 2) - (obstacleCenterX + scale));
                            double dy = Math.max(Math.max(obstacleCenterY - (newY + (double) scale / 2), 0), (newY + (double) scale / 2) - (obstacleCenterY + scale));

                            // Check if the distance is less than the radius of the circle
                            double robotRadius = (double) scale / 2 - 6; // Adjust this value to change the robot's hitbox size
                            if (dx * dx + dy * dy < robotRadius * robotRadius) {
                                collisionDetected = true;
                                break;
                            }
                        }
                    }
                    if (collisionDetected) {
                        break;
                    }
                }

                // Check if the robot is colliding with another robot
                // Get all robots in the by the group
                for (int i = 0; i < roomGroup.getChildren().size(); i++) {
                    if (roomGroup.getChildren().get(i) instanceof Group) {
                        Group robotGroup = (Group) roomGroup.getChildren().get(i);
                        // Exclude the current robot from the collision detection
                        if (!robotGroup.equals(robot)) {
                            double otherRobotX = robotGroup.getLayoutX();
                            double otherRobotY = robotGroup.getLayoutY();
                            double otherRobotRadius = (double) scale / 2 - 6; // Adjust this value to change the robot's hitbox size
                            double dxOther = Math.max(Math.max(otherRobotX - (newX + (double) scale / 2), 0), (newX + (double) scale / 2) - (otherRobotX + scale));
                            double dyOther = Math.max(Math.max(otherRobotY - (newY + (double) scale / 2), 0), (newY + (double) scale / 2) - (otherRobotY + scale));
                            if (dxOther * dxOther + dyOther * dyOther < otherRobotRadius * otherRobotRadius) {
                                collisionDetected = true;
                                break;
                            }
                        }
                    }
                }

                // If no collision detected, update position
                if (!collisionDetected) {
                    // get robot from room by robotId
                    AbstractRobot robotFromRoom = room.getRobotFromId(robot.getId());

                    // move the robot in the room
                    robotFromRoom.setPixelPosition(newX, newY);

                    // move the robot in the scene
                    robot.setLayoutX(newX);
                    robot.setLayoutY(newY);
                }
            }
        };
    }

    private static AnimationTimer forwardAutonomousRobotAnimationTimer(Group robot, Group roomGroup, int roomOffsetX, int width, int height, AnimationTimer rotationAutonomousTimer) {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                rotationAutonomousTimer.stop();
                double angle = robot.getRotate();
                // Calculate new position
                double newX = robot.getLayoutX() + Math.cos(Math.toRadians(angle)) * speed;
                double newY = robot.getLayoutY() + Math.sin(Math.toRadians(angle)) * speed;

                // Check if new position is within floor boundaries
                boolean collisionDetected = !(newX - (double) scale / 2 + 6 >= roomOffsetX && newX + (double) scale / 2 - 6 <= roomOffsetX + scale * width && newY - (double) scale / 2 + 6 >= 0 && newY + (double) scale / 2 - 6 <= scale * height);

                // Check if new position is not colliding with obstacle
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        if (room.obstacleAt(y, x)) {
                            // Calculate the center of the obstacle
                            double obstacleCenterX = roomOffsetX + scale * x + (double) scale / 2;
                            double obstacleCenterY = scale * y + (double) scale / 2;

                            // Calculate the distance from the center of the circle to the closest point in the square
                            double dx = Math.max(Math.max(obstacleCenterX - (newX + (double) scale / 2), 0), (newX + (double) scale / 2) - (obstacleCenterX + scale));
                            double dy = Math.max(Math.max(obstacleCenterY - (newY + (double) scale / 2), 0), (newY + (double) scale / 2) - (obstacleCenterY + scale));

                            // Check if the distance is less than the radius of the circle
                            double robotRadius = (double) scale / 2 - 6; // Adjust this value to change the robot's hitbox size
                            if (dx * dx + dy * dy < robotRadius * robotRadius) {
                                collisionDetected = true;
                                break;
                            }
                        }
                    }
                    if (collisionDetected) {
                        rotationAutonomousTimer.start();
                        break;
                    }
                }

                // Check if the robot is colliding with another robot
                // Get all robots in the by the group
                for (int i = 0; i < roomGroup.getChildren().size(); i++) {
                    if (roomGroup.getChildren().get(i) instanceof Group) {
                        Group robotGroup = (Group) roomGroup.getChildren().get(i);
                        // Exclude the current robot from the collision detection
                        if (!robotGroup.equals(robot)) {
                            double otherRobotX = robotGroup.getLayoutX();
                            double otherRobotY = robotGroup.getLayoutY();
                            double otherRobotRadius = (double) scale / 2 - 6; // Adjust this value to change the robot's hitbox size
                            double dxOther = Math.max(Math.max(otherRobotX - (newX + (double) scale / 2), 0), (newX + (double) scale / 2) - (otherRobotX + scale));
                            double dyOther = Math.max(Math.max(otherRobotY - (newY + (double) scale / 2), 0), (newY + (double) scale / 2) - (otherRobotY + scale));
                            if (dxOther * dxOther + dyOther * dyOther < otherRobotRadius * otherRobotRadius) {
                                collisionDetected = true;
                                rotationAutonomousTimer.start();
                                break;
                            }
                        }
                    }
                    if (collisionDetected) {
                        rotationAutonomousTimer.start();
                        break;
                    }
                }

                // If no collision detected, update position
                if (!collisionDetected) {
                    // get robot from room by robotId
                    AbstractRobot robotFromRoom = room.getRobotFromId(robot.getId());

                    // move the robot in the room
                    robotFromRoom.setPixelPosition(newX, newY);

                    // move the robot in the scene
                    robot.setLayoutX(newX);
                    robot.setLayoutY(newY);
                }
            }
        };
    }

    private static EventHandler<MouseEvent> rotationButtonHandler(AnimationTimer timer, boolean startStop) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (startStop){
                    timer.start();
                } else {
                    timer.stop();
                }
            }
        };
    }

    private static EventHandler<ActionEvent> forwardButtonHandler(Button button, AnimationTimer timer) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Objects.equals(button.getText(), "FORWARD")) {
                    button.setText("PAUSE");
                    timer.start();
                    // System log
                    System.out.println("INFO Controlled robot started moving forward");
                } else {
                    button.setText("FORWARD");
                    timer.stop();
                    // System log
                    System.out.println("INFO Controlled robot paused");
                }
            }
        };
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

    private static EventHandler<ActionEvent> stopResumeButtonHandler(Button button) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                forwardSimulationTimeline.stop();
                backwardSimulationTimeline.stop();
                if (Objects.equals(button.getText(), "Stop simulation")) {
                    button.setText("Resume simulation");
                    timeline.stop();
                    speed = 0;
                    // Close control panel for controlled robot
                    controlPanelGridPane.setVisible(false);
                    // Open control panel for simulation control
                    scenePicker.setMax(history.size());
                    scenePicker.setValue(history.size());
                    scenePicker.setPrefWidth(2 * history.size());
                    simulationControlPanel.setVisible(true);
                    // System log
                    System.out.println("INFO Simulation stopped");
                } else {
                    button.setText("Stop simulation");
                    history.removeAfter((int) scenePicker.getValue());
                    timeline.play();
                    speed = 2;
                    // Close control panel for simulation control
                    simulationControlPanel.setVisible(false);
                    // Open control panel for controlled robot
                    controlPanelGridPane.setVisible(true);
                    // System log
                    System.out.println("INFO Simulation resumed");
                }
            }
        };
    }

    public static SceneSnapshot backup() {
        return new SceneSnapshot(room.robots());
    }

    public static void restore(SceneSnapshot sceneSnapshot) {
        // for each robot in the snapshot
        for (AbstractRobot robot : sceneSnapshot.robots) {
            // change position to room
            for (AbstractRobot roomRobot : room.robots()) {
                if (roomRobot.getId().equals(robot.getId())) {
                    roomRobot.setPixelPosition(robot.getPixelPosition().getX(), robot.getPixelPosition().getY());
                    roomRobot.setAngle(robot.getAngle());
                }
            }
            
            // change position to robotModels
            for (Group robotModel : robotModels) {
                if (robotModel.getId().equals(robot.getId())) {
                    robotModel.setLayoutX(robot.getPixelPosition().getX());
                    robotModel.setLayoutY(robot.getPixelPosition().getY());
                    robotModel.setRotate(robot.getAngle());
                }
            }
        }
    }
}

