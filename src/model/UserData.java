package model;

import java.io.Serializable;

public class UserData implements Serializable {
    private int userId;
    static private int userCpt = 0;
    private String pseudo;
    static private int anonymeCpt = 0;
    
    public UserData(String pseudo){
        this.userId = ++userCpt;
        this.pseudo = pseudo;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public String getPseudo(){
        return this.pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public static UserData ANONYMOUS(){
        return new UserData("Utilisateur anonyme" + ++anonymeCpt);
    }

    public static UserData SERVER(){
        return new UserData("Serveur de Chat");
    }
}
