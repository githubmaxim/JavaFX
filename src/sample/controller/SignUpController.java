package sample.controller;

// тут будет описано 2 варианта развития событий:
// 1. все fxml страницы при открытии создаются новым класслоадером, а при закрытии закрываются .close()
// 2. в этом случае страница sample.fxml не закрывается, а скрывается. А потом при раскрытии снова на
// ней остаются все набранные ранее данные
// !!! Изменить нужно оба файла Controller.fxml и SignUpController.fxml !!!

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sample.animation.Shake;
import sample.database.DatabaseHandler;
import sample.user.User;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController {
    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private String firstName = "";
    private String lastName = "";
    private String userName = "";
    private String password = "";
    private String locat = "";
    private String gender = "";


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
    private RadioButton signUpRadioButtonMale;

    @FXML
    private ToggleGroup MyGroup;

    @FXML
    private RadioButton signUpRadioButtonFemale;

    @FXML
    private Label error_reg_field;

    @FXML
    private Label error_login_field;


    //обязательный к переопределению метод, в котором прописываются программы действия после нажатия на кнопки
    @FXML
    void initialize() {
        //скрываем поля ошибок
        error_reg_field.setVisible(false);
        error_login_field.setVisible(false);

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
        firstName = signUpName.getText();
        lastName = signUpLastName.getText();
        userName = login_field.getText();
        password = password_field.getText();
        locat = signUpCountry.getText();

        if(signUpRadioButtonMale.isSelected())
            gender = "Man";
        if(signUpRadioButtonFemale.isSelected())
            gender = "Woman";

        //проверяем на заполненность полей формы
        if ("".equals(firstName) || "".equals(lastName) || "".equals(userName) || "".equals(password) || "".equals(locat) || "".equals(gender)){
            error_reg_field.setVisible(true);
        }
        else{

            error_reg_field.setVisible(false);

            //заполняем полученными данными с полей формы поля класса User(в котором только конструктор и геттеры\сеттеры)
            loginUser(userName);

        }
    }


    //метод проверяет на наличие в Б.Д. введенног Login и потом или переходит на новую страницу
    //или трясет поле ввода Login и делает видимым поле ошибки
    private void loginUser(String loginText) {
        DatabaseHandler dbHandler = new DatabaseHandler();

        //создаем объект класса-прокладки и заполняем его одной переменной
        User user = new User();
        user.setUserName(loginText);

        ResultSet result = dbHandler.getUserLog(user);

        int counter = 0;

        try {
            while (result.next()) {
                counter++;
            }
        } catch (SQLException e) {
            log.error("SQLException in Controller", e);
            e.printStackTrace();
        }

        //если такого введенного Логина в Б.Д. нет, то переходим на начальную страницу +
        // заполняем Б.Д. новыми данными
        if (counter == 0){

            openNewScene("/sample/view/sample.fxml");

            User user1 = new User(firstName, lastName, userName, password, locat, gender);
            dbHandler.signUpUser(user1);
        }
        else {
            error_login_field.setVisible(true); //устанавливаем поле ошибки ввода логина видимым

            //трусим поле Login
            Shake userLoginAnim = new Shake(login_field);
            userLoginAnim.playAnim();
        }
    }


    //метод будет закрывать текущее окно и открывать скрытое предыдущее или
    //закрывать текущее окно и открывать новое предыдущее
    public void openNewScene(String window) {


        //закрываем текущее fxml окно
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        stage.close();


        // !!! Дальше идет блок только для ситуации №1, для ситуации №2 его удалить или скрыть !!!

        // блок создает новым класслоадером новое sample.fxml окно вместо закрытого
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot(); //корневой компонент в который будут вложены остальные элементы
        Stage stage1 = new Stage();
        stage1.setScene(new Scene(root));
        stage1.show();

    }
}
