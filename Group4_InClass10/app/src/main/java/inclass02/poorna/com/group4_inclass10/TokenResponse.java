package inclass02.poorna.com.group4_inclass10;

import java.io.Serializable;

/**
 * Created by poorn on 4/2/2018.
 */

public class TokenResponse implements Serializable {
    String token;
    int user_id;
    String user_email;
    String user_fname;
    String user_lname, user_role;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public String getUser_lname() {
        return user_lname;
    }

    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "token='" + token + '\'' +
                ", user_id=" + user_id +
                ", user_email='" + user_email + '\'' +
                ", user_fname='" + user_fname + '\'' +
                ", user_lname='" + user_lname + '\'' +
                ", user_role='" + user_role + '\'' +
                '}';
    }
}
