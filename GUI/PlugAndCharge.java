package GUI;

import javax.swing.*;
import java.util.ArrayList;

public class PlugAndCharge {

    private boolean emulateStation;
    private boolean emulateVehicle;
    private boolean multiTest;
    private static PlugAndCharge instance;
    private ArrayList<JButton> buttons;
    private JFrame frame;
    private PlugAndChargeGUI pcGUI;
    private DebugGUI debugGUI;

    public PlugAndCharge(){
        this.emulateStation = false;
        this.emulateVehicle = false;
        this.frame = frame;
        this.pcGUI = pcGUI;
        this.debugGUI = debugGUI;
        this.multiTest = false;
        if (instance == null){
            instance = this;
        }
    }

    public boolean isEmulateStation() {
        return emulateStation;
    }

    public void setEmulateStation(boolean emulateStation) {
        this.emulateStation = emulateStation;
    }

    public boolean isEmulateVehicle() {
        return emulateVehicle;
    }

    public void setEmulateVehicle(boolean emulateVehicle) {
        this.emulateVehicle = emulateVehicle;
    }

    public boolean isMultiTest() {
        return multiTest;
    }

    public void setMultiTest(boolean multiTest) {
        this.multiTest = multiTest;
    }

    public static PlugAndCharge getInstance(){
        return instance;
    }


    public void setButtons(ArrayList<JButton> buttons) {
        this.buttons = buttons;
    }

    public JFrame getFrame(){
        return frame;
    } 

    public void setFrame(JFrame frame){
        this.frame = frame;
    }

    public PlugAndChargeGUI getPCGUI(){
        return pcGUI;
    }

    public DebugGUI getDebugGUI(){
        return debugGUI;
    }

    public void setPCGUI(PlugAndChargeGUI pcGUI){
        this.pcGUI = pcGUI;
    }

    public void setDebugGUI(DebugGUI debugGUI){
        this.debugGUI = debugGUI;
    }
}
