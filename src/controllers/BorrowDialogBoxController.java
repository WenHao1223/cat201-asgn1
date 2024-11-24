package controllers;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import models.Library;
import models.Book;

public class BorrowDialogBoxController {
  private Library library;
  private Book book;

  @FXML
  private TextField tfBorrowerName;

  @FXML
  private TextField tfBorrowerPhone;

  @FXML
  private TextField tfBorrowerID;

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
  }

  public void handleCancelButtonAction(ActionEvent event) {
    // Show a confirmation alert box
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Cancel Borrowing Book");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to cancel borrowing book?");
    Optional<ButtonType> action = alert.showAndWait();

    if (action.isPresent() && action.get() == ButtonType.OK) {
      // User chose OK, proceed with redirection
      try {
        // close the dialog box
        ((Node) (event.getSource())).getScene().getWindow().hide();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      // User chose CANCEL or closed the dialog
      return;
    }
  }

  public void handleBorrowButtonAction(ActionEvent event) {
    String borrowerName = "";
    String borrowerPhone = "";
    long borrowerID = 0;

    try {
      borrowerName = tfBorrowerName.getText();
      borrowerPhone = tfBorrowerPhone.getText();
      borrowerID = Long.parseLong(tfBorrowerID.getText() == null || tfBorrowerID.getText().trim().isEmpty() ? "0"
          : tfBorrowerID.getText().trim());
    } catch (Exception e) {
      System.out.println("Wrong input format: " + e.getMessage());
    }

    if (borrowerName.isEmpty() || borrowerPhone.isEmpty() || tfBorrowerID.getText().isEmpty()) {
      Alert alert1 = new Alert(AlertType.ERROR);
      alert1.setTitle("Error");
      alert1.setHeaderText(null);
      alert1.setContentText("Please fill in all fields.");
      alert1.showAndWait();
      return;
    }

    // check for regex
    if (!borrowerPhone.matches("^(011-\\d{8})$|^(01[02-9]-\\d{7})$|^(0[2-9]\\d?-\\d{7})$")) {
      Alert alert1 = new Alert(AlertType.ERROR);
      alert1.setTitle("Error");
      alert1.setHeaderText(null);
      alert1.setContentText("Phone number must follow the format of 0x-xxxxxxx or 011-xxxxxxxx.");
      alert1.showAndWait();
      return;
    }

    if (!tfBorrowerID.getText().matches("^\\d{6}$|^\\d{8}$")) {
      Alert alert1 = new Alert(AlertType.ERROR);
      alert1.setTitle("Error");
      alert1.setHeaderText(null);
      alert1.setContentText(
          "Borrower ID must be a valid USM Student ID (e.g., 2230xxxx).");
      alert1.showAndWait();
      return;
    }

    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Borrow Book");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to borrow this book?");
    Optional<ButtonType> action = alert.showAndWait();
    ((Node) (event.getSource())).getScene().getWindow().hide();

    if (action.isPresent() && action.get() == ButtonType.OK) {
      try {
        book.borrowBook(borrowerName, borrowerPhone, borrowerID);

        // Show a confirmation alert box
        Alert alert2 = new Alert(AlertType.INFORMATION);
        alert2.setTitle("Borrow Book");
        alert2.setHeaderText(null);
        alert2.setContentText("Book borrowed successfully!");
        alert2.showAndWait();

        ((Node) (event.getSource())).getScene().getWindow().hide();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      return;
    }
  }

  public void setCloseRequestHandler(Stage stage) {
    stage.setOnCloseRequest(event -> {
      // Consume the event to prevent the window from closing
      event.consume();

      // Show a confirmation alert box
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Cancel Borrowing Book");
      alert.setHeaderText(null);
      alert.setContentText("Are you sure you want to cancel borrowing book?");
      Optional<ButtonType> action = alert.showAndWait();

      if (action.isPresent() && action.get() == ButtonType.OK) {
        // User chose OK, proceed with redirection
        stage.close();
      } else {
        // User chose CANCEL or closed the dialog
        return;
      }
    });
  }
}
