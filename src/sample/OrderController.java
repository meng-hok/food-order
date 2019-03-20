package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;


import java.lang.reflect.Array;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class OrderController implements Initializable {
    public AnchorPane orderAnchorPane;
    private  BorderPane border;
    @FXML
    private Button btnPay;

    @FXML
    private Button btnReset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        System.out.println(  LocalDate.now() );
//        System.out.println(LocalDate.of(2019,04,12));
//
//        if(LocalDate.of(2019,03,9) .compareTo(LocalDate.now() ) == 0 ){
//            System.out.println("possible");
//
//        }    else{
//            System.out.println("not possible");
//        }
       productProcess();
    }



    @FXML
    void payOnClick(ActionEvent event) {
        if(ImportantMethod.comfirmation("") == false){
            System.out.println("false");
            return;
        }

         Boolean boo = false;
         Integer price = 0;
         List<Text> listText = new ArrayList<>();
         int mainReceiptTotal = 0;
         Receipt receipt;
         ObservableList<receiptDetail> receiptDetails = FXCollections.observableArrayList();
         receiptDetail receiptDetaill = null;
            for (Node node : orderAnchorPane.getChildren()){

                if (node instanceof CheckBox){
                    if(((CheckBox)node).isSelected()){


                        price = Integer.parseInt(((CheckBox)node).getId());

                        boo = true;

                    }else{
                        price =0;
                        boo = false;
                    }
                }else if(boo == true){
                    if (node instanceof TextField){
//                        System.out.println(((TextField)node).getText());
                        int total = Integer.parseInt((((TextField)node).getText()))*price;
                        Text text = new Text(Integer.toString( total ));
                        text.setLayoutX(550);
                        text.setLayoutY(((TextField)node).getLayoutY()+20);
                        mainReceiptTotal +=total;

                        receiptDetaill = new receiptDetail(Integer.parseInt((((TextField)node).getId())),Integer.parseInt((((TextField)node).getText())),total);
                        receiptDetails.add(receiptDetaill);

                        listText.add(text);

                    }
                }
            }

            if(listText.size()>0){

                try{

                    orderAnchorPane.getChildren().addAll(listText);
                    receipt = new Receipt (mainReceiptTotal,LocalDate.now(),1,Main.userId);
                    if(ReceiptController.receiptDialog(receipt,receiptDetails)==false){
                        return;
                    }
                    ReceiptController.insertDataToReceipt(receipt);
                    ReceiptDetailController.save(receiptDetails);

                }catch (ConcurrentModificationException e){
                    e.printStackTrace();
                }
            }
    }

    @FXML
    void resetOnClick(ActionEvent event) {



    }

    private void productProcess(){
        Controller controller = new Controller();

        CheckBox checkBox;
        TextField textField;

        AddItemController addItemController = new AddItemController();
        ObservableList listProducts =  addItemController.getAllProducts();

        int layoutY = 90;
        for (Object product :listProducts){
            //System.out.println( ((Product)product).name);
            checkBox = new CheckBox(((Product)product).name);
            checkBox.setId(  Integer.toString(    ((Product) product).price     )   );
            checkBox.setLayoutX(92);
            checkBox.setLayoutY(layoutY);
            textField = new TextField("1");
            textField.setId(Integer.toString(((Product) product).id) );
            textField.setLayoutX(236);
            textField.setLayoutY(layoutY);
            orderAnchorPane.getChildren().addAll(checkBox,textField);
            layoutY +=30;
        }
    }

}
