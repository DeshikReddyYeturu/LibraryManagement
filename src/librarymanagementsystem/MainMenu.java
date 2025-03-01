
package librarymanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame implements ActionListener {
    JButton addBookButton, issueBookButton, returnBookButton, viewBooksButton, studentsButton;

    MainMenu() {
        setTitle("Library Management System - Main Menu");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        addBookButton = new JButton("Add Book");
        addBookButton.setPreferredSize(new Dimension(200, 50));
        addBookButton.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        addBookButton.addActionListener(this);
        panel.add(addBookButton, gbc);

        issueBookButton = new JButton("Issue Book");
        issueBookButton.setPreferredSize(new Dimension(200, 50));
        issueBookButton.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        issueBookButton.addActionListener(this);
        panel.add(issueBookButton, gbc);

        returnBookButton = new JButton("Return Book");
        returnBookButton.setPreferredSize(new Dimension(200, 50));
        returnBookButton.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        returnBookButton.addActionListener(this);
        panel.add(returnBookButton, gbc);

        viewBooksButton = new JButton("View Books");
        viewBooksButton.setPreferredSize(new Dimension(200, 50));
        viewBooksButton.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        viewBooksButton.addActionListener(this);
        panel.add(viewBooksButton, gbc);

        studentsButton = new JButton("Students");
        studentsButton.setPreferredSize(new Dimension(200, 50));
        studentsButton.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        studentsButton.addActionListener(this);
        panel.add(studentsButton, gbc);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBookButton) {
            new AddBook();
        } else if (e.getSource() == issueBookButton) {
            new IssueBook();
        } else if (e.getSource() == returnBookButton) {
            new ReturnBook();
        } else if (e.getSource() == viewBooksButton) {
           // System.out.println("View Books button clicked."); 

            try {
               // System.out.println("Creating ViewBooks frame."); 
                ViewBooks viewBooksFrame = new ViewBooks();
                viewBooksFrame.setVisible(true);
                //System.out.println("ViewBooks frame is now visible.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error opening View Books: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == studentsButton) {
            createStudentMenu();
        }
    }

    private void createStudentMenu() {
        JPopupMenu studentMenu = new JPopupMenu();
        JMenuItem addStudentItem = new JMenuItem("Add Student");
        JMenuItem viewStudentsItem = new JMenuItem("View Students");
        JMenuItem studentListItem = new JMenuItem("Student List");

        addStudentItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddStudent();
            }
        });

        viewStudentsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ViewStudents();
            }
        });
        studentListItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new StudentList();
            }
        });
        studentMenu.add(addStudentItem);
        studentMenu.add(viewStudentsItem);
        studentMenu.add(studentListItem);

        studentMenu.show(studentsButton, 0, studentsButton.getHeight());
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}