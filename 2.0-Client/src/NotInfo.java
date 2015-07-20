import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Jegoni on 30.05.2015.
 */
public class NotInfo extends JDialog{
    final String message;
    final Button exitButton;
    final private BorderPane bp;
    private Scene scene;

    NotInfo(final String message){
        super();
        this.message = message;
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        final JFXPanel panel = new JFXPanel();
        bp = new BorderPane();
        exitButton = new Button("Schliessen");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label text = new Label(message);
                text.setWrapText(true);
                text.setStyle("-fx-text-fill: red; -fx-font: 16px \"Comic sans MS\"; text-align: right;");
                bp.setCenter(text);
                exitButton.setCancelButton(true);
                exitButton.setDefaultButton(true);
                exitButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                exitButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        dispose();
                    }
                });
                bp.setBottom(exitButton);
                bp.setPadding(new Insets(4));
                scene = new Scene(bp, 400, 200);
                panel.setScene(scene);
            }
        });
        setAlwaysOnTop(true);
        this.add(panel);
        this.setTitle("Warnung!");
        this.setSize(400, 200);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    void closeNot(Boolean b){
        if(b){
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    System.exit(1);
                }
            });
            exitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.exit(1);
                }
            });
        }else{
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    dispose();
                }
            });
            exitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    dispose();
                }
            });
        }
    }

    void setNot(final String item){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Main.rootPane.rechteLeiste.notifications.remove(item);
                dispose();
            }
        });
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.rootPane.rechteLeiste.notifications.remove(item);
                dispose();
            }
        });
    }
}