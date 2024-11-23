package controllers;

import models.Library;
import models.Book;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ViewBookSceneController {
  private Library library;
  private Book book = new Book();

  @FXML
  private Label bookTitleLabel;

  @FXML
  private Label authorNameLabel;

  @FXML
  private Label isbnLabel;

  @FXML
  private Label isAvailableLabel;

  @FXML
  private Button btnBorrowOrReturn;

  @FXML
  private ImageView bookImageView;

  public void setLibrary(Library library) {
    this.library = library;
  }

  public void setBookDetails(String isbn) {
    System.out.println(isbn);
    this.book = library.getBookByISBN(isbn);

    if (this.book != null) {
      bookTitleLabel.setText(this.book.getTitle());
      authorNameLabel.setText(this.book.getAuthor());
      isbnLabel.setText(this.book.getISBN());
      isAvailableLabel
          .setText(this.book.getAvailability() ? "Available" : "Borrowed by " + this.book.getBorrowerName());
    } else {
      System.err.println("Book is null in setBookDetails");
    }
    setImage(isbn);

    if (!this.book.getAvailability()) {
      btnBorrowOrReturn.setText("Return");
    }
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
  private void handleBorrowOrReturnButtonAction(ActionEvent event) {
    if (this.book.getAvailability()) {
      System.out.println("Borrowing book...");
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Borrow Book");
      alert.setHeaderText(null);
      alert.setContentText("Are you sure you want to borrow this book?");
      Optional<ButtonType> action = alert.showAndWait();

      if (action.get() == ButtonType.OK) {
        try {
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/BorrowDialogBox.fxml"));
          Parent root = fxmlLoader.load();
          BorrowDialogBoxController borrowController = fxmlLoader.getController();
          borrowController.setLibrary(library);
          borrowController.setBookDetails(this.book.getISBN());

          Stage dialogStage = new Stage();
          dialogStage.setTitle("Borrow Book");
          dialogStage.initModality(Modality.WINDOW_MODAL);
          dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
          dialogStage.setResizable(false);
          dialogStage.setScene(new Scene(root));

          // Set the close request handler
          borrowController.setCloseRequestHandler(dialogStage);
          
          dialogStage.showAndWait();
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        return;
      }
    } else {
      System.out.println("Returning book...");
    }
    setBookDetails(this.book.getISBN());
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
