
package librarymanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
public class ViewBooks extends JFrame {
    JTable booksTable;
    DefaultTableModel model;

    ViewBooks() {
        setTitle("View Books");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"Book ID", "Book Name", "Author", "Available Quantity"};
        model = new DefaultTableModel(columnNames, 0);

        booksTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(booksTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    