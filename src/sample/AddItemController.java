package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;

public class AddItemController implements Initializable {

    @FXML
    private Button btnpUpdate;

    @FXML
    private Button btnpRemove;

    @FXML
    private TextField txtpPrice;

    @FXML
    private TextField txtpDescription;

    @FXML
    private TableView<Product> tblView;

    @FXML
    private TextField txtpHidden;

    @FXML
    private TextField txtpName;

    @FXML
    private DatePicker txtpDatepicker;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("hehhee");
       showDataInTableView();
        tableColumnOnClick();
    }
    /* ==================================== Access data==================== */
     @FXML
    void btnpUpdateOnClick(ActionEvent event) {
//        System.out.println(Integer.parseInt(txtpPrice.getText()));
//        System.out.println(txtpDatepicker.getValue());
         if(ImportantMethod.comfirmation("Please check then Press Continue ")==false){
             return;
         }
         GetConnection.openConnection();
         System.out.println(txtpDatepicker.getClass());
         String sqlInsert;


         //**validation/
         if (txtpPrice.getText().isEmpty() || txtpDatepicker.getValue() == null){
             ImportantMethod.msgToUser("Empty Value found","Found");
             return;
         }

         if(txtpHidden.getText().isEmpty()){

             sqlInsert = "INSERT INTO products(name, date,price, descripton) VALUES ('"+txtpName.getText()+"','"+txtpDatepicker.getValue()+"','"+Integer.parseInt(txtpPrice.getText())+"','"+txtpDescription.getText()+"')";

         }else {

             //sql = "UPDATE bms_authors SET first_name = ? ,last_name = ? ,gender = ? ,birth_date = ? ,email = ? ,phone_number = ? ,image_url = ? ,address =?  where id ="+Integer.parseInt(txtHidden.getText());
           sqlInsert = "UPDATE products SET name ='" + txtpName.getText() + "',price ='" + Integer.parseInt(txtpPrice.getText()) + "', date ='" + txtpDatepicker.getValue() + "',descripton ='" + txtpDescription.getText() + "' where id =" + Integer.parseInt(txtpHidden.getText());
             ///INSERT INTO `products`( `name`, `date`, `price`, `descripton`) VALUES ("Americano","2018-02-02",59,"myfav");
         }


        try {
            System.out.println("");
            PreparedStatement preparedStatement = GetConnection.connection.prepareStatement(sqlInsert);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            ImportantMethod.msgToUser("Insert Fail please CHECK","WARNING");

        }finally {
            GetConnection.closeConnection();
            clearDataIntxt();
            showDataInTableView();
        }
    }
    /*     =================================action ================================*/
    void clearDataIntxt(){
        txtpDescription.clear();
        txtpName.clear();
        txtpPrice.clear();
        txtpDatepicker.setValue(null);
        txtpHidden.clear();
    }
    @FXML
    void btnpRemoveOnClick(ActionEvent event) {
        if(txtpHidden.getText().isEmpty()){

            clearDataIntxt();

        }else{
            if(ImportantMethod.comfirmation("After Removed ,Product will NEVER be appear")==false){
                return;
            }
            GetConnection.openConnection();
            try {
                PreparedStatement preparedStatement = GetConnection.connection.prepareStatement("DELETE  FROM products WHERE id = "+Integer.parseInt(txtpHidden.getText()) +"");
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                GetConnection.closeConnection();
            }
            clearDataIntxt();
            showDataInTableView();
        }
    }

    private void tableColumnOnClick(){
        tblView.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                Product product = tblView.getSelectionModel().getSelectedItem();
                txtpName.setText(product.name);

                txtpPrice.setText(Integer.toString(product.price) );
                txtpDescription.setText(product.getDescription());
                txtpHidden.setText(Integer.toString(product.id));
                txtpDatepicker.setValue( ((java.sql.Date) product.date).toLocalDate()) ;
            }
        });
    }
    /* =================================SHOW AREA =========================================*/
    public ObservableList<Product> getAllProducts(){
        ObservableList<Product> observableList = FXCollections.observableArrayList();
        Product product ;
        GetConnection.openConnection();

        try {
            PreparedStatement preparedStatement = GetConnection.connection.prepareStatement("SELECT * FROM products");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                product = new Product(resultSet.getInt(1),resultSet.getString(2),resultSet.getDate(3),resultSet.getInt(4),resultSet.getString(5));
                observableList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            System.out.println(observableList);
        }

        return observableList;
    }

    public void showDataInTableView(){
        ObservableList<Product>  products = getAllProducts();
        TableColumn<Product,Integer> colId = new TableColumn<>("ID");
        TableColumn<Product,String> colName = new TableColumn<>("Name");
        TableColumn<Product, Date> colDate = new TableColumn<>("Date");
        TableColumn<Product,Integer> colPrice = new TableColumn<>("Price");
        TableColumn<Product,String> colDescription = new TableColumn<>("Description");

        colId.setCellValueFactory(new PropertyValueFactory<Product,Integer>("id"));
       colName.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<Product,Date>("date"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Product,Integer>("price"));
        colDescription.setCellValueFactory(new PropertyValueFactory<Product,String>("description"));

        tblView.setItems( products);
        tblView.getColumns().setAll(colId,colName,colDate,colPrice,colDescription);

    }
}
