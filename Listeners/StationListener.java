package Listeners;

import GUI.PlugAndCharge;
import GUI.PlugAndChargeGUI;
import GUI.DebugGUI;
import UnitTest.MemoryTest;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.format.DateTimeFormatter;

public class StationListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        JFrame frame = PlugAndCharge.getInstance().getFrame();
        JTextField textField = PlugAndChargeGUI.getTextField();
        JTextArea responseArea = PlugAndChargeGUI.getResponseArea();

        if (PlugAndCharge.getInstance().isEmulateVehicle()){
                    PlugAndChargeGUI.getButtons().get(1).setBackground(Color.WHITE); // set Emulate Vehicle Button back to default
                    PlugAndChargeGUI.getButtons().get(1).setForeground(Color.BLACK); // set Emulate Vehicle Button back to default
                    PlugAndCharge.getInstance().setEmulateVehicle(false);
                }
                button.setBackground(Color.BLACK); // Set Station Button to Black
                button.setForeground(Color.WHITE);
                PlugAndCharge.getInstance().setEmulateStation(true);
                
                // Change Menu
                frame.revalidate();
                frame.getContentPane().remove(0);
                frame.getContentPane().add(PlugAndCharge.getInstance().getOptionsESGUI().getUI());
                frame.repaint();
                frame.pack();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}