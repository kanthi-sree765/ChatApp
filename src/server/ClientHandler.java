package server;

import model.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ArrayList<ClientHandler> clients;

    private String username;

    public ClientHandler(Socket socket, ArrayList<ClientHandler> clients) {
        this.socket = socket;
        this.clients = clients;

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            username = (String) input.readObject();
            System.out.println(username + " joined");

            while (true) {
                Message msg = (Message) input.readObject();
                handleMessage(msg);
            }

        } catch (Exception e) {
            System.out.println(username + " disconnected");
        }
    }

    private void handleMessage(Message msg) {
        if (msg.getReceiver() == null) {
            broadcast(msg);
        } else {
            sendPrivate(msg);
        }
    }

    private void broadcast(Message msg) {
        for (ClientHandler client : clients) {
            try {
                client.output.writeObject(msg);
                client.output.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendPrivate(Message msg) {
        for (ClientHandler client : clients) {
            if (client.username.equals(msg.getReceiver())) {
                try {
                    client.output.writeObject(msg);
                    client.output.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}