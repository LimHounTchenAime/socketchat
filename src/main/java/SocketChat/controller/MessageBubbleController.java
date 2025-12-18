package SocketChat.controller;

import SocketChat.model.tools.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MessageBubbleController {

    @FXML
    private Label label1; // pour le texte du message

    @FXML
    private Label label; // pour le nom de l'expéditeur

    public void setMessage(Message message, boolean isLocal) {
        label.setText(isLocal ? "Moi" : message.getAuthor().getPseudo());
        label1.setText(message.getBody());
    }


}
