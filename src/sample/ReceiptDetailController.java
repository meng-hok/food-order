package sample;

import javafx.collections.ObservableList;

public class ReceiptDetailController {

    public static boolean save(ObservableList<receiptDetail> receiptDetails){
        if (receiptDetails.size()==0){
            return false;
        }
        int i=0;
        int number = ImportantMethod.getLastIdFromTable("receipts");
        if(number>0){
            for (receiptDetail item:receiptDetails) {
                ImportantMethod.updateReceiptToDatabase("INSERT INTO receiptsDetail( receipt_id, product_id, product_qty, product_total) VALUES ('"+number+"','"+item.product_id+"','"+item.product_qty+"','"+item.product_total+"')");
                i++;
            }
            System.out.println(i+"item was added");
        }
        return true;
    }
}
