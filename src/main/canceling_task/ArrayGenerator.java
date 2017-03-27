package main.canceling_task;

import java.util.Random;

/**
 * Created by G.Chalauri on 03/27/17.
 */
public class ArrayGenerator  {

    public int[] generateArray(int size) {
        int array[]=new int[size];
        Random random=new Random();
        for (int i=0; i<size; i++){
            array[i]=random.nextInt(10);
        }
        return array;
    }


}
