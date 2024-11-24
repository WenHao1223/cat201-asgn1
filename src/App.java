import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;

import models.Book;
import models.Library;

import controllers.HomePageSceneController;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        try {
            Parent root;
            FXMLLoader fxmlLoader;
            fxmlLoader = new FXMLLoader(getClass().getResource("/views/HomePageScene.fxml"));
            root = fxmlLoader.load();
            HomePageSceneController homeController = fxmlLoader.getController();
            homeController.setLibrary(library);
            Scene scene = new Scene(root);

            // primaryStage.setTitle("TLT Library");
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // Disable maximize button

            // Set close request handler
            primaryStage.setOnCloseRequest(event -> {
                event.consume(); // Consume the event to prevent the window from closing immediately
                handleWindowCloseRequest(primaryStage);
            });

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load screen");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    private void handleWindowCloseRequest(Stage stage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit Program Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to quit the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage.close();
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