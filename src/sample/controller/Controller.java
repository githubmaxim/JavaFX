package sample.controller;

// тут будет описано 2 варианта развития событий:
// 1. все fxml страницы при открытии создаются новым класслоадером, а при закрытии закрываются .close()
// 2. в этом случае страница sample.fxml не закрывается, а скрывается. А потом при раскрытии снова на
// ней остаются все набранные ранее данные
// !!! Изменить нужно оба файла Controller.fxml и SignUpController.fxml !!!

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sample.animation.Shake;
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
    private Label error_field;


    //обязательный к переопределению метод, в котором прописываются программы действия после нажатия на кнопки
    @FXML
    void initialize() {
        //изначально устанавливаем поле ошибки ввода логина/пароля невидимым
        error_field.setVisible(false);

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

            //переходим на страницу регистрации
            openNewScene("/sample/view/signUp.fxml");


 /*            //!!! для события №2 вместо предыдущей строки нужно следующее !!!

            //скрываем текущее fxml окно
            ((Stage)((Node)event.getSource()).getScene().getWindow()).hide();

            //открываем новое fxml окно
            openNewScene("/sample/view/signUp.fxml");

            //открываем скрытое fxml окно
            ((Stage)((Node)event.getSource()).getScene().getWindow()).show();
*/
        });
    }

    //метод проверяет на наличие в Б.Д. введенных Login/Password и потом или переходит на новую
    // страницу (метод openNewScene()) или трясет поля ввода и делает видимым поле ошибки
    private void loginUser(String loginText, String loginPassword) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();

        //заполняем поля для запроса
        user.setUserName(loginText);
        user.setPassword(loginPassword);

        //делаем запрос (из класса DatabaseHandler) в Б.Д.
        ResultSet result = dbHandler.getUserLogPass(user);

        int counter = 0;

        try {
             while (result.next()) {
                 counter++;
             }
        } catch (SQLException e) {
                log.error("SQLException in Controller", e);
                e.printStackTrace();
        }

        //если такие данные в Б.Д. есть переходим на страницу сайта
        if (counter >= 1){
            openNewScene("/sample/view/app.fxml");
        }
        //если нет показываем поле ошибки и трясем поля
        else {
            error_field.setVisible(true); //устанавливаем поле ошибки ввода логина/пароля видимым

            Shake userLoginAnim = new Shake(login_field);
            Shake userPassAnim = new Shake(password_field);
            userLoginAnim.playAnim();
            userPassAnim.playAnim();
        }
    }

    //метод будет скрывать текущее окно и открывать введенное новое или
    // закрывать текущее окно и открывать введенное новое
    public void openNewScene(String window) {


        //блок кода который закрывает текущее fxml окно
        Stage stage = (Stage) loginSignUpButton.getScene().getWindow();
        stage.close();
        //или можно написать - ((Stage) loginSignUpButton.getScene().getWindow()).close();


        //блок кода который запустит другое fxml окно вместо закрытого
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

/*        //!!! для события №2 вместо предыдущей строки нужно следующее !!!

        stage1.showAndWait();
*/





    }
}


