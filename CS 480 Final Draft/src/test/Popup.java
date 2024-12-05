package test;

import java.awt.EventQueue;

import javax.swing.JFrame;
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

public class Popup extends JFrame{
	private static JTextField textField;
	public static String promptstr;
	public static JLabel promptLabel;
	public static HomeScreen home;
	public static int buttonnum;
	public static boolean isadding;
	public static Popup window;
	public static JLabel errorLabel;
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
	
	public Popup(HomeScreen home, int buttonnum, boolean isadding, String promptstr) {
		this.home = home;
		this.buttonnum = buttonnum;
		this.isadding = isadding;
		this.promptstr = promptstr;
		initialize();
	}

	public boolean checkInput(String input) {
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

//		if(AlphaVantageApiHelper.getOverviewJSON(input).keySet().isEmpty()) {
//			condition = false;
//			String errorStr = "All data not\nIn Alpha Vantage!";
//			String errorDisplay = String.format("<html><span style='color:red;'>%s</span>", errorStr);
//			errorLabel.setText(errorDisplay);
//			errorLabel.setVisible(true);
//			return condition;
//		}
		
		for(int i = 0; i < 10; i++) {
			if(home.stocklist[i].getStocksymbol() == null || home.stocklist[i] == null) {
				if(HomeScreen.debug) System.out.println("Stock at position " + (i + 1) + " has stock symbol " + home.stocklist[i].getStocksymbol());
				continue;
			}
				
			if(input.equals(home.stocklist[i].Stocksymbol)) {
				condition = false;
				String errorStr = "Pre-Existing\nStock Symbol!";
				String errorDisplay = String.format("<html><span style='color:red;'>%s</span>", errorStr);
				errorLabel.setText(errorDisplay);
				errorLabel.setVisible(true);
				return condition;
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

					if(checkInput(input)) {
						home.updateHomeScreenButton(buttonnum, input);
						if(isadding == true) {
							home.addButtons[buttonnum - 1].setVisible(false);
							home.editButtons[buttonnum - 1].setVisible(true);
							home.removeButtons[buttonnum - 1].setVisible(true);
							home.stockViewButtons[buttonnum - 1].setVisible(true);
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
