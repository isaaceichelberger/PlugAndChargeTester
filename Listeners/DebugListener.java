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

public class DebugListener implements ActionListener {
    public void actionPerformed(ActionEvent e){
        JFrame frame = PlugAndCharge.getInstance().getFrame();
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
    }
}