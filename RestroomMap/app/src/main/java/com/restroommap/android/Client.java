package com.restroommap.android;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    public static String IP = "211.32.126.168";
    public static int PORT = 5001;
    private Socket socket;

    public ArrayList<String> sendMsg = new ArrayList<>();

    public Client(ArrayList<String> sendMsg) {
        this.sendMsg = sendMsg;
        new Thread(new ClientThread()).start();
    }

    class ClientThread implements Runnable {
        @Override
        public void run() {
            try {
                socket = new Socket(IP, PORT);
                OutputStream out = socket.getOutputStream();

                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));

                for (int i=0;i<sendMsg.size();i++) { //받은 메세지 서버로 전송
                    pw.println(sendMsg.get(i));
                    pw.flush();
                }
                pw.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
