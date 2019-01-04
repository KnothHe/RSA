import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RTextArea extends JTextArea {
    public RTextArea(String str) {
        super(str);
        this.setRows(10);
        this.setColumns(30);
        this.setFont(new Font("TimesRoman", Font.PLAIN, 12));
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        this.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }
}
