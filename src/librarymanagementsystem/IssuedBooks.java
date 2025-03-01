/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class IssuedBooks extends JFrame {
    JTable issuedBooksTable;

    IssuedBooks() {
        setTitle("Issued Books List");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"Issue ID", "Book ID", "Student ID", "Issue Date", "Return Date"};
        Object[][] data = {};

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = stmt.executeQuery("SELECT * FROM issued_books")) {

            rs.last(); // Move to the last row to get the count
            int rowCount = rs.getRow();
            rs.beforeFirst(); // Move back to the beginning

            data = new Object[rowCount][5];
            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getInt("issue_id");
                data[i][1] = rs.getInt("book_id");
                data[i][2] = rs.getInt("student_id");
                data[i][3] = rs.getString("issue_date");
                data[i][4] = rs.getString("return_date");
                i++;
            }

            issuedBooksTable = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(issuedBooksTable);
            add(scrollPane, BorderLayout.CENTER);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        setVisible(true);
    }
}