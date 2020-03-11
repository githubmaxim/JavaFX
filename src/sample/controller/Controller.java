package sample.controller;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sample.database.DatabaseHandler;
import sample.user.User;

public class Controller {
    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());


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
        authSigInButton.setOnAction(event -> {
            //присваиваем переменным значения полей 'Login' и 'Password'
            String loginText = login_field.getText().trim();
            String loginPassword = password_field.getText().trim();

            if(!loginText.equals("") && !loginPassword.equals(""))
                loginUser(loginText, loginPassword);
            else
                System.out.println("Login and/or password is empty");
                });

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

    private void loginUser(String loginText, String loginPassword) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;

        while (true){
            try {
                if (!result.next()) break;
            } catch (SQLException e) {
                log.error("SQLException in Controller", e);
                e.printStackTrace();
            }
            counter++;
        }

        if (counter >= 1){
            System.out.println("Success!");
        }
    }
}


