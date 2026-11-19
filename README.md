# SocketChat : messagerie client-serveur en Java

![CI](https://github.com/LimHounTchenAime/socketchat/actions/workflows/ci.yml/badge.svg)

Salon de discussion en temps réel : un serveur TCP multi-clients et un client graphique JavaFX qui échangent des
objets Java sérialisés sur des sockets. Projet du cours « Architecture des applications Internet » (master
informatique, 2025).

## Fonctionnalités

- Serveur sur le port 5555 : un thread par client connecté, diffusion de chaque message à tous les participants,
  message de bienvenue à la connexion.
- Client JavaFX (vues FXML) : choix d'un pseudo, puis fenêtre de discussion avec bulles de messages.
- Protocole : objets `Message` et `UserData` transmis par `ObjectOutputStream` / `ObjectInputStream`.

## Lancer

Prérequis : JDK 17 ou plus récent et Maven.

```bash
mvn compile
java -cp target/classes SocketChat.ServerApp   # serveur
mvn javafx:run                                  # un client ; relancer la commande pour en ouvrir d'autres
```

## Organisation

```
src/main/java/SocketChat
├── ServerApp, ClientApp   points d'entrée
├── controller/            contrôleurs JavaFX (connexion, discussion, bulle de message)
└── model/
    ├── server/            Server (acceptation des connexions), StubClient (un thread par client)
    ├── client/            Client (connexion et écoute du serveur)
    └── tools/             SocketManager, Message, UserData
src/main/resources/SocketChat   vues FXML
```

## Équipe

Aimé Lim Houn Tchen, MaTrabY
