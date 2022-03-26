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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


// TODO:
// Print Certificate Exchange
// End Station Process

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
                //String command = "python /home/pi/PlugAndChargeTester/PWM/generatePWMISO.py " + dc;
                try {
                    ProcessBuilder builder = new ProcessBuilder("python", "generatePWMISO.py", String.valueOf(dc));
                    builder.environment().put("PYTHONIOENCODING", "UTF-8");
                    builder.environment().put("PYTHONUNBUFFERED","TRUE");
                    builder.directory(new File("/home/pi/PlugAndChargeTester/PWM/"));
                    //Process process = Runtime.getRuntime().exec(command);
                    Process process = builder.start();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));
                    String line;
                    boolean flag = false;
                    boolean printed = false;
                    while ((line = reader.readLine()) != null) {
                        flag = true;
                        System.out.println(line);
                        responseArea.append(line + "\n");
                        if (flag && !printed){
                            responseArea.append("[INFO] J1772 Communication Complete\n");
                            printed = true;
                        }
                    }
                    reader.close();
                    process.waitFor();
                    process.destroy();
                    if (flag)
                        responseArea.append("[INFO] J1772 Communication Complete\n");
                    else
                        responseArea.append("[ERROR] Communication Unsuccessful\n");
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e2){
                    e2.printStackTrace();
                }
            });
            thread.start();
        
            Thread thread2 = new Thread(() -> {
                    try {
                        // wait to execute thread 2
                        Thread.sleep(5000);
                    } catch (InterruptedException e2){
                        e2.printStackTrace();
                    }
                String command = "java -jar /home/pi/PlugAndChargeTester/RISE-V2G-SECC/target/rise-v2g-secc-1.2.6.jar";
                try {
                    Process process = Runtime.getRuntime().exec(command);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        if (line.contains("XML representation") ||
                            line.contains("Base64 encoded")){
                                continue;
                        } else if (line.contains("<?xml version=")){
                            //Document xmlDoc = loadXMLFromString(line);
                            // TODO
                        } else {
                            if (line.contains(" INFO ")){
                                String line2 = line.substring(line.indexOf(": ")+1); // remove front junk
                                line2.trim();
                                responseArea.append("[INFO] " + line2 + "\n");
                            } else if (line.contains("Certificate used to verify signature")) {
                                String line2 = line.substring(line.indexOf(": ")+1); // remove front junk
                                line2.trim();
                                responseArea.append("[CERTIFICATE] " + line2 + "\n");
                            } else {
                                String line2 = line.substring(line.indexOf("] ")+1); // remove front junk
                                line2.trim();
                                debugArea.append(line2 + "\n");
                            }

                            //debugField.selectAll();
                            //debugArea.setCaretPosition(responseArea.getDocument().getLength());
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
                }                try {
                    ProcessBuilder builder = new ProcessBuilder("python", "generatePWM.py", String.valueOf(dc));
                    builder.environment().put("PYTHONIOENCODING", "UTF-8");
                    builder.environment().put("PYTHONUNBUFFERED","TRUE");
                    builder.directory(new File("/home/pi/PlugAndChargeTester/PWM/"));
                    //Process process = Runtime.getRuntime().exec(command);
                    Process process = builder.start();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));
                    boolean flag = false;
                    boolean printed = false;
                    String line;
                    while ((line = reader.readLine()) != null) {
                        flag = true;
                        System.out.println(line);
                        responseArea.append(line + "\n");
                        if (flag && !printed){
                            responseArea.append("[INFO] J1772 Communication Complete\n");
                            printed = true;
                        }
                    }
        
                    reader.close();
                    process.waitFor();
                    process.destroy();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e2){
                    e2.printStackTrace();
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
                try {
                    ProcessBuilder builder = new ProcessBuilder("python", "read_PWM.py");
                    builder.environment().put("PYTHONIOENCODING", "UTF-8");
                    builder.environment().put("PYTHONUNBUFFERED","TRUE");
                    builder.directory(new File("/home/pi/PlugAndChargeTester/PWM/"));
                    //Process process = Runtime.getRuntime().exec(command);
                    Process process = builder.start();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));
                    String line;
                    boolean flag = false;
                    boolean printed = false;
                    while ((line = reader.readLine()) != null) {
                        String amps = line;
                        responseArea.append("[INFO] Number of Amps Advertised: " + line + "\n");
                        flag = true;
                        if (flag && !printed){
                            responseArea.append("[INFO] J1772 Communication Complete\n");
                            printed = true;
                        }
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
                        if (line.contains(" INFO ")){
                            String line2 = line.substring(line.indexOf(": ")+1); // remove front junk
                            line2.trim();
                            responseArea.append("[INFO] " + line2 + "\n");
                        } else if (line.contains("Certificate to verify signature")) {
                            String line2 = line.substring(line.indexOf(": ")+1); // remove front junk
                            line2.trim();
                            responseArea.append("[CERTIFICATE] " + line2 + "\n");
                        } else {
                        String line2 = line.substring(line.indexOf("] ")+1); // remove front junk
                        line2.trim();
                        debugArea.append(line2 + "\n");
                        }
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

            Thread thread = new Thread(() -> {
            String command = "python /home/pi/PlugAndChargeTester/PWM/read_PWM.py";
            try {
                 ProcessBuilder builder = new ProcessBuilder("python", "read_PWM.py");
                builder.environment().put("PYTHONIOENCODING", "UTF-8");
                builder.environment().put("PYTHONUNBUFFERED","TRUE");
                builder.directory(new File("/home/pi/PlugAndChargeTester/PWM/"));
                //Process process = Runtime.getRuntime().exec(command);
                Process process = builder.start();
                String line;
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                boolean flag = false;
                boolean printed = false;
                while ((line = reader.readLine()) != null) {
                    String amps = line;
                    responseArea.append("[INFO] Number of Amps Advertised: " + line + "\n");
                    flag = true;
                    if (flag && !printed){
                        responseArea.append("[INFO] J1772 Communication Complete\n");
                        printed = true;
                    }
                }
                reader.close();
                process.destroy();
                responseArea.append("[INFO] Vehicle Charging\n");
                responseArea.append("[INFO] Test Completed\n");
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

/*public static Document loadXMLFromString(String xml) throws Exception
{
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    InputSource is = new InputSource(new StringReader(xml));
    return builder.parse(is);
}*/
}

