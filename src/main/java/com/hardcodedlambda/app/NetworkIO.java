package com.hardcodedlambda.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkIO implements IO {

    private final BufferedReader reader;
    private final PrintWriter writer;

    public NetworkIO(Socket socket) throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public void writeLine(String line) {
        writer.println(line);
    }
}
