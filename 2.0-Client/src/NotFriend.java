import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Jegoni on 30.05.2015.
 */
public class NotFriend extends JDialog{
    final String friend;
    final Button butAccept;
    final Button butLater;
    final Button butDecline;
    final private BorderPane bp;
    private Scene scene;

    NotFriend(final String friend){
        super();
        this.friend = friend;
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        final JFXPanel panel = new JFXPanel();
        bp = new BorderPane();
        butAccept = new Button("Akzeptieren");
        butLater = new Button("Später");
        butDecline = new Button("Ablehnen");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label text = new Label(friend + " möchte dich zu seiner Freundesliste hinzufügen!");
                text.setWrapText(true);
                text.setStyle("-fx-text-fill: red; -fx-font: 16px \"Comic sans MS\"; text-align: right;");
                bp.setCenter(text);
                butAccept.setDefaultButton(true);
                butAccept.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Main.connection.writeObject("11" + friend);
                        Main.rootPane.rechteLeiste.notifications.remove("10" + friend);
                        dispose();
                    }
                });
                butLater.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        dispose();
                    }
                });
                butDecline.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Main.connection.writeObject("12" + friend);
                        Main.rootPane.rechteLeiste.notifications.remove("10" + friend);
                    }
                });
                butDecline.setCancelButton(true);
                HBox hbox = new HBox(5);
                hbox.getChildren().addAll(butAccept, butLater, butDecline);
                hbox.setAlignment(Pos.CENTER);
                bp.setBottom(hbox);
                bp.setPadding(new Insets(4));
                scene = new Scene(bp, 400, 200);
                panel.setScene(scene);
            }
        });
        setAlwaysOnTop(true);
        this.add(panel);
        this.setTitle("Freundschaftsanfrage von " + friend);
        this.setSize(400, 200);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args){
        NotFriend x = new NotFriend("Peter");
    }
}