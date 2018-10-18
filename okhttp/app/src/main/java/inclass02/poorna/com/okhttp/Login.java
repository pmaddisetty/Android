package inclass02.poorna.com.okhttp;

import java.io.Serializable;

/**
 * Created by poorn on 4/2/2018.
 */

public class Login implements Serializable {
    String email,password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
