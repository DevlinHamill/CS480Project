package Controllers;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProgramRunner {
    public static void main (String[] args) {
        Logger logger = Logger.getLogger(HomeScreenController.class.getName());
        logger.setLevel(Level.INFO);
        SwingUtilities.invokeLater(() -> {
            new HomeScreenController();

        });
    }
}
