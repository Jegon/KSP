import java.util.ArrayList;

/**
 * Created by Jegoni on 02.06.2015.
 */
public class User implements java.io.Serializable {
    private final String username;
    private final ArrayList<String> friends;
    private String rang;
    private Boolean online;


    User(String username) {
        this.username = username;
        rang = null;
        online = false;
        friends = new ArrayList<>();
    }

    boolean isOnline() {
        return online;
    }

    String getRang() {
        return rang;
    }

    void setRang(String rang) {
        this.rang = rang;
    }

    String getUsername() {
        return username;
    }

    void setOnline() {
        online = true;
    }

    void setOffline() {
        online = false;
    }

    ArrayList<String> getFriends() {
        return friends;
    }

    void addFriend(String friend) {
        friends.add(friend);
    }

    void removeFriend(String friend) throws NullPointerException {
        friends.remove(friend);
    }
}
