package com.tekken.data;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebSocketServlet {

    public WebSocketServlet() {
        connect();
    }

    private void connect() {

        new Thread(){
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(8765);
                    Socket socket = serverSocket.accept();
                    OutputStream output = socket.getOutputStream();
                    InputStream input = socket.getInputStream();

                    String data = new Scanner(input,"UTF-8").useDelimiter("\\r\\n\\r\\n").next();
                    Matcher get = Pattern.compile("^GET").matcher(data);

                    Matcher match = Pattern.compile("Sec-WebSocket-Key: (.*)").matcher(data);
                    match.find();
                    byte[] response = ("HTTP/1.1 101 Switching Protocols\r\n"
                            + "Connection: Upgrade\r\n"
                            + "Upgrade: websocket\r\n"
                            + "Sec-WebSocket-Accept: "
                            + DatatypeConverter
                            .printBase64Binary(
                                    MessageDigest
                                            .getInstance("SHA-1")
                                            .digest((match.group(1) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11")
                                                    .getBytes("UTF-8")))
                            + "\r\n\r\n")
                            .getBytes("UTF-8");

                    output.write(response, 0, response.length);

                    while (true){
                        Thread.sleep(1000);
                        System.out.println("send data");
                        output.write("yoyo".getBytes());
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
