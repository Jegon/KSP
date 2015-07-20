import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by Jegoni on 01.06.2015.
 */
public class HauptPane extends GridPane {
    private final TextArea usernamen;
    private final Label userzahl;

    HauptPane() {
        Label label = new Label("Server läuft!");
        label.setFont(Font.font("Comic sans MS", 30));
        this.add(label, 0, 0, 2, 1);
        Label label1 = new Label("Verbundene Benutzer:");
        label1.setFont(Font.font("Comic sans MS", 20));
        this.add(label1, 0, 1);
        userzahl = new Label("0");
        userzahl.setFont(Font.font("Comic sans MS", 20));
        this.add(userzahl, 1, 1);
        usernamen = new TextArea();
        usernamen.setEditable(false);
        usernamen.setWrapText(true);
        this.add(usernamen, 0, 2, 2, 1);
        HBox hBox = new HBox(10);
        Button startClient = new Button("Start Client");
        Button reload = new Button("Lade props neu");
        Button message = new Button("Server-MSG");
        Button infoup = new Button("Update Infotab");
        Button programme = new Button("Programme");
        Button testButton = new Button("Test Button");
        hBox.getChildren().addAll(startClient, reload, message, infoup);
        HBox hBox1 = new HBox(10);
        this.add(hBox1, 0, 4, 2, 1);
        hBox1.getChildren().addAll(programme, testButton);
        this.add(hBox, 0, 3, 2, 1);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(30, 30, 30, 30));
        this.setAlignment(Pos.CENTER);
        startClient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Runtime.getRuntime().exec("cmd /c java -jar KP.jar");
                } catch (Exception ignored) {
                }
            }
        });
        reload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Enumeration<String> keys = Main.userVerwaltung.allUserVerbunds.keys();
                while(keys.hasMoreElements()){
                    Main.userVerwaltung.allUserVerbunds.get(keys.nextElement()).loadProperties();
                }
                Main.userVerwaltung.updateAll();
            }
        });
        message.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.rootPane.setInfoPane();
            }
        });
        infoup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.rootPane.setInfoTabPane();
            }
        });
        programme.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.rootPane.setProgrammPane();
            }
        });
        testButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //todo TEST:

            }
        });
        testButton.setDefaultButton(true);
    }

    public void update(final int i, final ArrayList<String> usernames) {
        String l = null;
        for (String s1 : usernames) {
            if(l == null)
                l = s1;
            else
                l = l + " | " + s1;
        }
        final String s = l;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                userzahl.setText(Integer.toString(i));
                usernamen.setText(s);
            }
        });
    }
}
