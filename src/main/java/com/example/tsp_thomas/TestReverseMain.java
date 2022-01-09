package com.example.tsp_thomas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TestReverseMain {
    public static Random rn = new Random();

   public static void main(String[] args){

       ArrayList<Integer>list=new ArrayList<>();
       for(int i=1;i<=10;i++){
           list.add(i);
       }

       //random.nextInt(max - min + 1) + min
       int r1=rn.nextInt((list.size()-1)-0+1)+0;
       int r2=rn.nextInt(list.size()-r1+1)+r1;
       System.out.println(r1+ "   "+r2);
       Collections.reverse(list.subList(r1,r2));
       System.out.println(list);
       Collections.reverse(list.subList(r1,r2));
       System.out.println(list);


    }
}
