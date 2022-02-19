package GUI;

import Listeners.ButtonListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.IOException;

// Compile and run
// javac -cp ".;C:\Users\isaac\Desktop\Projects\PlugAndChargeTester;" PlugAndChargeGUI.java
// java -cp ".;C:\Users\isaac\Desktop\Projects\PlugAndChargeTester;" GUI.PlugAndChargeGUI
// jar -cfve PlugAndCharge.jar GUI.PlugAndChargeGUI ./GUI ./Listeners (in the PlugAndCharge Folder)
// java -jar PlugAndCharge.jar

public class PlugAndChargeGUI {

    private static JTextField textField;
    private static JTextArea responseArea;
    private JPanel panel;
    private static JButton stationButton;
    private static JButton vehicleButton;
    private static ArrayList<JButton> buttons = new ArrayList<>();
    private PlugAndCharge instance = new PlugAndCharge();

    public PlugAndChargeGUI() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        panel = new JPanel();        
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        ButtonListener buttonListener = new ButtonListener();
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblPCT = new JLabel("Plug and Charge Tester V.0.1");
        lblPCT.setFont(new Font("Vernanda", Font.PLAIN, 16));
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 10, 0);
        gbc.weightx = 1;
        panel.add(lblPCT, gbc);

        JButton logoButton = new JButton();
        try {
            Image logo = ImageIO.read(getClass().getResource("../Assets/img/logo.png"));
            Image resizedLogo = logo.getScaledInstance(screenSize.width * 1/12, screenSize.height * 1/9, Image.SCALE_SMOOTH);
            logoButton.setIcon(new ImageIcon(resizedLogo));
        } catch (IOException e){
            e.printStackTrace();
        }

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 0.05;
        gbc.anchor = GridBagConstraints.NORTH;
        logoButton.setBackground(null);
        logoButton.setBackground(Color.WHITE);
        logoButton.setBorder(null);
        logoButton.setFocusPainted(false);
        panel.add(logoButton, gbc);

        stationButton = new JButton("Emulate Station");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        stationButton.addActionListener(buttonListener);
        stationButton.setBackground(Color.WHITE);
        //stationButton.setBorder(null);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 1;
        gbc.weighty = 0.8;      
        buttons.add(0, stationButton);
        panel.add(stationButton, gbc);

        JLabel optionsLabel = new JLabel("Options:");
        gbc = new GridBagConstraints();
        optionsLabel.setFont(new Font("Vernanda", Font.PLAIN, 16));
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.weightx = 1;
        gbc.weighty = 0.8;
        panel.add(optionsLabel, gbc);

        vehicleButton = new JButton("Emulate Vehicle");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        vehicleButton.addActionListener(buttonListener);
        vehicleButton.setBackground(Color.WHITE);
        //vehicleButton.setBorder(null);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 1;
        gbc.weighty = 0.8;      
        buttons.add(1, vehicleButton);
        panel.add(vehicleButton, gbc);

        JButton debugButton = new JButton("Debug");
        gbc = new GridBagConstraints();
        debugButton.addActionListener(buttonListener);
        debugButton.setBackground(Color.WHITE);
        //debugButton.setBorder(null);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 0.8;       
        gbc.insets = new Insets(0, 10, 0, 10);
        panel.add(debugButton, gbc);

        JButton startButton = new JButton("Start");
        gbc = new GridBagConstraints();
        startButton.addActionListener(buttonListener);
        gbc.fill = GridBagConstraints.BOTH;
        startButton.setBackground(Color.WHITE);
        //startButton.setBorder(null);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 1;
        gbc.weighty = 0.8;        
        panel.add(startButton, gbc);

        JButton testMultipleButton = new JButton("Test Twice");
        gbc = new GridBagConstraints();
        testMultipleButton.setBackground(Color.WHITE);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.weightx = 1;
        gbc.weighty = 0.8;       
        panel.add(testMultipleButton, gbc);

        textField = new JTextField(80);

        responseArea = new JTextArea(10, 80);
        responseArea.setEditable(false);
        responseArea.setLineWrap(true);
        responseArea.setForeground(Color.GREEN);
        Font font = new Font("Courier", Font.BOLD, 12);
        responseArea.setFont(font);
        responseArea.setBackground(Color.DARK_GRAY);
        JScrollPane scrollPane = new JScrollPane(responseArea);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.weightx = 1;
        gbc.weighty = 0.8;       
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

    public static ArrayList<JButton> getButtons() {
        return buttons;
    }

    public static JButton getVehicleButton() {
        return vehicleButton;
    }

    public static void setResponseArea(JTextArea area){
        responseArea = area;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("PlugAndCharge");
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                PlugAndChargeGUI pcGUI = new PlugAndChargeGUI();
                DebugGUI debugGUI = new DebugGUI();
                frame.setBounds(0, 0, screenSize.width, screenSize.height);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.getContentPane().setPreferredSize(screenSize);
                frame.getContentPane().add(pcGUI.getUI());
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                PlugAndChargeGUI.getButtons();
                PlugAndCharge.getInstance().setButtons(PlugAndChargeGUI.getButtons());
                PlugAndCharge.getInstance().setFrame(frame);
                PlugAndCharge.getInstance().setPCGUI(pcGUI);
                PlugAndCharge.getInstance().setDebugGUI(debugGUI);

                System.out.println("After Program initialization:");
                MemoryTest.testMemoryUsage();

            }
        });
    }

}