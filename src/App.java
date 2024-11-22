
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        int page = 2;
        /*
         * 1 - HomePageScene
         * 2 - AddBookScene
         */
        try {
            
            Parent root;
            switch (page) {
                case 1:
                    root = FXMLLoader.load(getClass().getResource("views/HomePageScene.fxml"));
                    break;
                case 2:
                    root = FXMLLoader.load(getClass().getResource("views/AddBookScene.fxml"));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid page number: " + page);
            }

            Scene scene = new Scene(root);
            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            switch (page) {
                case 1:
                    System.err.println("Failed to load HomePageScene.fxml");
                    break;
                case 2:
                    System.err.println("Failed to load AddBookScene.fxml");
                    break;
                default:
                    System.err.println("Failed to load scene for page: " + page);
                    break;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}