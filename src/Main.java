import Server.LoginServer;

public class Main {
    public static final int loginServerPort = 20814;
    public static final int RegistServerPort = 20815;

    public static void main(String[] args) {
        LoginServer loginServer = new LoginServer(loginServerPort);

        loginServer.start();
    }
}