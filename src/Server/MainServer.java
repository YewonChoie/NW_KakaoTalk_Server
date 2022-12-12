package Server;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer extends Thread {
    private final int port;
    private final HashMap<String, PrintWriter> userInfo = new HashMap<String, PrintWriter>();

    public MainServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        ExecutorService MainServerPool = Executors.newFixedThreadPool(10000);

        try (ServerSocket listener = new ServerSocket(port)){
            System.out.println("Main Server Online...");
            while (true) {
                MainServerPool.execute(new MainServerProcessor(listener.accept()));
            }
        } catch(Exception e) {
            System.out.println("Main Server Failed... " + e.getMessage());
        }
    }

    public static class MainServerProcessor implements Runnable {
        private Socket socket;
        private Scanner serverInput;
        private PrintWriter serverOutput;

        public MainServerProcessor(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
