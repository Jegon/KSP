import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 * Created by Jegoni on 19.07.2015.
 */
public class InfoPane extends GridPane{

    InfoPane(){
        Label label = new Label("Info Notification an alle schreiben:");
        label.setFont(Font.font("Comic sans MS", 20));
        this.add(label, 0, 0, 2, 1);
        final TextArea eingabe = new TextArea();
        this.add(eingabe, 0, 1, 2, 1);
        eingabe.setWrapText(true);
        final CheckBox checkBox = new CheckBox("Instant Popup");
        this.add(checkBox, 0, 2, 2, 1);
        Button back = new Button("Zurück");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.rootPane.setHauptPane();
            }
        });
        back.setCancelButton(true);
        back.setAlignment(Pos.CENTER_RIGHT);
        Button senden = new Button("Senden");
        senden.setDefaultButton(true);
        senden.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(checkBox.isSelected())
                    Main.userVerwaltung.msgAll("12" + eingabe.getText());
                else
                    Main.userVerwaltung.msgAll("11" + eingabe.getText());
                eingabe.setText("");
                Main.rootPane.setHauptPane();
            }
        });
        this.add(senden, 0, 3);
        this.add(back, 1 , 3);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(30, 30, 30, 30));
        this.setAlignment(Pos.CENTER);
    }
}
