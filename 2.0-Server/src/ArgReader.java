import java.util.List;

/**
 * Created by Jegoni on 01.06.2015.
 */
public class ArgReader {

    private int port;

    ArgReader(List<String> stringList) {
        if (!stringList.contains("-port"))
            port = -1;
        else {
            try {
                port = Integer.parseInt(stringList.get(stringList.indexOf("-port") + 1));
            } catch (Exception ex) {
                port = -1;
                Main.logger.warning("-port Argument ist fehlerhaft!");
            }
        }
    }

    public int getPort() {
        return port;
    }
}
