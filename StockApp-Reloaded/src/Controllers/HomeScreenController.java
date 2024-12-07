package Controllers;

import Models.Stock;
import Resource.Global;
import Utils.AvApiHelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class HomeScreenController {
    private static final int WIDTH = 707, HEIGHT = 543, NUM_BUTTONS = 10;
    private static final Color white = new Color(255, 255, 255);

    private JFrame frame;
    private JButton[] addButtons = new JButton[NUM_BUTTONS];
    private JButton[] removeButtons = new JButton[NUM_BUTTONS];
    private JButton[] editButtons = new JButton[NUM_BUTTONS];
    private JButton[] stockButtons = new JButton[NUM_BUTTONS];
    private GetSymbolPopUpFactory popUpFactory;

    private Logger logger = Global.logger;
    private AvApiHelper api = new AvApiHelper();

    ImageIcon addIcon = null;
    ImageIcon removeIcon = null;
    ImageIcon editIcon = null;

    private Stock[] stocks = new Stock[NUM_BUTTONS];

    public HomeScreenController() {
        initialize();
    }

    private void initialize() {
        logger.info(String.format("Running %s.initialize()", this.getClass().getName()));

        popUpFactory = new GetSymbolPopUpFactory();

        frame = new JFrame("StockViewer: Reloaded");
        frame.setResizable(false);
        frame.setBounds(100, 100, WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();

        try {
            // Open the ZIP file (assuming it's in the resource folder)
            InputStream zipStream = HomeScreenController.class.getResourceAsStream("/Resource/img/images.zip");

            if (zipStream == null) {
                Global.logger.warning("ZIP file not found!");
                return;
            }

            // Create a ZipInputStream
            ZipInputStream zipInputStream = new ZipInputStream(zipStream);
            ZipEntry entry;



            // Iterate through ZIP entries
            while ((entry = zipInputStream.getNextEntry()) != null) {
                switch (entry.getName()) {
                    case "addimage_2.png":
                        addIcon = new ImageIcon(ImageIO.read(zipInputStream));
                        break;
                    case "garbageicon4.jpg":
                        removeIcon = new ImageIcon(ImageIO.read(zipInputStream));
                        break;
                    case "editicon2.jpg":
                        editIcon = new ImageIcon(ImageIO.read(zipInputStream));
                        break;
                }
            }

            zipInputStream.close();  // Close the ZIP stream

            // If the images were loaded successfully, use them to set button icons
            if (addIcon != null && removeIcon != null && editIcon != null) {
                for (int i = 0; i < addButtons.length; i++) {
                    addButtons[i] = new JButton(addIcon);
                    removeButtons[i] = new JButton(removeIcon);
                    editButtons[i] = new JButton(editIcon);
                }
            } else {
                Global.logger.warning("One or more images could not be loaded.");
            }

        } catch (IOException e) {
            Global.logger.log(Level.SEVERE, "Error loading images from ZIP file", e);
        }

        // Now, add components to the frame or panels as needed, and make the frame visible
        frame.setLayout(new BorderLayout());
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.CENTER);

        for(int i = 0; i < NUM_BUTTONS; i++) {
            addButtons[i] = new JButton();
            removeButtons[i] = new JButton();
            editButtons[i] = new JButton();
            stockButtons[i] = new JButton();

            int finalI = i;
            addButtons[finalI].setBackground(white);
            addButtons[finalI].setIcon(addIcon);
            addButtons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    stocks[finalI] = new Stock(popUpFactory.getAddPopUp());
                    updateButtons();
                }
            });

            editButtons[finalI].setBackground(white);
            editButtons[finalI].setIcon(addIcon);
            editButtons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    stocks[finalI] = new Stock(popUpFactory.getEditPopUp());
                    updateButtons();
                }
            });

            removeButtons[finalI].setBackground(white);
            removeButtons[finalI].setIcon(addIcon);
            removeButtons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    removeStock(finalI);
                }
            });
        }



        frame.setVisible(true);
    }

    //TODO: add invoke later to each ui update call
    private void removeStock(int buttonNumber) {
        logger.info(String.format("Removing %s at button number %d", stocks[buttonNumber].getStockSymbol(), buttonNumber));
        stockButtons[buttonNumber].setVisible(false);
        addButtons[buttonNumber].setVisible(true);
        editButtons[buttonNumber].setVisible(false);
        removeButtons[buttonNumber].setVisible(false);
        stocks[buttonNumber] = null;

    }
    //TODO: add invoke later to each ui update call
    private void updateButtons() {
        String[] newData = api.getHomeScreenData();

       for(int i = 0; i < NUM_BUTTONS; i++) {
           logger.info("Updating button " + i + " with symbol " + stocks[i].getStockSymbol());
           addButtons[i].setVisible(false);
           editButtons[i].setVisible(true);
           removeButtons[i].setVisible(true);
           stockButtons[i].setVisible(true);



       }
    }

}
