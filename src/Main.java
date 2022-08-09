import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here

        File f1 = new File("D:\\study\\y4\\QC-Consent-Firstname-Lastname.pdf");
        File f2 = new File("C:\\Users\\Administrator\\Downloads\\document (2).pdf");
        File f3 = new File("C:\\Users\\Administrator\\Downloads\\document (3).pdf");
        PDDocument p1 = Loader.loadPDF(f1);
        PDDocument p2 = Loader.loadPDF(f2);
        PDDocument p3 = Loader.loadPDF(f3);
        p1.setAllSecurityToBeRemoved(true);
        p2.setAllSecurityToBeRemoved(true);
        p3.setAllSecurityToBeRemoved(true);
        p1.save("C:\\Users\\Administrator\\Downloads\\cst.pdf");
        p2.save("C:\\Users\\Administrator\\Downloads\\trs.pdf");
        p3.save("C:\\Users\\Administrator\\Downloads\\cer.pdf");
    }
}
