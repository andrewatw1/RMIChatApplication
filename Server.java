import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        try {
            Chat_itfImpl chat = new Chat_itfImpl(); // Create chat interface
            Chat_itf chat_stub = (Chat_itf)UnicastRemoteObject.exportObject(chat, 0);
            ClientRegistry_itfImpl clientRegistry = new ClientRegistry_itfImpl(); // Create client registry interface
            ClientRegistry_itf register_stub = (ClientRegistry_itf)UnicastRemoteObject.exportObject(clientRegistry, 0);
            Registry registry = null;
            if (args.length > 0) {
                registry = LocateRegistry.getRegistry(Integer.parseInt(args[0]));
            } else {
                registry = LocateRegistry.getRegistry();
            }

            registry.rebind("ChatService", chat_stub);
            registry.rebind("RegistryService", register_stub);
            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Error on server :" + String.valueOf(e));
            e.printStackTrace();
        }

    }
}