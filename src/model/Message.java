package model;
import java.io.Serializable;

public class Message implements Serializable {
    private UserData author;
    private String body;

    public Message(UserData author, String body) {
        this.author = author;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public UserData getAuthor() {
        return author;
    }

}
