package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;
    private static File historyFile = new File("chat_history.txt");

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // LOGIN
            out.println("Username:");
            String user = in.readLine();
            out.println("Password:");
            String pass = in.readLine();

            if (!UserDatabase.authenticate(user, pass)) {
                out.println("[SERVER] Login failed");
                socket.close();
                return;
            }

            username = user;
            out.println("[SERVER] Login successful");
            broadcast("[SERVER] " + username + " joined the chat");
            sendHistory();

            String msg;
            while ((msg = in.readLine()) != null) {
                if (msg.equalsIgnoreCase("/exit")) break;

                if (msg.equalsIgnoreCase("/users")) {
                    sendOnlineUsers();
                } else if (msg.startsWith("/pm")) {
                    privateMessage(msg);
                } else {
                    broadcast(username + ": " + msg);
                    saveHistory(username + ": " + msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ChatServer.clients.remove(this);
            broadcast("[SERVER] " + username + " left the chat");
        }
    }

    private void broadcast(String message) {
        synchronized (ChatServer.clients) {
            for (ClientHandler c : ChatServer.clients) {
                c.out.println(message);
            }
        }
    }

    // ONLINE USERS
    private void sendOnlineUsers() {
        StringBuilder sb = new StringBuilder("[ONLINE USERS] ");
        synchronized (ChatServer.clients) {
            for (ClientHandler c : ChatServer.clients) {
                if (c.username != null) sb.append(c.username).append(" ");
            }
        }
        out.println(sb.toString());
    }

    // PRIVATE MESSAGE
    private void privateMessage(String msg) {
        String[] parts = msg.split(" ", 3);
        if (parts.length < 3) return;

        String target = parts[1];
        String message = "[PM] " + username + ": " + parts[2];

        for (ClientHandler c : ChatServer.clients) {
            if (c.username != null && c.username.equals(target)) {
                c.out.println(message);
                out.println(message);
                return;
            }
        }
        out.println("[SERVER] User not found");
    }

    // CHAT HISTORY
    private void saveHistory(String msg) {
        try (FileWriter fw = new FileWriter(historyFile, true)) {
            fw.write(msg + "
");
        } catch (IOException e) {}
    }

    private void sendHistory() {
        if (!historyFile.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(historyFile))) {
            String line;
            out.println("--- Chat History ---");
            while ((line = br.readLine()) != null) {
                out.println(line);
            }
            out.println("--------------------");
        } catch (IOException e) {}
    }
}

