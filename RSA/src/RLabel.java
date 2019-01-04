import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RLabel extends JLabel {
    public RLabel(String str) {
        super(str);
        this.setFont(new Font("TimesRoman", Font.PLAIN, 12));
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        this.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }

    public RLabel(String str, int horizontalAlignment) {
        this(str);
        this.setHorizontalAlignment(horizontalAlignment);
    }
}
