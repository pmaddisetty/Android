package inclass02.poorna.com.testproj;

import java.io.Serializable;

/**
 * Created by poorn on 1/28/2018.
 */

public class Student implements Serializable {
    String name;
    String email;
    String programmingLang;
   String Search;
    int mood;

    public Student(String name, String email, String programmingLang, String search, int mood) {
        this.name = name;
        this.email = email;
        this.programmingLang = programmingLang;
        Search = search;
        this.mood = mood;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", programmingLang='" + programmingLang + '\'' +
                ", Search='" + Search + '\'' +
                ", mood=" + mood +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProgrammingLang() {
        return programmingLang;
    }

    public String getSearch() {
        return Search;
    }

    public int getMood() {
        return mood;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProgrammingLang(String programmingLang) {
        this.programmingLang = programmingLang;
    }

    public void setSearch(String search) {
        Search = search;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }
}
