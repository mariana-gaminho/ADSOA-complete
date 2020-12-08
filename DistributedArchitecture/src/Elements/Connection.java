package Elements;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
    // Socket to which we are connected
    Socket socketInfo;

    // What are we connected to (it can be either a node, client or server)
    String targetElement;

    ObjectOutputStream oos;

    // Socket info
    public Socket getSocketInfo()
    {
        return socketInfo;
    }
    public void setSocketInfo(Socket s)
    {
        this.socketInfo = s;
    }

    // Target element
    public String getTargetElement()
    {
        return targetElement;
    }
    public void setTargetElement(String element)
    {
        this.targetElement = element;
    }

    // Output stream
    public ObjectOutputStream getOos() {
        return oos;
    }
    public void setOos(ObjectOutputStream oos)
    {
        this.oos = oos;
    }
}