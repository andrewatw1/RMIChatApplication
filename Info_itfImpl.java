import java.io.Serializable;
import java.util.ArrayList;

public class Info_itfImpl implements Info_itf, Serializable {
    private final String name;
    private boolean inChat;

    public Info_itfImpl(String name) {
        this.name = name;
        this.inChat = false;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean getInChat() {
        return this.inChat;
    }

    @Override
    public void setInChat(boolean inChat) {
        this.inChat = inChat;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Info_itfImpl that = (Info_itfImpl) obj;
        return this.name.equals(that.name); // or other attributes for equality
    }

    @Override
    public int hashCode() {
        return this.name.hashCode(); // or other attributes for hash code
    }
}