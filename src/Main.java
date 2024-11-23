
// for testing models and interfaces
import models.Book;
import models.Library;

import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {
  public static void main(String[] args) {
    Library library = new Library();

    List<String[]> tempData = new ArrayList<>();

    String filepath = "src/data/data.csv";

    try {
      FileReader file = new FileReader(filepath);
      System.out.println("Reading data from " + filepath + "...\n");

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
      library.addExistingBook(
          data[0],
          data[1],
          data[2],
          Boolean.parseBoolean(data[3]),
          data[4],
          data[5],
          data[6] == null || data[6].trim().isEmpty() ? 0 : Long.parseLong(data[6].trim()));
    }

    // Display all book details
    library.viewBooks();

    // Add a new book
    library.addBook("The Alchemist", "Paulo Coelho", "978-0062315007");
    library.viewBooks();
    System.out.println("Number of books: " + library.getNumberOfBooks() + "\n");

    // Search for a book
    List<Book> foundBooks = library.searchBook("Great");
    for (Book book : foundBooks) {
      book.displayBookDetails();
      System.out.println();
    }

    // rewrite the data to the file
    try {
      System.out.println("Writing data to " + filepath + "...\n");
      FileWriter file = new FileWriter(filepath);

      file.write("title,author,ISBN,availability,borrowerName,borrowerPhone,borrowerID\n");

      for (Book book : library.getBooks()) {
        List<String> bookDetails = book.getBookDetailList();
        file.write(String.join(",", bookDetails) + "\n");
      }

      file.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
