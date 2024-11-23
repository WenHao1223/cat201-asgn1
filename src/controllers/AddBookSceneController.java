package controllers;

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
import javafx.stage.Stage;
import models.Library;

public class AddBookSceneController {
    private Library library;

    public void setLibrary(Library library) {
        this.library = library;
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        // Show a confirmation alert box
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cancel Adding Book");
        alert.setHeaderText("Confirmation Required");
        alert.setContentText("Are you sure you want to cancel adding book?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User chose OK, proceed with redirection
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/HomePageScene.fxml"));
                Parent root = fxmlLoader.load();
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
}
