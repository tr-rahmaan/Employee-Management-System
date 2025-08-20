import java.io.Serializable;

public class Employee extends Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String entryTime;
    private String leaveTime;
    private double salary;
    private Performance performance;

    public Employee(int id, String name, String email, String department, double salary, double rating) {
        super(name);
        setId(id);
        this.salary = salary;
        this.performance = new Performance(String.valueOf(id), (int) rating);
    }

    public String getEntryTime() {
        return entryTime;
    }
    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getLeaveTime() {
        return leaveTime;
    }
    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public double getSalary() {
        return salary;
    }
    public Performance getPerformance() {
        return performance;
    }


    public String toString() {
        return "ID: " + getId() + ", Name: " + getName() + ", Salary: " + salary + ", Rating: " + performance.getRating();
    }
}
