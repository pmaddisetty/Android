package inclass02.poorna.com.firedemo;

import java.io.Serializable;
import java.nio.file.SecureDirectoryStream;

public class Expense implements Serializable {
    String expname;
    String category;
    String amount;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
