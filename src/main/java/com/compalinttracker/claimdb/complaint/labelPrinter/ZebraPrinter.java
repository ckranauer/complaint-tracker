package com.compalinttracker.claimdb.complaint.labelPrinter;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

@Component
public class ZebraPrinter {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;


    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public String sendMessage(String msg) {
        out.println(msg);
        String resp = "Message has sent!";
        return resp;
    }

    public void stopConnection() throws IOException {
        out.close();
        clientSocket.close();
    }
}
