package service;

import java.io.Serializable;
/** Вспомогательный класс, содержащий типизацию текстовых данных
 * @author Сергей Шершавин*/
public class Message implements Serializable {
    private final MessageType type;
    private final String data;

    public Message(MessageType type) {
        this.type = type;
        data = null;
    }
/** Конструктор содержит:
 * @param type - тип текстового сообщения @see{@link MessageType}
 * @param data - сам текст*/
    public Message(MessageType type, String data) {
        this.type = type;
        this.data = data;
    }

    public MessageType getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}
