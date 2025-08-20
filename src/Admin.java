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
