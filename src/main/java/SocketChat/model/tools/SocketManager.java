package SocketChat.model.tools;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketManager {
    private Socket comm;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    
    public SocketManager() {
        this.comm = new Socket();
    }

    public SocketManager(Socket socket) {
        this.comm = socket;
        this.initialiseStreams();
    }
    
    public void connect(SocketAddress address) throws IOException{
        this.comm.connect(address);
        this.initialiseStreams();
    }

    public boolean isClosed(){
        return this.comm.isClosed();
    }

    public ObjectInputStream getInput() {
        return input;
    }
    
    public ObjectOutputStream getOutput() {
        return output;
    }

    public void initialiseStreams(){
        try {
            this.output = new ObjectOutputStream(comm.getOutputStream());
            this.output.flush(); 
            this.input = new ObjectInputStream(comm.getInputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) throws IOException{
        //System.out.println("Communication sortante : " + message);
        this.getOutput().writeObject(message);
    }

    public Message readMessage() throws ClassNotFoundException, IOException {
        Message message = (Message) this.input.readObject();
        //System.out.println("Communication entrante : " + message);
        return message;
    }

    public void close(){
        try {
            input.close();
            output.close();
            comm.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
