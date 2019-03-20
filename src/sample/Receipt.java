package sample;

import java.time.LocalDate;

public class Receipt {
    public int id;
    public int total;
    public LocalDate date;
    public int daily_id;
    public int user_id;

    public Receipt() {

    }

    public Receipt(int total, int daily_id, int user_id) {
        this.total = total;
        this.daily_id = daily_id;
        this.user_id = user_id;
    }

    public Receipt(int total, LocalDate date, int daily_id, int user_id) {
        this.total = total;
        this.date = date;
        this.daily_id = daily_id;
        this.user_id = user_id;
    }

    public Receipt(int id, int total, int daily_id, int user_id) {
        this.id = id;
        this.total = total;
        this.daily_id = daily_id;
        this.user_id = user_id;
    }

    public Receipt(int id, int total, LocalDate date, int daily_id, int user_id) {
        this.id = id;
        this.total = total;
        this.date = date;
        this.daily_id = daily_id;
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "i=" + id +
                ", total=" + total +
                ", date=" + date +
                ", daily_id=" + daily_id +
                ", user_id=" + user_id +
                '}'+'\n';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getDaily_id() {
        return daily_id;
    }

    public void setDaily_id(int daily_id) {
        this.daily_id = daily_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
