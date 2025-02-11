import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Client {

    public static void printChatMenu() {
        System.out.println("Here is the list of available commands:");
        System.out.println(":join - to join the chatroom");
        System.out.println(":menu - to access the list of commands");
        System.out.println(":history - to access the chat history");
        System.out.println(":leave - to leave the chatroom");
        System.out.println(":exit - to terminate the session");
    }

    public static void main(String[] args) throws IOException {
        try {
            if (args.length < 2) {
                System.out.println("Usage: java HelloClient <rmiregistry host> <rmiregistry port>");
                return;
            }

            String host = args[0];
            int port = Integer.parseInt(args[1]);


            Registry registry = LocateRegistry.getRegistry(host, port); // get registry rmi
            Chat_itf chat = (Chat_itf)registry.lookup("ChatService");
            ClientRegistry_itf clientRegistry = (ClientRegistry_itf)registry.lookup("RegistryService");
            Info_itf clientInfo = null;

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            boolean isRegistered = false;
            String username = null;
            boolean isInChat = false;

            String in;

            label:
            while(true) {

                while(!isRegistered) {
                    System.out.println("Please enter your username to continue");
                    username = br.readLine();
                    System.out.println("*********");
                    if (clientRegistry.isRegistered(username)) {
                        System.out.println("Welcome back " + username);
                        clientInfo = clientRegistry.getClient(username);
                    } else {
                        System.out.println("That username is not registered with us, we have gone ahead and " +
                                        "registered you in our system.");
                        clientInfo = new Info_itfImpl(username);
                        clientRegistry.register(clientInfo);

                    }
                    isRegistered = true;
                    isInChat = clientInfo.getInChat();
                    printChatMenu();
                }

                in = br.readLine();

                switch (in) {
                case ":exit":
                    break label;
                case ":menu":
                    printChatMenu();
                    break;
                case ":join":
                    if (isInChat) {
                        System.out.println("You are already in the chatroom");
                    } else {
                        clientRegistry.removeClient(clientInfo);
                        clientInfo.setInChat(true);
                        clientRegistry.register(clientInfo);
                        isInChat = true;
                        System.out.println("You are now in the chatroom");
                    }
//                    clientRegistry.saveClients();
                    break;
                case ":leave":
                    if (!isInChat) {
                        System.out.println(
                                "You are not in the chatroom, enter :join in the terminal to join the " + "chatroom");
                    } else {
                        clientRegistry.removeClient(clientInfo);
                        clientInfo.setInChat(true);
                        clientRegistry.register(clientInfo);
                        isInChat = false;
                        System.out.println("You have left the chatroom");
                    }
//                    clientRegistry.saveClients();
                    break;
                case ":history":
                    if (isInChat) {
                        ArrayList<String> chatHistory = chat.printHistory();
                        for (String s : chatHistory) {
                            System.out.println(s);
                        }
                    } else {
                        System.out.println("Please join a chatroom first");
                    }
                    break;
                default:
                    if (isInChat) {
                        chat.sendMessage(in, clientInfo.getName());
                        chat.saveChatHistory();
                    } else {
                        System.out.println("You are not in the chatroom");
                    }
                    break;
                }

                System.out.println("*********");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}