
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        int page = 1;
        /*
         * 1 - HomePageScene
         * 2 - AddBookScene
         * 3 - ViewBookScene
         * 4 - BookCard
         * 5 - BorrowDialogBox
         * 6 - BorrowerDetailsBox
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
                case 3:
                    root = FXMLLoader.load(getClass().getResource("views/ViewBookScene.fxml"));
                    break;
                case 4:
                    root = FXMLLoader.load(getClass().getResource("components/BookCard.fxml"));
                    break;
                case 5:
                    root = FXMLLoader.load(getClass().getResource("components/BorrowDialogBox.fxml"));
                    break;
                case 6:
                    root = FXMLLoader.load(getClass().getResource("components/BorrowerDetailsBox.fxml"));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid page number: " + page);
            }
            System.out.println("Loaded scene for page: " + page);
            Scene scene = new Scene(root);
            // primaryStage.setTitle("ABC Library");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // Disable maximize button
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
                case 3:
                    System.err.println("Failed to load ViewBookScene.fxml");
                    break;
                case 4:
                    System.err.println("Failed to load BookCard.fxml");
                    break;
                case 5:
                    System.err.println("Failed to load BorrowDialogBox.fxml");
                    break;
                case 6:
                    System.err.println("Failed to load BorrowerDetailsBox.fxml");
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