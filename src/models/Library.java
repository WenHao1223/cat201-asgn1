package models;

import interfaces.LibraryInterface;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Library extends Book implements LibraryInterface {
  ArrayList<Book> books = new ArrayList<Book>();

  public Library() {
    System.out.println("Library created.");
  }

  public void addExistingBook(String title, String author, String ISBN, boolean availability, String borrowerName,
      String borrowerPhone, long borrowerID) {
    System.out.println("Adding existing book...");
    System.out.println("-----------------");
    Book book = new Book(title, author, ISBN, availability, borrowerName, borrowerPhone, borrowerID);
    books.add(book);
    System.out.println("Book added: " + title);
    System.out.println("-----------------\n");
  }

  public void addBook(String title, String author, String ISBN) {
    System.out.println("Adding book...");

    for (Book book : books) {
      if (book.ISBN.equals(ISBN)) {
        System.out.println("Book already exists.");
        System.out.println("-----------------\n");
        return;
      }
    }

    System.out.println("-----------------");
    Book book = new Book(title, author, ISBN);
    books.add(book);
    System.out.println("Book added: " + title);
    System.out.println("-----------------\n");
  }

  public void viewBooks() {
    System.out.println("Viewing books...");
    System.out.println("-----------------");
    for (Book book : books) {
      book.displayBookDetails();
      System.out.println();
    }
    System.out.println("-----------------\n");
  }

  public ArrayList<Book> getBooks() {
    return books;
  }

  public ArrayList<Book> searchBook(String searched) {
    System.out.println("Searching for books with '" + searched + "' in title, author, or ISBN...\n");
    System.out.println("-----------------");
    ArrayList<Book> foundBooks = new ArrayList<Book>();
    for (Book book : books) {
      if (book.title.contains(searched) || book.author.contains(searched) || book.ISBN.contains(searched)) {
        foundBooks.add(book);
      }
    }
    System.out.println("-----------------\n");
    return foundBooks;
  }

  public int getNumberOfBooks() {
    return books.size();
  }
}
