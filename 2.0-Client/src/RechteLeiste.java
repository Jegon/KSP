import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jegoni on 04.06.2015.
 */
public class RechteLeiste extends Accordion {

    private TreeItem<String> friendRoot;
    Notifications notifications;
    private final ScrollPane sp;
    private final VBox provbox;
    private final Button firefox;
    private final Button angryIPScanner;
    private final Button notepad;
    private final Button notepadpp;
    private final Button powershell;
    private final Button benutzeradmin;
    private final TitledPane userPane;
    private final TreeItem<String> users;
    private final TreeItem<String> userplus;
    private final TreeItem<String> mods;
    private final TreeItem<String> admins;

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

        sp = new ScrollPane();
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        provbox = new VBox(5);

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

        firefox = new Button("Firefox");
        angryIPScanner = new Button("AngryIPScanner");
        notepad = new Button("Notepad");
        notepadpp = new Button("Notepad++");
        powershell = new Button("Powershell");
        benutzeradmin = new Button ("Benutzeradmin");
        provbox.getChildren().addAll(firefox, angryIPScanner, notepad, notepadpp, powershell, benutzeradmin);
        sp.setContent(provbox);
        TitledPane programme = new TitledPane("Programme", sp);
        notifications = new Notifications();

        TreeItem<String> allRoot = new TreeItem<>("AllRoot");
        TreeView<String> allTree = new TreeView<>(allRoot);
        allTree.setShowRoot(false);
        friendTree.setCellFactory(new Callback<TreeView, TreeCell>() {
            @Override
            public TreeCell call(TreeView treeView) {
                return new UserTreeCellFabrik();
            }
        });
        users = new TreeItem<>();
        users.setGraphic(new ImageView(Icons.user));
        userplus = new TreeItem<>();
        userplus.setGraphic(new ImageView(Icons.userplus));
        mods = new TreeItem<>();
        mods.setGraphic(new ImageView(Icons.mod));
        admins = new TreeItem<>();
        admins.setGraphic(new ImageView(Icons.admin));
        allRoot.getChildren().addAll(users, userplus, mods, admins);
        userPane = new TitledPane("Alle Benutzer", allTree);

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

    public void updateAlllist(ArrayList<User> userss) {
        friendRoot.getChildren().clear();
        for (User user : userss) {
            TreeItem<String> treeItem = new TreeItem<>(user.getUsername());
            if(user.getRang().equals("user")) {
                users.getChildren().add(treeItem);
                break;
            }
            if(user.getRang().equals("userplus")) {
                userplus.getChildren().add(treeItem);
                break;
            }
            if(user.getRang().equals("mod")) {
                mods.getChildren().add(treeItem);
                break;
            }
            if(user.getRang().equals("admin")) {
                admins.getChildren().add(treeItem);
                break;
            }
        }
    }

    public void updateFriendlist(ArrayList<User> users) {
        friendRoot.getChildren().clear();
        for (User user : users) {
            TreeItem<String> treeItem = new TreeItem<>(user.getUsername());
            friendRoot.getChildren().add(treeItem);
        }
    }

    public void updateProgramme(String s) {
        try {
            final String finalS1 = s.substring(0, s.indexOf("|"));
            firefox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        Process f = Runtime.getRuntime().exec(finalS1);
                    } catch (IOException ignored) {}
                }
            });
            s = s.substring(s.indexOf("|") + 1);
            final String finalS2 = s.substring(0, s.indexOf("|"));
            angryIPScanner.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        Process f = Runtime.getRuntime().exec(finalS2);
                    } catch (IOException ignored) {
                    }
                }
            });
            s = s.substring(s.indexOf("|") + 1);
            final String finalS3 = s.substring(0, s.indexOf("|"));
            notepad.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        Process f = Runtime.getRuntime().exec(finalS3);
                    } catch (IOException ignored) {
                    }
                }
            });
            s = s.substring(s.indexOf("|") + 1);
            final String finalS4 = s.substring(0, s.indexOf("|"));
            notepadpp.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        Process f = Runtime.getRuntime().exec(finalS4);
                    } catch (IOException ignored) {
                    }
                }
            });
            s = s.substring(s.indexOf("|") + 1);
            final String finalS5 = s.substring(0, s.indexOf("|"));
            powershell.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        Process f = Runtime.getRuntime().exec(finalS5);
                    } catch (IOException ignored) {
                    }
                }
            });
            s = s.substring(s.indexOf("|") + 1);
            final String finalS6 = s.substring(0, s.indexOf("|"));
            benutzeradmin.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        Process f = Runtime.getRuntime().exec(finalS6);
                    } catch (IOException ignored) {
                    }
                }
            });
        }catch(Exception ignored){}
    }

    public void rankdep(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(Main.users.thisUser.getRang().equals("mod") || Main.users.thisUser.getRang().equals("admin")){
                    sp.setContent(provbox);
                    getPanes().add(userPane);
                } else{
                    sp.setContent(new Label("Du hast nicht den \nerforderlichen Rang für dies!"));
                }
            }
        });
    }
}

