package inclass02.poorna.com.jsonpoj;

/**
 * Created by poorn on 2/18/2018.
 */

public class Person {
    String name;
    long id;
    int age;
    Address address;
    Person(){

    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", age=" + age +
                ", address=" + address +
                '}';
    }
}
