package librarymanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel; 
public class StudentList extends JFrame {
    JTable studentListTable;
    StudentList() {
        setTitle("Student List");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        String[] columnNames = {"Student ID", "Student Name", "Book ID", "Book Name", "Issue Date", "Return Date"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = stmt.executeQuery("SELECT s.student_id, s.student_name, ib.book_id, b.book_name, ib.issue_date, ib.return_date " +
                     "FROM students s " +
                     "LEFT JOIN issued_books ib ON s.student_id = ib.student_id " +
                     "LEFT JOIN books b ON ib.book_id = b.book_id")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("student_id"),
                        rs.getString("student_name"),
                        rs.getString("book_id"),
                        rs.getString("book_name"),
                        rs.getString("issue_date"),
                        rs.getString("return_date") != null ? rs.getString("return_date") : "Yet to be returned"
                });
            }
            studentListTable = new JTable(model); 
            JScrollPane scrollPane = new JScrollPane(studentListTable);
            add(scrollPane, BorderLayout.CENTER);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // User friendly error message.
        }
        setVisible(true);
    }
}
