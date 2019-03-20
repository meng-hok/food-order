package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class ImportantMethod {
    public static int dailyId = 0;
    public static LocalDate lastExecuteDate;
    public static ResultSet selectDataFromDatabase(String sql){

        ResultSet resultSet=null;
        GetConnection.openConnection();

        //String sql = "SELECT * From users WHERE users.account ='sok.menghok1@gmail.com' AND  password ='123'";

        try {

            PreparedStatement preparedStatement = GetConnection.connection.prepareStatement(sql);
            resultSet =  preparedStatement.executeQuery();


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {



        }

        return resultSet;
    }
    public static boolean updateReceiptToDatabase(String sqlInsert){
//
        if(sqlInsert == ""){
            return false;
        }


        GetConnection.openConnection();

        try {
            PreparedStatement preparedStatement = GetConnection.connection.prepareStatement(sqlInsert);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            GetConnection.closeConnection();
            return true;
        }
    }

    public static String getNameFromTable(int id){
        if(id==0){
            return "unknown";
        }
        ResultSet resultSet= selectDataFromDatabase("select name from users where id="+id+" limit 1 ");
        String name = "";
        try {
          while (resultSet.next()){
              name = resultSet.getString(1);
          }
        } catch (SQLException e) {
            e.printStackTrace();

        }finally {
            GetConnection.closeConnection();
        }
        return name;
    }
    public static String getNameFromTable(int id,String table){
        if(id==0){
            return "unknown";
        }
        ResultSet resultSet= selectDataFromDatabase("select name from "+table+" where id="+id+" limit 1 ");
        String name = "";
        try {
            while (resultSet.next()){
                name = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }finally {
            GetConnection.closeConnection();
        }
        return name;
    }
    public static int getLastIdFromTable(String table){
        ResultSet resultSet= selectDataFromDatabase("select id from "+table+" order by id Desc limit 1 ");
        int number = 0;
        try {
            while (resultSet.next()){
                number = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }finally {
            GetConnection.closeConnection();
        }
        return number;
    }


    public static boolean comfirmation(String textOnDialogContent){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Comfirmation");
        if(textOnDialogContent==""){
            alert.setContentText("Any Change ?");
        }else{
            alert.setContentText(textOnDialogContent);
        }

        ButtonType btnSave = new ButtonType("Continue");
        ButtonType btnCancel = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(btnSave,btnCancel);

        Optional<ButtonType> optional = alert.showAndWait();



        if(!optional.isPresent()){
            System.out.println("nothing clicked");
        }else if(optional.get()== btnSave ){
            return true;
        }else if(optional.get()== btnCancel ){

            return false;
        }

        return false;
    }

    public static boolean msgToUser(String textOnDialogContent,String title){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        if(title != ""){
//            alert.setTitle(title);
//        }else{
//            alert.setTitle(null);
//        }
        alert.setTitle("To User");
        alert.setHeaderText(null);
        alert.setContentText(textOnDialogContent);


        ButtonType btnSave = new ButtonType("Okay");


        alert.getButtonTypes().setAll(btnSave);

        Optional<ButtonType> optional = alert.showAndWait();



        if(!optional.isPresent()){

        }else if(optional.get()== btnSave ) {
            return true;
        }
//        }else if(optional.get()== btnCancel ){
//
//            return false;
//        }

        return false;
    }

    public static String registerValidation(String name,String account ,String password ){
        String error = "";
        if(name.length()<1){
            error +="Name is Empty"+'\n';
        }
        if(account.isEmpty()){
            error +="Account is Empty"+'\n';
        }

        if(password.length() < 6){

            error +="Password must be at least 6 Character"+'\n';
        }


        return error;
    }

    public static boolean uniqueAccount(String account){
        boolean boo= true;
        ResultSet resultSet = selectDataFromDatabase("select * from users where account = '"+account+"' ");
        try {
            while (resultSet.next()){
                boo = false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return boo;
    }


}
