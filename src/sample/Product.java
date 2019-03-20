package sample;

import java.util.Date;

public class Product {
    public int id;
    public String name;
    public Date date;
    public int price;
    public String description;

    public Product(){

    }
    public Product(int id, String name, Date date, int price, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.price = price;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}'+'\n';
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
