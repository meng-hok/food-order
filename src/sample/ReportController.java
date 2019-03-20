package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    @FXML
    private TableView<Receipt> tblrlView;

    @FXML
    private DatePicker rlDatePicker;

    @FXML
    private Button btnrlAll;

    @FXML
    private Button btnrlSearch;
    @FXML
    private Text txtrlUser;
    @FXML
    private TableView<receiptDetail> subTblrlView;
    @FXML
    private Text txtProInfo;

    @FXML
    public static void reportControllerNote(){

        System.out.println("Method show(Localdate) return data as observablelist by date");
        System.out.println("showDataInTableView(Observable) display data on tableview(overloading)");
        System.out.println("show() overloaded");
    }

    /**
     * @param event
     */
    public void rlSearchOnClick(ActionEvent event) {
        if (rlDatePicker.getValue() != null){
            showDataInTableView( show(rlDatePicker.getValue()));

        }else{
            showDataInTableView( show(null));
        }

    }

    @FXML
    void rlAllOnClick(ActionEvent event) {
        showDataInTableView( show(null));
    }

    public ObservableList<Receipt> show(LocalDate date) {
        String sql ="";
        if (date==null){
            sql = "select * from receipts";
        }else{
            sql = "select * from receipts where date ='"+date.toString()+"'";
        }
         ResultSet resultSet = ImportantMethod.selectDataFromDatabase(sql);
        ObservableList<Receipt> receipts = FXCollections.observableArrayList();
         try {
             Receipt receipt;
             while (resultSet.next()){
                 receipt = new Receipt(resultSet.getInt(1),resultSet.getInt(2),resultSet.getDate(3).toLocalDate(),resultSet.getInt(4),resultSet.getInt(5));
                 receipts.add(receipt);
             }
         }catch (SQLException e){
             e.printStackTrace();

         }finally {
            GetConnection.closeConnection();
            return receipts;
         }

    }
    public ObservableList<receiptDetail> show(int id) {
        String sql ="";
//        if (date==null){
//            sql = "select * from receiptsDetail";
//        }else{
            sql = "select * from receiptsDetail where receipt_id ='"+id+"'";
        //}

        ResultSet resultSet = ImportantMethod.selectDataFromDatabase(sql);
        ObservableList<receiptDetail> receipts = FXCollections.observableArrayList();
        try {
            receiptDetail receipt;
            while (resultSet.next()){
                receipt = new receiptDetail(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getInt(5));
                receipts.add(receipt);
            }
        }catch (SQLException e){
            e.printStackTrace();

        }finally {
            GetConnection.closeConnection();
            return receipts;
        }

    }

    public void showDataInTableView(ObservableList<Receipt> receipts){

       if(receipts.size()==0){
            tblrlView.getColumns().clear();
       }else {
           TableColumn<Receipt,Integer> colId = new TableColumn<>("ID");
           TableColumn<Receipt,Integer> colName = new TableColumn<>("Total");
           TableColumn<Receipt, LocalDate> colDate = new TableColumn<>("Date");
           TableColumn<Receipt,Integer> colPrice = new TableColumn<>("Daily_id");
           TableColumn<Receipt,Integer> colDescription = new TableColumn<>("User_id");

           colId.setCellValueFactory(new PropertyValueFactory<Receipt,Integer>("id"));
           colName.setCellValueFactory(new PropertyValueFactory<Receipt,Integer>("total"));
           colDate.setCellValueFactory(new PropertyValueFactory<Receipt,LocalDate>("date"));
           colPrice.setCellValueFactory(new PropertyValueFactory<Receipt,Integer>("daily_id"));
           colDescription.setCellValueFactory(new PropertyValueFactory<Receipt,Integer>("user_id"));

           tblrlView.setItems( receipts);
           tblrlView.getColumns().setAll(colId,colName,colDate,colPrice,colDescription);
       }
    }

    public void showDataInTableView(ObservableList<receiptDetail> receipts,int i){

        if(receipts.size()==0){
            subTblrlView.getColumns().clear();
        }else {
            TableColumn<receiptDetail,Integer> colId = new TableColumn<>("ID");
            TableColumn<receiptDetail,Integer> colName = new TableColumn<>("Main_Receipt");
            TableColumn<receiptDetail, Integer> colDate = new TableColumn<>("ProductID");
            TableColumn<receiptDetail,Integer> colPrice = new TableColumn<>("QTY");
            TableColumn<receiptDetail,Integer> colDescription = new TableColumn<>("Total");

            colId.setCellValueFactory(new PropertyValueFactory<receiptDetail,Integer>("id"));
            colName.setCellValueFactory(new PropertyValueFactory<receiptDetail,Integer>("receipt_id"));
            colDate.setCellValueFactory(new PropertyValueFactory<receiptDetail,Integer>("product_id"));
            colPrice.setCellValueFactory(new PropertyValueFactory<receiptDetail,Integer>("product_qty"));
            colDescription.setCellValueFactory(new PropertyValueFactory<receiptDetail,Integer>("product_total"));

            subTblrlView.setItems( receipts);
            subTblrlView.getColumns().setAll(colId,colName,colDate,colPrice,colDescription);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        showDataInTableView( show(null));
        tblrlView.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){

                Receipt receipt = tblrlView.getSelectionModel().getSelectedItem();
                System.out.println(receipt.getId());
                  showDataInTableView(show(receipt.getId()),1);
                  txtrlUser.setText(ImportantMethod.getNameFromTable(receipt.user_id));
            }
        });

        subTblrlView.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                receiptDetail receiptDetaill = subTblrlView.getSelectionModel().getSelectedItem();
                System.out.println(receiptDetaill.product_id);
                txtProInfo.setText( getProductInformation(receiptDetaill.product_id));

            }
        });
    }
    public String getProductInformation(int id){
        if(id == 0){
            return "not found";
        }
        String proInfo = "";
        Product product;
        ResultSet resultSet = ImportantMethod.selectDataFromDatabase("select * from products where id = "+id+" limit 1");
        try {
            while (resultSet.next()){
                product = new Product(resultSet.getInt(1),resultSet.getString(2),resultSet.getDate(3),resultSet.getInt(4),resultSet.getString(5));
                System.out.println(product.toString());
                proInfo = product.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proInfo;
    }
}
