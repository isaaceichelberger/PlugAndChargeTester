package GUI;

import Listeners.*;
import UnitTest.MemoryTest;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.IOException;

// Compile and run
// javac -cp ".;C:\Users\isaac\Desktop\Projects\PlugAndChargeTester;" PlugAndChargeGUI.java
// java -cp ".;C:\Users\isaac\Desktop\Projects\PlugAndChargeTester;" GUI.PlugAndChargeGUI
// java -Xms128m -Xmx512m -cp ".;C:\Users\isaac\Desktop\Projects\PlugAndChargeTester;" GUI.PlugAndChargeGUI
// jar -cfve PlugAndCharge.jar GUI.PlugAndChargeGUI ./GUI ./Listeners (in the PlugAndCharge Folder)
// java -jar PlugAndCharge.jar

public class PlugAndChargeGUI {

    private static JTextField textField;
    private static JTextArea responseArea;
    private static JScrollPane scrollPane;
    private JPanel panel;
    private static JButton stationButton;
    private static JButton vehicleButton;
    private static ArrayList<JButton> buttons = new ArrayList<>();
    private PlugAndCharge instance = new PlugAndCharge();

    public PlugAndChargeGUI() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();

        // TOP LABEL
        JLabel lblPCT = new JLabel("Plug and Charge Tester");
        lblPCT.setFont(new Font("Vernanda", Font.BOLD, 16));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 10, 10, 0);
        gbc.weightx = 0.25;
        panel.add(lblPCT, gbc);

        // RIVIAN LOGO
        JButton logoButton = new JButton();
        try {
            Image logo = ImageIO.read(getClass().getResource("/Assets/img/logo.png"));
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
        gbc.anchor = GridBagConstraints.EAST;
        logoButton.setBackground(null);
        logoButton.setBackground(Color.LIGHT_GRAY);
        logoButton.setBorder(null);
        logoButton.setFocusPainted(false);
        panel.add(logoButton, gbc);

        // EMULATE STATION BUTTON
        stationButton = new JButton("Emulate Station");
        stationButton.setFont(new Font("Vernanda", Font.PLAIN, 14));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        StationListener stationListener = new StationListener();
        stationButton.addActionListener(stationListener);
        stationButton.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 0.25;
        gbc.weighty = 0.8;
        buttons.add(0, stationButton);
        panel.add(stationButton, gbc);

        // EMULATE VEHICLE BUTTON
        vehicleButton = new JButton("Emulate Vehicle");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        VehicleListener vehicleListener = new VehicleListener();
        vehicleButton.addActionListener(vehicleListener);
        vehicleButton.setBackground(Color.WHITE);
        vehicleButton.setFont(new Font("Vernanda", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 0.25;
        gbc.weighty = 0.8;
        buttons.add(1, vehicleButton);
        panel.add(vehicleButton, gbc);

        // OPTIONS BUTTON
        JButton optionsButton = new JButton("Options");
        gbc = new GridBagConstraints();
        OptionsListener OptionsListener = new OptionsListener();
        optionsButton.addActionListener(OptionsListener);
        optionsButton.setBackground(Color.WHITE);
        optionsButton.setFont(new Font("Vernanda", Font.PLAIN, 14));
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.25;
        gbc.weighty = 0.8;
        gbc.insets = new Insets(0, 10, 0, 0);
        buttons.add(2, optionsButton);
        panel.add(optionsButton, gbc);

        // DEBUG BUTTON
        JButton debugButton = new JButton("Debug");
        gbc = new GridBagConstraints();
        DebugListener debugListener = new DebugListener();
        debugButton.addActionListener(debugListener);
        debugButton.setBackground(Color.WHITE);
        debugButton.setFont(new Font("Vernanda", Font.PLAIN, 14));
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.25;
        gbc.weighty = 0.8;
        gbc.insets = new Insets(0, 10, 0, 0);
        buttons.add(3, debugButton);
        panel.add(debugButton, gbc);

        // START BUTTON
        JButton startButton = new JButton("Start");
        gbc = new GridBagConstraints();
        StartListener startListener = new StartListener();
        startButton.addActionListener(startListener);
        startButton.setFont(new Font("Vernanda", Font.PLAIN, 14));
        gbc.fill = GridBagConstraints.BOTH;
        startButton.setBackground(Color.WHITE);
        //startButton.setBorder(null);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.weightx = 0.25;
        gbc.weighty = 0.8;
        panel.add(startButton, gbc);

        // JSCROLLPANE 
        textField = new JTextField(80);
        responseArea = new JTextArea(10, 80);
        responseArea.setEditable(false);
        responseArea.setLineWrap(true);
        responseArea.setForeground(Color.WHITE);
        Font font = new Font("Courier", Font.BOLD, 16);
        responseArea.setFont(font);
        responseArea.setBackground(Color.DARK_GRAY);
        scrollPane = new JScrollPane(responseArea);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 3;
        gbc.gridheight = 5;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.weightx = 1;
        gbc.weighty = 1;
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

    public static JScrollPane getScrollPane(){
        return scrollPane;
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


}
