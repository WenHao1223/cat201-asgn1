package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    Button button;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Parent root;
        // root = FXMLLoader.load(getClass().getResource("application/MainScene.fxml"));

        // main stage
        primaryStage.setTitle("Title of the Window");

        button = new Button();  // create button
        button.setText("Click me");

        StackPane layout = new StackPane(); // position button in the middle
        layout.getChildren().add(button);   // organise position of button

        // create scene
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}