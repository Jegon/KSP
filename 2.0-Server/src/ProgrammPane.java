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
public class ProgrammPane extends GridPane{

    ProgrammPane(){
        Label label = new Label("Programmbefehle eingeben:");
        label.setFont(Font.font("Comic sans MS", 20));
        this.add(label, 0, 0, 2, 1);
        final Label firefoxl = new Label("Firefox:");
        final TextField firefoxt = new TextField("cmd /c start ");
        this.add(firefoxl, 0, 1, 1, 1);
        this.add(firefoxt, 1, 1, 1, 1);
        final Label angryIPScannerl = new Label("AngryIPScanner:");
        final TextField angryIPScannert = new TextField("cmd /c start ");
        this.add(angryIPScannerl, 0, 2, 1, 1);
        this.add(angryIPScannert, 1, 2, 1, 1);
        final Label notepadl = new Label("Notepad:");
        final TextField notepadt = new TextField("cmd /c start notepad.exe");
        this.add(notepadl, 0, 3, 1, 1);
        this.add(notepadt, 1, 3, 1, 1);
        final Label notepadppl = new Label("Notepad++:");
        final TextField notepadppt = new TextField("cmd /c start ");
        this.add(notepadppl, 0, 4, 1, 1);
        this.add(notepadppt, 1, 4, 1, 1);
        final Label powershelll = new Label("Powershell:");
        final TextField powershellt = new TextField("cmd /c start powershell.exe");
        this.add(powershelll, 0, 5, 1, 1);
        this.add(powershellt, 1, 5, 1, 1);
        final Label benutzeradminl = new Label("Benutzeradmin:");
        final TextField benutzeradmint = new TextField("cmd /c start ");
        this.add(benutzeradminl, 0, 6, 1, 1);
        this.add(benutzeradmint, 1, 6, 1, 1);
        final Button back = new Button("Zurück");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.rootPane.setHauptPane();
            }
        });
        back.setCancelButton(true);
        final Button senden = new Button("Senden");
        senden.setDefaultButton(true);
        senden.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String s = firefoxt.getText() + "|" + angryIPScannert.getText() + "|" + notepadppt.getText()
                        + "|" + notepadppt.getText() + "|" + powershellt.getText() + "|" + benutzeradmint.getText();
                Main.userVerwaltung.msgAll("08" + s);
                Main.programmcom = s;
                Main.rootPane.setHauptPane();
            }
        });
        firefoxt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        angryIPScannert.requestFocus();
                    }
                });
            }
        });
        angryIPScannert.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        notepadt.requestFocus();
                    }
                });
            }
        });
        notepadt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        notepadppt.requestFocus();
                    }
                });
            }
        });
        notepadppt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        powershellt.requestFocus();
                    }
                });
            }
        });
        powershellt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        benutzeradmint.requestFocus();
                    }
                });
            }
        });
        benutzeradmint.setOnAction(new EventHandler<ActionEvent>() {
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
        this.add(senden, 0, 7);
        this.add(back, 1 , 7);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(30, 30, 30, 30));
        this.setAlignment(Pos.CENTER);
    }
}
