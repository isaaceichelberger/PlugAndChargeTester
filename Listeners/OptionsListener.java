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

public class OptionsListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        JFrame frame = PlugAndCharge.getInstance().getFrame();
        JTextField textField = PlugAndChargeGUI.getTextField();
        JTextArea responseArea = PlugAndChargeGUI.getResponseArea();
        
        /*System.out.println("Station");
        System.out.println(PlugAndCharge.getInstance().isEmulateStation());
        System.out.println("Vehicle");
        System.out.println(PlugAndCharge.getInstance().isEmulateVehicle());*/

        // If Emulate Vehicle, go to Emulate Vehicle Options
        if (PlugAndCharge.getInstance().isEmulateVehicle()){
                // Change Menu
                frame.revalidate();
                frame.getContentPane().remove(0);
                frame.getContentPane().add(PlugAndCharge.getInstance().getOptionsEVGUI().getUI());
                frame.repaint();
                frame.pack();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else if (PlugAndCharge.getInstance().isEmulateStation()){
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
}