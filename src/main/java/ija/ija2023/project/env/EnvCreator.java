/** 
 * @file EnvCreator.java
 * @brief Class for the window for creating elements inside the room and placing them
 * @author Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.env;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import ija.ija2023.project.common.Position;
import ija.ija2023.project.common.robot.AbstractRobot;
import ija.ija2023.project.common.robot.RobotFactory;
import ija.ija2023.project.room.ControlledRobot;
import ija.ija2023.project.room.Room;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;

/**
 * Class for the window for creating elements inside the room and placing them
 *
 * @version 1.0
 * @since 2024-04-30
 *
 * @see Stage
 * @see Room
 * @see EnvPresenter
 */
public class EnvCreator {
    static String[] lastClickedButton = {"obstacle"};
    static boolean controlledRobotAdded = false;
    static int scale = 100;
    static Room room;

    /**
     * Function for starting the window
     * @param roomInput Room object
     * @param beforeStage Stage of the previous window
     */
    public static void start(Room roomInput, Stage beforeStage) {
        // Set the class variables
        lastClickedButton[0] = "obstacle";
        room = roomInput;

        // Title of the window
        Stage creatorStage = new Stage();
        creatorStage.setTitle("Room Creator");

        // Calculate window size
        int width = room.cols();
        int height = room.rows();
        int controlPanelWidth = 500;
        int controlPanelHeight = 150;
        int sceneWidth = Math.max(scale * width, controlPanelWidth);
        int sceneHeight = scale * height + controlPanelHeight;

        // Layout of the control panel
        GridPane controlPanelGridPane = new GridPane();
        controlPanelGridPane.setAlignment(Pos.CENTER);
        controlPanelGridPane.setPrefSize(sceneWidth, controlPanelHeight);
        controlPanelGridPane.setHgap(10);

        // Button for creating obstacles
        Button obstacleButton = new Button("Add obstacle");
        obstacleButton.setOnAction(e -> {
            lastClickedButton[0] = "obstacle";
        });

        // Button for creating autonomous robots
        Button automatedRobotButton = new Button("Add automated robot");
        automatedRobotButton.setOnAction(e -> {
            lastClickedButton[0] = "automatedRobot";
        });

        // Button for creating controlled robots
        Button controlledRobotButton = new Button("Add controlled robot");
        controlledRobotButton.setOnAction(e -> {
            lastClickedButton[0] = "controlledRobot";
        });

        // Add buttons to the control panel
        controlPanelGridPane.add(obstacleButton, 0, 0);
        controlPanelGridPane.add(automatedRobotButton, 1, 0);
        controlPanelGridPane.add(controlledRobotButton, 2, 0);

        // Button for going back
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(cancelButtonHandler(creatorStage, beforeStage));

        // Button for exporting the environment
        Button exportButton = new Button("Export");
        exportButton.setOnAction(exportButtonHandler(creatorStage));

        // Button for creating the environment
        Button createButton = new Button("Create");
        createButton.setOnAction(createButtonHandler(creatorStage));
        
        // Layout of the navigation buttons
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox buttonBox = new HBox(cancelButton, spacer, exportButton, createButton);
        buttonBox.setPadding(new Insets(10));

        // Layout of the room
        GridPane roomGridPane = new GridPane();
        roomGridPane.setAlignment(Pos.CENTER);

        // Draw the room from the room object that was passed
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Draw floor
                Rectangle floor = new Rectangle(scale, scale);
                floor.setFill(javafx.scene.paint.Color.GRAY);
                floor.setOnMouseClicked(createObjectHandler(floor, roomGridPane));

                // Add floor to the scene
                roomGridPane.add(floor, x, y);

                // Check if there is an obstacle
                if (room.obstacleAt(y, x)) {
                    // Draw obstacle
                    Rectangle obstacle = new Rectangle(scale, scale);
                    obstacle.setFill(javafx.scene.paint.Color.BLACK);
                    obstacle.setOnMouseClicked(removeObstacleHandler(obstacle, roomGridPane));

                    // Add obstacle to the scene
                    roomGridPane.add(obstacle, x, y);
                    continue;
                }

                // Check if there is a robot
                if (room.robotAt(new Position(y, x))) {
                    // Check if the robot is controlled robot
                    AbstractRobot robot = room.getRobotFromPosition(new Position(y, x));
                    if (robot instanceof ControlledRobot) {
                        controlledRobotAdded = true;
                    }

                    // Draw robot
                    Group robotModel = drawRobotModel(scale, robot instanceof ControlledRobot);
                    robotModel.setOnMouseClicked(clickRobotHandler(robotModel, roomGridPane));
                    robotModel.setRotate(robot.getAngle());

                    // Add robot to the scene
                    roomGridPane.add(robotModel, x, y);
                    GridPane.setHalignment(robotModel, HPos.CENTER);
                    GridPane.setValignment(robotModel, VPos.CENTER);
                    continue;
                }
            }
        }

        // Layout of the window
        BorderPane root = new BorderPane();
        root.setTop(roomGridPane);
        root.setCenter(controlPanelGridPane);
        root.setBottom(buttonBox);
        Scene scene = new Scene(root, sceneWidth, sceneHeight);

        // Show the window
        creatorStage.setScene(scene);
        creatorStage.show();
    }

    /**
     * Function for creating the object handler
     * @param floor Rectangle of the floor
     * @param roomGridPane GridPane of the room
     * @return EventHandler for creating object
     */
    private static EventHandler<MouseEvent> createObjectHandler(Rectangle floor, GridPane roomGridPane) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Check if the left mouse button was clicked
                if (event.getButton() != MouseButton.PRIMARY) return;

                // Get row and column of the clicked floor
                int row = GridPane.getRowIndex(floor);
                int col = GridPane.getColumnIndex(floor);

                // Check if there is an object in the room
                if (room.obstacleAt(row, col) || room.robotAt(new Position(row, col))) {
                    System.out.println("WARNING Position is already occupied at " + col + " " + row);
                    return;
                }

                // Add desired object to the room
                switch(lastClickedButton[0]) {
                    case "obstacle":
                        createObstacleAt(row, col);
                        break;
                    case "automatedRobot":
                        createRobotAt(row, col, false);
                        break;
                    case "controlledRobot":
                        createRobotAt(row, col, true);
                        break;
                }               
            }

            private void createObstacleAt(int row, int col){
                // Add obstacle to the room
                if (!room.createObstacleAt(row, col)) {
                    System.out.println("ERROR Failed to create obstacle at " + col + " " + row);
                    return;
                }

                // Draw obstacle
                Rectangle obstacle = new Rectangle(scale, scale);
                obstacle.setFill(javafx.scene.paint.Color.BLACK);
                obstacle.setOnMouseClicked(removeObstacleHandler(obstacle, roomGridPane));

                // Add obstacle to the scene
                roomGridPane.add(obstacle, col, row);

                // System log
                System.out.println("INFO Creating obstacle at " + col + " " + row);
            }

            private void createRobotAt(int row, int col, Boolean controlled){
                // robot type
                String robotType = "Autonomous robot";

                // Check if controlled robot already exists
                if (controlled) {
                    
                    if (controlledRobotAdded) return;
                    controlledRobotAdded = true;
                    robotType = "Controlled robot";
                }
                
                // Create new robot
                RobotFactory.create(room, new Position(row, col), controlled);

                // Draw robot
                Group robotModel = drawRobotModel(scale, controlled);
                robotModel.setOnMouseClicked(clickRobotHandler(robotModel, roomGridPane));

                // Add robot to the scene
                roomGridPane.add(robotModel, col, row);
                GridPane.setHalignment(robotModel, HPos.CENTER);
                GridPane.setValignment(robotModel, VPos.CENTER);

                // System log
                System.out.println("INFO " + robotType + " created at " + col + " " + row);
            }
        };
    }

    /**
     * Function for removing the obstacle handler
     * @param Obstacle Rectangle of the obstacle
     * @param roomGridPane GridPane of the room
     * @return EventHandler for removing obstacle
     */
    private static EventHandler<MouseEvent> removeObstacleHandler(Rectangle Obstacle, GridPane roomGridPane) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Check if the right mouse button was clicked
                if (event.getButton() != MouseButton.SECONDARY) return;

                // Get row and column of the clicked obstacle
                int row = GridPane.getRowIndex(Obstacle);
                int col = GridPane.getColumnIndex(Obstacle);

                // Check if there is an obstacle in the room
                if (!room.obstacleAt(row, col)){
                    System.out.println("Exception missing obstacle at " + col + " " + row);
                    return;
                }

                // Remove obstacle from the room
                room.removeObstacleFrom(row, col);

                // Remove obstacle from the scene
                roomGridPane.getChildren().remove(Obstacle);

                // System log
                System.out.println("INFO Obstacle removed from " + col + " " + row);
            }
        };
    }

    /**
     * Function for clicking the robot handler
     * @param Robot Group of the robot
     * @param roomGridPane GridPane of the room
     * @return EventHandler for clicking the robot
     */
    private static EventHandler<MouseEvent> clickRobotHandler(Group Robot, GridPane roomGridPane) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Get row and column of the clicked robot
                int row = GridPane.getRowIndex(Robot);
                int col = GridPane.getColumnIndex(Robot);

                // Get robot from the room
                AbstractRobot robot = room.getRobotFromPosition(new Position(row, col));

                if (event.getButton() == MouseButton.PRIMARY) {
                    // Rotate robot in the room
                    robot.turn();

                    // rotate robotModel in the scene
                    Robot.setRotate(Robot.getRotate() + 45);

                    // System log
                    System.out.println("INFO Robot rotated 45 degrees clockwise at " + col + " " + row);
                }
                if (event.getButton() == MouseButton.SECONDARY) {
                    // robot type
                    String robotType = "Autonomous robot";

                    // Check if the robot is controlled robot
                    if (robot instanceof ControlledRobot) {
                        controlledRobotAdded = false;
                        robotType = "Controlled robot";
                    }
                    
                    // Remove robot from the room
                    room.removeRobotFrom(row, col);

                    // Remove obstacle from the scene
                    roomGridPane.getChildren().remove(Robot);
                    
                    // System log
                    System.out.println("INFO " + robotType + " removed from " + col + " " + row);
                }
            }
        };
    }

    /**
     * Function for drawing the robot model in the scene
     *
     * @param scale Scale of the robot model
     *              (size of the robot model is scale x scale)
     *
     * @return Group of the robot model
     */
    public static Group drawRobotModel(int scale) {
        return drawRobotModel(scale, false);
    }

    /**
     * Function for drawing the robot model in the scene
     *
     * @param scale Scale of the robot model
     *              (size of the robot model is scale x scale)
     * @param controlledRobot Boolean value if the robot is controlled
     *                        (true if the robot is controlled, false if the robot is autonomous)
     *
     * @return Group of the robot model
     */
    public static Group drawRobotModel(int scale, Boolean controlledRobot) {
        // Create robot model
        Group robotModel = new Group();

        // Draw robot head
        Circle head = new Circle(scale/2-6);
        if (controlledRobot) head.setFill(javafx.scene.paint.Color.BLUE);
        else head.setFill(javafx.scene.paint.Color.RED);

        // Draw robot eye
        Circle eye = new Circle(scale/10);
        eye.setFill(javafx.scene.paint.Color.BLACK);
        eye.setTranslateX(scale/3-2);

        // Add all parts to the robot model
        robotModel.getChildren().addAll(head, eye);
        return robotModel;
    }

    /**
     * Function for handling the cancel button
     * @param primaryStage Stage of the current window
     * @param beforeStage Stage of the previous window
     * @return EventHandler for the cancel button
     */
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

    /**
     * Function for handling the export button
     * @param primaryStage Stage of the current window
     * @return EventHandler for the export button
     */
    private static EventHandler<ActionEvent> exportButtonHandler(Stage primaryStage) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Get the room as a string
                String export = room.toString();

                // Export the room to a file
                
                FileChooser fileChooser = new FileChooser();

                // Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
                fileChooser.getExtensionFilters().add(extFilter);

                // Set default file name
                LocalDateTime date = LocalDateTime.now();
                String exportFileName = "room_D" + date.toString().replace(" ", "_").replace(":", "-").replace("T", "_T").replace(".", "-") + ".csv";
                fileChooser.setInitialFileName(exportFileName);

                // Show save file dialog
                File file = fileChooser.showSaveDialog(primaryStage);

                if (file != null) {
                    // Write to the file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        writer.write(export);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                // System log
                System.out.println("INFO Room exported");
            }
        };
    }

    /**
     * Function for handling the create button
     * @param primaryStage Stage of the current window
     * @return EventHandler for the create button
     */
    private static EventHandler<ActionEvent> createButtonHandler(Stage primaryStage) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Move to the next window
                primaryStage.close();
                EnvPresenter.start(room, primaryStage);
            }
        };
    }
}
