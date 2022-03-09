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

public class ButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        String buttonName = button.getText();
        JFrame frame = PlugAndCharge.getInstance().getFrame();
        JTextField textField = PlugAndChargeGUI.getTextField();
        JTextArea responseArea = PlugAndChargeGUI.getResponseArea();
        JTextField debugField = DebugGUI.getTextField();
        JTextArea debugArea = DebugGUI.getResponseArea();
        switch (buttonName){
            case "Start":
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
                break;
            case "Emulate Station":
                if (PlugAndCharge.getInstance().isEmulateVehicle()){
                    PlugAndChargeGUI.getButtons().get(1).setBackground(Color.WHITE); // set Emulate Vehicle Button back to default
                    PlugAndChargeGUI.getButtons().get(1).setForeground(Color.BLACK); // set Emulate Vehicle Button back to default
                    PlugAndCharge.getInstance().setEmulateStation(true);
                }
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
                PlugAndCharge.getInstance().setEmulateStation(true);

                break;
            case "Emulate Vehicle":
                if (PlugAndCharge.getInstance().isEmulateStation()){
                    PlugAndChargeGUI.getButtons().get(0).setBackground(Color.WHITE); // set Emulate Vehicle Button back to default
                    PlugAndChargeGUI.getButtons().get(0).setForeground(Color.BLACK); // set Emulate Vehicle Button back to default
                    PlugAndCharge.getInstance().setEmulateVehicle(true);
                }
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
                PlugAndCharge.getInstance().setEmulateVehicle(true);
                break;
            case "Debug":
                frame.revalidate();
                frame.getContentPane().remove(0);
                frame.getContentPane().add(PlugAndCharge.getInstance().getDebugGUI().getUI());
                frame.repaint();
                frame.pack();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //System.out.println("While Debug Menu is Displayed:");
                //MemoryTest.testMemoryUsage();
                break;
            case "Back":
                frame.revalidate();
                frame.getContentPane().remove(0);
                frame.getContentPane().add(PlugAndCharge.getInstance().getPCGUI().getUI());
                frame.repaint();
                frame.pack();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //System.out.println("After UI is Redisplayed:");
                //MemoryTest.testMemoryUsage();
                break;
            case "Export Log":
                // In case the directory is not made
                if (!debugArea.getText().equals("")) { // provided the area is not empty
                    new File("../logs").mkdirs();
                    DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
                    File logFile = new File("../logs/log" + timeStampPattern.format(java.time.LocalDateTime.now()) + ".log");
                    try {
                        logFile.createNewFile();
                        FileWriter writer = new FileWriter("../logs/" + logFile.getName());
                        writer.write(debugArea.getText());
                        writer.close();
                        debugArea.append("INFO: Log file written to " + logFile.getName() + "\n");
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
        }
    }

}
