package Server;

import Query.ConnectDB;
import Query.Insert;
import Query.Select;
import Utilization.Util;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginServer extends Thread {
    private final int port;

    public LoginServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        ExecutorService loginServerPool = Executors.newFixedThreadPool(1000);

        try (ServerSocket listener = new ServerSocket(port)){
            System.out.println("Login Server Online ...");
            while (true) {
                loginServerPool.execute(new LoginServerProcessor(listener.accept()));
            }
        } catch(Exception e) {
            System.out.println("Login Server Failed... " + e.getMessage());
        }
    }

    public static class LoginServerProcessor implements Runnable {
        private Socket socket;
        private Scanner scanner;
        private PrintWriter writer;

        public LoginServerProcessor(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                System.out.println(socket.getInetAddress().getHostAddress());
                scanner = new Scanner(socket.getInputStream());
                writer = new PrintWriter(socket.getOutputStream(), true);

                while (scanner.hasNextLine()) {
                    String request = scanner.nextLine();

                    JSONParser parser = new JSONParser();
                    JSONObject client = (JSONObject) parser.parse(request);

                    if (!request.isEmpty()) {
                        int requestCode = Integer.parseInt(String.valueOf(client.get("code")));

                        switch (requestCode) {
                            case 1001:
                                checkIdProcess(client);
                                break;
                            case 1002:
                                checkNicknameProcess(client);
                                break;
                            case 1003:
                                checkEmailProcess(client);
                                break;
                            case 1004:
                                registProcess(client);
                                break;
                            case 2001:
                                loginProcess(client);
                            default:
                                break;
                        }
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        private void checkIdProcess(JSONObject clientJson) {
            System.out.println(Util.createLogString("Login", socket.getInetAddress().getHostAddress(), "ID Check Request"));

            String id = String.valueOf(clientJson.get("id"));
            System.out.println(id);
            boolean isPossible = Select.isIdPossible(new ConnectDB(), id);
            System.out.println(isPossible);

            if (isPossible == true) {
                writer.println(Util.createSingleJSON(200, "id", "true"));
            }
            else {
                writer.println(Util.createSingleJSON(200, "id", "false"));
            }
        }

        private void checkNicknameProcess(JSONObject clientJson) {
            System.out.println(Util.createLogString("Login", socket.getInetAddress().getHostAddress(), "Nickname Check Request"));

            String nickName = String.valueOf(clientJson.get("nickname"));
            System.out.println(nickName);
            boolean isPossible = Select.isNicknamePossible(new ConnectDB(), nickName);
            System.out.println(isPossible);

            if (isPossible == true) {
                writer.println(Util.createSingleJSON(200, "nickname", "true"));
            }
            else {
                writer.println(Util.createSingleJSON(200, "nickName", "false"));
            }
        }

        private void checkEmailProcess(JSONObject clientJson) {
            System.out.println(Util.createLogString("Login", socket.getInetAddress().getHostAddress(), "Email Check Request"));

            String email = String.valueOf(clientJson.get("email"));
            System.out.println(email);
            boolean isPossible = Select.isEmailPossible(new ConnectDB(), email);
            System.out.println(isPossible);

            if (isPossible == true) {
                writer.println(Util.createSingleJSON(200, "email", "true"));
            }
            else {
                writer.println(Util.createSingleJSON(200, "email", "false"));
            }
        }

        private void registProcess(JSONObject clientJson) {
            System.out.println(Util.createLogString("Login", socket.getInetAddress().getHostAddress(), "Regist Request"));

            String id = String.valueOf(clientJson.get("id"));
            String pwd = String.valueOf(clientJson.get("pwd"));
            String name = String.valueOf(clientJson.get("name"));
            String gender = String.valueOf(clientJson.get("gender"));
            String nickName = String.valueOf(clientJson.get("nickname"));
            String birth = String.valueOf(clientJson.get("birth"));
            String phone = String.valueOf(clientJson.get("phone"));
            String email = String.valueOf(clientJson.get("email"));

            boolean isSuccess = Insert.InsertUserInfo(new ConnectDB(), id, pwd, name, gender, nickName, birth, phone, email);

            if (isSuccess == true) {
                System.out.println(Util.createLogString("Login", socket.getInetAddress().getHostAddress(), "Register Success"));
                writer.println(Util.createSingleJSON(200, "registration", "true"));
            }
            else {
                System.out.println(Util.createLogString("Login", socket.getInetAddress().getHostAddress(), "Register Failed"));
                writer.println(Util.createSingleJSON(200, "registration", "false"));
            }
            System.out.println(isSuccess);
        }

        private void loginProcess(JSONObject clientJSON) {
            System.out.println(Util.createLogString("Login", socket.getInetAddress().getHostAddress(), "Login Request"));

            String id = String.valueOf(clientJSON.get("id"));
            String pwd = String.valueOf(clientJSON.get("pwd"));

            String nickName = Select.Login(new ConnectDB(), id, pwd);

            if (!(nickName.equals("") && nickName.equals(null))) {
                System.out.println(Util.createLogString("Login", socket.getInetAddress().getHostAddress(), "Login Success"));

                HashMap<String, Object> loginResponse = new HashMap<String, Object>();
                loginResponse.put("nickname", nickName);
                loginResponse.put("login", "true");

                writer.println(Util.createJSON(200, loginResponse));
            }
            else {
                System.out.println(Util.createLogString("Login", socket.getInetAddress().getHostAddress(), "Login Failed"));

                HashMap<String, Object> loginResponse = new HashMap<String, Object>();
                loginResponse.put("nickname", "");
                loginResponse.put("login", "false");

                writer.println(Util.createJSON(200, loginResponse));
            }
            System.out.println(nickName);
        }
    }
}
