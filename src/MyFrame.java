import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import java.util.Objects;

public class MyFrame extends JFrame implements ActionListener {
    JButton button;
    JButton submit, clear;
    JTextField rootAddress;
    JTextArea wenJianMuLu;
    JTextArea chaZhaoLeiXing;
    JComboBox<String> fileTypes;
    JTextArea output;
    JTable table;
    JScrollPane bar;
    MyFrame(){
        button = new JButton("#NAME?");
        button.setBounds(200,100,100,50);
        button.addActionListener(this);
        button.setFocusable(false);
        button.setBorder(BorderFactory.createEtchedBorder());
        submit = new JButton("提交");
        submit.addActionListener(this);
        submit.setFocusable(false);
        submit.setBorder(BorderFactory.createEtchedBorder());

        wenJianMuLu = new JTextArea("文件目录");
        wenJianMuLu.setFocusable(false);

        rootAddress = new JTextField();
        rootAddress.setPreferredSize(new Dimension(250,30));
        rootAddress.setText("D:\\study\\y3");

        chaZhaoLeiXing = new JTextArea("查找类型");
        chaZhaoLeiXing.setFocusable(false);

        fileTypes = new JComboBox<>(new String[]{"ISDA","EMIR","MIFID","pdf","其他"});

        output = new JTextArea();
        output.setSize(new Dimension(100,100));
        clear = new JButton("清除");
        clear.addActionListener(this);
        clear.setFocusable(false);
        clear.setBorder(BorderFactory.createEtchedBorder());

        table = new JTable(5,5);

        PrintStream printStream = new PrintStream(new CustomOutputStream(output,table));
        System.setOut(printStream);
        System.setErr(printStream);
        bar = new JScrollPane(output);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setSize(500,500);
        this.setVisible(true);
        this.add(button);
        this.add(wenJianMuLu);
        this.add(rootAddress);
        this.add(submit);
        this.add(chaZhaoLeiXing);
        this.add(fileTypes);
        this.add(output);
        this.add(clear);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==button){
            CustomOutputStream.setChoice(CustomOutputStream.Output.TABLE);
            String msg = rootAddress.getText();
            System.out.println(msg);
            button.setEnabled(false);
        }
        if(e.getSource()==submit){
            String target = Objects.requireNonNull(fileTypes.getSelectedItem()).toString();
            String regexp = ".*"+target+".*";
            File file = new File(rootAddress.getText());
            if(file.isDirectory()){
                File[] files = file.listFiles();
                assert files != null;
                for(File f: files) {
                    if (f.getName().matches(regexp))
                        System.out.println(f.getName());
                }
            }
        }
        if(e.getSource()==clear){
            output.setText("");
        }
    }
}
