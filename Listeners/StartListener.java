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
        // STATION
        if (PlugAndCharge.getInstance().isEmulateStation()){
            // J1772
            responseArea.append("Starting J1772 Emulation: Station Side\n");
            responseArea.append("[INFO] Max Amps Selected: " + PlugAndCharge.getInstance().getStationAmps() + "\n");
            responseArea.append("[INFO] Starting PWM Wave\n");

            // If the Station Uses ISO 15118
            if (PlugAndCharge.getInstance().isUseIso()){
                Thread thread = new Thread(() -> {
                double dc;
                double amps = (double) PlugAndCharge.getInstance().getStationAmps();
                if (amps < 51){
                    dc = amps / 0.6;
                } else {
                    dc = (amps / 2.5) + 64.0;
                }
                String command = "python ../PWM/generatePWMISO.py " + dc;
                try {
                    Process process = Runtime.getRuntime().exec(command);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));
                    String line;
                    System.out.println("Ran python process");
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        responseArea.append(line);
                    }
                    reader.close();
                    process.destroy();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
            thread.start();

            System.out.println("hey");

            Thread thread2 = new Thread(() -> {
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
        thread2.start();

        } else {
            Thread thread = new Thread(() -> {
                double dc;
                double amps = (double) PlugAndCharge.getInstance().getStationAmps();
                if (amps < 51){
                    dc = amps / 0.6;
                } else {
                    dc = (amps / 2.5) + 64.0;
                }
                String command = "python ../PWM/generatePWM.py " + dc;
                try {
                    Process process = Runtime.getRuntime().exec(command);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));
                    String line;
                    System.out.println("Ran python process");
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        responseArea.append(line);
                    }
                    reader.close();
                    process.destroy();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
            thread.start();
        }
            
        // VEHICLE
        } else if (PlugAndCharge.getInstance().isEmulateVehicle()) {
            // J1772
            responseArea.append("Starting J1772 Emulation: Vehicle Side\n");
            responseArea.append("[INFO] Reading Station Max Amps.\n");

            if (PlugAndCharge.getInstance().isUseIso()){
                Thread thread = new Thread(() -> {
                String command = "python ../PWM/read_PWM.py";
                try {
                    Process process = Runtime.getRuntime().exec(command);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));
                    String line;
                    System.out.println("Ran python process");
                    while ((line = reader.readLine()) != null) {
                        String amps = line;
                        responseArea.append("[INFO] Number of Amps Advertised: " + line);
                    }
                    reader.close();
                    process.destroy();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
            thread.start();

            Thread thread2 = new Thread(() -> {
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
            }});
            thread2.start();
            } else {
            // J1772

            if (PlugAndCharge.getInstance().isUseIso()){
                Thread thread = new Thread(() -> {
                String command = "python ../PWM/read_PWM.py";
                try {
                    Process process = Runtime.getRuntime().exec(command);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));
                    String line;
                    System.out.println("Ran python process");
                    while ((line = reader.readLine()) != null) {
                        String amps = line;
                        responseArea.append("[INFO] Number of Amps Advertised: " + line);
                    }
                    reader.close();
                    process.destroy();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
            thread.start();

            }
            
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
}