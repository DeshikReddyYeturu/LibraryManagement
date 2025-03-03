package librarymanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ViewBooks extends JFrame {

    private JTable booksTable;
    private DefaultTableModel model;

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

    @Override
    public void setVisible(boolean b) {
        if (b) {
            refreshData();
        }
        super.setVisible(b);
    }
    private void refreshData() {
        model.setRowCount(0);

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT b.book_id, b.book_name, b.author, " +
                     "(b.quantity - COUNT(CASE WHEN ib.return_date IS NULL THEN ib.book_id ELSE NULL END)) AS available_quantity " +
                     "FROM books b " +
                     "LEFT JOIN issued_books ib ON b.book_id = ib.book_id " +
                     "GROUP BY b.book_id, b.book_name, b.author")) {
            while (rs.next()) {
                String bookId = rs.getString("book_id");
                String bookName = rs.getString("book_name");
                String author = rs.getString("author");
                int availableQuantity = rs.getInt("available_quantity");

                model.addRow(new Object[]{bookId, bookName, author, availableQuantity});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
