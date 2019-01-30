import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    protected Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter((socket.getOutputStream()));
            String input;
            String username;
            out.print("Enter a nickname\r\n");
            out.flush();
            username = in.readLine();
            while (ChatServer.users.containsKey(username)) {
                out.print("Nickname is already in use!\r\n");
                out.print("Enter a nickname\r\n");
                out.flush();
                username = in.readLine();
            }
            ChatServer.users.put(username, this.socket);
            ChatServer.broadcast(username + " has entered the chat");
            while ((input = in.readLine()) != null) {
                if(input == "quit")
                    break;
                ChatServer.messages.put(username + ": " + input);
            }
            ChatServer.users.remove(username);
            this.socket.close();
            ChatServer.broadcast(username + " has left the chat");
        } catch (IOException e) {
            System.err.println(e);
        } catch (InterruptedException e){
            System.err.println(e);
        }

    }
}