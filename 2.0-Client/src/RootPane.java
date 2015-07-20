import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

import java.net.InetAddress;

/**
 * Created by Jegoni on 01.06.2015.
 */
public class RootPane extends BorderPane {
    protected final LeisteOben leisteOben = new LeisteOben();
    //------------------------------------------------------------------------
    final TabLeiste tabLeiste = new TabLeiste();
    final RechteLeiste rechteLeiste = new RechteLeiste();
    private final LoadingPane loadingPane = new LoadingPane();
    //----------------------------------------------------------------------------
    private final EingabePane eingabePane = new EingabePane();
    final TabMitte tabMitte = new TabMitte();

    RootPane() {
        this.setTop(leisteOben);
        this.setCenter(loadingPane);
        this.setStyle("-fx-background-color: lightgray");
        Main.primaryStage.getIcons().add(new Image("KSP.png"));
    }

    private void removeEverythingExceptTop() {
        this.setCenter(null);
        this.setBottom(null);
        this.setRight(null);
        this.setLeft(null);
    }

    public void setLoading() {
        removeEverythingExceptTop();
        this.setCenter(loadingPane);
    }

    public void setEingabe() {
        removeEverythingExceptTop();
        this.setCenter(eingabePane);
        Main.primaryStage.setTitle("Bitte Daten eingeben!");
    }

    public void setEingabe(boolean b, InetAddress inetAddress, int port) {
        removeEverythingExceptTop();
        this.setCenter(eingabePane);
        if (b) {
            eingabePane.setFailure(inetAddress, port);
            Main.primaryStage.setTitle("Fehlgeschlagen! Bitte erneut Daten eingeben!");
        } else
            Main.primaryStage.setTitle("Bitte Daten eingeben!");
    }

    public void setHauptContent() {
        Main.primaryStage.setTitle("GL HF @KSP");
        removeEverythingExceptTop();
        this.setRight(rechteLeiste);
        this.setCenter(tabMitte);
        this.setBottom(tabLeiste);
    }
}
