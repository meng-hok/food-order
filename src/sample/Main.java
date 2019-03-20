package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main extends Application {
    public static String working_panel = "";
    public static int userId;
    public static String userName="";
    private Stage window ;
    @Override
    public void start(Stage primaryStage) throws Exception{
//
        getStartedData();

        this.window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Have a nice day");
        primaryStage.setScene(new Scene(root, 1024, 780));
        primaryStage.show();
    }

    public void getStartedData(){
        ResultSet resultSet = ImportantMethod.selectDataFromDatabase("select date,daily_id from receipts order by id desc limit 1");

        try {
            while (resultSet.next()){
               ImportantMethod.lastExecuteDate = resultSet.getDate(1).toLocalDate();
               ImportantMethod.dailyId = resultSet.getInt(2);
            }
        }catch (SQLException s){
            s.printStackTrace();
        }

        GetConnection.closeConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void loadMainScreen() throws Exception{
        Stage window = this.window;
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene scene=new Scene(root,1024, 720);
        this.window.setScene(scene);
        this.window.show();
    }
}
