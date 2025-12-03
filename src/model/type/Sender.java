package model.type;

import model.Message;
import model.UserData;

public abstract class Sender {
    public Message BuildMessage(UserData userdata, String body){
        return new Message(userdata, body);
    }
}
