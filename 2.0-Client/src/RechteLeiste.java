import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.ArrayList;

/**
 * Created by Jegoni on 04.06.2015.
 */
public class RechteLeiste extends Accordion {

    private TreeItem<String> friendRoot;
    Notifications notifications;

    RechteLeiste() {
        friendRoot = new TreeItem<>("RootItem");
        TreeView friendTree = new TreeView<>(friendRoot);
        friendTree.setShowRoot(false);
        friendTree.setEditable(false);
        friendTree.setCellFactory(new Callback<TreeView, TreeCell>() {
            @Override
            public TreeCell call(TreeView treeView) {
                return new UserTreeCellFabrik();
            }
        });
        VBox fvBox = new VBox(5);
        final String newFriendt = "Freund hinzufügen";
        final SimpleStringProperty newFriendTT = new SimpleStringProperty("");
        final TextField newFriend = new TextField(newFriendt);
        fvBox.getChildren().addAll(friendTree, newFriend);
        newFriend.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1) {
                    newFriend.setText(newFriendTT.getValue());
                }
                else {
                    newFriendTT.setValue(newFriend.getText());
                    newFriend.setText(newFriendt);
                }
            }
        });
        newFriend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(newFriend.getText());
                Main.connection.writeObject("10" + newFriend.getText());
                newFriend.setText("");
            }
        });
        TitledPane friendlist = new TitledPane("Friendlist", fvBox);

        ScrollPane sp = new ScrollPane();
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox vBox = new VBox(5);

        /*
        Icon awtImage = FileSystemView.getFileSystemView().getSystemIcon(new File("C:\\Windows\\regedit.exe"));
        BufferedImage bImg;
        bImg = new BufferedImage(awtImage.getIconWidth(), awtImage.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bImg.createGraphics();
        graphics.drawImage(iconToImage(awtImage), 0, 0, null);
        graphics.dispose();
        Image fxImage = SwingFXUtils.toFXImage(bImg, null);
        ImageView xy = new ImageView(fxImage);
        xy.setFitHeight(100);
        xy.setFitWidth(100);*/


        sp.setContent(vBox);
        TitledPane programme = new TitledPane("Programme", sp);
        notifications = new Notifications();

        this.getPanes().addAll(friendlist, programme, notifications);
        this.setExpandedPane(this.getPanes().get(0));
        /*//todo shit
        this.getPanes().get(0).setCollapsible(false);
        this.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
            @Override
            public void changed(ObservableValue<? extends TitledPane> property, final TitledPane oldPane, final TitledPane newPane) {
                if (oldPane != null) oldPane.setCollapsible(true);
                if (newPane != null) Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        newPane.setCollapsible(false);
                    }
                });
            }
        });*/
    }


    /*
    static java.awt.Image iconToImage(Icon icon) { //für die Programme
        if (icon instanceof ImageIcon) {
            return ((ImageIcon) icon).getImage();
        } else {
            int w = icon.getIconWidth();
            int h = icon.getIconHeight();
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            BufferedImage image = gc.createCompatibleImage(w, h);
            Graphics2D g = image.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();
            return image;
        }
    }*/

    public void updateFriendlist(ArrayList<User> users) {
        friendRoot.getChildren().clear();
        for (User user : users) {
            TreeItem<String> treeItem = new TreeItem<>(user.getUsername());
            friendRoot.getChildren().add(treeItem);
        }
    }

    public void updateProgramme() {

    }

    public void rankdep(){
        //todo program as rank
    }
}

