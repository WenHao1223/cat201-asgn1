import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainSceneController {

    @FXML
    private TextField tfTitle;

    @FXML
    void btnOKClicked(ActionEvent event) {
        if (tfTitle != null) {
            Stage mainWindow = (Stage) tfTitle.getScene().getWindow();
            String title = tfTitle.getText();
            mainWindow.setTitle(title);
        } else {
            System.err.println("tfTitle is null");
        }
    }

    @FXML
    void tfTitle(ActionEvent event) {
        if (tfTitle != null) {
            System.out.println(tfTitle.getText());
        } else {
            System.err.println("tfTitle is null");
        }
    }

    @FXML
    void initialize() {
        // Initialization code if needed
    }
}