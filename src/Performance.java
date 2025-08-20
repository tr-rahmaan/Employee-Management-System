import java.io.Serializable;

public class Performance implements Serializable {
    private static final long serialVersionUID = 1L;

    private String employeeID;
    private int rating;

    public Performance(String employeeID, int rating) {
        this.employeeID = employeeID;
        this.rating = rating;
    }

    public void displayPerformance() {
        System.out.println("Employee " + employeeID + " rating: " + rating);
    }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
}
