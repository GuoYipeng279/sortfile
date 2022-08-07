import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sort extends JFrame {
    private JPanel Main;
    private JButton change;
    private JButton submit;
    private JButton clear;
    private JTable window;
    private JTextField rootAddress;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JRadioButton REGRadioButton;
    private JTextField textField2;
    private JTextField ifother;
    DefaultTableModel model;

    public Sort() {
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        Sort s = new Sort();
        s.setContentPane(s.Main);
        s.setTitle("sortFile");
        s.setVisible(true);
        s.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
