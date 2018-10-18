package inclass02.poorna.com.testsamthread;

import java.util.Random;

/**
 * Created by poorn on 2/4/2018.
 */

public class HeavyWork {
    static final int COUNT = 900000;

    static double getNumber()
    {

        double num = 0;

        Random rand = new Random();

        for(int i=0;i<COUNT; i++)
        {

            num = num + rand.nextDouble();

        }

        return num / ((double) COUNT);

    }

}