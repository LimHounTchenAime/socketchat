package SocketChat.model.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import SocketChat.model.tools.Message;
import SocketChat.model.tools.Sender;
import SocketChat.model.tools.UserData;


public class Server extends Sender {

    private List<StubClient> clients;
    private ServerSocket server;

    public Server() {
        this.clients = new ArrayList<StubClient>();
        try {
            this.server = new ServerSocket(5555);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void launch() {
        new Thread(() -> {
            while(true){
                try {
                    Socket comm = this.server.accept();
                    this.addClient(comm);
                    System.out.println("Connection établie par un client");
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    private void addClient(Socket comm){
        StubClient stub = new StubClient(comm, ud -> this.deleteClient(ud), message -> this.notifyObservers(message));
        this.clients.add(stub);
        stub.start();
        try {
            Message welcome = this.BuildMessage("Bienvenue sur le chat !");
            stub.notify(welcome);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private void deleteClient(UserData userData){
        this.clients.removeIf(client -> client.getUserData().equals(userData));    
        System.out.println("bye bye, " + userData.getPseudo());
    }
    
    private void notifyObservers(Message message) {
        for (StubClient instanceClient : clients) {
            try {
                if(!instanceClient.getUserData().equals(message.getAuthor())){
                    instanceClient.notify(message);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    public void close(){
        try {
            this.server.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    private Message BuildMessage(String string) {
        return this.BuildMessage(UserData.SERVER(), string);
    }


}
