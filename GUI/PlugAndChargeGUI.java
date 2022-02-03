package GUI;

import Listeners.ButtonListener;
import Listeners.TextListener;

import javax.swing.*;
import java.awt.*;


public class PlugAndChargeGUI {

    private static JTextField textField;
    private static JTextArea responseArea;
    private JPanel panel;

    public PlugAndChargeGUI() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblPCT = new JLabel("Plug and Charge Tester");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 1;
        panel.add(lblPCT, gbc);

        JLabel lblVersion = new JLabel("V.0.1");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 10);
        gbc.weightx = 3;
        panel.add(lblVersion, gbc);



        JButton stationButton = new JButton("Emulate Station");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 1;
        panel.add(stationButton, gbc);

        JLabel optionsLabel = new JLabel("Options:");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.weightx = 1;
        panel.add(optionsLabel, gbc);

        JButton chargerButton = new JButton("Emulate Charger");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 1;
        panel.add(chargerButton, gbc);

        JButton debugButton = new JButton("Show Logs");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 10, 0, 10);
        panel.add(debugButton, gbc);

        JButton startButton = new JButton("Start");
        gbc = new GridBagConstraints();
        //ButtonListener buttonListener = new ButtonListener();
        //startButton.addActionListener(buttonListener);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 1;
        panel.add(startButton, gbc);

        JButton testMultipleButton = new JButton("Test Twice");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.weightx = 1;
        panel.add(testMultipleButton, gbc);

        textField = new JTextField(20);
        //TextListener textListener = new TextListener();
        //textField.addActionListener(textListener);
        responseArea = new JTextArea(5, 20);
        responseArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(responseArea);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.weightx = 1;
        panel.add(responseArea, gbc);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("PlugAndCharge");
                frame.getContentPane().add(new PlugAndChargeGUI().getUI());
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}