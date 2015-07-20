import java.net.InetAddress;
import java.util.List;

/**
 * Created by Jegoni on 01.06.2015.
 */
public class ArgReader {

    private InetAddress inetAddress;
    private Boolean noVid;
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
        if (!stringList.contains("-ip"))
            inetAddress = null;
        else
            try {
                inetAddress = InetAddress.getByName(stringList.get(stringList.indexOf("-ip") + 1));
            } catch (Exception ex) {
                inetAddress = null;
                Main.logger.warning("-ip Argument ist fehlerhaft!");
            }
        if (!stringList.contains("+noVid"))
            noVid = false;
        else
            noVid = true;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public int getPort() {
        return port;
    }

    public boolean getNoVid() {
        return noVid;
    }
}