package androidintents.poorna.com.myapplication;

import java.io.Serializable;

/**
 * Created by poorn on 1/27/2018.
 */

public class User implements Serializable{
    String name;
    int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
