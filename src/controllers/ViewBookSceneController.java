package controllers;

import models.Library;
import models.Book; 

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
}
}
