package librarymanagementsystem;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReturnBook extends JFrame implements ActionListener {
    JTextField bookIdField, bookNameField, studentIdField, studentNameField, studentContactField;
    JButton returnButton;
    ReturnBook() {
        setTitle("Return Book");
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

        returnButton = new JButton("Return Book");
        returnButton.setBounds(150, 200, 100, 30);
        returnButton.addActionListener(this);
        add(returnButton);

        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        String bookId = bookIdField.getText();
        String bookName = bookNameField.getText();
        String studentId = studentIdField.getText();
        String studentName = studentNameField.getText();
        String studentContact = studentContactField.getText();
        String returnDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Connection conn = null;
        try {
            conn = DataBaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Update return_date in issued_books
            try (PreparedStatement returnStmt = conn.prepareStatement("UPDATE issued_books SET return_date = ? WHERE book_id = ? AND student_id = ?")) {
                returnStmt.setString(1, returnDate);
                returnStmt.setString(2, bookId);
                returnStmt.setString(3, studentId);
                int rows = returnStmt.executeUpdate();

                if (rows == 0) {
                    JOptionPane.showMessageDialog(this, "Book return failed. Book ID or Student ID may be incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
                    conn.rollback();
                    return;
                }
            }

            // student info upd
            try (PreparedStatement studentStmt = conn.prepareStatement("INSERT INTO students (student_id, student_name, student_contact) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE student_name = ?, student_contact = ?")) {
                studentStmt.setString(1, studentId);
                studentStmt.setString(2, studentName);
                studentStmt.setString(3, studentContact);
                studentStmt.setString(4, studentName);
                studentStmt.setString(5, studentContact);

                System.out.println("Adding/Updating student with ID: " + studentId);
                studentStmt.executeUpdate();
            }

            // Increase book quantity in books table
            try (PreparedStatement updateQuantityStmt = conn.prepareStatement("UPDATE books SET quantity = quantity + 1 WHERE book_id = ?")) {
                updateQuantityStmt.setString(1, bookId);
                updateQuantityStmt.executeUpdate();
            }

            conn.commit();
            JOptionPane.showMessageDialog(this, "Book returned successfully!");
            this.dispose();

        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new ReturnBook();
    }
}