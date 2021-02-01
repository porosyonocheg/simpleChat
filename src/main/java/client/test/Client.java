package client.test;

import service.Connection;
import service.ConsoleHelper;
import service.Message;
import service.MessageType;

import java.io.IOException;
import java.net.Socket;
/** Версия клиента чата для отправки и получения сообщений через консоль. Класс является базовым для остальных клиентов.
 * @author Сергей Шершавин*/
public class Client {
    protected Connection connection;
    private volatile boolean clientConnected = false;

    public void run() {
        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true);
        socketThread.start();
        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                ConsoleHelper.writeMessage("Ошибка во время ожидания потока сокета");
             }
        }
        if (clientConnected) {
            ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду 'exit'.");
            String current = ConsoleHelper.readString();

            while (!current.equals("exit") && clientConnected) {
                if (shouldSendTextFromConsole()) sendTextMessage(current);
            current = ConsoleHelper.readString();
            }
        }
        else ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");

    }

    protected String getServerAddress() {
        return ConsoleHelper.readString();
    }

    protected int getServerPort() {
        return ConsoleHelper.readInt();
    }

    protected String getUserName() {
        return ConsoleHelper.readString();
    }

    protected boolean shouldSendTextFromConsole() {
        return true;
    }

    protected SocketThread getSocketThread() {
    return new SocketThread();
    }

    protected void sendTextMessage(String text) {
        try {
            connection.send(new Message(MessageType.TEXT, text));
        } catch (IOException e) {
            clientConnected = false;
            ConsoleHelper.writeMessage("Ошибка отправки сообщения");
        }

    }

    public static void main(String[] args) {
        (new Client()).run();
    }

    public class SocketThread extends Thread {

        public void run() {
            String serverAddress = getServerAddress();
            int serverPort = getServerPort();
            try {
                Socket socket = new Socket(serverAddress, serverPort);
                connection = new Connection(socket);
                clientHandshake();
                clientMainLoop();
            }
            catch (IOException | ClassNotFoundException e) {
                notifyConnectionStatusChanged(false);
                ConsoleHelper.writeMessage(e.toString());
            }
        }
/** Отображает в консоль входящее текстовое сообщение от сервера*/
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
        }
/** Отображает в консоль информацию о добавлении нового клиента в чат
 * @param userName - имя нового клиента*/
        protected void informAboutAddingNewUser(String userName) {
            ConsoleHelper.writeMessage(userName + " присоединился к чату");
        }

/** Отображает в консоль информацию об отключении клиента от чата
* @param userName - имя отключенного клиента*/
        protected void informAboutDeletingNewUser(String userName) {
            ConsoleHelper.writeMessage(userName + " покинул чат");
        }

        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this) {
                Client.this.notify();
            }
        }
/** Диалог с сервером на запрос имени нового клиента*/
        protected void clientHandshake() throws IOException, ClassNotFoundException {
            while (true) {
                Message msg = connection.receive();
                if (msg.getType() == MessageType.NAME_REQUEST) {
                    connection.send(new Message(MessageType.USER_NAME, getUserName()));
                } else if (msg.getType() == MessageType.NAME_ACCEPTED) {
                    notifyConnectionStatusChanged(true);
                    return;
                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }
/** Получение сообщений от сервера в бесконечном цикле*/
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            while (true) {
                Message msg = connection.receive();
                if (msg.getType() == MessageType.TEXT) {
                    processIncomingMessage(msg.getData());
                } else if (msg.getType() == MessageType.USER_ADDED) {
                    informAboutAddingNewUser(msg.getData());
                } else if (msg.getType() == MessageType.USER_REMOVED) {
                    informAboutDeletingNewUser(msg.getData());
                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

    }
}
