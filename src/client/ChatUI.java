package client;

import model.Message;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatUI extends JFrame {

    private JTextPane chatPane;
    private JTextField messageField;
    private JButton sendButton;

    private ChatClient client;
    private String username;

    // 🎨 Pixel theme
    private final Color BG = new Color(170, 140, 255);
    private final Color PANEL = new Color(255, 240, 250);
    private final Color TEXT = new Color(110, 95, 130);
    private final Color BORDER = new Color(255, 200, 230);

    public ChatUI(String username) {
        this.username = username;

        setTitle("Pixel Chat ✦ " + username);
        setSize(520, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 🧸 Chat Pane (supports images)
        chatPane = new JTextPane();
        chatPane.setEditable(false);
        chatPane.setBackground(PANEL);

        JScrollPane scrollPane = new JScrollPane(chatPane);
        scrollPane.setBorder(new LineBorder(BORDER, 4));

        // 🪟 Input
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(BG);

        messageField = new JTextField();
        messageField.setFont(new Font("Monospaced", Font.BOLD, 18));
        messageField.setBackground(PANEL);
        messageField.setForeground(TEXT);
        messageField.setCaretColor(TEXT);
        messageField.setBorder(new LineBorder(BORDER, 4));

        sendButton = new JButton("OK");
        sendButton.setFont(new Font("Monospaced", Font.BOLD, 14));
        sendButton.setBackground(new Color(255, 210, 230));
        sendButton.setForeground(TEXT);
        sendButton.setBorder(new LineBorder(BORDER, 4));

        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        sendButton.addActionListener(e -> sendMessage());
        messageField.addActionListener(e -> sendMessage());

        connect();

        setVisible(true);
    }

    private void connect() {
        try {
            client = new ChatClient("localhost", 1234, username);

            client.receiveMessages(msg -> {
                SwingUtilities.invokeLater(() -> {
                    appendStyledMessage(msg);
                });
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Connection failed");
        }
    }

    // 💖 Styled message with icon
    private void appendStyledMessage(Message msg) {
        try {
            StyledDocument doc = chatPane.getStyledDocument();

            Style style = chatPane.addStyle("Style", null);
            StyleConstants.setFontFamily(style, "Monospaced");
            StyleConstants.setBold(style, true);
            StyleConstants.setFontSize(style, 16);
            StyleConstants.setForeground(style, TEXT);

            String time = new SimpleDateFormat("HH:mm")
                    .format(new Date(msg.getTimestamp()));

            // 🧸 Choose icon randomly (or based on sender)
            ImageIcon original = new ImageIcon(getClass().getResource("/assets/avo.png"));

            Image scaled = original.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

            ImageIcon icon = new ImageIcon(scaled);
            chatPane.setCaretPosition(doc.getLength());
            chatPane.insertIcon(icon);

            doc.insertString(doc.getLength(),
                    " " + msg.getSender() + " [" + time + "]\n",
                    style);

            doc.insertString(doc.getLength(),
                    "   " + msg.getContent() + "\n\n",
                    style);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String text = messageField.getText().trim();

        if (!text.isEmpty()) {
            Message msg = new Message(username, null, text);
            client.sendMessage(msg);
            messageField.setText("");
        }
    }
}