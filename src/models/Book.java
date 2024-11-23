package models;
import interfaces.BookInterface;

public class Book implements BookInterface {
  String title;
  String author;
  String ISBN;
  boolean availability;
  String borrowerName;
  String borrowerPhone;
  long borrowerID;

  public Book () {
    this.title = "";
    this.author = "";
    this.ISBN = "";
    this.availability = true;
    this.borrowerName = "";
    this.borrowerPhone = "";
    this.borrowerID = 0;
  }

  public Book (String title, String author, String ISBN) {
    this.title = title;
    this.author = author;
    this.ISBN = ISBN;
    this.availability = true;
    this.borrowerName = "";
    this.borrowerPhone = "";
    this.borrowerID = 0;
  }

  public Book (String title, String author, String ISBN, boolean availability, String borrowerName, String borrowerPhone, long borrowerID) {
    this.title = title;
    this.author = author;
    this.ISBN = ISBN;
    this.availability = availability;
    this.borrowerName = borrowerName;
    this.borrowerPhone = borrowerPhone;
    this.borrowerID = borrowerID;
  }

  public void addBook(String title, String author, String ISBN) {
    System.out.println("Adding book...");
    this.title = title;
    this.author = author;
    this.ISBN = ISBN;
    System.out.println("Book added: " + this.title);
  }

  public void addDetailsWithBorrower(String title, String author, String ISBN, boolean availability, String borrowerName, String borrowerPhone, long borrowerID) {
    this.title = title;
    this.author = author;
    this.ISBN = ISBN;
    this.availability = availability;
    this.borrowerName = borrowerName;
    this.borrowerPhone = borrowerPhone;
    this.borrowerID = borrowerID;
  }

  public void setAvailability(boolean availability) {
    this.availability = availability;
  }

  public void addBorrowerDetails(String borrowerName, String borrowerPhone, long borrowerID) {
    this.borrowerName = borrowerName;
    this.borrowerPhone = borrowerPhone;
    this.borrowerID = borrowerID;
  }
  
  public void borrowBook(String borrowerName, String borrowerPhone, long borrowerID) {
    System.out.println("Borrowing book...");

    if (!this.availability) {
      System.out.println("Book is already borrowed");
      return;
    }

    this.availability = false;
    this.borrowerName = borrowerName;
    this.borrowerPhone = borrowerPhone;
    this.borrowerID = borrowerID;
    System.out.println("Book borrowed by " + this.borrowerName);
  }

  public void returnBook() {
    System.out.println("Returning book...");

    if (this.availability) {
      System.out.println("Book is already available");
      return;
    }

    this.availability = true;
    this.borrowerName = "";
    this.borrowerPhone = "";
    this.borrowerID = 0;
    System.out.println("Book returned");
  }

  public void displayBookDetails() {
    System.out.println("Displaying book details...");
    System.out.println("Title: " + this.title);
    System.out.println("Author: " + this.author);
    System.out.println("ISBN: " + this.ISBN);

    if (this.availability) {
      System.out.println("Availability: Available");
    } else {
      System.out.println("Availability: Borrowed by " + this.borrowerName);
    }
  }

  public void displayBorrowerDetails() {
    System.out.println("Displaying borrower details...");
    System.out.println("Borrower Name: " + this.borrowerName);
    System.out.println("Borrower Phone: " + this.borrowerPhone);
    System.out.println("Borrower ID: " + this.borrowerID);
  }
}