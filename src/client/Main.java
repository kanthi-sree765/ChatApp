package client;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        String username = JOptionPane.showInputDialog("Enter your name:");

        if (username != null && !username.trim().isEmpty()) {
            new ChatUI(username);
        }
    }
}