package com.tekken.shopper.network.ipn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.regex.Pattern;

public class Paypal {

    private final Pattern regexHostname = Pattern.compile("/paypal\\.com$/");

    private final Map<String, String> queries;
    private final boolean useSandbox;
    private final String host;

    public Paypal(String host, Map<String, String> queries, boolean useSandbox) {
        this.host = host;
        this.queries = queries;
        this.useSandbox = useSandbox;
    }

    public Paypal(String host, Map<String, String> queries) {
        this(host, queries, false);
    }

    public boolean confirmBySocket(){
        try {
            Socket socket = new Socket("ssl://www.paypal.com", 443);
            socket.setSoTimeout(3000);

            BufferedReader in = new BufferedReader(new
                    InputStreamReader(socket.getInputStream()));
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream());

            socketOut.print("GET /index.html\n\n");
            socketOut.flush();

            String line;

            while ((line = in.readLine()) != null){
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean comfirmByHostname(){
        try {
            String hostname = InetAddress.getByName(host).getHostName();
            return regexHostname.matcher(hostname).find();
        }catch (Exception e){
            return false;
        }
    }

    enum URL{

        PAYPAL("https://www.paypal.com/cgi-bin/webscr"),
        PAYPAL_SANDBOX("https://www.sandbox.paypal.com/cgi-bin/webscr");


        private final String link;

        URL(String link) {
            this.link = link;
        }

        public String getLink() {
            return link;
        }
    }
}
