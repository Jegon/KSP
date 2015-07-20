import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Jegoni on 01.06.2015.
 */
public class UserCon extends Thread {
    private final UserVerbund userVerbund;
    private Boolean running;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Socket socket;

    UserCon(UserVerbund userVerbund, Socket socket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        running = true;
        this.userVerbund = userVerbund;
        this.socket = socket;
        /*try {
            this.socket.setSoTimeout(5 * 1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }*/
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
    }

    public boolean writeObject(Object o) {
        try {
            objectOutputStream.writeObject(o);
            objectOutputStream.reset();
            objectOutputStream.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Object getObject() {
        try {
            return objectInputStream.readObject();
        } catch (IOException e) {
            UserVerbund.logger.warning(e.getLocalizedMessage());
            return null;
        } catch (ClassNotFoundException ignored) {
            return null;
        }
    }

    public void terminate() {
        running = false;
        try {
            objectOutputStream.close();
        } catch (IOException ignored) {
        } finally {
            userVerbund.setUserCon(null); //ruft in der userverwaltung disconnect auf
        }
    }

    public void terminate(Boolean b) {//todo für server shutdown
        if (b)
            writeObject("00");
        terminate();
    }

    @Override
    public void run() {
        while (running) {
            try {
                action((String) objectInputStream.readObject());
            } catch (IOException ex) {
                terminate();
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
    }

    private void action(String s){
        int k = Integer.parseInt(s.substring(0, 2));
        s = s.substring(2);
        switch(k){
            case 0:
                break;
            case 1:
                break;
            case 2:
                Main.userVerwaltung.updateAll();
                break;
            case 3:
                Main.userVerwaltung.msgAll("03" + this.userVerbund.user.getUsername() + "|" + s); //todo move to lobby
                break;
            case 4:
                writeObject("02");
                writeObject(Main.userVerwaltung.getUsers());
                break;
            case 5:
                writeObject("05");
                for(String s1: userVerbund.importantNots){
                    writeObject(s1);
                }
                break;
            case 6:
                writeObject("11Herzlich willkommen! Dies ist nur eine Beta³ Version...");
                break;
            case 7:
                writeObject("06" + Main.infoTab);
                break;
            case 10:
                if(!Main.userVerwaltung.checkInit(s))
                    writeObject("12" + s + " ist kein User hier!");
                else{
                    if(userVerbund.user.getFriends().contains(s)){
                        writeObject("12" + "Ihr seid bereits befreundet!");
                    }else {
                        userVerbund.removeSentFriendRequest(s);
                        Main.userVerwaltung.allUserVerbunds.get(s).addImportantNot("10" + userVerbund.user.getUsername());
                        if(Main.userVerwaltung.checkOnline(s)){
                            Main.userVerwaltung.allUserVerbunds.get(s).userCon.writeObject("10" + s);
                        }
                        System.out.println("WICHTIG:" + Main.userVerwaltung.allUserVerbunds.get(s).user.getUsername());//todo remove
                        userVerbund.addSentFriendRequest(s);
                        writeObject("12" + "Es wurde an " + s + " eine Freundschaftsanfrage gesendet!");
                    }
                }
                break;
            case 11:
                if(!Main.userVerwaltung.allUserVerbunds.get(s).removeSentFriendRequest(userVerbund.user.getUsername()))
                    writeObject("12" + "Es ist ein Fehler aufgetreten!");
                else{
                    userVerbund.addFriend(s);
                    userVerbund.removeImportantNot("10" + s);
                    Main.userVerwaltung.allUserVerbunds.get(s).addFriend(userVerbund.user.getUsername());
                    writeObject("02");
                    writeObject(Main.userVerwaltung.getUsers());
                    if(Main.userVerwaltung.checkOnline(s)){
                        Main.userVerwaltung.allUserVerbunds.get(s).userCon.writeObject("02");
                        Main.userVerwaltung.allUserVerbunds.get(s).userCon.writeObject(Main.userVerwaltung.getUsers());
                    }
                }
                break;
            case 12:
                if(!Main.userVerwaltung.allUserVerbunds.get(s).removeSentFriendRequest(userVerbund.user.getUsername()))
                    writeObject("12" + "Es ist ein Fehler aufgetreten!");
                userVerbund.removeImportantNot("10" + s);
                break;
            case 13:
                if(!userVerbund.user.getFriends().contains(s))
                    writeObject("12" + s + " ist gar nicht auf deiner Freundesliste!");
                else{
                    userVerbund.removeFriend(s);
                    Main.userVerwaltung.allUserVerbunds.get(s).removeFriend(userVerbund.user.getUsername());
                    writeObject("02");
                    writeObject(Main.userVerwaltung.getUsers());
                    if(Main.userVerwaltung.checkOnline(s)){
                        Main.userVerwaltung.allUserVerbunds.get(s).userCon.writeObject("02");
                        Main.userVerwaltung.allUserVerbunds.get(s).userCon.writeObject(Main.userVerwaltung.getUsers());
                    }
                }
                break;
        }
    }
}
