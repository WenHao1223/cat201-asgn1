package interfaces;

import java.util.List;

public interface BookInterface {
  public void borrowBook(String borrowerName, String borrowerPhone, long borrowerID);

  public void returnBook();

  public void displayBookDetails();

  public List<String> getBookDetailList();

  public String getTitle();

  public String getAuthor();

  public String getISBN();
}