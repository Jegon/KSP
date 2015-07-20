import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * Created by Jegoni on 02.06.2015.
 */
public class MyTab extends Tab {

    ObservableList<MSG> data;
    TableView<MSG> tableView;

    MyTab() {
        tableView = new TableView<>();
        TableColumn<MSG, String> user = new TableColumn<>("Benutzer:");
        TableColumn<MSG, String> nachricht = new TableColumn<>("Nachricht:");
        /*user.setCellFactory(new Callback<TreeView, TreeCell>() {
            @Override
            public TreeCell call(TreeView treeView) {
                return new UserTreeCellFabrik();
            }
        });*/
        user.setCellValueFactory(new PropertyValueFactory<MSG, String>("absender"));
        nachricht.setCellValueFactory(new PropertyValueFactory<MSG, String>("text"));
        user.setComparator(null);
        nachricht.setComparator(null);
        tableView.getColumns().add(user);
        tableView.getColumns().add(nachricht);
        data = FXCollections.observableArrayList();
        tableView.setItems(data);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        HBox eingabe = new HBox();
        final TextField textField = new TextField();
        textField.setPrefColumnCount(10000);
        textField.setStyle("-fx-background-color: lightgray");
        eingabe.getChildren().add(textField);
        textField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!textField.getText().equals("")) {
                    Main.connection.writeObject("03" + textField.getText());
                    textField.setText("");
                }
            }
        });
        VBox box = new VBox(5);
        box.setPadding(new Insets(0, 10, 10, 10));
        box.getChildren().addAll(tableView, eingabe);
        this.setContent(box);

        Callback<TableColumn<MSG, String>, TableCell<MSG, String>> myCallback
                = new Callback<TableColumn<MSG, String>, TableCell<MSG, String>>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableCell() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        if (empty) super.setVisible(false);
                        else super.setText(item.toString());
                    }
                };
            }
        };
        user.setCellFactory(new Callback<TableColumn<MSG, String>, TableCell<MSG, String>>() {
            @Override
            public TableCell<MSG, String> call(TableColumn<MSG, String> msgStringTableColumn) {
                return new UserTableCellFabrik();
            }
        });
        Callback<TableView<MSG>, TableRow<MSG>> myCallback2 =
                new Callback<TableView<MSG>, TableRow<MSG>>() {
                    @Override
                    public TableRow call(TableView table) {
                        return new TableRow<MSG>() {
                            protected void updateItem(MSG item, boolean empty) {
                                if (empty) super.setStyle("-fx-background-color: lightgray");
                                super.updateItem(item, empty);
                            }
                        };
                    }
                };
        //tableView.setRowFactory(myCallback2);
    }

    public void update(String absender, String nachricht) {
        data.add(new MSG(absender, nachricht));
        tableView.setItems(data);
        tableView.scrollTo(tableView.getItems().size());
    }

    public void refresh() {
        tableView.setVisible(false);
        tableView.setVisible(true);//todo funzt nicht
    }

    public static class MSG {

        private final SimpleStringProperty absender;
        private final SimpleStringProperty text;

        MSG(String nAbsender, String nText) {
            this.absender = new SimpleStringProperty(nAbsender);
            this.text = new SimpleStringProperty(nText);
        }

        public String getAbsender() {
            return absender.get();
        }

        public void setAbsender(String nAbsender) {
            absender.set(nAbsender);
        }

        public String getText() {
            return text.get();
        }

        public void setText(String nText) {
            text.set(nText);
        }
    }
}