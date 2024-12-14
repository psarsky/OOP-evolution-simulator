package proj;

import javafx.application.Application;
import proj.simulation.SimulationApp;

public class WorldGUI {
    public static void main(String[] args) {
        System.out.println("START");
        Application.launch(SimulationApp.class, args);
        System.out.println("STOP");
    }
}
