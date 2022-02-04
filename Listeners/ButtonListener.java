package Listeners;

import GUI.PlugAndCharge;
import GUI.PlugAndChargeGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                break;
            case "Emulate Station":
                if (PlugAndCharge.getInstance().isEmulateVehicle()){
                    PlugAndChargeGUI.getButtons().get(1).setBackground(null); // set Emulate Vehicle Button back to default
                    PlugAndChargeGUI.getButtons().get(1).setForeground(null); // set Emulate Vehicle Button back to default
                    PlugAndCharge.getInstance().setEmulateStation(true);
                }
                // TODO Disable functionality if Start has been pressed
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
                // TODO Disable functionality if Start has been pressed
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
                PlugAndCharge.getInstance().setEmulateStation(true);
                break;
        }
    }
}
