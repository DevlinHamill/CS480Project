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

public class popup extends JFrame{
	
	
	private static JTextField textField;
	public static String promptstr;
	public static JLabel promptLabel;
	public static HomeScreen home;
	public static int buttonnum;
	public static boolean isadding;
	public static popup window;
	public static JLabel errorLabel;
	private JFrame frame;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new popup();
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
	public popup() {
		initialize();
	}
	
	public popup(HomeScreen home, int buttonnum, boolean isadding, String promptstr) {
		this.home = home;
		this.buttonnum = buttonnum;
		this.isadding = isadding;
		this.promptstr = promptstr;
		initialize();
	}


	
	public boolean CheckInput(String input) {
		boolean condition = true;
		
		for(int i = 0; i < input.length(); i++) {
			char currentCharacter = input.charAt(i);
			
			if(( Character.isDigit(currentCharacter) ) || ( !Character.isLetterOrDigit(currentCharacter) )) {
				condition = false; 
				String errorStr = "Stock symbol\ndoesnt exist!";
				String errorDisplay = String.format("<html><span style='color:red;'>%s</span>", errorStr);
				errorLabel.setText(errorDisplay);
				errorLabel.setVisible(true);
				return condition;
			}
			
		}
		
		if(!AlphaVantageApiHelper.stockExists(input)) {
			condition = false; 
			String errorStr = "Stock symbol\ndoesnt exist!";
			String errorDisplay = String.format("<html><span style='color:red;'>%s</span>", errorStr);
			errorLabel.setText(errorDisplay);
			errorLabel.setVisible(true);
			return condition;
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
		
		JPanel panel = new JPanel();

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
		
		JButton confirm_button = new JButton("Confirm");
		confirm_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(home != null) {
					String input = textField.getText().toUpperCase();
					
					if(CheckInput(input)) {
									
						HashMap<String, String> currentdata = AlphaVantageApiHelper.getHomeScreenData(input);
						HashMap<String, String> StockViewdata = AlphaVantageApiHelper.getStockViewData(input);
						if(buttonnum == 1) {
							
							String Delta = currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE);
							String DeltaPrec = currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE);
							
							home.storeStockData(currentdata, StockViewdata, 1);
							home.UpdateButton(home.stockviewbutton1, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
							
							
							/*
							 * testing:
							 *
							 */
							System.out.println(input);
							System.out.println(AlphaVantageApiHelper.stockExists(input));
							
							System.out.println(Constants.STOCK_SYMBOL+": " + currentdata.get(Constants.STOCK_SYMBOL)+ " "+
												Constants.PREVIOUS_CLOSE+": " +currentdata.get(Constants.PREVIOUS_CLOSE)+" "+
												Constants.CURRENT_VALUE.toString()+": " +currentdata.get(Constants.CURRENT_VALUE)+ " "+
												Constants.CHANGE_SINCE_PREVIOUS_CLOSE+": " +currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE)+" "+
												Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE+": " +currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
							
							if(isadding == true) {
								home.addbutton1.setVisible(false);
								home.editbutton1.setVisible(true);
								home.removebutton1.setVisible(true);
								home.stockviewbutton1.setVisible(true);
							}
							
						}else if(buttonnum == 2) {
							
							home.UpdateButton(home.stockviewbutton2, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));	
							home.storeStockData(currentdata, StockViewdata, 2);
							if(isadding == true) {
								home.addbutton2.setVisible(false);
								home.editbutton2.setVisible(true);
								home.removebutton2.setVisible(true);
								home.stockviewbutton2.setVisible(true);
							}
						}else if(buttonnum == 3) {
							home.UpdateButton(home.stockviewbutton3, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
							home.storeStockData(currentdata, StockViewdata, 3);
							if(isadding == true) {
								home.addbutton3.setVisible(false);
								home.editbutton3.setVisible(true);
								home.removebutton3.setVisible(true);
								home.stockviewbutton3.setVisible(true);
							}
						}else if(buttonnum == 4) {
							home.UpdateButton(home.stockviewbutton4, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
							home.storeStockData(currentdata, StockViewdata, 4);
							if(isadding == true) {
								home.addbutton4.setVisible(false);
								home.editbutton4.setVisible(true);
								home.removebutton4.setVisible(true);
								home.stockviewbutton4.setVisible(true);
							}
						}else if(buttonnum == 5) {
							home.UpdateButton(home.stockviewbutton5, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
							home.storeStockData(currentdata, StockViewdata, 5);
							if(isadding == true) {
								home.addbutton5.setVisible(false);
								home.editbutton5.setVisible(true);
								home.removebutton5.setVisible(true);
								home.stockviewbutton5.setVisible(true);
							}
						}else if(buttonnum == 6) {
							
							home.UpdateButton(home.stockviewbutton6, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
							home.storeStockData(currentdata, StockViewdata, 6);
							if(isadding == true) {
								home.addbutton6.setVisible(false);
								home.editbutton6.setVisible(true);
								home.removebutton6.setVisible(true);
								home.stockviewbutton6.setVisible(true);
							}
						}else if(buttonnum == 7) {
							
							home.UpdateButton(home.stockviewbutton7, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
							home.storeStockData(currentdata, StockViewdata, 7);
							if(isadding == true) {
								home.addbutton7.setVisible(false);
								home.editbutton7.setVisible(true);
								home.removebutton7.setVisible(true);
								home.stockviewbutton7.setVisible(true);
							}
						}else if(buttonnum == 8) {
							home.UpdateButton(home.stockviewbutton8, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
							home.storeStockData(currentdata, StockViewdata, 8);
							if(isadding == true) {
								home.addbutton8.setVisible(false);
								home.editbutton8.setVisible(true);
								home.removebutton8.setVisible(true);
								home.stockviewbutton8.setVisible(true);
							}
						}else if(buttonnum == 9) {
							home.storeStockData(currentdata, StockViewdata, 9);
							home.UpdateButton(home.stockviewbutton9, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
							
							if(isadding == true) {
								home.addbutton9.setVisible(false);
								home.editbutton9.setVisible(true);
								home.removebutton9.setVisible(true);
								home.stockviewbutton9.setVisible(true);
							}
						}else if(buttonnum == 10) {
							home.UpdateButton(home.stockviewbutton10, currentdata.get(Constants.STOCK_SYMBOL), currentdata.get(Constants.CURRENT_VALUE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE), currentdata.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));
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
