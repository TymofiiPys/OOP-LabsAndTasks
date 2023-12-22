package org.example.Task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Task1Serv {
    ServerSocket server;
    Socket socket;
    ObjectInputStream in;
    SerObject received;

    public Task1Serv() {
        try {
            server = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        received = new ArrayList<>();
    }

    public void connectReceiveSaveObjects() {
        socket = null;
        try {
            System.out.println("Waiting for a client...");
            socket = server.accept();
            System.out.println("Client connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in = new ObjectInputStream(socket.getInputStream());
            SerObject messageFromClient = (SerObject) in.readObject();
            System.out.println(messageFromClient.message + messageFromClient.message2 + messageFromClient.number);
//            received.add(messageFromClient);
            received = messageFromClient;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Task1Serv server = new Task1Serv();
        server.connectReceiveSaveObjects();
    }
}
