package test;

import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.awt.Font;

/**
 * @author StockFullyOptomistic
 * CS 480
 */

public class Popup extends JFrame{
	
	/**
	 * creates a textfield to be used as an input
	 */
	private static JTextField textField;
	/**
	 * creates a local prompt string for the popup
	 */
	public static String promptstr;
	/**
	 * creates a local prompt label that will change the title of the popup
	 */
	public static JLabel promptLabel;
	/**
	 * creates a local object for the homescreen
	 */
	public static HomeScreen home;
	/**
	 * saves the current button number
	 */
	public static int buttonnum;
	/**
	 * checks if it is adding
	 */
	public static boolean isadding;
	/**
	 *  keeps track of the current instance of the class
	 */
	public static Popup window;
	/**
	 * Label that will be used to display an error message
	 */
	public static JLabel errorLabel;
	/**
	 * the frame of the GUI
	 */
	private JFrame frame;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new Popup();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Popup() {
		initialize();
	}
	
	/**
	 * updates the input variabiles to the local variabls so that it can be accessed
	 * @param home the current home instance
	 * @param buttonnum the button number
	 * @param isadding boolean that checks if the button is currently adding
	 * @param promptstr the prompt that will be display on the top of the poppup
	 */
	public Popup(HomeScreen home, int buttonnum, boolean isadding, String promptstr) {
		this.home = home;
		this.buttonnum = buttonnum;
		this.isadding = isadding;
		this.promptstr = promptstr;
		initialize();
	}


	/**
	 * checks the input if the stock is valid
	 * @param input the input being evaluated
	 * @return the result of the checks
	 */
	public boolean CheckInput(String input) {
		/**
		 * used to check if the stock symbol is invalid or not
		 */
		boolean condition = true;
		
		if(input.isEmpty()) {
			condition = false;
			/**
			 * creates the error message string
			 */
			String errorStr = "No provided\nStock Symbol!";
			/**
			 * sets the format of the error message to be all red
			 */
			String errorDisplay = String.format("<html><span style='color:red;'>%s</span>", errorStr);
			errorLabel.setText(errorDisplay);
			errorLabel.setVisible(true);
			return condition;		
		}else {
		
			for(int i = 0; i < input.length(); i++) {
				/**
				 * retrieves the current character
				 */
				char currentCharacter = input.charAt(i);
	
				if(( Character.isDigit(currentCharacter) ) || ( !Character.isLetterOrDigit(currentCharacter) )) {
					condition = false; 
					/**
					 * creates the error message string
					 */
					String errorStr = "Stock symbol\ndoesnt exist!";
					/**
					 * sets the format of the error message to be all red
					 */
					String errorDisplay = String.format("<html><span style='color:red;'>%s</span>", errorStr);
					errorLabel.setText(errorDisplay);
					errorLabel.setVisible(true);
					return condition;
				}
	
			}
	
			
			if(!AlphaVantageApiHelper.stockExists(input)) {
				condition = false; 
				/**
				 * creates the error message string
				 */
				String errorStr = "Stock symbol\ndoesnt exist!";
				/**
				 * sets the format of the error message to be all red
				 */
				String errorDisplay = String.format("<html><span style='color:red;'>%s</span>", errorStr);
				errorLabel.setText(errorDisplay);
				errorLabel.setVisible(true);
				return condition;
			}
			
			for(int i = 0; i < 10; i++) {
				
				if(input.equals(home.stocklist[i].Stocksymbol)) {
					
					condition = false;
					/**
					 * creates the error message string
					 */
					String errorStr = "Pre-Existing\nStock Symbol!";
					/**
					 * sets the format of the error message to be all red
					 */
					String errorDisplay = String.format("<html><span style='color:red;'>%s</span>", errorStr);
					errorLabel.setText(errorDisplay);
					errorLabel.setVisible(true);
					return condition;
					
				}
				
				
			}
		}
		
		return condition;
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 564, 191);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/**
		 * creates the current panel that will contain all the buttons
		 */
		JPanel panel = new JPanel();

		/**
		 * creates the cancel button that will dispose of the current frame
		 */
		JButton cancel_button = new JButton("Cancel");
		cancel_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		
		promptLabel = new JLabel(promptstr);
		errorLabel = new JLabel("");
		errorLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		
		textField = new JTextField();
		textField.setColumns(10);
		/**
		 * creates the confirm button that will help check the input and add the input after its sucessfuly validated
		 */
		JButton confirm_button = new JButton("Confirm");
		confirm_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(home != null) {
					/**
					 * retrieves the input
					 */
					String input = textField.getText().toUpperCase();
					
					if(CheckInput(input)) {
						/**
						 * gets the home screen data
						 */
						HashMap<String, String> currentdata = AlphaVantageApiHelper.getHomeScreenData(input);
						/**
						 * gets the stock view data
						 */
						HashMap<String, String> StockViewdata = AlphaVantageApiHelper.getStockViewData(input);
						if(buttonnum == 1) {
							
							home.storeStockData(currentdata, StockViewdata, 1);
							home.UpdateButtonText(home.stockviewbutton1, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));

							
							if(isadding == true) {
								home.addbutton1.setVisible(false);
								home.editbutton1.setVisible(true);
								home.removebutton1.setVisible(true);
								home.stockviewbutton1.setVisible(true);
							}

						}else if(buttonnum == 2) {

							home.UpdateButtonText(home.stockviewbutton2, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
							home.storeStockData(currentdata, StockViewdata, 2);
							if(isadding == true) {
								home.addbutton2.setVisible(false);
								home.editbutton2.setVisible(true);
								home.removebutton2.setVisible(true);
								home.stockviewbutton2.setVisible(true);
							}
						}else if(buttonnum == 3) {
							home.UpdateButtonText(home.stockviewbutton3, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
							home.storeStockData(currentdata, StockViewdata, 3);
							if(isadding == true) {
								home.addbutton3.setVisible(false);
								home.editbutton3.setVisible(true);
								home.removebutton3.setVisible(true);
								home.stockviewbutton3.setVisible(true);
							}
						}else if(buttonnum == 4) {
							home.UpdateButtonText(home.stockviewbutton4, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
							home.storeStockData(currentdata, StockViewdata, 4);
							if(isadding == true) {
								home.addbutton4.setVisible(false);
								home.editbutton4.setVisible(true);
								home.removebutton4.setVisible(true);
								home.stockviewbutton4.setVisible(true);
							}
						}else if(buttonnum == 5) {
							home.UpdateButtonText(home.stockviewbutton5, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
							home.storeStockData(currentdata, StockViewdata, 5);
							if(isadding == true) {
								home.addbutton5.setVisible(false);
								home.editbutton5.setVisible(true);
								home.removebutton5.setVisible(true);
								home.stockviewbutton5.setVisible(true);
							}
						}else if(buttonnum == 6) {

							home.UpdateButtonText(home.stockviewbutton6, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
							home.storeStockData(currentdata, StockViewdata, 6);
							if(isadding == true) {
								home.addbutton6.setVisible(false);
								home.editbutton6.setVisible(true);
								home.removebutton6.setVisible(true);
								home.stockviewbutton6.setVisible(true);
							}
						}else if(buttonnum == 7) {

							home.UpdateButtonText(home.stockviewbutton7, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
							home.storeStockData(currentdata, StockViewdata, 7);
							if(isadding == true) {
								home.addbutton7.setVisible(false);
								home.editbutton7.setVisible(true);
								home.removebutton7.setVisible(true);
								home.stockviewbutton7.setVisible(true);
							}
						}else if(buttonnum == 8) {
							home.UpdateButtonText(home.stockviewbutton8, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
							home.storeStockData(currentdata, StockViewdata, 8);
							if(isadding == true) {
								home.addbutton8.setVisible(false);
								home.editbutton8.setVisible(true);
								home.removebutton8.setVisible(true);
								home.stockviewbutton8.setVisible(true);
							}
						}else if(buttonnum == 9) {
							home.storeStockData(currentdata, StockViewdata, 9);
							home.UpdateButtonText(home.stockviewbutton9, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));

							if(isadding == true) {
								home.addbutton9.setVisible(false);
								home.editbutton9.setVisible(true);
								home.removebutton9.setVisible(true);
								home.stockviewbutton9.setVisible(true);
							}
						}else if(buttonnum == 10) {
							home.UpdateButtonText(home.stockviewbutton10, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
							home.storeStockData(currentdata, StockViewdata, 10);
							if(isadding == true) {
								home.addbutton10.setVisible(false);
								home.editbutton10.setVisible(true);
								home.removebutton10.setVisible(true);
								home.stockviewbutton10.setVisible(true);
							}
						}

						frame.dispose();
					}
				}
			}
		});

		/**
		 * saves the group layout info thats auto generated from window builder
		 */
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(0)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(103))
		);

		/**
		 * creates a group layout panel.
		 */
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(promptLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 520, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(cancel_button, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(errorLabel, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
							.addComponent(confirm_button, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
						.addComponent(textField, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 520, GroupLayout.PREFERRED_SIZE))
					.addGap(20))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(promptLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(cancel_button, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(errorLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(confirm_button, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
					.addGap(16))
		);
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);
	}
}
