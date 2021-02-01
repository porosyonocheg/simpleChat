package server;


import service.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/** Сервер чата содержит класс-обработчик, отвечающий за пересылку сообщения, отправленного опеределенным клиентом, всем клиентам чата. Хранит карту "имя клиента - соединение" (@see{@link Connection}
 *
 * @author Сергей Шершавин*/
public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    private static class Handler extends Thread {
       private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            ConsoleHelper.writeMessage("Установлено соединение с удалённым адресом: " + socket.getRemoteSocketAddress());
            String userName = null;
            try {
            Connection connection = new Connection(socket);
            userName = serverHandshake(connection);
            sendBroadcastMessage(new Message(MessageType.USER_ADDED,userName));
            notifyUsers(connection,userName);
            serverMainLoop(connection,userName);
            }
            catch (ClassNotFoundException | IOException ex) {ConsoleHelper.writeMessage("Ошибка при обмене данными с удалённым адресом");}
            finally {
                if (userName != null) {
                    connectionMap.remove(userName);
                    sendBroadcastMessage(new Message(MessageType.USER_REMOVED,userName));
                }
                ConsoleHelper.writeMessage("Соединение с удалённым адресом закрыто.");
            }
        }
/**Диалог получения имени нового клиента чата и включения его в карту "имя - соединение"
 * @param connection - */
        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {

            while (true) {
                connection.send(new Message(MessageType.NAME_REQUEST, "Введите имя"));
                Message message = connection.receive();
                if (message.getType().equals(MessageType.USER_NAME) && !message.getData().isEmpty() && !connectionMap.containsKey(message.getData())) {
                    connectionMap.put(message.getData(), connection);
                    connection.send(new Message(MessageType.NAME_ACCEPTED, "Имя принято"));
                    return message.getData();
                }
            }
        }
/**Уведомление активных клиентов чата о подключении нового клиента*/
        private void notifyUsers(Connection connection, String userName) throws IOException {
            for (Map.Entry<String,Connection> en : connectionMap.entrySet()) {
                if (!en.getKey().equals(userName)) {
                    connection.send(new Message(MessageType.USER_ADDED,en.getKey()));
                }
            }
        }
/** */
        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message msg = connection.receive();
                if (msg.getType() == MessageType.TEXT) {
                    String current = msg.getData();
                    if (current.contains("IMAGE")) connectionMap.get(current.split(" ")[1]).send(new Message(MessageType.TEXT, "IMAGE " + current.split(" ")[2]));
                 else {
                    for (Map.Entry<String,String> entry : Glossary.getMap().entrySet())
                            current = current.replaceAll(entry.getKey(), entry.getValue());
                    sendBroadcastMessage(new Message(MessageType.TEXT,"<" + userName + ">: " + current));
                  }
                }
                else ConsoleHelper.writeMessage("Данное сообщение не является текстом");
            }
        }
    }
/** Метод отправки сообщений всем клиентам чата:
 * @param message - пересылаемое сообщение*/
    public static void sendBroadcastMessage(Message message) {
        for (Connection connection : connectionMap.values()) {
            try {
                connection.send(message);
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Ошибка отправки сообщения");
            }
        }
    }
/** Запуск сервера в отдельном потоке при назначении номера определенного порта, вводимого в консоль*/
    public static void main(String[] args) {
        try (
            ServerSocket serverSocket = new ServerSocket(ConsoleHelper.readInt())) {
            ConsoleHelper.writeMessage("Сервер запущен");
            while (true) {
                new Handler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
