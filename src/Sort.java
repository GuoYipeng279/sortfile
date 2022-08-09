import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.function.Function;

import static java.lang.Math.max;

public class Sort extends JFrame {
    private JPanel Main;
    private JButton change, submit, clear;
    private JTextField rootAddress;
    private JComboBox comboBox1;
    private JTextField textField1, textField2;
    private JRadioButton REGRadioButton;
    private JTextField ifother;
    private JRadioButton ALLRadioButton, RecurRadioButton;
    private JTable table1;
    private JButton to_csv;
    private JRadioButton KW1REG, KW2REG;
    private JTextField fileCounter;
    private JTextField keyword;
    int fileCnt=0;
    DefaultTableModel model;
    Object[] header = new Object[]{"File","Date","Keyword1","Keyword2"};
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    private int searchFile(File file, String regexp, Function<File, Integer> func, boolean recur){
        int cnt = 1;
        fileCnt++;
        fileCounter.setText(String.valueOf(fileCnt));
        if(file.isDirectory()){
            File[] files = file.listFiles();
            assert files != null;
            for(File f: files) {
                if (f.getName().matches(regexp))
                    func.apply(f);
                if (recur) cnt+=searchFile(f,regexp,func,true);
            }
        }
        return cnt;
    }

    public static void exportToCSV(JTable table, String path) {
        try {
            TableModel model = table.getModel();
            FileWriter csv = new FileWriter(new File(path));
            for (int i = 0; i < model.getColumnCount(); i++) {
                csv.write(model.getColumnName(i) + ",");
            }
            csv.write("\n");
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    System.out.println(model.getValueAt(i, j));
                    csv.write(model.getValueAt(i, j).toString() + ",");
                }
                csv.write("\n");
            }
            csv.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    public Sort() {
        RecurRadioButton.setSelected(true);
        REGRadioButton.setSelected(true);
        ALLRadioButton.setSelected(true);
        KW1REG.setSelected(true);
        KW2REG.setSelected(true);
        submit.addActionListener(e -> {
            String target = Objects.requireNonNull(comboBox1.getSelectedItem()).toString();
            if(Objects.equals(target, "其他")) target = ifother.getText();
            if (REGRadioButton.isSelected()) target = ".*"+target+".*";
            File file = new File(rootAddress.getText());
            searchFile(file, target, child -> {
                String kw1 = textField1.getText();
                String kw2 = textField2.getText();
                String kw1found = "Not Found";
                String kw2found = "Not Found";
                if((KW1REG.isSelected() || KW2REG.isSelected()) &&
                        FilenameUtils.getExtension(child.getName()).equals("pdf")) {
                    System.out.println(child.getName());
                    PDDocument document;
                    try {
                        document = Loader.loadPDF(child);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    String text = null;
                    try {
                        text = new PDFTextStripper().getText(document);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    if(KW1REG.isSelected()) {
                        int at1 = text.indexOf(kw1);
                        if (at1 >= 0) kw1found = text.substring(max(at1 - 20, 0), max(at1 - 10, 0) + 60);
                    }
                    if(KW2REG.isSelected()) {
                        int at2 = text.indexOf(kw2);
                        if (at2 >= 0) kw2found = text.substring(max(at2 - 20, 0), max(at2 - 10, 0) + 60);
                    }
//                    System.out.println(text);
                }
                model.addRow(new Object[]{
                        child.getName(),
                        dateFormat.format(child.lastModified()),
                        kw1found,
                        kw2found
                });
                return 0;
            }, RecurRadioButton.isSelected());
        });
        clear.addActionListener(e -> model.setRowCount(0));
        to_csv.addActionListener(e -> exportToCSV(table1,"D:\\study\\javao\\sortfile.csv"));
        ALLRadioButton.addActionListener(e -> {
            REGRadioButton.setSelected(ALLRadioButton.isSelected());
            KW1REG.setSelected(ALLRadioButton.isSelected());
            KW2REG.setSelected(ALLRadioButton.isSelected());
        });
    }

    public static void main(String[] args) throws IOException {
        Sort s = new Sort();
        s.setContentPane(s.Main);
        s.setTitle("sortFile");
        s.setVisible(true);
        s.setBounds(200,200,700,700);
        s.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        model = new DefaultTableModel(0,4);
        model.setColumnIdentifiers(header);
        table1 = new JTable(model);
    }
}
