import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Chat_itfImpl implements Chat_itf {
    private ArrayList<String> messages;

    @SuppressWarnings("unchecked")
    public Chat_itfImpl() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./messages.ser"))) {
            this.messages = (ArrayList<String>) ois.readObject(); // Read once and assign
            System.out.println("Chat history loaded");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            this.messages = new ArrayList<>(); // Ensure it is initialized if loading fails
        }

    }

    @Override
    public void sendMessage(String message, String userName) throws RemoteException {
        String sb = userName + ":  " + message;
        messages.add(sb);
    }

    @Override
    public ArrayList<String> printHistory() throws RemoteException {
        return messages;
    }

    @Override
    public void saveChatHistory() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("messages.ser"))) {
            oos.writeObject(messages);
            System.out.println("List saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
