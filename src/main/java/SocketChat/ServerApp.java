package SocketChat;
import SocketChat.model.server.Server;

/**
 * Hello world!
 *
 */
public class ServerApp 
{
    public static void main( String[] args )
    {
        Server server = new Server();
        server.launch();
    }
}
 