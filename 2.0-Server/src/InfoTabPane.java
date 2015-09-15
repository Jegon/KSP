import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
        final Label label1 = new Label("Filterpfad eingeben:");
        final Label label2 = new Label("(Vorsicht!!! mögliche unendlich Schleife durch Weiterleitungen!!!)");
        label1.setFont(Font.font("Comic sans MS", 20));
        label2.setFont(Font.font("Comic sans MS", 10));
        this.add(label1, 0, 2, 2, 1);
        this.add(label2, 0, 3, 2, 1);
        final TextField filterpfad = new TextField();
        senden.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (filterpfad.getText().equals("")) {
                    Main.userVerwaltung.msgAll("07" + eingabe.getText() + "|||false");
                    Main.infoTab = eingabe.getText() + "|||false";
                } else {
                    Main.userVerwaltung.msgAll("07" + eingabe.getText() + "|||" + filterpfad.getText());
                    Main.infoTab = eingabe.getText() + "|||" + filterpfad.getText();
                }
                Main.rootPane.setHauptPane();
            }
        });
        eingabe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        filterpfad.requestFocus();
                    }
                });
            }
        });
        filterpfad.setOnAction(new EventHandler<ActionEvent>() {
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
        this.add(filterpfad, 0, 4, 2, 1);
        this.add(senden, 0, 5);
        this.add(back, 1, 5);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(30, 30, 30, 30));
        this.setAlignment(Pos.CENTER);
    }
}
