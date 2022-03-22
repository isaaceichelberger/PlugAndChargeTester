package Listeners;

import GUI.PlugAndCharge;
import GUI.PlugAndChargeGUI;
import GUI.DebugGUI;
import UnitTest.MemoryTest;
import GUI.OptionsES;
import GUI.OptionsEV;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


public class SliderListener implements ChangeListener{

    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            JTextArea responseArea = OptionsES.getResponseArea();
            int amps = (int) source.getValue(); 
            responseArea.append(Integer.toString(amps));
            PlugAndCharge.getInstance().setStationAmps(amps);
            String text = responseArea.getText();
            text.replaceAll("Amps: \b([0-9]|[1-9][0-9])\b", "Amps: " + amps);
            responseArea.selectAll();
            responseArea.replaceSelection(""); // Reset Response Area
            responseArea.append(text);
        }
    }
}

