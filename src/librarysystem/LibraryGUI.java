package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class EmptyFieldException extends Exception {
    public EmptyFieldException(String message) {
        super(message);
    }
}

public class LibraryGUI extends JFrame implements ActionListener {

    JLabel nameLabel, rollLabel, titleLabel, categoryLabel, issueLabel, returnLabel, remarksLabel;

    JTextField nameField, rollField, titleField, issueField, returnField;
    JTextArea remarksArea;

    JComboBox<String> categoryBox;
    JRadioButton newBtn, oldBtn;
    ButtonGroup group;

    JButton issueBtn, resetBtn, exitBtn;

    public LibraryGUI() {

        setTitle("Library Book Issue System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       
        nameLabel = new JLabel("Student Name");
        rollLabel = new JLabel("Roll Number");
        titleLabel = new JLabel("Book Title");
        categoryLabel = new JLabel("Book Category");
        issueLabel = new JLabel("Issue Date (dd/MM/yyyy)");
        returnLabel = new JLabel("Return Date (dd/MM/yyyy)");
        remarksLabel = new JLabel("Remarks");

        nameField = new JTextField();
        rollField = new JTextField();
        titleField = new JTextField();
        issueField = new JTextField();
        returnField = new JTextField();

        remarksArea = new JTextArea(3, 20);

        String categories[] = {"Select", "Programming", "AI", "Databases", "Networking"};
        categoryBox = new JComboBox<>(categories);

        newBtn = new JRadioButton("New Edition");
        oldBtn = new JRadioButton("Old Edition");

        group = new ButtonGroup();
        group.add(newBtn);
        group.add(oldBtn);

        issueBtn = new JButton("Issue Book");
        resetBtn = new JButton("Reset");
        exitBtn = new JButton("Exit");

        issueBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        exitBtn.addActionListener(this);

       
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JScrollPane scroll = new JScrollPane(remarksArea);

        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addComponent(nameLabel)
                        .addComponent(rollLabel)
                        .addComponent(titleLabel)
                        .addComponent(categoryLabel)
                        .addComponent(issueLabel)
                        .addComponent(returnLabel)
                        .addComponent(remarksLabel)
                    )
                    .addGroup(layout.createParallelGroup()
                        .addComponent(nameField)
                        .addComponent(rollField)
                        .addComponent(titleField)
                        .addComponent(categoryBox)
                        .addComponent(issueField)
                        .addComponent(returnField)
                        .addComponent(scroll)
                    )
                )

                .addGroup(layout.createSequentialGroup()
                    .addComponent(newBtn)
                    .addComponent(oldBtn)
                )

                .addGroup(layout.createSequentialGroup()
                    .addComponent(issueBtn)
                    .addComponent(resetBtn)
                    .addComponent(exitBtn)
                )
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameField))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(rollLabel)
                    .addComponent(rollField))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(titleLabel)
                    .addComponent(titleField))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(categoryLabel)
                    .addComponent(categoryBox))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(issueLabel)
                    .addComponent(issueField))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(returnLabel)
                    .addComponent(returnField))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(remarksLabel)
                    .addComponent(scroll))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(newBtn)
                    .addComponent(oldBtn))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(issueBtn)
                    .addComponent(resetBtn)
                    .addComponent(exitBtn))
        );

        add(panel);

        setSize(600, 450);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == issueBtn) {

            try {
                if (nameField.getText().isEmpty() ||
                    rollField.getText().isEmpty() ||
                    titleField.getText().isEmpty() ||
                    issueField.getText().isEmpty() ||
                    returnField.getText().isEmpty()) {

                    throw new EmptyFieldException("Please fill all required fields");
                }

                if (!rollField.getText().matches("[0-9]+")) {
                    throw new Exception("Invalid Roll Number");
                }

                if (categoryBox.getSelectedIndex() == 0) {
                    throw new Exception("Please select book category");
                }

                if (!newBtn.isSelected() && !oldBtn.isSelected()) {
                    throw new Exception("Please select book type");
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                Date issueDate = sdf.parse(issueField.getText());
                Date returnDate = sdf.parse(returnField.getText());

                if (returnDate.before(issueDate)) {
                    throw new Exception("Return date cannot be earlier than issue date");
                }

                Integer.parseInt(rollField.getText());

                JOptionPane.showMessageDialog(this, "Book Issued Successfully!");

            } catch (EmptyFieldException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Roll Number must be numeric");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }

        else if (e.getSource() == resetBtn) {
            nameField.setText("");
            rollField.setText("");
            titleField.setText("");
            issueField.setText("");
            returnField.setText("");
            remarksArea.setText("");
            categoryBox.setSelectedIndex(0);
            group.clearSelection();
        }

        else if (e.getSource() == exitBtn) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new LibraryGUI();
    }
}