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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;

public class EnvCreator {
    static String lastClickedButton[] = {"obstacle"};
    static boolean controlledRobotAdded = false;

    public static void start(Room room, Stage beforeStage) {

        // Create the environment
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        int width = room.cols();
        int height = room.rows();
        lastClickedButton[0] = "obstacle";
        Stage primaryStage = new Stage();
        BorderPane root = new BorderPane();

        primaryStage.setTitle("Room Creator");

        int controlPanelWidth = 500;
        int ControlPanelHeight = 150;


        int scale = 100;
        int sceneWidth = Math.max(scale * width, controlPanelWidth);
        int sceneHeight = scale * height + ControlPanelHeight;

        int controlPanelOffset = (sceneWidth - controlPanelWidth) / 2;

        // Create control panel
        Rectangle controlPanel = new Rectangle(controlPanelOffset, height * scale, controlPanelWidth, ControlPanelHeight);
        controlPanel.setFill(javafx.scene.paint.Color.LIGHTGRAY);

        // Create buttons
        GridPane createButtonsGridPane = new GridPane();
        createButtonsGridPane.setPrefSize(sceneWidth, ControlPanelHeight);
        createButtonsGridPane.setHgap(10);
        Button obstacleButton = new Button("Add obstacle");
        obstacleButton.setOnAction(e -> {
            lastClickedButton[0] = "obstacle";
        });
        Button automatedRobotButton = new Button("Add automated robot");
        automatedRobotButton.setOnAction(e -> {
            lastClickedButton[0] = "automatedRobot";
        });
        Button controlledRobotButton = new Button("Add controlled robot");
        controlledRobotButton.setOnAction(e -> {
            lastClickedButton[0] = "controlledRobot";
        });

        createButtonsGridPane.add(obstacleButton, 0, 0);
        createButtonsGridPane.add(automatedRobotButton, 1, 0);
        createButtonsGridPane.add(controlledRobotButton, 2, 0);

        // align content to center of a pane
        createButtonsGridPane.setAlignment(Pos.CENTER);

        root.setCenter(createButtonsGridPane);

        Button createButton = new Button("Create");
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e2 -> {
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

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                // Draw floor
                Rectangle floor = new Rectangle(scale, scale);
                floor.setFill(javafx.scene.paint.Color.GRAY);
                floor.setOnMouseClicked(createObjectHandler(floor, roomGridPane, room, scale));
                // Add floor to the scene
                roomGridPane.add(floor, i, j);

                // Check if there is an obstacle
                if (room.obstacleAt(i, j)) {
                    // Draw obstacle
                    Rectangle obstacle = new Rectangle(scale, scale);
                    obstacle.setFill(javafx.scene.paint.Color.BLACK);
                    obstacle.setOnMouseClicked(removeObstacleHandler(obstacle, roomGridPane, room));
                    // Add obstacle to the scene
                    roomGridPane.add(obstacle, i, j);
                    continue;
                }

                // Check if there is a robot
                if (room.robotAt(new Position(i, j))) {
                    AbstractRobot robot = room.getRobotFromPosition(new Position(i, j));
                    if (robot instanceof ControlledRobot) {
                        controlledRobotAdded = true;
                        // Draw robot
                        Circle robotModel = new Circle(scale/2);
                        robotModel.setFill(javafx.scene.paint.Color.BLUE);
                        robotModel.setOnMouseClicked(clickRobotHandler(robotModel, roomGridPane, room));
                        // Add robot to the scene
                        roomGridPane.add(robotModel, i, j);
                        continue;
                    }
                    if (robot instanceof AutonomousRobot) {
                        // Draw robot
                        Circle robotModel = new Circle(scale/2);
                        robotModel.setFill(javafx.scene.paint.Color.RED);
                        robotModel.setOnMouseClicked(clickRobotHandler(robotModel, roomGridPane, room));
                        // Add robot to the scene
                        roomGridPane.add(robotModel, i, j);
                        continue;
                    }
                }
            }
        }

        Scene scene = new Scene(root, sceneWidth, sceneHeight);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private static EventHandler<MouseEvent> createObjectHandler(Rectangle floor, GridPane roomGridPane, Room room, int scale) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() != MouseButton.PRIMARY) return;
                // Get row and column of the clicked floor
                int row = GridPane.getRowIndex(floor);
                int col = GridPane.getColumnIndex(floor);
                // Check which button was clicked
                switch(lastClickedButton[0]) {
                    case "obstacle":
                        createObstacleAt(row, col);
                        break;
                    case "automatedRobot":
                        createAutomatedRobotAt(row, col);
                        break;
                    case "controlledRobot":
                        createControlledRobotAt(row, col);
                        break;
                }
                
            }

            private void createObstacleAt(int row, int col){
                // Add obstacle to the room
                room.createObstacleAt(row, col);
                // Draw obstacle
                Rectangle obstacle = new Rectangle(scale, scale);
                obstacle.setFill(javafx.scene.paint.Color.BLACK);
                obstacle.setOnMouseClicked(removeObstacleHandler(obstacle, roomGridPane, room));
                // Add obstacle to the scene
                roomGridPane.add(obstacle, col, row);
            }

            private void createAutomatedRobotAt(int row, int col){
                // Create new robot
                RobotFactory.create(room, new Position(row, col));
                // Draw robot
                Circle robotModel = new Circle(scale/2);
                robotModel.setFill(javafx.scene.paint.Color.RED);
                robotModel.setOnMouseClicked(clickRobotHandler(robotModel, roomGridPane, room));
                // Add robot to the scene
                roomGridPane.add(robotModel, col, row);
            }

            private void createControlledRobotAt(int row, int col){
                if (controlledRobotAdded) return;
                controlledRobotAdded = true;
                // Create new robot
                RobotFactory.create(room, new Position(row, col), true);
                // Draw robot
                Circle robotModel = new Circle(scale/2);
                robotModel.setFill(javafx.scene.paint.Color.BLUE);
                robotModel.setOnMouseClicked(clickRobotHandler(robotModel, roomGridPane, room));
                // Add robot to the scene
                roomGridPane.add(robotModel, col, row);
            }
        };
    }

    private static EventHandler<MouseEvent> removeObstacleHandler(Rectangle Obstacle, GridPane roomGridPane, Room room) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() != MouseButton.SECONDARY) return;
                // enable click event on floor
                int row = GridPane.getRowIndex(Obstacle);
                int col = GridPane.getColumnIndex(Obstacle);
                // Remove obstacle from the room
                room.removeObstacleFrom(row, col);
                // Remove obstacle from the scene
                roomGridPane.getChildren().remove(Obstacle);
            }
        };
    }

    private static EventHandler<MouseEvent> clickRobotHandler(Circle Robot, GridPane roomGridPane, Room room) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    // rotate robot TODO
                }
                if (event.getButton() == MouseButton.SECONDARY) {
                    int row = GridPane.getRowIndex(Robot);
                    int col = GridPane.getColumnIndex(Robot);
                    // Remove robot from the room
                    room.removeRobotFrom(row, col);
                    // Remove obstacle from the scene
                    roomGridPane.getChildren().remove(Robot);
                    // Set controlledRobotAdded to false if controlled robot was removed
                    if (Robot.getFill() == javafx.scene.paint.Color.BLUE) {
                        controlledRobotAdded = false;
                    }
                }
            }
        };
    }
}
