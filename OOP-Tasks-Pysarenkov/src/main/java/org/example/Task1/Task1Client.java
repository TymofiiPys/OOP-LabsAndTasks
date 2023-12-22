package org.example.Task1;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Task1Client {
    Socket socket;
    ObjectOutputStream out;
    public Task1Client() {
        try {
            System.out.println("Connecting to server...");
            socket = new Socket("localhost", 8080);
            System.out.println("Connected");
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendObject(SerObject ser) {
        try {
            out.writeObject(ser);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Task1Client client = new Task1Client();
        SerObject ser = new SerObject();
        ser.message = "Доброго вечора ";
        ser.message2 = " ми з України";
        ser.number = 482;
        client.sendObject(ser);
    }
}
