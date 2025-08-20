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
