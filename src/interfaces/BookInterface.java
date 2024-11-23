package interfaces;

public interface BookInterface {
  public void borrowBook(String borrowerName, String borrowerPhone, long borrowerID);

  public void returnBook();

  public void displayBookDetails();
}