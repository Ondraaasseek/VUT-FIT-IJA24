package ija.ija2023.project;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class InputInterface extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Robot Simulation");
        TextField textField1 = new TextField();
        TextField textField2 = new TextField();
        TextField textField3 = new TextField();
        Button button1 = new Button("Load file");
        Button button2 = new Button("Start simulation");

        VBox vbox = new VBox(textField1, textField2, textField3, button1, button2);
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        // This is the main method of the project that runs the application and initializes the main window.
        // It also handles file loading and storing.

    }
}
