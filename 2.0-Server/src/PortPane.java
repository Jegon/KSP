import javafx.application.Platform;
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

/**
 * Created by Jegoni on 01.06.2015.
 */
public class PortPane extends GridPane {

    private final Label label;
    private final TextField textField;

    PortPane() {
        label = new Label("Willkommen!\n" +
                "Bitte Port eingeben:");
        label.setFont(Font.font("Comic sans MS", 20));
        this.add(label, 0, 0, 2, 1);
        Label portLabel = new Label("Port:");
        this.add(portLabel, 0, 1);
        textField = new TextField("24242");
        textField.setPrefColumnCount(5);
        textField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > 5)
                    textField.setText(textField.getText(0, 5));
                else if (newValue.intValue() > oldValue.intValue()) {
                    char ch = textField.getText().charAt(oldValue.intValue());
                    if (!(ch >= '0' && ch <= '9')) {
                        textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
                    }
                }
            }

        });
        this.add(textField, 1, 1);
        final Button button = new Button("Fertig!");
        HBox hBox = new HBox(10);
        hBox.getChildren().add(button);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        this.add(hBox, 1, 2);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(30, 30, 30, 30));
        this.setAlignment(Pos.CENTER);
        textField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                button.requestFocus();
            }
        });
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!Main.createPortListener(Integer.parseInt(textField.getText()))) {
                    Main.rootPane.setPorteingabe();
                    setFailure(Integer.parseInt(textField.getText()));
                }
            }
        });
    }

    public void setFailure(final int port) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label.setText("Letzter Versuch, einen PortListener auf Port '" + port + "'\n" +
                        "zu erstellen, ist fehlgeschlagen!");
                textField.setText("24242");
                label.setTextFill(Color.RED);
            }
        });
    }
}
