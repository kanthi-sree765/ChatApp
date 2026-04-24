package client;

import model.Message;

import java.io.*;
import java.net.Socket;

public class ChatClient {

    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ChatClient(String host, int port, String username) throws IOException {
        Socket socket = new Socket(host, port);

        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());

        // send username
        output.writeObject(username);
        output.flush();
    }

    public void sendMessage(Message message) {
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessages(MessageListener listener) {
        new Thread(() -> {
            try {
                while (true) {
                    Message msg = (Message) input.readObject();
                    listener.onMessage(msg);
                }
            } catch (Exception e) {
                listener.onMessage(new Message("System", null, "Disconnected","/assets/avo.png"));
            }
        }).start();
    }

    public interface MessageListener {
        void onMessage(Message message);
    }
}   