import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Logger;

/**
 * Created by Jegoni on 02.06.2015.
 */
public class UserVerwaltung {
    protected static final Logger logger = Logger.getLogger(UserVerwaltung.class.getName());
    final Hashtable<String, UserVerbund> allUserVerbunds;
    private final ArrayList<String> onlineUsers;

    UserVerwaltung() {
        allUserVerbunds = new Hashtable<>();
        onlineUsers = new ArrayList<>();

        //Überprüfe ob Verzeichnis vorhanden:
        File verzeichnis = new File("userdata");
        if (!verzeichnis.exists() | !verzeichnis.isDirectory()) {
            logger.finest("Verzeichnis 'userdata' wird erstellt, weil es noch nicht existiert!");
            if (!verzeichnis.mkdir())
                logger.warning("Verzeichnis 'userdata' konnte nicht erstellt werden!");
        }
        //Lade alle User in die Hashtable
        File folder = new File("userdata");
        try {
            for (final File fileEntry : folder.listFiles()) {
                if (!fileEntry.isDirectory()) {
                    allUserVerbunds.put(fileEntry.getName(), new UserVerbund(fileEntry.getName()));
                }
            }
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }
    }

    public boolean checkInit(String username) {
        return allUserVerbunds.get(username) != null;
    }

    public boolean checkOnline(String username) {
        if (checkInit(username))
            if (allUserVerbunds.get(username).user.isOnline())
                return true;
            else
                return false;
        else
            return false;
    }

    public void connectUser(String username, Socket socket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        if (!checkInit(username))
            allUserVerbunds.put(username, new UserVerbund(username));
        allUserVerbunds.get(username).setUserCon(new UserCon(allUserVerbunds.get(username), socket, objectInputStream, objectOutputStream));
    }

    public void userConnected(String username) {
        System.out.println(username + " hat connected!");
        onlineUsers.add(username);
        //allUserVerbunds.get(username).user.setOnline(); doppelt
        Main.rootPane.hauptPane.update(onlineUsers.size(), onlineUsers);
    }

    public void userDisconnected(String username) {
        System.out.println(username + " hat disconnected!");
        onlineUsers.remove(username);
        //allUserVerbunds.get(username).user.setOffline(); doppelt
        msgAll("02", getUsers());
        Main.rootPane.hauptPane.update(onlineUsers.size(), onlineUsers);
    }

    public void terminateAll(){
        Enumeration<String> enumeration = allUserVerbunds.keys();
        String ck;
        while(enumeration.hasMoreElements()){
            ck = enumeration.nextElement();
            allUserVerbunds.get(ck).saveProperties();
            try{
                allUserVerbunds.get(ck).userCon.terminate();
            }catch(Exception ignored){}
        }
    }

    Hashtable<String, User> getUsers(){
        //System.out.println("AB HIER!!!!");
        Hashtable<String, User> out = new Hashtable<>();
        Enumeration<String> keys = allUserVerbunds.keys();
        while(keys.hasMoreElements()){
            String s = keys.nextElement();
            out.put(s, allUserVerbunds.get(s).user);
            //System.out.println(s + ":  " + allUserVerbunds.get(s).user.isOnline());
        }
        return out;
    }
    /*public ArrayList<User> getUsers(){
        ArrayList<User> out = new ArrayList<>();
        Enumeration<String> key = allUserVerbunds.keys();
        while(key.hasMoreElements()){
            out.add(allUserVerbunds.get(key.nextElement()).user);
        }
        return out;
    }*/

    public void msgAll(Object o){
        for(String string : onlineUsers){
            allUserVerbunds.get(string).userCon.writeObject(o);
        }
    }

    public void msgAll(Object o1, Object o2) {
        System.out.println(onlineUsers.size());
        for (int i = 0; i < onlineUsers.size(); i++) {
            System.out.println(allUserVerbunds.get(onlineUsers.get(i)).user.getUsername() + " wurde geupdated");
            allUserVerbunds.get(onlineUsers.get(i)).userCon.writeObject(o1);
            allUserVerbunds.get(onlineUsers.get(i)).userCon.writeObject(o2);
        }
    }

    public void updateAll() {
        msgAll("02", getUsers());
    }
}