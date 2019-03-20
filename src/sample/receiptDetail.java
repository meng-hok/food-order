package sample;

import java.lang.reflect.Constructor;

public class receiptDetail {
    public int id;
    public int receipt_id;
    public int product_id;
    public int product_qty;
    public int product_total;

    public receiptDetail(int id, int receipt_id, int product_id, int product_qty, int product_total) {
        this.id = id;
        this.receipt_id = receipt_id;
        this.product_id = product_id;
        this.product_qty = product_qty;
        this.product_total = product_total;
    }

    public receiptDetail(int product_id, int product_qty, int product_total) {
        this.product_id = product_id;
        this.product_qty = product_qty;
        this.product_total = product_total;
    }

    public receiptDetail(){

    }
    @Override
    public String toString() {
        return "receiptDetail{" +
                "id=" + id +
                ", receipt_id=" + receipt_id +
                ", product_id=" + product_id +
                ", product_qty=" + product_qty +
                ", product_total=" + product_total +
                '}'+'\n';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(int receipt_id) {
        this.receipt_id = receipt_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(int product_qty) {
        this.product_qty = product_qty;
    }

    public int getProduct_total() {
        return product_total;
    }

    public void setProduct_total(int product_total) {
        this.product_total = product_total;
    }
}
