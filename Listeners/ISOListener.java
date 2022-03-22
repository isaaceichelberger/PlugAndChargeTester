package Listeners;

import GUI.PlugAndCharge;
import GUI.PlugAndChargeGUI;
import GUI.DebugGUI;
import UnitTest.MemoryTest;
import GUI.OptionsES;
import GUI.OptionsEV;

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

public class ISOListener implements ActionListener {
    public void actionPerformed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        JTextArea responseArea;
        if (PlugAndCharge.getInstance().isEmulateStation()){
            responseArea = OptionsES.getResponseArea();
        } else {
            responseArea = OptionsEV.getResponseArea();
        }
        
        if (PlugAndCharge.getInstance().isUseIso()){
            button.setBackground(Color.WHITE); // Set Station Button to Black
            button.setForeground(Color.BLACK);

            PlugAndCharge.getInstance().setUseIso(false);
            responseArea.append("[LOG] ISO15118 Disabled!\n");
        } else {
            button.setBackground(Color.BLACK); // Set Station Button to Black
            button.setForeground(Color.WHITE);

            PlugAndCharge.getInstance().setUseIso(true);
            responseArea.append("[LOG] ISO15118 Enabled!\n");
        }

    }
}