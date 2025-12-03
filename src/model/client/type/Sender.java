package model.client.type;

import model.tools.Message;
import model.tools.UserData;

public abstract class Sender {
    public Message BuildMessage(UserData userdata, String body){
        return new Message(userdata, body);
    }
}
