package controllers;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// class
import models.Library;
import models.Book;

import java.util.ArrayList;

public class HomePageSceneController {
  @FXML
  private FlowPane bookFlowPane;

  @FXML
  private TextField searchTextField;

  private Library library;

  public void setLibrary(Library library) {
    this.library = library;
    if (library != null) {
        loadBookCards(library.getBooks());
    } else {
        System.err.println("Library is null in setLibrary");
    }
  }

  @FXML
  public void initialize() {

  }

  private void loadBookCards(ArrayList<Book> books) {
    bookFlowPane.getChildren().clear();
    try {
      for (Book book : books) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/components/BookCard.fxml"));
        Node bookCard = fxmlLoader.load();
        BookCardController controller = fxmlLoader.getController();
        controller.setBookDetails(book.getTitle(), book.getAuthor(), book.getISBN());
        controller.setLibrary(library);
        bookFlowPane.getChildren().add(bookCard);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleSearchBookButtonAction(ActionEvent event) {
    String searchText = searchTextField.getText().trim();
    if (searchText.isEmpty()) {
      loadBookCards(library.getBooks());
    } else {
      ArrayList<Book> filteredBooks = library.searchBook(searchText);
      loadBookCards(filteredBooks);
    }
  }

  @FXML
  private void handleSearchCloseButtonAction(ActionEvent event) {
    searchTextField.clear();
    loadBookCards(library.getBooks());
  }

  @FXML
  private void handleAddBookButtonAction(ActionEvent event) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to add book?");
    Optional<ButtonType> action = alert.showAndWait();

    if (action.get() == ButtonType.OK) {
      try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/AddBookScene.fxml"));
        Parent root = fxmlLoader.load();
        AddBookSceneController addBookController = fxmlLoader.getController();
        addBookController.setLibrary(library);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      // User chose CANCEL or closed the dialog
      return;
    }
  }
}
