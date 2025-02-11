import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientRegistry_itfImpl implements ClientRegistry_itf {
    ArrayList<Info_itf> clients = new ArrayList<>();

    public ClientRegistry_itfImpl() {
    }

    @Override
    public boolean isRegistered(String client) throws RemoteException {
        return clients.stream().anyMatch(clientInfo -> {
            try {
                return clientInfo.getName().equals(client);
            } catch (RemoteException e) {
                System.err.println("Error retrieving client name: " + e.getMessage());
                return false; // Ignore this client and continue
            }
        });
    }

    @Override
    public void register(Info_itf client) throws RemoteException {
        this.clients.add(client);
    }

    @Override
    public void removeClient(Info_itf client) throws RemoteException {
        this.clients.remove(client);
    }

    @Override
    public Info_itf getClient(String username) throws RemoteException {
        return this.clients.stream().filter(client -> {
            try {
                return client.getName().equals(username);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }).findFirst().orElse(null);
    }
}