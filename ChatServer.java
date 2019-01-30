import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServer {
    static ServerSocket mainSocket = null;
    public static Map<String, Socket> users = null;
    public static BlockingQueue<String> messages = null;

    public static void main(String[] args) {
        int PORT = 0;
        users = new ConcurrentHashMap<String, Socket>();
        messages = new LinkedBlockingQueue<String>();
        if (args.length != 1) {
            System.err.println("Usage: main PORT");
            System.exit(1);
        }
        try {
            PORT = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Error: " + e);
            System.exit(1);
        }

        try {
            System.out.println("Port:" + PORT);
            mainSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println("Error opening server: " + e);
            System.exit(1);
        }

        new MainThread(mainSocket).start();
        while (true) {
            while (ChatServer.messages.size() != 0) {
                broadcast(ChatServer.messages.remove());
            }
        }

    }

    public static void broadcast(String message) {
        for (Map.Entry<String, Socket> entry : users.entrySet()) {
            try {
                PrintWriter out = new PrintWriter((entry.getValue().getOutputStream()));
                out.print(message + "\r\n");
                out.flush();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

}