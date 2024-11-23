package controllers;

import models.Library;
import models.Book;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
}
