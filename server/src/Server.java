import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static int PORT = 5001;
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Waiting Connect ..");

            Socket socket = server.accept();

            InetAddress inetaddr = socket.getInetAddress();
            System.out.println(inetaddr.getHostAddress()+ " 로부터 접속했습니다.");

            InputStream in = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line = "";
            while((line = br.readLine()) != null) {
                System.out.println("클라이언트로부터 전송받은 문자열 : "+line);
            }

            br.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
