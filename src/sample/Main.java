package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    //обязательное переопределение абстрактного метода start() и класса Application
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/sample.fxml"));
        primaryStage.setTitle("My first JavaFX"); //установка надписи для Stage

        //дальше вкладываем в Stage Scene с размерами 700 на 400, где root это корневой компонент
        // нашего пользовательского интерфейса, остальные компоненты вложены в него.
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show(); //запуск Stage ("primaryStage")
    }

    //стандартный запуск JavaFX приложения
    public static void main(String[] args) {
        launch(args);
    }
}
