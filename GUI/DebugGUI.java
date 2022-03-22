package GUI;

import javax.swing.*;
import java.awt.*;
import Listeners.*;

public class DebugGUI {

    private static JTextField textField;
    private static JTextArea responseArea;
    private JPanel panel;

    public DebugGUI() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblPCT = new JLabel("Plug and Charge Tester Debug");
        lblPCT.setFont(new Font("Vernanda", Font.BOLD, 16));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 10, 0);
        gbc.weightx = 1;
        panel.add(lblPCT, gbc);

        JButton backButton = new JButton("Back");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        ButtonListener buttonListener = new ButtonListener();
        backButton.addActionListener(buttonListener);
        backButton.setBackground(Color.WHITE);
        backButton.setFont(new Font("Vernanda", Font.PLAIN, 14));
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 10, 10);
        gbc.weightx = 1;
        gbc.weighty = 2;
        gbc.gridheight = 1;
        panel.add(backButton, gbc);

        JButton logsButton = new JButton("Export Log");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        LogListener logsListener = new LogListener();
        logsButton.addActionListener(logsListener);
        logsButton.setBackground(Color.WHITE);
        logsButton.setFont(new Font("Vernanda", Font.PLAIN, 14));
        gbc.gridwidth = 1;
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 10, 10);
        gbc.weightx = 0.25;
        gbc.weighty = 2;
        gbc.gridheight = 1;
        panel.add(logsButton, gbc);

        responseArea = new JTextArea(20, 80);
        responseArea.setEditable(false);
        responseArea.setLineWrap(true);
        responseArea.setForeground(Color.WHITE);
        Font font = new Font("Courier", Font.BOLD, 16);
        responseArea.setFont(font);
        responseArea.setBackground(Color.DARK_GRAY);
        JScrollPane scrollPane = new JScrollPane(responseArea);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 5;
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

    public static JTextField getTextField(){
        return textField;
    }

    public static JTextArea getResponseArea(){
        return responseArea;
    }


}
