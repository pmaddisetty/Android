package inclass02.poorna.com.fragdemoexpense;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by poorn on 3/19/2018.
 */

public class Expense implements Serializable {
    String expname;
    String category;
    String date;
    int amount;

    public String getExpname() {
        return expname;
    }

    public void setExpname(String expname) {
        this.expname = expname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expname='" + expname + '\'' +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}
