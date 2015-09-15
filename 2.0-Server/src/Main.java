import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Jegoni on 01.06.2015.
 */
public class Main extends Application {
    public static final Logger logger = Logger.getGlobal();
    public static Stage primaryStage;
    public static RootPane rootPane;
    public static PortListener portListener;
    public static UserVerwaltung userVerwaltung;
    private ArgReader argReader;
    static String infoTab = "swaggaboisarehere.html|||false";//todo standard seite
    public static String programmcom = " | | | | |";

    public static boolean createPortListener(int port) {
        try {
            rootPane.setLoading();
            portListener = new PortListener(port);
            rootPane.setHauptPane();
            return true;
        } catch (IOException ex) {
            logger.warning(ex.getLocalizedMessage());
            return false;
        }
    }

    public static void shutdown() {
        try{
            portListener.terminate();
            userVerwaltung.terminateAll();
        }catch(Exception ignored){
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        userVerwaltung = new UserVerwaltung();
        primaryStage = stage;
        argReader = new ArgReader(getParameters().getRaw());
        rootPane = new RootPane();
        Scene scene = new Scene(rootPane, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        if (argReader.getPort() == -1)
            rootPane.setPorteingabe();
        else if (!createPortListener(argReader.getPort()))
            rootPane.setPorteingabe(true, argReader.getPort());
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                shutdown();
            }
        });
    }
}
