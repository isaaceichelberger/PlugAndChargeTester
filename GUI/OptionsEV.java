package GUI;

import Listeners.*;
import UnitTest.MemoryTest;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.IOException;

public class OptionsEV {
        
    private static JTextField textField;
    private static JTextArea responseArea;
    private JPanel panel;
    private PlugAndCharge instance = new PlugAndCharge();

    public OptionsEV() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();

        // TOP LABEL
        JLabel lblPCT = new JLabel("Emulate Vehicle Options");
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

        // BACK  BUTTON
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Vernanda", Font.PLAIN, 14));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        BackListener BackListener = new BackListener();
        backButton.addActionListener(BackListener);
        backButton.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 10, 30, 0);
        gbc.weightx = 0.25;
        gbc.weighty = 0.8;
        panel.add(backButton, gbc);

        // USE ISO BUTTON
        JButton isoButton = new JButton("Use ISO15118");
        ISOListener ISOListener = new ISOListener();
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        isoButton.addActionListener(ISOListener);
        isoButton.setBackground(Color.WHITE);
        isoButton.setFont(new Font("Vernanda", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 10, 30, 0);
        gbc.weightx = 0.25;
        gbc.weighty = 0.8;
        panel.add(isoButton, gbc);

        // JSCROLLPANE FOR OUTPUT
        textField = new JTextField(80);
        responseArea = new JTextArea(10, 80);
        responseArea.setEditable(false);
        responseArea.setLineWrap(true);
        responseArea.setForeground(Color.WHITE);
        Font font = new Font("Courier", Font.BOLD, 14);
        responseArea.setFont(font);
        responseArea.setBackground(Color.DARK_GRAY);
        JScrollPane scrollPane = new JScrollPane(responseArea);
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

    public static void setResponseArea(JTextArea area){
        responseArea = area;
    }
}