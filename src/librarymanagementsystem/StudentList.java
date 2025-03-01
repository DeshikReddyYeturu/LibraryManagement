
package librarymanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StudentList extends JFrame {
    JTable studentListTable;
    StudentList() {
        setTitle("Student List");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"Student ID", "Student Name", "Book ID", "Book Name", "Issue Date", "Return Date"};
        Object[][] data = {};

        try (Connection conn = DataBaseConnection.getConnection();
             //scrolling
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = stmt.executeQuery("SELECT s.student_id, s.student_name, ib.book_id, b.book_name, ib.issue_date, ib.return_date " +
                     "FROM students s " +
                     "LEFT JOIN issued_books ib ON s.student_id = ib.student_id " +
                     "LEFT JOIN books b ON ib.book_id = b.book_id")) {

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            data = new Object[rowCount][6];
            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getString("student_id");
                data[i][1] = rs.getString("student_name");
                data[i][2] = rs.getString("book_id");
                data[i][3] = rs.getString("book_name");
                data[i][4] = rs.getString("issue_date");
                data[i][5] = rs.getString("return_date") != null ? rs.getString("return_date") : "Yet to be returned";
                i++;
            }

            studentListTable = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(studentListTable);
            add(scrollPane, BorderLayout.CENTER);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        setVisible(true);
    }
}