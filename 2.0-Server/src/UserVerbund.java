import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Jegoni on 01.06.2015.
 */
public class UserVerbund {
    protected static final Logger logger = Logger.getLogger(UserVerbund.class.getName());
    public User user;
    UserCon userCon;
    private Properties properties;
    private File file;
    ArrayList<String> importantNots;
    private ArrayList<String> sentFriendRequests;

    UserVerbund(String username) {
        importantNots = new ArrayList<>();
        sentFriendRequests = new ArrayList<>();
        user = new User(username);
        properties = new Properties();
        file = new File("userdata/" + username);
        loadProperties();
    }

    private void checkProperties() {
        if (!file.exists() | file.isDirectory()) {
            logger.finest("Datei '" + file.getAbsolutePath() + "' wird erstellt, weil sie noch nicht existiert!");
            try {
                if (!file.createNewFile())
                    logger.warning("Datei '" + file.getAbsolutePath() + "' konnte nicht erstellt werden!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void saveProperties() {
        checkProperties();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            properties.store(fos, null);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadProperties() {
        checkProperties();
        try {
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
            fis.close();
        } catch (IOException ignored) {
        }
        user.setRang(properties.getProperty("rang"));
        if(user.getRang() == null)
            setRang("user");
        String fges = properties.getProperty("friends");
        if(fges != null){
            String fsub;
            fges += " ";
            while(fges.contains("|")){
                fsub = fges.substring(0, fges.indexOf("|"));
                fges = fges.substring(fges.indexOf("|") + 1);
                user.addFriend(fsub);
            }
        }
        importantNots.clear();
        String s = properties.getProperty("nots") + " ";
        while(s.contains("||")){
            importantNots.add(s.substring(0, s.indexOf("||")));
            s = s.substring(s.indexOf("||" + 2));
        }
        sentFriendRequests.clear();
        s = properties.getProperty("sentFriendRequests") + "  ";
        while(s.contains("||")){
            sentFriendRequests.add(s.substring(0, s.indexOf("||")));
            s = s.substring(s.indexOf("||") + 2);
        }
    }

    public void setUserCon(UserCon userCon) {
        this.userCon = userCon;
        if (userCon == null) {
            user.setOffline();
            Main.userVerwaltung.userDisconnected(user.getUsername());
        } else {
            user.setOnline();
            this.userCon.start();
            Main.userVerwaltung.userConnected(user.getUsername());
        }
    }

    public void addSentFriendRequest(String intendedFriend){
        sentFriendRequests.add(intendedFriend);
        String s = "";
        for(String s1 : sentFriendRequests){
            s += s1 +"||";
        }
        properties.setProperty("sentFriendRequests", s);
    }

    public boolean removeSentFriendRequest(String formerFriend){
        if(sentFriendRequests.contains(formerFriend)){
            sentFriendRequests.remove(formerFriend);
            String s = "";
            for(String s1 : sentFriendRequests){
                s += s1 +"||";
            }
            properties.setProperty("sentFriendRequests", s);
            return true;
        }else
            return false;
    }

    public void removeFriend(String friend){
        user.removeFriend(friend);
        String s = "";
        ArrayList<String> allf = user.getFriends();
        for(String s1 : allf){
            s += s1 + "|";
        }
        properties.setProperty("friends", s);
    }

    public void addFriend(String friend){
        user.addFriend(friend);
        String s = "";
        ArrayList<String> allf = user.getFriends();
        for(String s1 : allf){
            s += s1 + "|";
        }
        properties.setProperty("friends", s);
    }

    public void setRang(String s){
        user.setRang(s);
        properties.setProperty("rang", s);
        saveProperties();
    }

    void addImportantNot(String not){
        importantNots.add(not);
        propnots();
    }

    void propnots(){
        String s = "";
        for(int i = 0; i > importantNots.size(); i++){
            s += importantNots.get(i) + "||";
        }
        System.out.println("propnots:" + s); //todo remove
        properties.setProperty("nots", s);
    }

    void removeImportantNot(String not){
        importantNots.remove(not);
        propnots();
    }

    //todo Hitlers
}
