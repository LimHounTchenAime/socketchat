package SocketChat.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField pseudoField;

    @FXML
    private void onEnterChat() {
        try {
            String pseudo = pseudoField.getText().trim();
            if (pseudo.isEmpty()) return;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/SocketChat/ChatView.fxml"));
            Scene chatScene = new Scene(loader.load());

            ChatController chatController = loader.getController();
            chatController.setUserPseudo(pseudo);

            Stage stage = (Stage) pseudoField.getScene().getWindow();
            stage.setHeight(600);
            stage.setWidth(1000);
            stage.setScene(chatScene);
            stage.setTitle("Chat - " + pseudo);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
