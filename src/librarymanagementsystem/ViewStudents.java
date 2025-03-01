
package librarymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewStudents extends JFrame {
    JTable studentsTable;

    ViewStudents() {
        setTitle("View Students");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"Student ID", "Student Name", "Student Contact"};
        Object[][] data = {};

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = stmt.executeQuery("SELECT student_id, student_name, student_contact FROM students")) {

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            data = new Object[rowCount][3];
            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getString("student_id");
                data[i][1] = rs.getString("student_name");
                data[i][2] = rs.getString("student_contact");
                i++;
            }

            studentsTable = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(studentsTable);
            add(scrollPane, BorderLayout.CENTER);

        } catch (SQLException ex) {
            ex.printStackTrace();
            
        }

        setVisible(true);
    }
}
