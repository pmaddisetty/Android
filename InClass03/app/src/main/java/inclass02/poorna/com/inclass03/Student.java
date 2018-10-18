package inclass02.poorna.com.inclass03;

import java.io.Serializable;

/**
 * Created by poorn on 1/29/2018.
 */

public class Student implements Serializable {
    String name;
    String email;
    String Department;
    int mood;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public Student(String name, String email, String department, int mood) {
        this.name = name;
        this.email = email;
        Department = department;
        this.mood = mood;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", Department='" + Department + '\'' +
                ", mood=" + mood +
                '}';
    }
}
