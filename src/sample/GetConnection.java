package sample;

import java.sql.*;

public class GetConnection {

    public static Connection connection = null;

    public static void  openConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/shop";

        try {
            connection = DriverManager.getConnection(url,"root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

        }
//        PreparedStatement preparedStatement = connection.prepareStatement("select name from users");
//        ResultSet resultSet = preparedStatement.executeQuery();
//        while (resultSet.next()){
//            System.out.println(resultSet.getString(1));
//        }

    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

        }
    }
}
