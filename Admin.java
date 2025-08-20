import java.io.Serializable;

public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;

    private String user;
    private String pass;

    public Admin(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public boolean login(String username, String password) {
        return user.equals(username) && pass.equals(password);
    }
}
import java.io.Serializable;

public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {

        return name;
    }

    public void setId(int id) {

        this.id = id;
    }
}
