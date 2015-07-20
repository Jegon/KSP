import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

/**
 * Created by Jegoni on 01.06.2015.
 */
public class LeisteOben extends StackPane {
    private final Label label;
    private final HBox linksOben;
    private final HBox rechtsOben;

    LeisteOben() {
        linksOben = new HBox(10);
        rechtsOben = new HBox(2);
        linksOben.setPadding(new Insets(5));
        rechtsOben.setPadding(new Insets(5));
        label = new Label(Main.username + ": Noch nicht mit Server verbunden!");
        label.setFont(Font.font("Comic sans MS", 20));
        Button minimize = new Button();
        minimize.setGraphic(new ImageView((new Image(("minimize.png")))));
        Button close = new Button();
        close.setGraphic(new ImageView(new Image("close.png")));
        linksOben.getChildren().addAll(label);
        linksOben.setAlignment(Pos.TOP_LEFT);
        rechtsOben.getChildren().addAll(minimize, close);
        rechtsOben.setAlignment(Pos.TOP_RIGHT);
        minimize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.primaryStage.toBack();
            }
        });
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.shutdown();
            }
        });
        this.getChildren().addAll(linksOben, rechtsOben);
    }

    public void update() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label.setText(Main.username + ": " + Main.users.thisUser.getRang());
            }
        });
    }
}
