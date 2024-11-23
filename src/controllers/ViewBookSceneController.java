package controllers;

import models.Library;
import models.Book;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewBookSceneController {
  private Library library;

  @FXML
  private Label bookTitleLabel;

  @FXML
  private Label authorNameLabel;

  @FXML
  private Label isbnLabel;

  @FXML
  private Label isAvailableLabel;

  @FXML
  private ImageView bookImageView;

  public void setLibrary(Library library) {
    this.library = library;
  }

  public void setBookDetails(String isbn) {
    System.out.println(isbn);
    Book book = library.getBookByISBN(isbn);
    if (book != null) {
      bookTitleLabel.setText(book.getTitle());
      authorNameLabel.setText(book.getAuthor());
      isbnLabel.setText(book.getISBN());
      isAvailableLabel.setText(book.getAvailability() ? "Available" : "Borrowed by " + book.getBorrowerName());
    } else {
      System.err.println("Book is null in setBookDetails");
    }
    setImage(isbn);
  }

  private void setImage(String isbn) {
    String imagePath = "/data/thumbnail/" + isbn + ".jpg";
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

  @FXML
  private void handleBackButtonAction(ActionEvent event) {
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
  }
}
