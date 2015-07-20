import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.util.Callback;

/**
 * Created by Jegoni on 19.07.2015.
 */
public class Notifications extends TitledPane {
    private ObservableList<String> data = FXCollections.observableArrayList();

    Notifications(){
        this.setText("Notifications (0)");
        ListView<String> listView = new ListView<>();
        this.setContent(listView);
        listView.setItems(data);
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                return new NotListCellFabrik();
            }
        });
    }

    void add(final String item){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                data.add(item);
                setText("Notifications (" + data.size() + ")");
            }
        });
    }

    void remove(String item){
        data.remove(item);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setText("Notifications (" + data.size() + ")");
            }
        });
    }

    void leeren(){
        data.clear();
    }
}
