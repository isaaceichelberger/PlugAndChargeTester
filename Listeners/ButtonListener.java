package Listeners;

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
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);

                // TODO May need a list of all buttons to be able to check status of the current buttons,
                // TODO or just make flags? not sure
                break;
            case "Emulate Charger":
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
                break;
        }
    }
}
