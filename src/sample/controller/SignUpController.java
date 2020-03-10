package sample.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import sample.database.DatabaseHandler;
import sample.user.User;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField signUpName;

    @FXML
    private TextField signUpLastName;

    @FXML
    private TextField signUpCountry;

    @FXML
    private CheckBox signUpCheckBoxMale;

    @FXML
    private CheckBox signUpCheckBoxFemale;

    @FXML
    private RadioButton signUpRadioButtonMale;

    @FXML
    private ToggleGroup MyGroup;

    @FXML
    private RadioButton signUpRadioButtonFemale;


    @FXML
    void initialize() {
        signUpButton.setOnAction(event -> {
            signUpNewUser();
         });
    }
//В этом методе мы будем наполнять данными SQL запрос на добавление полей в Б.Д. из метода signUpUser класса
// DatabaseHandler. Используется при этом класс-прокладка User, который (в случае необходимости) будет закрыт от
// изменения извне.
// Короче тут идет связка трех классов и трех методов для одного элементарного действия:
// получения данных из формы и наполнения ими SQL запроса на вставку в Б.Д.
    private void signUpNewUser() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        //получаем данные с полей в форме
        String firstName = signUpName.getText();
        String lastName = signUpLastName.getText();
        String userName = login_field.getText();
        String password = password_field.getText();
        String location = signUpCountry.getText();
        String gender = "";
        if(signUpRadioButtonMale.isSelected())
            gender = "Man";
        else
            gender = "Woman";
        //заполняем полученными данными с полей формы поля класса User(в котором только конструктор и геттеры\сеттеры)
        User user = new User(firstName, lastName, userName, password, location, gender);

        dbHandler.signUpUser(user);
    }
}
