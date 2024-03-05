import java.net.*;
import java.io.*;

public class ChatClientThread extends Thread {
    private ChatClient client = null;
    private Socket socket = null;
    private DataInputStream in = null;
    private int id = -1;
    private PrintStream out = null;

    public ChatClientThread(ChatClient client, Socket socket) {
        super();
        this.client = client;
        this.socket = socket;
        id = socket.getPort();
    }

    public void send(String msg) {
        out.println(msg);
        out.flush();
    }

    public int getID() {
        return id;
    }

    public void run() {
        System.out.println("Server thread " + id + " running");
        while (true) {
            try {
                client.handle(in.readLine());
            } catch (IOException e) {
                System.out.println(id + " error reading: " + e.getMessage());
                client.stop();
            }
        }
    }

    public void open() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        out = new PrintStream(socket.getOutputStream());
    }

    public void close() throws IOException {
        if (socket != null)
            socket.close();
        if (in != null)
            in.close();
        if (out != null)
            out.close();
    }
}

