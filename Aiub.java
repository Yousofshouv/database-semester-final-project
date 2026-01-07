 import javax.swing.*;
import java.awt.event.*;

public class Aiub extends JFrame {
    private Department cse = new Department("CSE");
    private Department eee = new Department("EEE");
    private Department bba = new Department("BBA");

    private JTextField nameField, idField, searchField;
    private JTextArea displayArea;
    private JRadioButton cseRadio, eeeRadio, bbaRadio;

    public Aiub() {
        setTitle("AIUB Student Management System");
        setSize(500, 600);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Input Labels and Fields
        JLabel l1 = new JLabel("Name:"); l1.setBounds(20, 20, 80, 25); add(l1);
        nameField = new JTextField(); nameField.setBounds(100, 20, 150, 25); add(nameField);

        JLabel l2 = new JLabel("ID:"); l2.setBounds(20, 55, 80, 25); add(l2);
        idField = new JTextField(); idField.setBounds(100, 55, 150, 25); add(idField);

        // Department Selection
        cseRadio = new JRadioButton("CSE", true); cseRadio.setBounds(20, 90, 60, 25);
        eeeRadio = new JRadioButton("EEE"); eeeRadio.setBounds(85, 90, 60, 25);
        bbaRadio = new JRadioButton("BBA"); bbaRadio.setBounds(150, 90, 60, 25);
        ButtonGroup group = new ButtonGroup(); group.add(cseRadio); group.add(eeeRadio); group.add(bbaRadio);
        add(cseRadio); add(eeeRadio); add(bbaRadio);

        // Buttons
        JButton addBtn = new JButton("Add Student"); addBtn.setBounds(20, 130, 120, 30); add(addBtn);
        JButton viewBtn = new JButton("View All"); viewBtn.setBounds(150, 130, 100, 30); add(viewBtn);

        searchField = new JTextField(); searchField.setBounds(20, 180, 120, 25); add(searchField);
        JButton searchBtn = new JButton("Search ID"); searchBtn.setBounds(150, 180, 100, 25); add(searchBtn);

        displayArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(displayArea);
        scroll.setBounds(20, 220, 440, 300); add(scroll);

        // Action Listeners
        addBtn.addActionListener(e -> {
            Department selected = getSelectedDept();
            if(selected.addStudent(nameField.getText(), idField.getText())) {
                displayArea.setText("Student added successfully!");
            } else {
                displayArea.setText("Error: ID already exists or connection failed.");
            }
        });

        viewBtn.addActionListener(e -> displayArea.setText(getSelectedDept().getAllStudents()));

        searchBtn.addActionListener(e -> displayArea.setText(getSelectedDept().searchById(searchField.getText())));

        // Shutdown hook
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { DatabaseConnection.closeConnection(); }
        });
    }

    private Department getSelectedDept() {
        if (cseRadio.isSelected()) return cse;
        if (eeeRadio.isSelected()) return eee;
        return bba;
    }

    public static void main(String[] args) {
        new Aiub().setVisible(true);
    }
}
