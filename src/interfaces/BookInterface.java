package interfaces;

public interface BookInterface {
  void addBook(String title, String author, String ISBN);
  void addDetailsWithBorrower(String title, String author, String ISBN, boolean availability, String borrowerName, String borrowerPhone, long borrowerID);
  void borrowBook(String borrowerName, String borrowerPhone, long borrowerID);
  void returnBook();
  void displayBookDetails();
  void displayBorrowerDetails();
}