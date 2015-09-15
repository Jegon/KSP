import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;

/**
 * Created by Jegoni on 04.06.2015.
 */
public class TabMitte extends TabPane {

    private final WebEngine homeWebEngine;
    private final Tab josamilude;
    private final Tab infos;
    private final WebEngine infosWebEngine;
    private String filterpfad;
    private String pfad;

    TabMitte() {
        infos = new Tab("Infos");
        infos.setClosable(false);
        WebView infoWebView = new WebView();
        infosWebEngine = infoWebView.getEngine();
        infosWebEngine.loadContent("<h1 style=\"text-align:center\"> Lädt... </h1>");
        josamilude = new Tab("Josas Website!");
        josamilude.setClosable(false);
        infos.setContent(infoWebView);
        WebView homeWebView = new WebView();
        homeWebEngine = homeWebView.getEngine();
        //ScrollPane homeScrollPane = new ScrollPane();
        //homeScrollPane.setContent(homeWebView);
        homeWebEngine.loadContent("<h1 style=\"text-align:center\"> Lädt... </h1>");
        homeWebEngine.load("http://www.josamilu.de/index.html");
        homeWebEngine.locationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!(t1.contains("josamilu.de")))
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            homeWebEngine.load("http://josamilu.de/index.html");
                        }
                    });
            }
        });
        josamilude.setContent(homeWebView);
        Tab x = new Tab("Blaze it");
        x.setClosable(false);
        Button button = new Button("Krasser lade Screen");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.rootPane.setLoading();
            }
        });
        Button betaRefresh = new Button("Beta Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.rootPane.setLoading();
            }
        });
        betaRefresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.connection.writeObject("04");
                Main.connection.writeObject("05");
                Main.connection.writeObject("07");
            }
        });
        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(button, betaRefresh);
        x.setContent(hBox);
        this.getTabs().addAll(infos, x);
    }

    public void update() {
    }

    public void updateInfos(final String input){
        if(!input.contains("|||"))
            throw new NullPointerException("TabMitte.updateInfos(): es konnte im input string nicht \"|||\" gefunden werden!");
        pfad = input.substring(0, input.indexOf("|||"));
        filterpfad = input.substring(input.indexOf("|||") + 3);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                infosWebEngine.load(pfad);
            }
        });
        if(filterpfad.equals("false"))
            infosWebEngine.locationProperty().removeListener(infoW);
        else
            infosWebEngine.locationProperty().addListener(infoW);
    }

    void rankdep(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(!Main.users.thisUser.getRang().equals("user")){
                    getTabs().add(josamilude);
                }else
                if(getTabs().contains(josamilude))
                    getTabs().remove(josamilude);
            }
        });
    }

    private ChangeListener<String> infoW = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
            if (!(t1.contains(filterpfad)))
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        infosWebEngine.load(pfad);
                    }
                });
        }
    };
}
