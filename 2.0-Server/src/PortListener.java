import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Jegoni on 01.06.2015.
 */
public class PortListener extends Thread {

    private final ServerSocket serverSocket;
    private Boolean running;

    PortListener(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;
        this.start();
    }

    public void terminate() {
        running = false;
        try {
            serverSocket.close();
        } catch (IOException ignored) {
        }
    }

    @Override
    public void run() {
        Socket socket;
        String s = null;
        int k = 0;
        while (running) {
            try {
                socket = serverSocket.accept();
                socket.setSoTimeout(5000);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                try {
                    s = (String) objectInputStream.readObject();
                    if (Main.userVerwaltung.checkOnline(s)){
                        objectOutputStream.writeObject("01");
                        System.out.println(socket.getRemoteSocketAddress() + " hat versucht zu connecten!");
                    }
                    else{
                        socket.setSoTimeout(0);
                        Main.userVerwaltung.connectUser(s, socket, objectInputStream, objectOutputStream);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                //Todo fehlermeldung, dass bereits connected!
            } catch (IOException e) {
                Main.logger.warning(e.getLocalizedMessage());
                k++;
                if (k < 10) {
                    System.exit(01);
                }
            }
        }
    }
}