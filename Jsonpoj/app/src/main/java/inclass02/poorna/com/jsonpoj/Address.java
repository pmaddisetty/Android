package inclass02.poorna.com.jsonpoj;

/**
 * Created by poorn on 2/18/2018.
 */

public class Address {
    String line1,city,state,zip;
    Address(){

    }

    @Override
    public String toString() {
        return "Address{" +
                "line1='" + line1 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
