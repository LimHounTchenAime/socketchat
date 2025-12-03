package model.server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.function.Consumer;

import model.Message;
import model.SocketManager;
import model.UserData;

public class StubClient extends Thread {
    public SocketManager socketManager;
    public Consumer<UserData> onClose;
    public Consumer<Message> onMessage;
    
    public UserData userData;
    
    
    public StubClient(Socket comm, Consumer<UserData> onClose, Consumer<Message> onMessage){
        this.socketManager = new SocketManager(comm);
        this.onClose = onClose;
        this.onMessage = onMessage;
    }
    
    public UserData getUserData() {
        return userData;
    }

    private void diffuseMessage(Message message){
        this.onMessage.accept(message);
    }

    private void closeCommunication(){
        this.onClose.accept(this.userData);
    }

    private void sendMessage(Message message) throws IOException{
        this.socketManager.sendMessage(message);
    }

    public void notify(Message message) throws IOException{
        this.sendMessage(message);
    }

    public void run(){
        try {
            Message message = this.socketManager.readMessage();
            while(true){
                message = (Message) this.socketManager.readMessage();
                this.userData = message.getAuthor();
                this.diffuseMessage(message);
            } 
        } catch (SocketException e) {
            System.err.println(e.getMessage());
            this.closeCommunication();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

}
