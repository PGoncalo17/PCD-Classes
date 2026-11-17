import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    public static final int PORTO = 8080;
    private Map <String, ObjectOutputStream> clients = new LinkedHashMap<>();

    public static void main(String[] args) {
        try {
            new ChatServer().startServing();
        } catch (IOException e) { }
    }

    public void startServing() throws IOException {
        ServerSocket ss = new ServerSocket(PORTO);
        System.out.println("Chat Server active in: " + PORTO + "...");
        try {
            while (true) {
                Socket socket = ss.accept();
                new ClientHandler(socket).start();
            }
        } finally {
            ss.close();
        }
    }

    // Ex 4.1
    // private synchronized void addClient(ObjectOutputStream out) {
    //     clientWriters.add(out);
    // }
    
    // private synchronized void removeClient(ObjectOutputStream out) {
    //     clientWriters.remove(out);
    // }

    // private synchronized void broadcast(String msg) throws IOException {
    //     System.out.print(msg);
    //     for (ObjectOutputStream out : clientWriters) {
    //         out.writeObject(msg + "\n");
    //     }
    // }

    private synchronized void registerClient(String username, ObjectOutputStream out) {
        clients.put(username, out);
        broadcastMessage(new ChatMessage(ChatMessage.Type.PUBLIC_MSG, "System", null, username + " joined chat."));
        broadcastUserList();
    }

    private synchronized void unregisterClient(String username) {
        if (username != null && clients.remove(username) != null) {
            broadcastMessage(new ChatMessage(ChatMessage.Type.PUBLIC_MSG, "System", null, username + " left chat."));
            broadcastUserList();
        }
    }

    private synchronized void broadcastUserList() {
        List<String> userList = new ArrayList<>(clients.keySet());
        ChatMessage listMsg = new ChatMessage(userList);
        broadcastMessage(listMsg);
    }

    private synchronized void broadcastMessage(ChatMessage msg) {
        if (msg.getType() == ChatMessage.Type.PUBLIC_MSG) {
            System.out.println(msg.getSender() + ": " + msg.getContent());
        }
        for (ObjectOutputStream out : clients.values()) {
            try {
                out.writeObject(msg);
                out.reset(); 
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void sendPrivateMessage(ChatMessage msg) {
        System.out.println("[Private] " + msg.getSender() + " -> " + msg.getRecipient() + ": " + msg.getContent());
        ObjectOutputStream recipientOut = clients.get(msg.getRecipient());
        ObjectOutputStream senderOut = clients.get(msg.getSender());

        try {
            if (recipientOut != null) {
                recipientOut.writeObject(msg);
                recipientOut.reset();
                recipientOut.flush();
            }

            if (senderOut != null && senderOut != recipientOut) {
                senderOut.writeObject(msg);
                senderOut.reset();
                senderOut.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private class ClientHandler extends Thread {
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                username = (String) in.readLine();
                registerClient(username, out);

                while (true) {
                    ChatMessage msg = (ChatMessage) in.readObject();
                    if (msg.getType() == ChatMessage.Type.PRIVATE_MSG) {
                        sendPrivateMessage(msg);
                    } else if (msg.getType() == ChatMessage.Type.PUBLIC_MSG) {
                        broadcastMessage(msg);
                    }
                }
            } catch (Exception e) { }
            finally {
                unregisterClient(username);
                try {
                    socket.close();
                } catch (IOException e) { }
            }
        }
    }


}