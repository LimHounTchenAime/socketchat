package SocketChat.controller;

import java.io.IOException;

import SocketChat.model.client.Client;
import SocketChat.model.tools.Message;
import SocketChat.model.tools.UserData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChatController {

    @FXML private VBox rootVBox;
    @FXML private ScrollPane scrollPane;
    @FXML private ListView<HBox> messagesListView;
    @FXML private TextField messageTextField;
    @FXML private Button sendButton;

    private Client client;

    @FXML
    public void setUserPseudo(String pseudo) {

        client = new Client(new UserData(pseudo));
        client.setOnMessageReceived(message -> {
            Platform.runLater(() -> {
                addMessage(message);
            });
        });


        try {
            client.connectToServer();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }


        sendButton.setOnAction(e -> sendMessage());
        messageTextField.setOnAction(e -> sendMessage());
    }

    private void sendMessage() {
        String text = messageTextField.getText().trim();

        if (text.isEmpty()) return;

        try {
            Message msg = client.BuildMessage(client.getUserData(), text);

            client.sendMessage(msg);

            addMessage(msg);

        } catch (IOException e) {
            e.printStackTrace();
        }

        messageTextField.clear();
    }

    public void addMessage(Message message) {
        boolean isOwnMessage = message.getAuthor().equals(client.getUserData());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SocketChat/MessageBubble.fxml"));
            HBox bubble = loader.load();

            MessageBubbleController controller = loader.getController();

            controller.setMessage(message, isOwnMessage);


            Platform.runLater(() -> {
                messagesListView.getItems().add(bubble);

                messagesListView.scrollTo(messagesListView.getItems().size() - 1);
            });
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

