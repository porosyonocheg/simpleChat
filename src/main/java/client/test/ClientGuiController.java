package client.test;

import service.ConsoleHelper;
/**Клиент чата с графическим интерфейсом. Помимо стандартных возможностей консольного клиента имеет возможность
 * принимать изображения в отдельном окне
 * @author Сергей Шершавин*/
public class ClientGuiController extends Client {
    private ClientGuiModel model = new ClientGuiModel();
    private ClientGuiView view = new ClientGuiView(this);

    public static void main(String[] args) {
        new ClientGuiController().run();
    }

    public ClientGuiModel getModel() {
        return model;
    }

    @Override
    public void run() {
        getSocketThread().run();
    }

    @Override
    protected int getServerPort() {
        return view.getServerPort();
    }

    @Override
    protected String getUserName() {
        return view.getUserName();
    }

    @Override
    protected String getServerAddress() {
        return view.getServerAddress();
    }

    @Override
    protected SocketThread getSocketThread() {
        return new GuiSocketThread();
    }

    public class GuiSocketThread extends SocketThread {
        @Override
        protected void processIncomingMessage(String message) {
            if (message.contains("IMAGE")) try {new JFrameWithImage(message.split(" ")[1]);} catch(Exception e) {ConsoleHelper.writeMessage("Something wrong with Image");}
            else {
                model.setNewMessage(message);
                view.refreshMessages();
            }
        }

        @Override
        protected void informAboutAddingNewUser(String userName) {
            model.addUser(userName);
            ConsoleHelper.writeMessage(userName + " присоединился к чату");
            view.refreshUsers();
        }

        @Override
        protected void informAboutDeletingNewUser(String userName) {
            model.deleteUser(userName);
            ConsoleHelper.writeMessage(userName + " покинул чат");
            view.refreshUsers();
        }

        @Override
        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            view.notifyConnectionStatusChanged(clientConnected);
            super.notifyConnectionStatusChanged(clientConnected);
        }
    }

}
