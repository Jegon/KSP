import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.net.InetAddress;
import java.util.logging.Logger;

/**
 * Created by Jegoni on 01.06.2015.
 */
public class Main extends Application {
    public static final Logger logger = Logger.getGlobal();
    //public static final String username = System.getProperty("user.name"); //todo öndern
    public static final String username = "swagger";
    public static final Users users = new Users();
    public static Stage primaryStage;
    public static Connection connection;
    public static RootPane rootPane;
    private static ArgReader argReader;

    public static void shutdown() {
        System.exit(01);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void rankdep(){
        Main.rootPane.tabMitte.rankdep();
        Main.rootPane.rechteLeiste.rankdep();
    }

    public static boolean createConnection(InetAddress inetAddress, int port) {
        try {
            connection = new Connection(inetAddress, port);
            connection.start();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        argReader = new ArgReader(getParameters().getRaw());
        rootPane = new RootPane();
        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);

        //Fenster beschreiben:
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                shutdown();
            }
        });

        //Weida:
        if (argReader.getPort() == -1 || argReader.getInetAddress() == null)
            rootPane.setEingabe();
        else if (!createConnection(argReader.getInetAddress(), argReader.getPort()))
            rootPane.setEingabe(true, argReader.getInetAddress(), argReader.getPort());
    }
}
