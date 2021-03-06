package service;

import java.io.*;
import java.net.Socket;
//import java.net.SocketAddress;
/** Класс соединения содержит сокет текущего подключения, а также потоки ввода-вывода,
 * отвечающие за сериализацию сообщений для данного клиента
 * @author Сергей Шершавин*/
public class Connection implements Closeable {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void send(Message message) throws IOException {
        synchronized (out) {
            out.writeObject(message);
        }
    }

    public Message receive() throws IOException, ClassNotFoundException {
        synchronized (in) {
        return  (Message)in.readObject();
        }
    }
/*
    public SocketAddress getRemoteSocketAddress() {
    return socket.getRemoteSocketAddress();
    }

    public Socket getSocket() {
        return socket;
    }*/

    public void close() throws IOException {
        socket.close();
        out.close();
        in.close();
    }
}
