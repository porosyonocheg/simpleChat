package service;

import client.test.Client;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
/** Разновидность клиента @see {@link Client} выполняет ряд установленных команд от других клиентов при обнаружении их в чате
 * @author Сергей Шершавин*/
public class BotClient extends Client {

    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    protected boolean shouldSendTextFromConsole() {
        return false;
    }
/** Формирование имени бота @return String включающую постоянную подстроку с обозначением типа клиента и его случайный номер от 0 до 100*/
    protected String getUserName() {
        return "smart_bot_" + (int)(Math.random()*100);
    }

    public static void main(String[] args) {
        new BotClient().run();
    }
    public class BotSocketThread extends Client.SocketThread {

        @Override
        /** Получение входящего сообщения
         * @param message - */
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            if (message.contains("/")) {
                String userName = message.split(": ")[0].substring(1,message.split(": ")[0].lastIndexOf(">"));
                String command = message.split(": ")[1].split(" ")[0];
                SimpleDateFormat sdf = null;
            switch (command) {
                case("/дата"): sendTextMessage("Информация для " + userName + ": " + new SimpleDateFormat("d.MM.YYYY").format(Calendar.getInstance().getTime())); break;
                case("/время"): sendTextMessage("Информация для " + userName + ": " + new SimpleDateFormat("H:mm:ss").format(Calendar.getInstance().getTime())); break;
                case("/день"): sendTextMessage("Информация для " + userName + ": " + new SimpleDateFormat("EEEE").format(Calendar.getInstance().getTime())); break;
                case("/посчитай"): sendTextMessage("Результат вычислений для " + userName + ": " + calculate(message.split(": ")[1].substring(10))); break;
                case("/погода"): sendTextMessage(parsing()); break;
                case("/баш"): sendTextMessage("\n" + bash()); break;
                case("/картинка"): sendTextMessage("IMAGE " + userName + " " + imageLink()); break;
                case("/предскажи"): sendTextMessage("<" + userName + ">: " + predict()); break;
                default: sendTextMessage("<" + userName + ">: " + "Не понял вас, повторите запрос...");
            }
            }
        }

        @Override
        /**Отправляет приветственные пояснительные сообщения после установки соединения*/
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: /дата, /время, /день, /погода, /баш, /картинка, /предскажи, /посчитай.");
            sendTextMessage("Доступные операции: ^ + - * /. Пример ввода: 2 ^ 3");
            super.clientMainLoop();
        }

        private Double calculate(String str) {
            String[] num = str.split(" ");
            try {
            switch (num[1]) {
                case ("*"): return Double.valueOf(num[0]) * Double.valueOf(num[2]);
                case ("/"): return Double.valueOf(num[0]) / Double.valueOf(num[2]);
                case ("+"): return Double.valueOf(num[0]) + Double.valueOf(num[2]);
                case ("-"): return Double.valueOf(num[0]) - Double.valueOf(num[2]);
                case ("^"): return Math.pow(Double.valueOf(num[0]),Double.valueOf(num[2]));
                default: sendTextMessage("Недоступная операция!");
            }
            }
            catch(ArrayIndexOutOfBoundsException e){sendTextMessage("Возможно вы забыли про пробелы или ввели какую-то дичь");}
            return null;
        }
        private String parsing() {
            String url = "https://rp5.ru/%D0%9F%D0%BE%D0%B3%D0%BE%D0%B4%D0%B0_%D0%B2_%D0%9C%D0%BE%D1%81%D0%BA%D0%B2%D0%B5_(%D1%86%D0%B5%D0%BD%D1%82%D1%80,_%D0%91%D0%B0%D0%BB%D1%87%D1%83%D0%B3)";
            try {
                Document doc = Jsoup.connect(url).get();
                url = doc.select("meta[name=description]").toString();
                url = url.substring(34, url.length()-6);
            }
            catch(IOException e){}
            return url;
        }

        private String bash() {
            String url = "https://bash.im/random";
            try {
                Document doc = Jsoup.connect(url).get();
                Element el = doc.getElementsByClass("quote__body").get(0);
                url = el.html().replaceAll("(<br>)\n+(\\1)*", "\n").replaceAll("<br>", "");
            }
            catch(IOException e) {}
            return url;
        }

        private String imageLink() {
            String url = "https://assets-natgeotv.fnghub.com/POD/";
               int number = 0;
               while (number%2 == 0) number = (int) (Math.random()*10244)+939;
               url += number + ".jpg";
            return url;
        }

        private String predict() {
            ArrayList<Integer> numbersList = new ArrayList<>();
            HashMap<Integer, String> answers = new HashMap<>();
            for (int i = 0; i < 10;) {
                numbersList.add(++i);
            }
            answers.put(1,"Возможно");
            answers.put(2,"Да");
            answers.put(3,"Нет");
            answers.put(4,"Скорее всего да");
            answers.put(5,"Скорее всего нет");
            answers.put(6,"50 на 50");
            answers.put(7,"Есть такая вероятность");
            answers.put(8,"Особо не надейся");
            answers.put(9,"Есть небольшой шанс");
            answers.put(10,"Шансы на это весьма высоки");
            Collections.shuffle(numbersList);
            return answers.get((int)(Math.random()*10)+1);
        }
    }
}
