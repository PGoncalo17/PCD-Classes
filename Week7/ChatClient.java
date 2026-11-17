import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class ChatClient {
    private static final String ALL_USERS = "Everyone (Public)";

    private String username;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private JFrame frame;
    private JTextField textField;
    private JTextArea textArea;
    private DefaultListModel<String> userListModel;
    private JList<String> userList;

    public static void main(String[] args) {
        new ChatClient().startClient();
    }

    public void startClient() {
        username = JOptionPane.showInputDialog(null, "Type your username:", "Login", JOptionPane.PLAIN_MESSAGE);
        if (username == null || username.trim().isEmpty()) {
            System.exit(0);
        }

        buildGUI();

        try {
            socket = new Socket(InetAddress.getByName(null), ChatServer.PORTO);
            // Cliente: IN primeiro, OUT depois (ordem inversa ao servidor)[cite: 38, 40]
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            // Envia o username
            out.writeObject(username);
            out.flush();

            // Thread de receção
            new Thread(() -> {
                try {
                    while (true) {
                        ChatMessage msg = (ChatMessage) in.readObject();
                        SwingUtilities.invokeLater(() -> handleIncomingMessage(msg));
                    }
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> textArea.append("[Connection to Server ended]\n"));
                }
            }).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error connecting to Server: " + e.getMessage());
            System.exit(1);
        }
    }

    private void handleIncomingMessage(ChatMessage msg) {
        switch (msg.getType()) {
            case PUBLIC_MSG:
                textArea.append(msg.getSender() + ": " + msg.getContent() + "\n");
                break;

            case PRIVATE_MSG:
                textArea.append("[Private from " + msg.getSender() + " to " + msg.getRecipient() + "]: " + msg.getContent() + "\n");
                break;

            case USER_LIST:
                String selected = userList.getSelectedValue();
                userListModel.clear();
                userListModel.addElement(ALL_USERS);
                for (String user : msg.getActiveUsers()) {
                    userListModel.addElement(user);
                }

                if (selected != null && userListModel.contains(selected)) {
                    userList.setSelectedValue(selected, true);
                } else {
                    userList.setSelectedIndex(0);
                }
                break;
        }
    }

    private void buildGUI() {
        frame = new JFrame("Chat - " + username);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Topo: JTextField + JButton
        JPanel topPanel = new JPanel(new BorderLayout());
        textField = new JTextField();
        JButton sendButton = new JButton("Send");
        topPanel.add(textField, BorderLayout.CENTER);
        topPanel.add(sendButton, BorderLayout.EAST);

        // Centro: JTextArea
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane chatScroll = new JScrollPane(textArea);

        userListModel = new DefaultListModel<>();
        userListModel.addElement(ALL_USERS);
        userList = new JList<>(userListModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setSelectedIndex(0);
        JScrollPane userScroll = new JScrollPane(userList);
        userScroll.setPreferredSize(new Dimension(150, 0));
        userScroll.setBorder(BorderFactory.createTitledBorder("Utilizadores"));

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(chatScroll, BorderLayout.CENTER);
        frame.add(userScroll, BorderLayout.EAST);

        ActionListener sendAction = e -> {
            String text = textField.getText().trim();
            if (!text.isEmpty() && out != null) {
                try {
                    String selectedUser = userList.getSelectedValue();
                    ChatMessage msg;
                    if (selectedUser == null || selectedUser.equals(ALL_USERS)) {
                        msg = new ChatMessage(ChatMessage.Type.PUBLIC_MSG, username, null, text);
                    } else {
                        msg = new ChatMessage(ChatMessage.Type.PRIVATE_MSG, username, selectedUser, text);
                    }
                    out.writeObject(msg);
                    out.reset();
                    out.flush();
                    textField.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };

        sendButton.addActionListener(sendAction);
        textField.addActionListener(sendAction);

        frame.setVisible(true);
    }
}