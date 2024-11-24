package controllers;

import models.Library;
import models.Book;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BorrowerDetailsBoxController {
  private Library library;
  private Book book;

  @FXML
  private Label tfBorrowerName;

  @FXML
  private Label tfBorrowerPhone;

  @FXML
  private Label tfBorrowerID;

  public void setLibrary(Library library) {
    this.library = library;
  }

  public void setBookDetails(String isbn) {
    if (library != null) {
      Book book = library.getBookByISBN(isbn);
      if (book != null) {
        this.book = book;

        if (!this.book.getAvailability()) {
          tfBorrowerName.setText(this.book.getBorrowerName());
          tfBorrowerPhone.setText(this.book.getBorrowerPhone());
          tfBorrowerID.setText(String.valueOf(this.book.getBorrowerID()));
        }
      } else {
        System.err.println("Book not found for ISBN: " + isbn);
      }
    } else {
      System.err.println("Library is null in BorrowerDetailsBoxController");
    }
  }
}
