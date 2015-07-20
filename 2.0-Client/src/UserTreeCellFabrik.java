import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.image.ImageView;

/**
 * Created by Jegoni on 21.06.2015.
 */
public class UserTreeCellFabrik extends TreeCell<String> {

    private ContextMenu contextMenuYF = new ContextMenu();
    private ContextMenu contextMenuNF = new ContextMenu();
    private User user;
    private MenuItem addFriend;
    private MenuItem removeFriend;

    public UserTreeCellFabrik() {
        user = null;
        MenuItem testItem = new MenuItem("Nur fürs Testen du Lappen");
        removeFriend = new MenuItem("Entfreunde mich!");
        addFriend = new MenuItem("Befreunde mich!");
        contextMenuYF.getItems().addAll(testItem, removeFriend);
        contextMenuNF.getItems().addAll(testItem, addFriend);
        testItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(getTreeItem().getValue());
            }
        });
    }

    @Override
    public void updateItem(final String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            super.setVisible(false);
            setText(null);
            setGraphic(null);
        } else {
            setText(getTreeItem().getValue());
            if (getTreeItem().isLeaf()) {
                user = Main.users.getUser(getTreeItem().getValue());
                if (Main.users.thisUser.getFriends().contains(getTreeItem().getValue())){
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
            }
            else
                setGraphic(getTreeItem().getGraphic());
        }
    }
}