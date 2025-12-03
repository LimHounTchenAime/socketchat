package model.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

import model.Message;
import model.SocketManager;
import model.UserData;
import model.type.Sender;

public class Client extends Sender {
    private SocketManager socketManager;
    private UserData userData;

    public Client(UserData userData) {
        this.socketManager = new SocketManager();
        this.userData = userData;
        System.out.println("Bienvenue, " + userData.getPseudo());
    }

    @SuppressWarnings("resource")
    public void connectToServer() throws IOException, ClassNotFoundException{
        System.out.println("Connection au server localhost:5555");
        this.socketManager.connect(new InetSocketAddress("localhost", 5555));
        
        Message message = this.BuildMessage(userData, "established");  // Peut etre insérer cette logique lors de la connection mais difficile à executer car socketManager n'est pas sensé avoir accès aux messages
        this.socketManager.sendMessage(message);                            //
        this.launchListening();                                             //

        Scanner keyboard = new Scanner(System.in);
        System.out.print("commencez à écrire un message : ");
        while (true){
            if(keyboard.hasNextLine()){
                String line = keyboard.nextLine();
                message = this.BuildMessage(userData, line);
                this.socketManager.sendMessage(message);
            }
        }
    }

    public void launchListening(){ 
        new Thread(() -> {
            try {
                while (!this.socketManager.isClosed()) {
                    Message message = this.socketManager.readMessage();
                    this.printMessage(message);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

     // Pourquoi pas utiliser cette fonction comme point d'entrée sur le JavaFx
    public void printMessage(Message message) {
        System.out.println(message.getAuthor().getPseudo() + " : " + message.getBody());
    }
    
    public void quitServer() throws IOException {
        this.socketManager.close();
    }
}
