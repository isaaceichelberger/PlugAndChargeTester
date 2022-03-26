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

public class LogListener implements ActionListener {
    public void actionPerformed(ActionEvent e){

        JButton button = (JButton) e.getSource();
        JFrame frame = PlugAndCharge.getInstance().getFrame();
        JTextField textField = PlugAndChargeGUI.getTextField();
        JTextField debugField = DebugGUI.getTextField();
        JTextArea debugArea = DebugGUI.getResponseArea();
        JTextArea responseArea = PlugAndChargeGUI.getResponseArea();

        // In case the directory is not made
        if (!debugArea.getText().equals("")) { // provided the area is not empty
            new File("../logs").mkdirs();
            DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            File logFile = new File("/logs/log" + timeStampPattern.format(java.time.LocalDateTime.now()) + ".log");
            try {
                logFile.createNewFile();
                FileWriter writer = new FileWriter("/logs/" + logFile.getName());
                writer.write(debugArea.getText());
                writer.close();
                debugArea.append("[INFO] Log file written to " + logFile.getName() + "\n");
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
}