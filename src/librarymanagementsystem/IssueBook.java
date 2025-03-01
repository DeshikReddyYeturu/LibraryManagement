
package librarymanagementsystem;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IssueBook extends JFrame implements ActionListener {
    JTextField bookIdField, bookNameField, studentIdField, studentNameField, studentContactField;
    JButton issueButton;

    IssueBook() {
        setTitle("Issue Book");
        setSize(400, 300); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setBounds(50, 30, 100, 20);
        add(bookIdLabel);

        bookIdField = new JTextField();
        bookIdField.setBounds(150, 30, 150, 20);
        add(bookIdField);

        JLabel bookNameLabel = new JLabel("Book Name:");
        bookNameLabel.setBounds(50, 60, 100, 20);
        add(bookNameLabel);

        bookNameField = new JTextField();
        bookNameField.setBounds(150, 60, 150, 20);
        add(bookNameField);

        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdLabel.setBounds(50, 90, 100, 20);
        add(studentIdLabel);

        studentIdField = new JTextField();
        studentIdField.setBounds(150, 90, 150, 20);
        add(studentIdField);

        JLabel studentNameLabel = new JLabel("Student Name:");
        studentNameLabel.setBounds(50, 120, 100, 20);
        add(studentNameLabel);

        studentNameField = new JTextField();
        studentNameField.setBounds(150, 120, 150, 20);
        add(studentNameField);

        JLabel studentContactLabel = new JLabel("Student Contact:");
        studentContactLabel.setBounds(50, 150, 100, 20);
        add(studentContactLabel);

        studentContactField = new JTextField();
        studentContactField.setBounds(150, 150, 150, 20);
        add(studentContactField);

        issueButton = new JButton("Issue Book");
        issueButton.setBounds(150, 200, 100, 30);
        issueButton.addActionListener(this);
        add(issueButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String bookId = bookIdField.getText();
        String bookName = bookNameField.getText();
        String studentId = studentIdField.getText();
        String studentName = studentNameField.getText();
        String studentContact = studentContactField.getText();
        String issueDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement issueStmt = conn.prepareStatement("INSERT INTO issued_books (book_id, student_id, issue_date) VALUES (?, ?, ?)")) {

            issueStmt.setString(1, bookId);
            issueStmt.setString(2, studentId);
            issueStmt.setString(3, issueDate);
            int rows = issueStmt.executeUpdate();

            if (rows > 0) {
                try (PreparedStatement studentStmt = conn.prepareStatement("INSERT INTO students (student_id, student_name, student_contact) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE student_name = ?, student_contact = ?")) {
                    studentStmt.setString(1, studentId);
                    studentStmt.setString(2, studentName);
                    studentStmt.setString(3, studentContact);
                    studentStmt.setString(4, studentName);
                    studentStmt.setString(5, studentContact);
                    studentStmt.executeUpdate();
                }

                JOptionPane.showMessageDialog(this, "Book issued successfully!");
                this.dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new IssueBook();
    }
}