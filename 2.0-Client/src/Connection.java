import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Hashtable;
import java.util.logging.Logger;

/**
 * Created by Jegoni on 01.06.2015.
 */
public class Connection extends Thread {
    private static final Logger logger = Logger.getLogger(Connection.class.getName());
    private Socket socket;
    private Boolean running;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    Connection(InetAddress inetAddress, int port) throws IOException {
        socket = new Socket(inetAddress, port);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream.writeObject(Main.username);
        Main.rootPane.setHauptContent();
        running = true;
    }

    public boolean writeObject(String o) {
        try {
            objectOutputStream.writeObject(o);
            objectOutputStream.flush();
            objectOutputStream.reset();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Object getObject() {
        try {
            return objectInputStream.readObject();
        } catch (IOException e) {
            logger.warning(e.getLocalizedMessage());
            return null;
        } catch (ClassNotFoundException ignored) {
            ignored.printStackTrace();
            return null;
        }
    }

    public void terminate() {
        running = false;
        NotInfo notInfo = new NotInfo("Die Verbindung wurde unterbrochen!");
        notInfo.closeNot(true);
        try {
            objectOutputStream.close();
        } catch (IOException ignored) {
        }


    }

    @Override
    public void run() {
        //fordere infos an:
        writeObject("02");
        writeObject("05");
        writeObject("06");
        writeObject("07");
        writeObject("08");
        while (running) {
            try {
                Object object = objectInputStream.readObject();
                action((String) object);
            } catch (IOException ex) {
                terminate();
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
    }

    private void action(String s) throws IOException {
        int k = Integer.parseInt(s.substring(0, 2));
        s = s.substring(2);
        switch (k) {
            case 0:
                NotInfo notInfo1 = new NotInfo("Der Server wurde gschlossen!");
                notInfo1.closeNot(true);
                break;
            case 1:
                NotInfo notInfo = new NotInfo("Du bist bereits mit diesem Server verbunden!");
                notInfo.closeNot(true);
                break;
            case 2:
                Main.users.setUsers((Hashtable<String, User>) getObject());
                Main.rankdep();//todo move, weil sonst andauernd unnötig aufgerufen
                break;
            case 3:
                Main.rootPane.tabLeiste.global.update(s.substring(0, s.indexOf("|")), s.substring(s.indexOf("|") + 1));
                break;
            case 5:
                Main.rootPane.rechteLeiste.notifications.leeren();
                break;
            case 6:
                Main.rootPane.tabMitte.updateInofs(s);
                break;
            case 8:
                Main.rootPane.rechteLeiste.updateProgramme(s);
                break;
            case 10:
                Main.rootPane.rechteLeiste.notifications.add("10" + s);
                break;
            case 11:
                Main.rootPane.rechteLeiste.notifications.add("11" + s);
                break;
            case 12:
                NotInfo not12 = new NotInfo(s);
                not12.setTitle("Servernachricht");
                break;
        }
    }
}