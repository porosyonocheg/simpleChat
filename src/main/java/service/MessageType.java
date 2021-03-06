package service;
/**enum содержит типы сообщений, которые используются в чате, для определенности действий сервера:
 * 1. Сообщение содержит ЗАПРОС ИМЕНИ клиента
 * 2. Сообщение содержит ИМЯ КЛИЕНТА в ответ на запрос
 * 3. Сообщение подтверждающие, что имя клиента принято
 * 4. Текстовое сообщение
 * 5. Сообщение с информацией о добавлении нового клиента
 * 6. Сообщение с информацией об отключении определенного клиента
 * @author Сергей Шершавин
 * */
public enum MessageType {
    NAME_REQUEST,
    USER_NAME,
    NAME_ACCEPTED,
    TEXT,
    USER_ADDED,
    USER_REMOVED
}
