
package librarymanagementsystem;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
public class AddBook extends JFrame implements ActionListener {
    JTextField bookIdField, bookNameField, authorField, quantityField;
    JButton addButton;

    AddBook() {
        setTitle("Add Book");
        setSize(400, 250);
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

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(50, 90, 100, 20);
        add(authorLabel);

        authorField = new JTextField();
        authorField.setBounds(150, 90, 150, 20);
        add(authorField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(50, 120, 100, 20);
        add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBounds(150, 120, 150, 20);
        add(quantityField);

        addButton = new JButton("Add Book");
        addButton.setBounds(150, 160, 100, 30);
        addButton.addActionListener(this);
        add(addButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String bookId = bookIdField.getText();
        String bookName = bookNameField.getText();
        String author = authorField.getText();
        int quantity = Integer.parseInt(quantityField.getText());

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO books (book_id, book_name, author, quantity) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, bookId);
            stmt.setString(2, bookName);
            stmt.setString(3, author);
            stmt.setInt(4, quantity);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Book added successfully! Book ID: " + bookId);
                this.dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AddBook();
    }
}