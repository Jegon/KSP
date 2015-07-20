import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.image.ImageView;

/**
 * Created by Jegoni on 21.06.2015.
 */
public class UserTableCellFabrik extends TableCell<MyTab.MSG, String> {

    private ContextMenu contextMenuYF = new ContextMenu();
    private ContextMenu contextMenuNF = new ContextMenu();
    private String nitem;
    private User user;
    private MenuItem removeFriend;
    private MenuItem addFriend;

    public UserTableCellFabrik() {
        user = null;
        MenuItem testItem = new MenuItem("Make Smth");
        removeFriend = new MenuItem("Entfreunde mich!");
        addFriend = new MenuItem("Befreunde mich!");
        contextMenuYF.getItems().addAll(testItem, removeFriend);
        contextMenuNF.getItems().addAll(testItem, addFriend);
        testItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(getText());
            }
        });
    }

    @Override
    public void updateItem(final String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item);
            if (true) {
                try {
                    user = Main.users.getUser(item);
                    if (Main.users.thisUser.getUsername().equals(item))
                        setContextMenu(null);
                    else if (Main.users.thisUser.getFriends().contains(item)){
                        removeFriend.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Main.connection.writeObject("13" + item);
                            }
                        });
                        setContextMenu(contextMenuYF);
                    }
                    else {
                        addFriend.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Main.connection.writeObject("10" + item);
                            }
                        });
                        setContextMenu(contextMenuNF);
                    }
                    if (user.isOnline()) {
                        if (user.getRang().equals("admin"))
                            setGraphic(new ImageView(Icons.admin_on));
                        if (user.getRang().equals("mod"))
                            setGraphic(new ImageView(Icons.mod_on));
                        if (user.getRang().equals("userplus"))
                            setGraphic(new ImageView(Icons.userplus_on));
                        if (user.getRang().equals("user"))
                            setGraphic(new ImageView(Icons.user_on));
                    } else {
                        if (user.getRang().equals("admin"))
                            setGraphic(new ImageView(Icons.admin_off));
                        if (user.getRang().equals("mod"))
                            setGraphic(new ImageView(Icons.mod_off));
                        if (user.getRang().equals("userplus"))
                            setGraphic(new ImageView(Icons.userplus_off));
                        if (user.getRang().equals("user"))
                            setGraphic(new ImageView(Icons.user_off));
                    }
                } catch (Exception ex) {
                    //ex.printStackTrace();
                }
            } else
                setGraphic(getGraphic());
        }
    }
}