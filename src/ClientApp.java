import java.util.Scanner;

import model.client.Client;
import model.tools.UserData;

public class ClientApp {
    
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        System.out.print("Select a username : \n> ");
        String username;
        UserData ud;
        Scanner scan = new Scanner(System.in);
        try{
            username = scan.nextLine();
            if(username.trim().isBlank()){
                throw new Exception("Pseudo Invalide, connexion en mode anonyme");
            } 
            ud = new UserData(username);
        }catch(Exception e){
            System.err.println(e.getMessage());
            ud = UserData.ANONYMOUS();
        }
        Client client = new Client(ud);
        try {
            client.connectToServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
