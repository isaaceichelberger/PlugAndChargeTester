package Listeners;

import GUI.PlugAndCharge;
import GUI.PlugAndChargeGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        String buttonName = button.getText();
        switch (buttonName){
            case "Start":
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
                JTextField textField = PlugAndChargeGUI.getTextField();
                JTextArea responseArea = PlugAndChargeGUI.getResponseArea();
                String text = textField.getText();
                responseArea.append(text + "Starting Test\n");
                textField.selectAll();
                //Make sure the new text is visible, even if there
                //was a selection in the text area.
                responseArea.setCaretPosition(responseArea.getDocument().getLength());
                if (PlugAndCharge.getInstance().isEmulateStation()){
                    Thread thread = new Thread(() -> {
                        String command = "java -cp ../RISE-V2G-SECC/target/rise-v2g-secc-1.2.6.jar com.v2gclarity.risev2g.secc.main.StartSECC";
                        try {
                            Process process = Runtime.getRuntime().exec(command);
                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(process.getInputStream()));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                                responseArea.append(textField.getText() + line + "\n");
                                textField.selectAll();
                                responseArea.setCaretPosition(responseArea.getDocument().getLength());
                            }
                            reader.close();
                            process.destroy();

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    });
                    thread.start();

                } else if (PlugAndCharge.getInstance().isEmulateVehicle()) {
                    Thread thread = new Thread(() -> {
                        String command = "java -cp ../RISE-V2G-EVCC/target/rise-v2g-evcc-1.2.6.jar com.v2gclarity.risev2g.evcc.main.StartEVCC";
                        try {
                            Process process = Runtime.getRuntime().exec(command);
                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(process.getInputStream()));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                                responseArea.append(textField.getText() + line + "\n");
                                textField.selectAll();
                                responseArea.setCaretPosition(responseArea.getDocument().getLength());
                            }
                            reader.close();
                            process.destroy();

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    });
                    thread.start();
                }
                PlugAndChargeGUI.getButtons().get(0).setBackground(null); // set Emulate Station Button back to default
                PlugAndChargeGUI.getButtons().get(0).setForeground(null); // set Emulate Station Button back to default
                PlugAndChargeGUI.getButtons().get(1).setBackground(null); // set Emulate Vehicle Button back to default
                PlugAndChargeGUI.getButtons().get(1).setForeground(null); // set Emulate Vehicle Button back to default
                ((JButton) e.getSource()).setBackground(null);
                ((JButton) e.getSource()).setForeground(null);

                break;
            case "Emulate Station":
                if (PlugAndCharge.getInstance().isEmulateVehicle()){
                    PlugAndChargeGUI.getButtons().get(1).setBackground(null); // set Emulate Vehicle Button back to default
                    PlugAndChargeGUI.getButtons().get(1).setForeground(null); // set Emulate Vehicle Button back to default
                    PlugAndCharge.getInstance().setEmulateStation(true);
                }
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
                PlugAndCharge.getInstance().setEmulateStation(true);

                break;
            case "Emulate Vehicle":
                if (PlugAndCharge.getInstance().isEmulateStation()){
                    PlugAndChargeGUI.getButtons().get(0).setBackground(null); // set Emulate Vehicle Button back to default
                    PlugAndChargeGUI.getButtons().get(0).setForeground(null); // set Emulate Vehicle Button back to default
                    PlugAndCharge.getInstance().setEmulateVehicle(true);
                }
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
                PlugAndCharge.getInstance().setEmulateVehicle(true);
                break;
        }
    }
}
