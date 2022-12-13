package Server;

import Query.ConnectDB;
import Query.Insert;
import Query.Select;
import Utilization.Util;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
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
                // 클라이언트와 연결
            }
        } catch(Exception e) {
            System.out.println("Login Server Failed... " + e.getMessage());
        }
    }

    public static class LoginServerProcessor implements Runnable {
        private Socket socket;
        private Scanner serverInput;
        private PrintWriter serverOutput;

        public LoginServerProcessor(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                System.out.println(socket.getInetAddress().getHostAddress());
                serverInput = new Scanner(socket.getInputStream());
                serverOutput = new PrintWriter(socket.getOutputStream(), true);
                // 인풋 아웃풋 설정

                while (serverInput.hasNextLine()) {
                    String request = serverInput.nextLine();
                    // 클라이언트 요청 수신

                    JSONParser parser = new JSONParser();
                    JSONObject client = (JSONObject) parser.parse(request);
                    // JSON 파싱

                    if (!request.isEmpty()) {
                        int requestCode = Integer.parseInt(String.valueOf(client.get("code")));
                        // 요청 코드 확인

                        switch (requestCode) {
                            case 1001:
                                checkIdProcess(client);
                                break;
                                // 아이디 중복 확인
                            case 1002:
                                checkNicknameProcess(client);
                                break;
                                // 닉네임 중복 확인
                            case 1003:
                                checkEmailProcess(client);
                                break;
                                // 이메일 중복 확인
                            case 1004:
                                registProcess(client);
                                break;
                                // 회원가입 프로세스
                            case 2001:
                                loginProcess(client);
                                break;
                                // 로그인 프로세스
                            default:
                                break;
                        }
                    }
                } // 클라이언트의 요청이 있을 경우
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
            // 데이터베이스에서 탐색

            if (isPossible == true) {
                serverOutput.println(Util.createSingleJSON(200, "id", "true"));
            } // 사용 가능한 경우
            else {
                serverOutput.println(Util.createSingleJSON(200, "id", "false"));
            } // 사용 불가능한 경우
        } // ID 중복 확인 프로세스

        private void checkNicknameProcess(JSONObject clientJson) {
            System.out.println(Util.createLogString("Login", socket.getInetAddress().getHostAddress(), "Nickname Check Request"));

            String nickName = String.valueOf(clientJson.get("nickname"));
            System.out.println(nickName);
            boolean isPossible = Select.isNicknamePossible(new ConnectDB(), nickName);
            System.out.println(isPossible);
            // 데이터베이스에서 탐색

            if (isPossible == true) {
                serverOutput.println(Util.createSingleJSON(200, "nickname", "true"));
            } // 사용 가능한 경우
            else {
                serverOutput.println(Util.createSingleJSON(200, "nickName", "false"));
            } // 사용 불가능한 경우
        } // 닉네임 중복 확인 프로세스

        private void checkEmailProcess(JSONObject clientJson) {
            System.out.println(Util.createLogString("Login", socket.getInetAddress().getHostAddress(), "Email Check Request"));

            String email = String.valueOf(clientJson.get("email"));
            System.out.println(email);
            boolean isPossible = Select.isEmailPossible(new ConnectDB(), email);
            System.out.println(isPossible);
            // 데이터베이스에서 탐색

            if (isPossible == true) {
                serverOutput.println(Util.createSingleJSON(200, "email", "true"));
            } // 사용 가능한 경우
            else {
                serverOutput.println(Util.createSingleJSON(200, "email", "false"));
            } // 사용 불가능한 경우
        } // 이메일 중복 확인 프로세스

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
            // 요청받은 유저 정보 저장

            boolean isSuccess = Insert.InsertUserInfo(new ConnectDB(), id, pwd, name, gender, nickName, birth, phone, email);
            // 데이터베이스에 추가

            if (isSuccess == true) {
                System.out.println(Util.createLogString("Login", socket.getInetAddress().getHostAddress(), "Register Success"));
                serverOutput.println(Util.createSingleJSON(200, "registration", "true"));
            } // 성공적으로 추가된 경우
            else {
                System.out.println(Util.createLogString("Login", socket.getInetAddress().getHostAddress(), "Register Failed"));
                serverOutput.println(Util.createSingleJSON(200, "registration", "false"));
            } // 추가에 실패한 경우
            System.out.println(isSuccess);
        } // 회원가입 프로세스

        private void loginProcess(JSONObject clientJSON) {
            System.out.println(Util.createLogString("Login", socket.getInetAddress().getHostAddress(), "Login Request"));

            String id = String.valueOf(clientJSON.get("id"));
            String pwd = String.valueOf(clientJSON.get("pwd"));

            String nickName = Select.Login(new ConnectDB(), id, pwd);
            // 데이터베이스에서 탐색

            if (!(nickName.equals("") && nickName.equals(null))) {
                System.out.println(Util.createLogString("Login", socket.getInetAddress().getHostAddress(), "Login Success"));

                HashMap<String, Object> loginResponse = new HashMap<String, Object>();
                loginResponse.put("nickname", nickName);
                loginResponse.put("login", "true");
                loginResponse.put("port", "20815");

                serverOutput.println(Util.createJSON(200, loginResponse));
                // 클라이언트에게 결과 전송

                try {
                    serverInput.close();
                    serverOutput.close();
                    socket.close();
                    // 로그인 서버 소켓 연결 종료
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } // 성공적으로 로그인한 경우
            else {
                System.out.println(Util.createLogString("Login", socket.getInetAddress().getHostAddress(), "Login Failed"));

                HashMap<String, Object> loginResponse = new HashMap<String, Object>();
                loginResponse.put("nickname", "");
                loginResponse.put("login", "false");

                serverOutput.println(Util.createJSON(200, loginResponse));
                // 클라이언트에게 결과 전송
            } // 로그인 실패한 경우
            System.out.println(nickName);
        } // 로그인 프로세스
    }
}
