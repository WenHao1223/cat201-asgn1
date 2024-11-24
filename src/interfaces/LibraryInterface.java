package interfaces;

import models.Book;
import java.util.ArrayList;

public interface LibraryInterface {
  public void addExistingBook(String title, String author, String ISBN, boolean availability, String borrowerName,
      String borrowerPhone, long borrowerID);

  public int addBook(String title, String author, String ISBN);

  public void viewBooks();

  public ArrayList<Book> getBooks();

  public Book getBookByISBN(String ISBN);

  public ArrayList<Book> searchBook(String searched);

  public int getNumberOfBooks();
}