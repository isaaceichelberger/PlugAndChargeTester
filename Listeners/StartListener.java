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

public class StartListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        String buttonName = button.getText();
        JFrame frame = PlugAndCharge.getInstance().getFrame();
        JTextField textField = PlugAndChargeGUI.getTextField();
        JTextArea responseArea = PlugAndChargeGUI.getResponseArea();
        JTextField debugField = DebugGUI.getTextField();
        JTextArea debugArea = DebugGUI.getResponseArea();

       button.setBackground(Color.BLACK);
            button.setForeground(Color.WHITE);
            //Make sure the new text is visible, even if there
            //was a selection in the text area.
            responseArea.selectAll();
            responseArea.replaceSelection(""); // Reset Response Area
            debugArea.selectAll();
            debugArea.replaceSelection("");
            responseArea.setCaretPosition(responseArea.getDocument().getLength());
            if (PlugAndCharge.getInstance().isEmulateStation()){
                String text = textField.getText();
                responseArea.append(text + "Starting Test\n");
                textField.selectAll();
                Thread thread = new Thread(() -> {
                    String command = "java -jar /home/pi/PlugAndChargeTester/RISE-V2G-SECC/target/rise-v2g-secc-1.2.6.jar";
                    try {
                        Process process = Runtime.getRuntime().exec(command);
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(process.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            if (line.contains("XML representation") ||
                                line.contains("Base64 encoded") ||
                                line.contains("<?xml version")){
                                    continue;
                            } else {
                                String line2 = line.substring(line.indexOf("] ")+1); // remove front junk
                                line2.trim();
                                debugArea.append(line2 + "\n");
                                //debugField.selectAll();
                                debugArea.setCaretPosition(responseArea.getDocument().getLength());
                            }
                            //System.out.println("While Emulating Station:");
                            //MemoryTest.testMemoryUsage();
                        }
                        reader.close();
                        process.destroy();
                        //System.out.println("After Emulating Station:");
                        //MemoryTest.testMemoryUsage();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                });
                thread.start();

            } else if (PlugAndCharge.getInstance().isEmulateVehicle()) {
                String text = textField.getText();
                responseArea.append(text + "Starting Test\n");
                textField.selectAll();
                Thread thread = new Thread(() -> {
                    String command = "java -jar /home/pi/PlugAndChargeTester/RISE-V2G-EVCC/target/rise-v2g-evcc-1.2.6.jar";
                    try {
                        Process process = Runtime.getRuntime().exec(command);
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(process.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.contains("XML representation") || 
                                line.contains("Base64 encoded") ||
                                line.contains("<?xml version")){
                                    continue;
                            } else {
                                String line2 = line.substring(line.indexOf("] ")+1); // remove front junk
                                line2.trim();
                                // TODO, might need JTextPane instead of TextArea
                                if (line2.contains("DummyEVController")){
                                    debugArea.setForeground(Color.GREEN); // Signal New Phase
                                } else if (line2.contains("V2GCommunicationSessionHandler")){
                                    debugArea.setForeground(Color.CYAN); // Signal Session Changes
                                }
                                debugArea.append(line2 + "\n");
                                // Currently changes the whole thing, not one line
                                debugArea.setForeground(Color.WHITE); // Incase color has changed
                                //debugField.selectAll();
                                debugArea.setCaretPosition(responseArea.getDocument().getLength());
                            }
                        }
                        reader.close();
                        process.destroy();
                        //System.out.println("After Emulating Vehicle:");
                        //MemoryTest.testMemoryUsage();

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
                thread.start();
            }
            PlugAndChargeGUI.getButtons().get(0).setBackground(Color.WHITE); // set Emulate Station Button back to default
            PlugAndChargeGUI.getButtons().get(0).setForeground(Color.BLACK); // set Emulate Station Button back to default
            PlugAndChargeGUI.getButtons().get(1).setBackground(Color.WHITE); // set Emulate Vehicle Button back to default
            PlugAndChargeGUI.getButtons().get(1).setForeground(Color.BLACK); // set Emulate Vehicle Button back to default
            ((JButton) e.getSource()).setBackground(Color.WHITE);
            ((JButton) e.getSource()).setForeground(Color.BLACK);
            PlugAndCharge.getInstance().setEmulateStation(false);
            PlugAndCharge.getInstance().setEmulateVehicle(false);
            //responseArea.append("Test complete.\n");
    
    }
}