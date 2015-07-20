import javafx.scene.layout.BorderPane;

/**
 * Created by Jegoni on 01.06.2015.
 */
public class RootPane extends BorderPane {
    public final HauptPane hauptPane = new HauptPane();
    private final LoadingPane loadingPane = new LoadingPane();
    private final PortPane portPane = new PortPane();
    private final InfoPane infoPane = new InfoPane();
    private final InfoTabPane infoTabPane = new InfoTabPane();

    RootPane() {
        this.setCenter(loadingPane);
    }

    public void setLoading() {
        this.getChildren().removeAll(this.getChildren());
        this.setCenter(loadingPane);
    }

    public void setPorteingabe() {
        this.getChildren().removeAll(this.getChildren());
        this.setCenter(portPane);
        Main.primaryStage.setTitle("Bitte Port eingeben!");
    }

    public void setPorteingabe(boolean b, int port) {
        this.getChildren().removeAll(this.getChildren());
        this.setCenter(portPane);
        if (b) {
            portPane.setFailure(port);
            Main.primaryStage.setTitle("Fehlgeschlagen! Bitte Port eingeben!");
        } else
            Main.primaryStage.setTitle("Bitte Port eingeben!");
    }

    public void setHauptPane() {
        this.getChildren().removeAll(this.getChildren());
        this.setCenter(hauptPane);
        Main.primaryStage.setTitle("Server läuft!");
    }

    public void setInfoPane(){
        this.getChildren().removeAll(this.getChildren());
        this.setCenter(infoPane);
        Main.primaryStage.setTitle("Server-MSG verfassen");
    }

    public void setInfoTabPane(){
        this.getChildren().removeAll(this.getChildren());
        this.setCenter(infoTabPane);
        Main.primaryStage.setTitle("Neuen Pfad für Info angeben!");
    }
}
