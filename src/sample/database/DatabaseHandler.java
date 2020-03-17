package sample.database;

import org.apache.log4j.Logger;
import sample.user.User;

import java.lang.invoke.MethodHandles;
import java.sql.*;

public class DatabaseHandler extends Config {
    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    Connection dbConnection;

    //метод подключающий к Б.Д.
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        //формируем URL
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?" + dbTimezone;

        //загружаем драйвер БД "DriverManager"
        Class.forName("com.mysql.cj.jdbc.Driver");

        //подключаемся к БД
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    //метод формирует SQL запрос, который записывает данные пользователя в поля таблицы БД
    public void signUpUser(User user) {
        String insert = "INSERT INTO" + " " + Const.USER_TABLE + "(" +
                Const.USER_FIRSTNAME + "," +Const.USER_LASTNAME + "," +
                Const.USER_USERNAME + "," + Const.USER_PASSWORD + "," +
                Const.USER_LOCATION + "," + Const.USER_GENDER + ")" +
                "VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getFirstName());
            prSt.setString(2, user.getLastName());
            prSt.setString(3, user.getUserName());
            prSt.setString(4, user.getPassword());
            prSt.setString(5, user.getLocation());
            prSt.setString(6, user.getGender());

            prSt.executeUpdate();
        } catch (ClassNotFoundException e) {
            log.error("ClassNotFoundException", e);
            e.printStackTrace();
        } catch (SQLException e) {
            log.error("SQLException", e);
            e.printStackTrace();
        }
    }


    //Метод пытающийся получить всю строку из Б.Д. по введенным Login и Password
    public ResultSet getUserLogPass(User user){
        ResultSet resSet = null;

        String select = "SELECT * FROM" + " " + Const.USER_TABLE + " " + "WHERE" + " " +
                Const.USER_USERNAME + "=? AND " +  Const.USER_PASSWORD + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getUserName());
            prSt.setString(2, user.getPassword());

            resSet = prSt.executeQuery();
        } catch (ClassNotFoundException e) {
            log.error("ClassNotFoundException", e);
            e.printStackTrace();
        } catch (SQLException e) {
            log.error("SQLException in DatabaseHandler", e);
            e.printStackTrace();
        }

        return resSet;
    }


    //Метод пытающийся получить всю строку из Б.Д. по введенному Login
    public ResultSet getUserLog(User user){
        ResultSet resSet = null;

        String select = "SELECT * FROM" + " " + Const.USER_TABLE + " " + "WHERE" + " " +
                Const.USER_USERNAME + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getUserName());

            resSet = prSt.executeQuery();
        } catch (ClassNotFoundException e) {
            log.error("ClassNotFoundException", e);
            e.printStackTrace();
        } catch (SQLException e) {
            log.error("SQLException in DatabaseHandler", e);
            e.printStackTrace();
        }

        return resSet;
    }

}
