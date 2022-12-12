import Server.LoginServer;
import Server.MainServer;

public class Main {
    public static final int loginServerPort = 20814;
    public static final int MainServerPort = 20815;

    public static void main(String[] args) {
        LoginServer loginServer = new LoginServer(loginServerPort);
        MainServer mainServer = new MainServer(MainServerPort);


        loginServer.start();
        mainServer.start();
    }
}