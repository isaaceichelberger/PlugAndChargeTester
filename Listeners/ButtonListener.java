package Listeners;

import GUI.PlugAndChargeGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        if (button.getName().equalsIgnoreCase("Start")){
            JTextField textField = PlugAndChargeGUI.getTextField();
            JTextArea responseArea = PlugAndChargeGUI.getResponseArea();
            String text = textField.getText();
            responseArea.append(text + "Starting Process");
            textField.selectAll();

            //Make sure the new text is visible, even if there
            //was a selection in the text area.
            responseArea.setCaretPosition(responseArea.getDocument().getLength());
        }
    }
}
