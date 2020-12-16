package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void writeMessage(String message) {
        System.out.println(message);
    }
    public static String readString() {
        String s="";
        while (s.equals("")) {
        try {
            s = reader.readLine();
        } catch (IOException e) {
            System.out.println("Произошла ошибка при попытке ввода текста. Попробуйте еще раз.");;
        }
        }
        return s;
    }
    public static int readInt() {
        int number = 0;
        boolean flag = false;
        while (!flag) {
            try {
                number = Integer.parseInt(readString());
                flag = true;
            }
            catch(NumberFormatException e) {
                System.out.println("Произошла ошибка при попытке ввода числа. Попробуйте еще раз.");
            }
        }
        return number;
    }
}
