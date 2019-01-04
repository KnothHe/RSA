import javax.swing.*;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class RScrollPane extends JScrollPane {
    public RScrollPane(JTextArea jTextArea) {
        super(jTextArea);
        JScrollBar jScrollBar = new JScrollBar();
        final Color jScrollBarColor = new Color(100, 100, 100);
        ScrollBarUI jScrollBarUI = new BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(jScrollBarColor);
                return button;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(jScrollBarColor);
                return button;
            }
        };

        jScrollBar.setUI(jScrollBarUI);
        this.setVerticalScrollBar(jScrollBar);
        this.setHorizontalScrollBar(null);
    }
}
