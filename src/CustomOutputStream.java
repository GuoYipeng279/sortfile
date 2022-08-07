import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class CustomOutputStream extends OutputStream {
    public enum Output {TEXT, TABLE}
    private final JTextArea textArea;
    private final JTable table;
    private static Output choice=Output.TEXT;

    private StringBuilder builder;

    public CustomOutputStream(JTextArea textArea, JTable table) {
        this.textArea = textArea;
        this.table = table;
        builder = new StringBuilder();
    }

    public static void setChoice(Output o){
        choice = o;
        System.out.println("改变状态");
    }

    @Override
    public void write(int b) throws IOException {
        if(choice == Output.TEXT) {
            // redirects data to the text area
            textArea.append(String.valueOf((char) b));
            // scrolls the text area to the end of data
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }else if(choice == Output.TABLE){
            if(b==13) {
                table.setValueAt(builder.toString(),table.getEditingRow(),table.getEditingColumn());
                builder = new StringBuilder();
                table.changeSelection(table.getEditingRow()+1,table.getEditingColumn(),false,false);
            }
            else{
                builder.append((char) b);
            }
        }
    }
}
