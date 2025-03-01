
package librarymanagementsystem;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AddStudent extends JFrame implements ActionListener {
    JTextField studentIdField, studentNameField, studentContactField;
    JButton addButton;

    AddStudent() {
        setTitle("Add Student");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdLabel.setBounds(50, 30, 100, 20);
        add(studentIdLabel);

        studentIdField = new JTextField();
        studentIdField.setBounds(150, 30, 150, 20);
        add(studentIdField);

        JLabel studentNameLabel = new JLabel("Student Name:");
        studentNameLabel.setBounds(50, 60, 100, 20);
        add(studentNameLabel);

        studentNameField = new JTextField();
        studentNameField.setBounds(150, 60, 150, 20);
        add(studentNameField);

        JLabel studentContactLabel = new JLabel("Student Contact:");
        studentContactLabel.setBounds(50, 90, 100, 20);
        add(studentContactLabel);

        studentContactField = new JTextField();
        studentContactField.setBounds(150, 90, 150, 20);
        add(studentContactField);

        addButton = new JButton("Add Student");
        addButton.setBounds(150, 130, 100, 30);
        addButton.addActionListener(this);
        add(addButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String studentId = studentIdField.getText();
        String studentName = studentNameField.getText();
        String studentContact = studentContactField.getText();

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO students (student_id, student_name, student_contact) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE student_name = ?, student_contact = ?")) {

            stmt.setString(1, studentId);
            stmt.setString(2, studentName);
            stmt.setString(3, studentContact);
            stmt.setString(4, studentName);
            stmt.setString(5, studentContact);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Student added successfully!");
                this.dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
           
        }
    }

    public static void main(String[] args) {
        new AddStudent();
    }
}