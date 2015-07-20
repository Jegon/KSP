import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by Jegoni on 02.06.2015.
 */
public class EingabePane extends GridPane {

    private final Label label;
    private final TextField ipField;
    private final TextField portField;

    EingabePane() {
        label = new Label("Willkommen!\n" +
                "Bitte Daten eingeben:");
        label.setFont(Font.font("Comic sans MS", 20));
        this.add(label, 0, 0, 2, 1);
        Label ipLabel = new Label("IP:");
        this.add(ipLabel, 0, 1);
        ipField = new TextField("localhost");
        this.add(ipField, 1, 1);
        final Label portLabel = new Label("Port:");
        this.add(portLabel, 0, 2);
        portField = new TextField("24242");
        portField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > 5)
                    portField.setText(portField.getText(0, 5));
                else if (newValue.intValue() > oldValue.intValue()) {
                    char ch = portField.getText().charAt(oldValue.intValue());
                    if (!(ch >= '0' && ch <= '9')) {
                        portField.setText(portField.getText().substring(0, portField.getText().length() - 1));
                    }
                }
            }

        });
        this.add(portField, 1, 2);
        final Button button = new Button("Fertig!");
        HBox hBox = new HBox(10);
        hBox.getChildren().add(button);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        this.add(hBox, 1, 3);
        this.setPadding(new Insets(30));
        this.setHgap(10);
        this.setVgap(10);
        this.setAlignment(Pos.CENTER);
        ipField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                portField.requestFocus();
            }
        });
        portField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                button.requestFocus();
            }
        });
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (!Main.createConnection(InetAddress.getByName(ipField.getText()), Integer.parseInt(portField.getText())))
                        setFailure(InetAddress.getByName(ipField.getText()), Integer.parseInt(portField.getText()));
                } catch (IOException ex) {
                    label.setText("Fehlerhafte Eingabe!!!");
                    label.setTextFill(Color.RED);
                }
            }
        });
    }


    public void setFailure(InetAddress inetAddress, int port) {
        label.setText("Letzter Versuch, mit " + inetAddress.getHostAddress() + ":" + port + " zu verbinden,\n" +
                "ist fehlgeschlagen!");
        label.setTextFill(Color.RED);
    }
}
