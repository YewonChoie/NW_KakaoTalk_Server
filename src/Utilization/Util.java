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
    } // JSON 생성

    public static String createSingleJSON(int code, String key, String value) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put(key, value);

        return json.toString();
    } // 싱글 키 JSON 생성

    public static String createLogString(String tag, String IP, String msg) {
        return "[" + tag + "][" + IP + "] " + msg;
    } // 로그 출력
}
