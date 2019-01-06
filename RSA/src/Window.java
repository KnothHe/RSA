import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;

public class Window extends JFrame {
    private RSA rsa = new RSA();

    public Window() {
        super("RSA Algorithm");

        RButton newKeyButton = new RButton("New Key");
        RButton encodeButton = new RButton("Encode");
        RButton decodeButton = new RButton("Decode");
        RButton chooseButton = new RButton("Choose");

        RLabel infoIntroLabel =
                new RLabel("The maximum number of characters" +
                        " in the current combined encryption: ");
        RLabel infoNumLabel =
                new RLabel("     " + rsa.getMaxComLen() +
                        " (Range: 1 - 4)", JLabel.LEFT);

        RLabel publicKeyIntroLabel = new RLabel("Public Key:     ");
        RLabel publicKeyNumLabel =
                new RLabel("n: " + rsa.getKeyn().toString() + "     ,     " +
                        "e: " + rsa.getKeye().toString());

        RLabel privateKeyIntroLabel = new RLabel("Private Key:      ");
        RLabel privateKeyNumLabel =
                new RLabel("n: " + rsa.getKeyn().toString() + "     ,     " +
                        "d : " + rsa.getKeyd().toString());

        RTextArea encodeInputArea = new RTextArea("Encode Input");
        RTextArea encodeOutputArea = new RTextArea("Encode Output");

        RTextArea decodeInputArea = new RTextArea("Decode Input");
        RTextArea decodeOutputArea = new RTextArea("Encode Output");

        encodeOutputArea.setEditable(false);
        decodeOutputArea.setEditable(false);
        RScrollPane encodeOutputScrollPane = new RScrollPane(encodeOutputArea);
        RScrollPane decodeInputScrollPane = new RScrollPane(decodeInputArea);

        newKeyButton.addActionListener(actionEvent -> {
            rsa = new RSA(1);
            publicKeyNumLabel.setText("n: " + rsa.getKeyn().toString() +
                    "     ,     " + "e: " + rsa.getKeye().toString());
            privateKeyNumLabel.setText("n: " + rsa.getKeyn().toString() +
                    "     ,     " + "d: " + rsa.getKeyd().toString());
        });

        encodeButton.addActionListener(actionEvent -> {
            String msg = encodeInputArea.getText();
            BigInteger[] cs = rsa.encode(msg);
            StringBuilder stringBuilder = new StringBuilder();
            for (BigInteger c : cs) {
                stringBuilder.append(c).append("\n");
            }
//            System.out.println(stringBuilder.toString());
            encodeOutputArea.setText(stringBuilder.toString());
        });

        decodeButton.addActionListener(actionEvent -> {
            String cMsg = decodeInputArea.getText();
            String[] cMsgs = cMsg.split("\n");
            BigInteger[] cs = new BigInteger[cMsgs.length];
            for (int i = 0; i < cs.length; i++) {
                cs[i] = new BigInteger(cMsgs[i]);
            }
            String m = rsa.decode(cs);
            decodeOutputArea.setText(m);
        });

        chooseButton.addActionListener(actionEvent -> {
            String s = JOptionPane.showInputDialog(null,
                    "Input one number(Range: 1 - 4): ");
            int num = Integer.parseInt(s);
            if (num <= 4 && num >= 1) {
                rsa.setMaxComLen(num);
                infoNumLabel.setText("     " +
                        rsa.getMaxComLen() + " (Range: 1 - 4)");
            } else {
                JOptionPane.showMessageDialog(null, "Wrong Number!!!");
            }
        });

        BorderLayout infoLayout = new BorderLayout();
        JPanel infoPanel = new JPanel(infoLayout);
        infoPanel.add(infoIntroLabel, BorderLayout.WEST);
        infoPanel.add(infoNumLabel, BorderLayout.CENTER);

        BorderLayout publicKeyLayout = new BorderLayout();
        JPanel publicKeyPanel = new JPanel(publicKeyLayout);
        publicKeyPanel.add(publicKeyIntroLabel, BorderLayout.WEST);
        publicKeyPanel.add(publicKeyNumLabel, BorderLayout.CENTER);

        BorderLayout privateKeyLayout = new BorderLayout();
        JPanel privateKeyPanel = new JPanel(privateKeyLayout);
        privateKeyPanel.add(privateKeyIntroLabel, BorderLayout.WEST);
        privateKeyPanel.add(privateKeyNumLabel, BorderLayout.CENTER);

        BorderLayout encodeLayout = new BorderLayout();
        JPanel encodePanel = new JPanel(encodeLayout);
        encodePanel.add(encodeInputArea, BorderLayout.WEST);
        encodePanel.add(encodeOutputScrollPane, BorderLayout.CENTER);

        BorderLayout decodeLayout = new BorderLayout();
        JPanel decodePanel = new JPanel();
        decodePanel.setLayout(decodeLayout);
        decodePanel.add(decodeInputScrollPane, BorderLayout.WEST);
        decodePanel.add(decodeOutputArea, BorderLayout.CENTER);

        GridLayout topLayout = new GridLayout(3, 1);
        JPanel topPanel = new JPanel(topLayout);
        topPanel.add(publicKeyPanel);
        topPanel.add(privateKeyPanel);
        topPanel.add(infoPanel);

        GridLayout centerLayout = new GridLayout(2, 1);
        JPanel centerPanel = new JPanel(centerLayout);
        centerPanel.add(encodePanel);
        centerPanel.add(decodePanel);

        GridLayout bottomLayout = new GridLayout(1, 3);
        JPanel bottomPanel = new JPanel(bottomLayout);
        bottomPanel.add(newKeyButton);
        bottomPanel.add(encodeButton);
        bottomPanel.add(decodeButton);
        bottomPanel.add(chooseButton);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Window window = new Window();
    }
}
