package GUI;

import javax.swing.*;
import java.awt.*;

public class DebugGUI {

    private static JTextField textField;
    private static JTextArea responseArea;
    private JPanel panel;

    public DebugGUI() {

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblPCT = new JLabel("Plug and Charge Tester Debug");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 10, 0);
        gbc.weightx = 1;
        panel.add(lblPCT, gbc);

        JButton backButton = new JButton("Back");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //backButton.addActionListener(buttonListener);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.weightx = 1;
        panel.add(backButton, gbc);

        responseArea = new JTextArea(20, 80);
        responseArea.setEditable(false);
        responseArea.setLineWrap(true);
        responseArea.setForeground(Color.GREEN);
        Font font = new Font("Courier", Font.BOLD, 12);
        responseArea.setFont(font);
        responseArea.setBackground(Color.DARK_GRAY);
        JScrollPane scrollPane = new JScrollPane(responseArea);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.gridheight = 6;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.weightx = 4;
        gbc.weighty = 6;
        panel.add(scrollPane, gbc);

    }

    public JPanel getUI(){
        return panel;
    }
}
