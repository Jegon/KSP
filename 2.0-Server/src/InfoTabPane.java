import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 * Created by Jegoni on 19.07.2015.
 */
public class InfoTabPane extends GridPane{

    InfoTabPane(){
        Label label = new Label("Neuen InfoTab Pfad angeben:");
        label.setFont(Font.font("Comic sans MS", 20));
        this.add(label, 0, 0, 2, 1);
        final TextField eingabe = new TextField();
        this.add(eingabe, 0, 1, 2, 1);
        Button back = new Button("Zurück");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.rootPane.setHauptPane();
            }
        });
        back.setCancelButton(true);
        back.setAlignment(Pos.CENTER_RIGHT);
        final Button senden = new Button("Senden");
        senden.setDefaultButton(true);
        senden.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.userVerwaltung.msgAll("06" + eingabe.getText());
                Main.infoTab = eingabe.getText();
                Main.rootPane.setHauptPane();
            }
        });
        eingabe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        senden.requestFocus();
                    }
                });
            }
        });
        this.add(senden, 0, 2);
        this.add(back, 1 , 2);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(30, 30, 30, 30));
        this.setAlignment(Pos.CENTER);
    }
}
