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

public class VehicleListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        JFrame frame = PlugAndCharge.getInstance().getFrame();
        JTextField textField = PlugAndChargeGUI.getTextField();
        JTextArea responseArea = PlugAndChargeGUI.getResponseArea();

        if (PlugAndCharge.getInstance().isEmulateStation()){
                    PlugAndChargeGUI.getButtons().get(0).setBackground(Color.WHITE); // set Emulate Station Button back to default
                    PlugAndChargeGUI.getButtons().get(0).setForeground(Color.BLACK); // set Emulate Station Button back to default
                    PlugAndCharge.getInstance().setEmulateStation(false);
                    PlugAndCharge.getInstance().setUseIso(false);
                }

                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
                PlugAndCharge.getInstance().setEmulateVehicle(true);
                frame.revalidate();
                frame.getContentPane().remove(0);
                frame.getContentPane().add(PlugAndCharge.getInstance().getOptionsEVGUI().getUI());
                frame.repaint();
                frame.pack();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}