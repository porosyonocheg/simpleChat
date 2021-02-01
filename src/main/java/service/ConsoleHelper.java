package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**Вспомогательный класс чтения и отправки сообщений из консоли с целью сокращения использования шаблонного кода.
 * @author Сергей Шешршавин*/
public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    /** Вывод сообщения в консоль*/
    public static void writeMessage(String message) {
        System.out.println(message);
    }
    /** Чтение строки, введенной в консоль
     * @throws IOException
     * @return String */
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
    /** Чтение целочисленного значения, введенного в консоль
     * @throws NumberFormatException в случае некорректного ввода данных
     * @return int*/
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
