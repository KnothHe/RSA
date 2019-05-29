import javax.swing.*;
import java.awt.*;

public class RButton extends JButton {
    public RButton(String str) {
        super(str);
        this.setBackground(new Color(100, 100, 100));
        this.setForeground(new Color(251, 165, 36));
        this.setFont(new Font("TimesRoman", Font.PLAIN, 12));
   }
}
