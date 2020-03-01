package sample.controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button authSigInButton;

    @FXML
    private Button loginSignUpButton;

    @FXML
    void initialize() {
        loginSignUpButton.setOnAction(event -> {
            loginSignUpButton.getScene().getWindow().hide(); //при нажатии на эту кнопку будет прятаться все окно "sample.fxml"

            //Дальше идет блок который запустит другое fxml окно вместо скрытого
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/signUp.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot(); //корневой компонент в который будут вложены остальные элементы
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

        });
    }
}


