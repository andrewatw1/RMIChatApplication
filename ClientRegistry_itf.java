import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientRegistry_itf extends Remote {
    boolean isRegistered(String client) throws RemoteException;

    void register(Info_itf client) throws RemoteException;

    void removeClient(Info_itf client) throws RemoteException;

    Info_itf getClient(String client) throws RemoteException;
}