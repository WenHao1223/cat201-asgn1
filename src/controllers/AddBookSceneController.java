package controllers;

import java.io.File;
import java.io.IOException;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import models.Library;

public class AddBookSceneController {
    private Library library;

    private File lFile;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextField tfAuthor;

    @FXML
    private TextField tfISBN;

    @FXML
    private Label labelThumbnail;

    public void setLibrary(Library library) {
        this.library = library;
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        // Show a confirmation alert box
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cancel Adding Book");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to cancel adding book?");
        Optional<ButtonType> action = alert.showAndWait();
        
        if (action.isPresent() && action.get() == ButtonType.OK) {
            // User chose OK, proceed with redirection
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/HomePageScene.fxml"));
                Parent root = fxmlLoader.load();
                HomePageSceneController homeController = fxmlLoader.getController();
                homeController.setLibrary(library);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // User chose CANCEL or closed the dialog
            return;
        }
    }

    @FXML
    private void handleUploadThumbnailAction (ActionEvent event) {
        // by clicking, file picker will open
        // should be able to store the location to image file at lFile
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) { 
            lFile = file;
            System.out.println("Selected file: " + lFile.getAbsolutePath());

            labelThumbnail.setText(lFile.getName());
            labelThumbnail.setDisable(false);
        } else {
            System.out.println("No file selected");
            labelThumbnail.setText("<Empty>");
            labelThumbnail.setDisable(true);
        }
    }
}
