package com.example.lib;

import java.util.Scanner;

public class guess {
    public static void main(String[] args) {
//        System.out.println((int)(Math.random()*100));
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter min: ");
        int min = sc.nextInt();
        System.out.print("Enter max: ");
        int max = sc.nextInt();
        int comp = (int)((Math.random()*max)+min);
//        System.out.println(comp);
        int count = 0;
        while(true) {
            System.out.print("enter your number: ");
            int user = sc.nextInt();
            count++;
            if(user<comp){
                System.out.println("higher number!!");
            }
            else if(user>comp){
                System.out.println("smaller number!!");
            }
            else{
                System.out.println("won, the number is "+ comp+" in "+count+" attempts");
                break;
            }
        }
    }
}