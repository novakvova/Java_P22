package org.example;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        One();
//        Two();
        //JDBC
    }

    public static void Two() {
        //        boolean t = true;
        //short, double, float, char, long, String
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вкажіть розмір масиву");
        int n = Integer.parseInt(scanner.nextLine());
        int [] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = GetRandom(1, 100);
        }

        for(var item : array) {
            System.out.print(item + "\t");
        }
//        for (int i = 0; i < n; i++) {
//            System.out.print(array[i] + "\t");
//        }
        System.out.println();
    }

    public static int GetRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static void One() {
        int age;
        System.out.println("Вкажіть Ваш вік:");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        age = Integer.parseInt(str);
        System.out.println("Вам зараз " + age);
    }
}