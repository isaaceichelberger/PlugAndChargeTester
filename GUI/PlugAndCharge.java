package GUI;

import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;

// Compile and run
// javac -cp "/home/pi/PlugAndChargeTester/" PlugAndCharge.java
// java -cp "/home/pi/PlugAndChargeTester/" GUI.PlugAndCharge
// java -Xms128m -Xmx512m -cp ".;C:\Users\isaac\Desktop\Projects\PlugAndChargeTester;" GUI.PlugAndChargeGUI
// jar -cfve PlugAndCharge.jar GUI.PlugAndChargeGUI ./GUI ./Listeners (in the PlugAndCharge Folder)
// java -jar PlugAndCharge.jar

public class PlugAndCharge {

    private boolean emulateStation;
    private boolean emulateVehicle;
    private boolean useIso;
    private int stationAmps;
    private static PlugAndCharge instance;
    private ArrayList<JButton> buttons;
    private JFrame frame;
    private PlugAndChargeGUI pcGUI;
    private DebugGUI debugGUI;
    private OptionsES optionsESGUI;
    private OptionsEV optionsEVGUI;

    public PlugAndCharge(){
        this.emulateStation = false;
        this.emulateVehicle = false;
        this.frame = frame;
        this.pcGUI = pcGUI;
        this.debugGUI = debugGUI;
        this.optionsESGUI = optionsESGUI;
        this.optionsEVGUI = optionsEVGUI;
        this.useIso = false;
        this.stationAmps = 0;
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

    public boolean isUseIso() {
        return useIso;
    }

    public void setUseIso(boolean useIso) {
        this.useIso = useIso;
    }

    public int getStationAmps(){
        return stationAmps;
    }

    public void setStationAmps(int stationAmps){
        this.stationAmps = stationAmps;
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


    public void setPCGUI(PlugAndChargeGUI pcGUI){
        this.pcGUI = pcGUI;
    }

    public DebugGUI getDebugGUI(){
        return debugGUI;
    }

    public void setDebugGUI(DebugGUI debugGUI){
        this.debugGUI = debugGUI;
    }

    public OptionsES getOptionsESGUI(){
        return optionsESGUI;
    }

    public void setOptionsESGUI(OptionsES optionsESGUI){
        this.optionsESGUI = optionsESGUI;
    }   

    public OptionsEV getOptionsEVGUI(){
        return optionsEVGUI;
    }

    public void setOptionsEVGUI(OptionsEV optionsEVGUI){
        this.optionsEVGUI = optionsEVGUI;
    }   



    public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            JFrame frame = new JFrame("PlugAndCharge");
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            PlugAndChargeGUI pcGUI = new PlugAndChargeGUI();
            DebugGUI debugGUI = new DebugGUI();
            OptionsES optionsESGUI = new OptionsES();
            OptionsEV optionsEVGUI = new OptionsEV();
            frame.setBounds(0, 0, screenSize.width, screenSize.height);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.getContentPane().setPreferredSize(screenSize);
            frame.getContentPane().add(pcGUI.getUI());
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            PlugAndChargeGUI.getButtons();
            PlugAndCharge.getInstance().setButtons(PlugAndChargeGUI.getButtons());
            PlugAndCharge.getInstance().setFrame(frame);
            // Add GUIs to Instance so they can be referenced again
            PlugAndCharge.getInstance().setPCGUI(pcGUI);
            PlugAndCharge.getInstance().setDebugGUI(debugGUI);
            PlugAndCharge.getInstance().setOptionsESGUI(optionsESGUI);
            PlugAndCharge.getInstance().setOptionsEVGUI(optionsEVGUI);
            //PlugAndCharge.getInstance().getOptionsESGUI().getResponseArea().append("Amps: 30"); // set default

            //System.out.println("After Program initialization:");
            //MemoryTest.testMemoryUsage();

        }
    });
}
}
