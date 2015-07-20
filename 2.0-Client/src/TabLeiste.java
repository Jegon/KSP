import javafx.scene.control.TabPane;

/**
 * Created by Jegoni on 02.06.2015.
 */
public class TabLeiste extends TabPane {
    MyTab global = new MyTab();

    TabLeiste() {
        this.getTabs().add(global);
        global.setClosable(false);
        global.setText("Global");
    }
}
