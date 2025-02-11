import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Info_itf extends Remote {
    String getName() throws RemoteException;

    boolean getInChat() throws RemoteException;

    void setInChat(boolean inChat) throws RemoteException;
}