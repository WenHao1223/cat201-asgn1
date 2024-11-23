package controllers;

import models.Library;
import models.Book;

public class BorrowDialogBoxController {
  private Library library;
  private Book book;

  public void setLibrary(Library library) {
    this.library = library;
  }

  public void setBookDetails(String isbn) {
    if (library != null) {
      Book book = library.getBookByISBN(isbn);
      if (book != null) {
        this.book = book;
      } else {
        System.err.println("Book not found for ISBN: " + isbn);
      }
    } else {
      System.err.println("Library is null in BorrowDialogBoxController");
    }

    book.displayBookDetails();
  }
}
