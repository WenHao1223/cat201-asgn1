package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class BookCardController {
  @FXML
  private Label bookTitleLabel;

  @FXML
  private Label authorNameLabel;

  @FXML
  private Label isbnLabel;

  @FXML
  private ImageView bookImageView;

  public void setBookDetails(String title, String author, String isbn) {
    if (bookTitleLabel == null || authorNameLabel == null || isbnLabel == null) {
      System.err.println("Labels are not initialized");
      return;
    }
    System.out.println("Setting book details: " + title + ", " + author + ", " + isbn);
    bookTitleLabel.setText(title);
    authorNameLabel.setText(author);
    isbnLabel.setText(isbn);
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
}
