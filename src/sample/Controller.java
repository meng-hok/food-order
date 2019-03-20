package sample;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    public GridPane gridPane;

    public AnchorPane anchorPane;

    public Label lbTop;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    @FXML
    public BorderPane borderPane;

        //registermember
    @FXML
    private TextField txtrAcc;

    @FXML
    private TextField txtlAcc;

    @FXML
    private Button btnrCreate;

    @FXML
    private TextField txtrPassword;

    @FXML
    private TextField txtlPassword;

    @FXML
    private TextField txtrName;
        //end
        //afterlog 
        
    @FXML
    private Button btnAddProduct;

    @FXML
    private Button btnReport;

    @FXML
    private Button btnUse;

    @FXML
    private Button btnTesting;
         //end

    @FXML
    private Button btnLeftProduct;

    @FXML
    private Button btnLeftReport;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
    public void loadSubscreen(String location){
        Main.working_panel = location;
        try {

            borderPane.setCenter(new FXMLLoader().load(getClass().getResource(location)));

        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }
    @FXML
    void loginClick(ActionEvent event) {
        //System.out.println("login");
       loadSubscreen("login.fxml");


    }

    @FXML
    void resClick(ActionEvent event) {


        loadSubscreen("register.fxml");


    }

    public void resOnClick(ActionEvent actionEvent) {

//        System.out.println(txtrName.getText() + txtrAcc.getText() + txtrPassword.getText());
        String error =ImportantMethod.registerValidation(txtrName.getText() ,txtrAcc.getText(),txtrPassword.getText());

        if(error!=""){
            ImportantMethod.msgToUser(error,"");
            return;
        }

        String sqlInsert = "INSERT INTO users(name, account, password) VALUES ('"+txtrName.getText()+"','"+txtrAcc.getText()+"','"+txtrPassword.getText()+"')";

        GetConnection.openConnection();

        try {
            PreparedStatement preparedStatement = GetConnection.connection.prepareStatement(sqlInsert);
            preparedStatement.executeUpdate();
            ImportantMethod.msgToUser("Sign up sucessfully Welcome","");

        } catch (MySQLIntegrityConstraintViolationException s){
            ImportantMethod.msgToUser("This account is already registered Please Choose another one","");
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            GetConnection.closeConnection();
        }

    }

    public void loginOnClick(ActionEvent actionEvent) {
        Main.userName = "" ;
        Main.userId = 0;
        ResultSet resultSet;
        GetConnection.openConnection();
        String sql = "SELECT id,name  From users WHERE users.account = '"+txtlAcc.getText()+"'AND  password ='"+txtlPassword.getText()+"' limit 1";
        //String sql = "SELECT * From users WHERE users.account ='sok.menghok1@gmail.com' AND  password ='123'";

        try {
            PreparedStatement preparedStatement = GetConnection.connection.prepareStatement(sql);

            resultSet =  preparedStatement.executeQuery();
            //System.out.println(resultSet.getString(2));
            while (resultSet.next()){
                Main.userId = resultSet.getInt(1);
                Main.userName = resultSet.getString(2);
            }

            if(Main.userName==""){
                ImportantMethod.msgToUser("Account Or Password is not correct","");
            }else{
                ImportantMethod.msgToUser("Login Sucess please refresh to access more","");
                System.out.println(Main.userId);
                System.out.println(Main.userName);
               //
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            GetConnection.closeConnection();

        }


    }

    public void changeBtn(){
        btnLogin.setVisible(false);
        btnRegister.setVisible(false);
        btnTesting.setVisible(true);
        btnLeftProduct.setVisible(true);
        btnLeftReport.setVisible(true);
    }

    public void useOnClick(ActionEvent actionEvent) {
//        System.out.println("Working Directory = " +
//                System.getProperty("user.dir"));
//        System.out.println("hehehe");
        //loadSubscreen("sample.register.fxml");
    }

    public void productOnClick(ActionEvent actionEvent) {
//        System.out.println("hhahahaha");
        loadSubscreen("register.fxml");
    }

    public void reportOnClick(ActionEvent actionEvent) {
    }


    public void refreshClick(ActionEvent actionEvent) {
//        System.out.println("Working Directory = " +
//                System.getProperty("user.dir"));

        if(Main.userName == ""){

       }else{
            changeBtn();
            borderPane.setCenter(null);

            if(Main.working_panel!="login.fxml" && Main.working_panel!="register.fxml" ){
                loadSubscreen(Main.working_panel);
            }

         }

    }

    public void testOnClick(ActionEvent actionEvent) {
       loadSubscreen("view/order.fxml");
    }


    public void leftProductOnClick(ActionEvent actionEvent) {
        loadSubscreen("addProduct.fxml");
    }

    public void leftReportOnClick(ActionEvent actionEvent) {
        loadSubscreen("receiptList.fxml");
    }

    public void txtAccOnChange(TouchEvent mouseEvent) {
        System.out.println("hahahahah");
    }
}

//    public void testOnClick(ActionEvent actionEvent) {
//    }
