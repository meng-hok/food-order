package sample;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReceiptController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static boolean insertDataToReceipt(Receipt receipt){

        System.out.println(receipt.getTotal() +"=>"+receipt.getDate()+"=L"+receipt.getDaily_id()+"=>"+receipt.getUser_id());

        System.out.println(ImportantMethod.lastExecuteDate);
        if(ImportantMethod.lastExecuteDate.compareTo(receipt.getDate())==0){
            ImportantMethod.dailyId = ImportantMethod.dailyId + 1;
        }else{
            ImportantMethod.dailyId = 1;
            ImportantMethod.lastExecuteDate = receipt.getDate();
        }
            String sql =  "INSERT INTO receipts(total,date, daily_id, user_id) VALUES ('"+receipt.getTotal()+"','"+ receipt.getDate()+"','"+ImportantMethod.dailyId+"','"+receipt.getUser_id()+"')";
          System.out.println(sql);
            if(ImportantMethod.updateReceiptToDatabase(sql)){
                System.out.println("success");
            }else{
                System.out.println("not");
            };

     return true;

    }

    public void insertDataToReceiptList(ObservableList<receiptDetail> receiptDetails){
        for (receiptDetail receiptDetail:receiptDetails){
            System.out.println(receiptDetail);
        }
    }

    public static boolean receiptDialog(Receipt receipt,ObservableList<receiptDetail> receiptDetails){
//        System.out.println(receipt);
//        System.out.println(receiptDetails);
        String content="";
        content +=  ""+ImportantMethod.dailyId +'\n';

        content +="Operated by : "+ImportantMethod.getNameFromTable(receipt.getUser_id()) +'\n' ;
        content +="Date : " + receipt.getDate() +'\n';

        for (receiptDetail rec:receiptDetails
             ) {
            content +=ImportantMethod.getNameFromTable(rec.getProduct_id(),"products") +" :"+ rec.getProduct_qty()+ " : "+ rec.getProduct_total()+'\n';
        }

        content +="Total = "+receipt.getTotal();
        if(ImportantMethod.comfirmation(content)==true){
            return true;
        }else{
            return false;
        }

    }


}
