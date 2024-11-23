
// for testing models and interfaces
import models.Book;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;

public class Main {
  public static void main(String[] args) {
    ArrayList<Book> books = new ArrayList<Book>();
    List<String[]> tempData = new ArrayList<>();

    String filepath = "src/data/data.csv";

    try {
      FileReader file = new FileReader(filepath);
      System.out.println("Reading data from " + filepath + "...");

      int c;
      String line = "";

      // skip the first line
      while ((c = file.read()) != -1) {
        if (c == '\n') {
          break;
        }
      }

      while ((c = file.read()) != -1) {
        if (c == '\n') {
          String[] data = line.split(",");
          System.out.println(line);

          if (data.length == 7) {
            tempData.add(data);
          } else {
            System.err.println("Invalid data format: " + line);
          }
          line = "";
        } else {
          line += (char) c;
        }
      }

      file.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Create Book objects for first time program run
    for (String[] data : tempData) {
      Book book = new Book();
      book.addDetailsWithBorrower(data[0], data[1], data[2], Boolean.parseBoolean(data[3]), data[4], data[5],
          data[6].trim().isEmpty() ? 0 : Long.parseLong(data[6].trim()));
      books.add(book);
    }

    // Display all book details
    for (Book book : books) {
      book.displayBookDetails();
      System.out.println();
    }

    System.out.println("--------------------");

    // Add a new book
    Book newBook = new Book();
    newBook.addBook("The Alchemist", "Paulo Coelho", "978-0062315007");
    books.add(newBook);
    System.out.println();
    books.get(books.size() - 1).displayBookDetails();

    System.out.println("--------------------");

    // Borrow a book
    books.get(0).displayBookDetails();
    System.out.println();
    books.get(0).borrowBook("John Doe", "1234567890", 3567890);
    System.out.println();
    books.get(0).displayBookDetails();
    System.out.println();
    books.get(0).displayBorrowerDetails();
    System.out.println();

    books.get(1).displayBookDetails();
    System.out.println();
    books.get(1).borrowBook("Jane Doe", "0987654321", 86543210);
    System.out.println();
    books.get(1).displayBookDetails();
    System.out.println();
    books.get(1).displayBorrowerDetails();
    System.out.println();
    
    System.out.println("--------------------");

    // Return a book
    books.get(0).displayBookDetails();
    System.out.println();
    books.get(0).displayBorrowerDetails();
    System.out.println();
    books.get(0).returnBook();
    System.out.println();
    books.get(0).displayBookDetails();
    System.out.println();
    books.get(0).displayBorrowerDetails();
    System.out.println();

    books.get(3).displayBookDetails();
    System.out.println();
    books.get(3).displayBorrowerDetails();
    System.out.println();
    books.get(3).returnBook();
    System.out.println();

  }
}
