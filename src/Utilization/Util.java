package Utilization;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;

public class Util {
//    public static ServerInfo getServerInfo() {
//        ServerInfo info = new ServerInfo("localhost", 20814);
//
//        try {
//            // 파일에서 접속 정보 가져오기
//            File serverDataFile = new File("config.dat");
//            FileReader fileReader = new FileReader(serverDataFile);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//
//            // Connection Info를 담고 있는 파일은 첫 번째 줄에 서버 주소, 두 번째 줄에 포트 넘버를 가지고 있음.
//            info.serverIP = bufferedReader.readLine();
//            info.serverPort = Integer.parseInt(bufferedReader.readLine());
//        } catch (Exception e) {
//            System.out.println("Error : " + e.getMessage() + "\nUsing default infomation.");
//        }
//
//        return info;
//    }

    public static String encryptSHA256(String originalString) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] stringBytes = originalString.getBytes(StandardCharsets.UTF_8);
            messageDigest.update(stringBytes);
            return String.format("%064x", new BigInteger(1, messageDigest.digest()));
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    public static String encryptSHA512(String originalString) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            byte[] stringBytes = originalString.getBytes(StandardCharsets.UTF_8);
            messageDigest.update(stringBytes);
            return String.format("%0128x", new BigInteger(1, messageDigest.digest()));
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    public static String createJSON(int code, HashMap<String, Object> elements) {
        JSONObject json = new JSONObject();
        json.put("code", code);

        Iterator<String> keyIterator = elements.keySet().iterator();

        while(keyIterator.hasNext()) {
            String key = keyIterator.next();
            Object value = elements.get(key);
            json.put(key, String.valueOf(value));
        }

        return json.toString();
    }

    public static String createSingleJSON(int code, String key, String value) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put(key, value);

        return json.toString();
    }

    public static String createLogString(String tag, String IP, String msg) {
        return "[" + tag + "][" + IP + "] " + msg;
    }
}
