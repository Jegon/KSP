import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by Jegoni on 04.06.2015.
 */
public class Users {
    public User thisUser;
    private Hashtable<String, User> users;

    Users() {
    }

    public void setUsers(Hashtable<String, User> users) {
        this.users = users;
        thisUser = this.users.get(Main.username);
        thisUser.getRang();
        Main.rootPane.leisteOben.update();
        Main.rootPane.tabLeiste.global.refresh();

        ArrayList<User> friendl = new ArrayList<>();
        ArrayList<String> friends = this.thisUser.getFriends();
        for(String s : friends){
            friendl.add(this.users.get(s));
        }
        Main.rootPane.rechteLeiste.updateFriendlist(friendl);
        if(thisUser.getRang().equals("mod") || thisUser.getRang().equals("admin")){
            ArrayList<User> swag = new ArrayList<>();
            Enumeration<String> enu = users.keys();
            while(enu.hasMoreElements()){
                swag.add(users.get(enu.nextElement()));
            }
            Main.rootPane.rechteLeiste.updateAlllist(swag);
        }


        /*
        ArrayList<User> usersl = new ArrayList<>();
        Enumeration<String> keys = this.users.keys();
        while (keys.hasMoreElements()) {
            usersl.add(this.users.get(keys.nextElement()));
        }
        for (User user : usersl) {
            System.out.println(user.getUsername() + " : " + user.isOnline() + " : " + user.getRang()); //todo remove
        }
        Main.rootPane.rechteLeiste.updateFriendlist(usersl);*/
    }

    public User getUser(String username) {
        return users.get(username);
    }
}