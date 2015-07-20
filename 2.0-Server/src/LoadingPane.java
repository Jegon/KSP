import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;

/**
 * Created by Jegoni on 01.06.2015.
 */
public class LoadingPane extends StackPane {
    LoadingPane() {
        ProgressBar progressBar = new ProgressBar(-1);
        progressBar.setScaleX(4);
        progressBar.setScaleY(4);
        Label label = new Label("Lädt...");
        label.setScaleX(2);
        label.setScaleY(2);
        this.getChildren().addAll(progressBar, label);
    }
}
