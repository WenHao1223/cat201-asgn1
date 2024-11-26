package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Library;

import java.io.IOException;

public class BookCardController {
  @FXML
  private AnchorPane rootPane;

  @FXML
  private Label bookTitleLabel;

  @FXML
  private Label authorNameLabel;

  @FXML
  private Label isbnLabel;

  @FXML
  private Label availableLabel;

  @FXML
  private ImageView bookImageView;

  private Library library;

  public void setLibrary(Library library) {
    this.library = library;
  }

  @FXML
  public void initialize() {
    rootPane.setCursor(Cursor.HAND);

    rootPane.setOnMouseClicked(event -> {
      try {
        String isbn = isbnLabel.getText();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ViewBookScene.fxml"));
        Parent root = fxmlLoader.load();
        ViewBookSceneController viewBookController = fxmlLoader.getController();
        viewBookController.setLibrary(library);
        viewBookController.setBookDetails(isbn);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  public void setBookDetails(String title, String author, String isbn, boolean isAvailable) {
    if (bookTitleLabel == null || authorNameLabel == null || isbnLabel == null || availableLabel == null
        || bookImageView == null) {
      System.err.println("Labels are not initialized");
      return;
    }
    
    bookTitleLabel.setText(title);
    authorNameLabel.setText(author);
    isbnLabel.setText(isbn);
    availableLabel.setText(isAvailable ? "Available" : "Borrowed");
    availableLabel.getStyleClass().add(isAvailable ? "green" : "red");
    setImage(isbn);
  }

  private String getImagePath(String isbn) {
    String[] extensions = { ".jpg", ".png", ".gif" };
    for (String ext : extensions) {
      String imagePath = "/data/thumbnail/" + isbn + ext;
      if (getClass().getResource(imagePath) != null) {
        return imagePath;
      }
    }
    // Return a default image path if none of the files exist
    return "/data/thumbnail/default.jpg";
  }

  private void setImage(String isbn) {
    String imagePath = getImagePath(isbn);
    System.out.println("Image path: " + imagePath);
    Image image;
    try {
      if (getClass().getResource(imagePath) != null) {
        image = new Image(getClass().getResourceAsStream(imagePath));
      } else {
        image = new Image(getClass().getResourceAsStream("/data/thumbnail/default.jpg"));
        System.out.println("Image not found: " + imagePath);
      }
      bookImageView.setImage(image);
    } catch (Exception e) {
      System.err.println("Error loading image: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
