package SocketChat.model.tools;

public abstract class Sender {
    public Message BuildMessage(UserData userdata, String body){
        return new Message(userdata, body);
    }
}
