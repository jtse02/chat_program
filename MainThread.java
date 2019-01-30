import java.net.*;
import java.io.*;
public class MainThread extends Thread{
    static Socket clientSocket = null;
    static ServerSocket mainSocket = null;
    public MainThread(ServerSocket s){
        this.mainSocket = s;
    }
    public void run(){
        while (true) {
            try {
            clientSocket = mainSocket.accept();
            } catch (IOException e){
                System.err.println("Error loading client: " + e);
                System.exit(1);
            }
            new ClientThread(clientSocket).start();
        }
    }
}
