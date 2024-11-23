
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// class
import models.Book;
import models.Library;

import java.util.List;

import controllers.AddBookSceneController;
import controllers.HomePageSceneController;
import controllers.ViewBookSceneController;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;

public class App extends Application {
    private static Library library = new Library();
    private static String filePath = "src/data/data.csv";

    // initialize
    public App() {
        System.out.println("Program is starting...");
        loadDataFromFile(library, filePath);
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Program is running...");

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
            FXMLLoader fxmlLoader;
            switch (page) {
                case 1:
                    fxmlLoader = new FXMLLoader(getClass().getResource("/views/HomePageScene.fxml"));
                    root = fxmlLoader.load();
                    HomePageSceneController homeController = fxmlLoader.getController();
                    homeController.setLibrary(library);
                    break;
                case 2:
                    fxmlLoader = new FXMLLoader(getClass().getResource("/views/AddBookScene.fxml"));
                    root = fxmlLoader.load();
                    AddBookSceneController addBookController = fxmlLoader.getController();
                    addBookController.setLibrary(library);
                    break;
                case 3:
                    fxmlLoader = new FXMLLoader(getClass().getResource("/views/ViewBookScene.fxml"));
                    root = fxmlLoader.load();
                    ViewBookSceneController viewBookController = fxmlLoader.getController();
                    viewBookController.setLibrary(library);
                    break;
                case 4:
                    fxmlLoader = new FXMLLoader(getClass().getResource("/components/BookCard.fxml"));
                    root = fxmlLoader.load();
                    break;
                case 5:
                    fxmlLoader = new FXMLLoader(getClass().getResource("/components/BorrowDialogBox.fxml"));
                    root = fxmlLoader.load();
                    break;
                case 6:
                    fxmlLoader = new FXMLLoader(getClass().getResource("/components/BorrowerDetailsBox.fxml"));
                    root = fxmlLoader.load();
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

    public void stop() {
        System.out.println("Program is terminated");
        String filePath = "src/data/data.csv";

        // Write data to CSV file
        writeDataToFile(library, filePath);
    }

    private void loadDataFromFile(Library library, String filepath) {
        List<String[]> tempData = new ArrayList<>();

        try {
            FileReader file = new FileReader(filepath);
            System.out.println("Reading data from " + filepath + "...\n");

            int c;
            String line = "";

            // skip the first line
            while ((c = file.read()) != -1) {
                if (c == '\n') {
                    break;
                }
            }

            while ((c = file.read()) != -1) {
                if (c == '\n') {
                    String[] data = line.split(",");

                    if (data.length == 7) {
                        tempData.add(data);
                    } else {
                        System.err.println("Invalid data format: " + line);
                    }
                    line = "";
                } else {
                    line += (char) c;
                }
            }

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create Book objects for first time program run
        for (String[] data : tempData) {
            library.addExistingBook(
                    data[0],
                    data[1],
                    data[2],
                    Boolean.parseBoolean(data[3]),
                    data[4],
                    data[5],
                    data[6] == null || data[6].trim().isEmpty() ? 0 : Long.parseLong(data[6].trim()));
        }
    }

    private void writeDataToFile(Library library, String filepath) {
        try {
            FileWriter file = new FileWriter(filepath);
            System.out.println("Writing data to " + filepath + "...\n");

            file.write("title,author,ISBN,availability,borrowerName,borrowerPhone,borrowerID\n");

            for (Book book : library.getBooks()) {
                List<String> bookDetails = book.getBookDetailList();
                file.write(String.join(",", bookDetails) + "\n");
            }

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}