import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;

/**
 * Created by Jegoni on 19.07.2015.
 */
public class NotListCellFabrik extends ListCell<String>{
    @Override
    public void updateItem(final String item, boolean empty){
        super.updateItem(item, empty);
        if(item != null){
            int k = Integer.parseInt(item.substring(0, 2));
            switch(k){
                case 10:
                    setText("Freundschaftsanfrage von " + item.substring(2));
                    setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            NotFriend notFriend = new NotFriend(item.substring(2));
                        }
                    });
                    break;
                case 11:
                    setText("Servermeldung");
                    setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            NotInfo notInfo = new NotInfo(item.substring(2));
                            notInfo.setTitle("Servermeldung");
                            notInfo.setNot(item);
                        }
                    });
                    break;
            }
        }
    }
}