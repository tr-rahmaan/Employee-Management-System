import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeManagementSystem {
    private List<Employee> employeeList = new ArrayList<>();
    private Admin admin = new Admin("admin", "1234");
    private final String FILE_NAME = "employees.dat";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeManagementSystem().start());
    }

    private void start() {
        loadEmployees();
        showRoleSelection();
    }

    private void showRoleSelection() {
        JFrame frame = new JFrame("Select Role");
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton adminBtn = new JButton("Admin");
        JButton empBtn = new JButton("Employee");
        JButton exitBtn = new JButton("Exit");

        adminBtn.addActionListener(e -> {
            frame.dispose(); showAdminLogin();
        });
        empBtn.addActionListener(e -> { frame.dispose(); showEmployeeLogin(); });
        exitBtn.addActionListener(e -> System.exit(0));

        frame.add(adminBtn); frame.add(empBtn); frame.add(exitBtn);
        frame.setLocationRelativeTo(null); frame.setVisible(true);
    }

    private void showAdminLogin() {
        JFrame frame = new JFrame("Admin Login");
        frame.setSize(300, 150);
        frame.setLayout(new GridLayout(3, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Login");
        JButton backBtn = new JButton("Back");

        frame.add(new JLabel("Username:")); frame.add(userField);
        frame.add(new JLabel("Password:")); frame.add(passField);
        frame.add(backBtn); frame.add(loginBtn);

        loginBtn.addActionListener(e -> {
            if (admin.login(userField.getText(), new String(passField.getPassword()))) {
                frame.dispose(); showAdminMenu();
            } else JOptionPane.showMessageDialog(frame, "Invalid Credentials!");
        });

        backBtn.addActionListener(e -> { frame.dispose(); showRoleSelection(); });

        frame.setLocationRelativeTo(null); frame.setVisible(true);
    }

    private void showAdminMenu() {
        JFrame frame = new JFrame("Admin Menu");
        frame.setSize(400, 350);
        frame.setLayout(new GridLayout(5, 1, 5, 5));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton addBtn = new JButton("Add Employee");
        JButton viewBtn = new JButton("View Employees");
        JButton attBtn = new JButton("View Attendance");
        JButton delBtn = new JButton("Delete Employee");
        JButton logoutBtn = new JButton("Logout");

        addBtn.addActionListener(e -> addEmployee());
        viewBtn.addActionListener(e -> viewEmployees(false));
        attBtn.addActionListener(e -> viewEmployees(true));
        delBtn.addActionListener(e -> deleteEmployee());
        logoutBtn.addActionListener(e -> { frame.dispose(); showRoleSelection(); });

        frame.add(addBtn); frame.add(viewBtn); frame.add(attBtn);
        frame.add(delBtn); frame.add(logoutBtn);

        frame.setLocationRelativeTo(null); frame.setVisible(true);
    }

    private void showEmployeeLogin() {
        JFrame frame = new JFrame("Employee Attendance");
        frame.setSize(300, 150);
        frame.setLayout(new GridLayout(3, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField idField = new JTextField();
        JButton entryBtn = new JButton("Mark Entry");
        JButton leaveBtn = new JButton("Mark Leave");
        JButton exitBtn = new JButton("Exit");

        frame.add(new JLabel("Employee ID:")); frame.add(idField);
        frame.add(entryBtn); frame.add(leaveBtn);
        frame.add(new JLabel("")); frame.add(exitBtn);

        entryBtn.addActionListener(e -> markTime(idField, true));
        leaveBtn.addActionListener(e -> markTime(idField, false));
        exitBtn.addActionListener(e -> { frame.dispose(); showRoleSelection(); });

        frame.setLocationRelativeTo(null); frame.setVisible(true);
    }

    private void markTime(JTextField idField, boolean entry) {
        try {
            int id = Integer.parseInt(idField.getText());
            Employee emp = findEmployeeById(id);
            if (emp != null) {
                String time = new Date().toString();
                if (entry) emp.setEntryTime(time); else emp.setLeaveTime(time);
                saveEmployees();
                JOptionPane.showMessageDialog(null, entry ? "Entry time recorded." : "Leave time recorded.");
            } else JOptionPane.showMessageDialog(null, "Employee not found.");
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(null, "Invalid ID."); }
    }

    private Employee findEmployeeById(int id) {
        for (Employee emp : employeeList) if (emp.getId() == id) return emp;
        return null;
    }

    private void addEmployee() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField salaryField = new JTextField();
        JTextField ratingField = new JTextField();

        Object[] msg = {"ID:", idField, "Name:", nameField, "Salary:", salaryField, "Performance Rating:", ratingField};
        int option = JOptionPane.showConfirmDialog(null, msg, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                double rating = Double.parseDouble(ratingField.getText());
                employeeList.add(new Employee(id, name, "", "", salary, rating));
                saveEmployees();
                JOptionPane.showMessageDialog(null, "Employee added.");
            } catch (Exception e) { JOptionPane.showMessageDialog(null, "Invalid input."); }
        }
    }

    private void viewEmployees(boolean showAttendance) {
        StringBuilder sb = new StringBuilder();
        if (employeeList.isEmpty()) sb.append("No employees available.");
        else {
            for (Employee emp : employeeList) {
                if (showAttendance) {
                    sb.append("ID: ").append(emp.getId())
                            .append(", Name: ").append(emp.getName())
                            .append(", Entry: ").append(emp.getEntryTime())
                            .append(", Leave: ").append(emp.getLeaveTime()).append("\n");
                } else sb.append(emp.toString()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void deleteEmployee() {
        String input = JOptionPane.showInputDialog("Enter Employee ID to delete:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                boolean removed = employeeList.removeIf(emp -> emp.getId() == id);
                if (removed) saveEmployees();
                JOptionPane.showMessageDialog(null, removed ? "Employee deleted." : "Employee not found.");
            } catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Invalid ID."); }
        }
    }

    private void saveEmployees() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(employeeList);
            System.out.println("Data saved successfully to " + new File(FILE_NAME).getAbsolutePath());
        } catch (IOException e) { System.out.println("Error saving data: " + e.getMessage()); }
    }


    private void loadEmployees() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            employeeList = (ArrayList<Employee>) ois.readObject();
            System.out.println("Data loaded. Employees count: " + employeeList.size());
        } catch (IOException | ClassNotFoundException e) { System.out.println("Error loading data: " + e.getMessage()); }
    }
}
