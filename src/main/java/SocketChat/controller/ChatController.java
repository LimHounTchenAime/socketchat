package SocketChat.controller;

import java.io.IOException;

import SocketChat.model.client.Client;
import SocketChat.model.tools.Message;
import SocketChat.model.tools.UserData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChatController {

    @FXML private VBox rootVBox;
    @FXML private ScrollPane scrollPane;
    @FXML private VBox messagesVBox;
    @FXML private TextField messageTextField;
    @FXML private Button sendButton;

    private Client client;

    @FXML
    public void setUserPseudo(String pseudo) {

        // On crée le client AVEC le pseudo choisi
        client = new Client(new UserData(pseudo));

        // Callback réception messages
        client.setOnMessageReceived(message -> {
            Platform.runLater(() -> 
                addMessage(message)
            );
        });

        try {
            client.connectToServer();
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Bouton + enter
        sendButton.setOnAction(e -> sendMessage());
        messageTextField.setOnAction(e -> sendMessage());
    }

    private void sendMessage() {
        String text = messageTextField.getText().trim();

        if (text.isEmpty()) return;

        try {
            // Crée un message complet avec l'auteur
            Message msg = client.BuildMessage(client.getUserData(), text);

            // 🔥 Envoi SOCKET via ton modèle
            client.sendMessage(msg);

            // Ajout uniquement visuel (côté expéditeur)
            addMessage(msg);

        } catch (IOException e) {
            e.printStackTrace();
        }

        messageTextField.clear();
    }

    public void addMessage(Message message) {
        boolean isOwnMessage = message.getAuthor().equals(client.getUserData());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/SocketChat/MessageBubble.fxml"));
            HBox bubble = loader.load();

            MessageBubbleController controller = loader.getController();

            controller.setMessage(message, isOwnMessage);

            if (isOwnMessage) {
                bubble.setStyle("-fx-alignment: CENTER-LEFT;");
            } else {
                bubble.setStyle("-fx-alignment: CENTER-RIGHT;");
            }

            messagesVBox.getChildren().add(bubble);

            scrollPane.layout();
            scrollPane.setVvalue(1.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
