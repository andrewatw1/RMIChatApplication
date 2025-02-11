import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Chat_itf extends Remote {
    void sendMessage(String message, String name) throws RemoteException;

    ArrayList<String> printHistory() throws RemoteException;

    void saveChatHistory() throws RemoteException;
}
