package SocketChat.model.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.function.Consumer;

import SocketChat.model.tools.Sender;
import SocketChat.model.tools.Message;
import SocketChat.model.tools.SocketManager;
import SocketChat.model.tools.UserData;

public class Client extends Sender {
    
    private SocketManager socketManager;
    private UserData userData;
    private Consumer<Message> onMessageReceived;
    
    public Client(UserData userData) {
        this.socketManager = new SocketManager();
        this.userData = userData;
        System.out.println("Client créé : " + userData.getPseudo());
    }
    
    public UserData getUserData() {
        return userData;
    }

    public void setOnMessageReceived(Consumer<Message> onMessageReceived) {
        this.onMessageReceived = onMessageReceived;
    }
    
    public void connectToServer() throws IOException, ClassNotFoundException {
        System.out.println("Connexion au serveur...");
        this.socketManager.connect(new InetSocketAddress("localhost", 5555));

        // Signal "established"
        Message message = this.BuildMessage(userData, "established");
        this.socketManager.sendMessage(message);

        // Lance l'écoute des messages
        launchListening();
    }

    public void launchListening() {
        new Thread(() -> {
            try {
                while (!this.socketManager.isClosed()) {
                    Message message = this.socketManager.readMessage();
                    printMessage(message);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessage(Message message) throws IOException {
        System.out.println("Moi : " + message.getBody());
        this.socketManager.sendMessage(message);
    }

    public void printMessage(Message message) {
        if (onMessageReceived != null) {
            onMessageReceived.accept(message);
        }

        System.out.println(message.getAuthor().getPseudo() + " : " + message.getBody());
    }

    public void quitServer() throws IOException {
        this.socketManager.close();
    }


}
