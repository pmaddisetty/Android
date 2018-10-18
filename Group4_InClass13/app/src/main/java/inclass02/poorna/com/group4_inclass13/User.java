package inclass02.poorna.com.group4_inclass13;

public class User {
    String name,email,userid;

    public User(String name, String email,String userid) {
        this.name = name;

        this.email = email;
        this.userid=userid;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}
