package test;

import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.SwingConstants;

public class HomeScreen {

	private JFrame frame;
	public static JButton addbutton1, addbutton2, addbutton3, addbutton4,addbutton5, addbutton6, addbutton7, addbutton8, addbutton9, addbutton10;
	public static JButton stockviewbutton1, stockviewbutton2, stockviewbutton3, stockviewbutton4, stockviewbutton5, stockviewbutton6, stockviewbutton7, stockviewbutton8, stockviewbutton9, stockviewbutton10;
	public static JButton editbutton1, editbutton2, editbutton3, editbutton4,editbutton5, editbutton6, editbutton7, editbutton8, editbutton9, editbutton10;
	public static JButton removebutton1, removebutton2, removebutton3, removebutton4, removebutton5, removebutton6, removebutton7, removebutton8, removebutton9, removebutton10;

	JButton[] stockViewButtons = new JButton[10];
	JButton[] addButtons = new JButton[10];
	JButton[] editButtons = new JButton[10];
	JButton[] removeButtons = new JButton[10];

	public static HomeScreen home;
	public Stock[] stocklist = new Stock[10];
	public SaveLayoutHelper saver = new SaveLayoutHelper();

	private boolean hasLoaded = false;
	public static final boolean debug = true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeScreen window = new HomeScreen();
					home = window;
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
	public HomeScreen() {
		initialize();
		updateButtonFromLayout();
	}

	private void removeData(int number) {
		number = number - 1;
		stocklist[number].Currentstockprice = "";
		stocklist[number].Stocksymbol = null;
		stocklist[number].Changefrompreviousclose = "";
		stocklist[number].ChangefrompreviousclosePrecentage = "";

		stocklist[number].StockName = "";
		stocklist[number].Previous_Close = "";
		stocklist[number].Open = "";
		stocklist[number].Bid = "";
		stocklist[number].Ask = "";
		stocklist[number].DaysRange = "";
		stocklist[number].FiftyTwo_WeekRange = "";
		stocklist[number].Volume = "";
		stocklist[number].AvgVolume = "";
		stocklist[number].MarketCap_intraday = "";
		stocklist[number].Beta_5Y_Monthly = "";
		stocklist[number].PERatio_TTM = "";
		stocklist[number].EPS_TTM = "";
		stocklist[number].Earnings_Date = "";
		stocklist[number].Forward_Dividend_and_Yield = "";
		stocklist[number].Ex_Dividend_Date = "";
		stocklist[number].y_Target_Est = "";

		stocklist[number].intraday_dates[0] = "";
		stocklist[number].intraday_dates[1] = "";
		stocklist[number].intraday_dates[2] = "";
		stocklist[number].intraday_dates[3] = "";
		stocklist[number].intraday_dates[4] = "";

		stocklist[number].intraday_values[0] = "";
		stocklist[number].intraday_values[1] = "";
		stocklist[number].intraday_values[2] = "";
		stocklist[number].intraday_values[3] = "";
		stocklist[number].intraday_values[4] = "";

		stocklist[number].daily_dates[0] = "";
		stocklist[number].daily_dates[1] = "";
		stocklist[number].daily_dates[2] = "";
		stocklist[number].daily_dates[3] = "";
		stocklist[number].daily_dates[4] = "";

		stocklist[number].daily_values[0] = "";
		stocklist[number].daily_values[1] = "";
		stocklist[number].daily_values[2] = "";
		stocklist[number].daily_values[3] = "";
		stocklist[number].daily_values[4] = "";

		stocklist[number].monthly_dates[0] = "";
		stocklist[number].monthly_dates[1] = "";
		stocklist[number].monthly_dates[2] = "";
		stocklist[number].monthly_dates[3] = "";
		stocklist[number].monthly_dates[4] = "";

		stocklist[number].monthly_values[0] = "";
		stocklist[number].monthly_values[1] = "";
		stocklist[number].monthly_values[2] = "";
		stocklist[number].monthly_values[3] = "";
		stocklist[number].monthly_values[4] = "";

		saver.removeStockAtPosition(number);
	}

	public void storeStockData(HashMap<String,String> homepage, HashMap<String, String> StockViewpage, int number) {

		number = number - 1;
		stocklist[number].Currentstockprice = homepage.get(Constants.CURRENT_VALUE);
		stocklist[number].Stocksymbol = homepage.get(Constants.STOCK_SYMBOL);
		stocklist[number].Changefrompreviousclose = homepage.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE);
		stocklist[number].ChangefrompreviousclosePrecentage = homepage.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE);

		stocklist[number].StockName = StockViewpage.get(Constants.NAME);
		stocklist[number].Previous_Close = StockViewpage.get(Constants.PREVIOUS_CLOSE);
		stocklist[number].Open = StockViewpage.get(Constants.OPEN);
		stocklist[number].Bid = StockViewpage.get(Constants.BID);
		stocklist[number].Ask = StockViewpage.get(Constants.ASK);
		stocklist[number].DaysRange = StockViewpage.get(Constants.DAYSRANGE);
		stocklist[number].FiftyTwo_WeekRange = StockViewpage.get(Constants.FIFTYTWO_WEEKRANGE);
		stocklist[number].Volume = StockViewpage.get(Constants.VOLUME);
		stocklist[number].AvgVolume = StockViewpage.get(Constants.AVERAGE_VOLUME);
		stocklist[number].MarketCap_intraday = StockViewpage.get(Constants.MARKETCAP_INTRADAY);
		stocklist[number].Beta_5Y_Monthly = StockViewpage.get(Constants.BETA_5Y_MONTHLY);
		stocklist[number].PERatio_TTM = StockViewpage.get(Constants.PE_RATION_TTM);
		stocklist[number].EPS_TTM = StockViewpage.get(Constants.EPS_TTM);
		stocklist[number].Earnings_Date = StockViewpage.get(Constants.EARNINGS_DATE);
		stocklist[number].Forward_Dividend_and_Yield = StockViewpage.get(Constants.FORWARD_DIVIDEND_AND_YIELD);
		stocklist[number].Ex_Dividend_Date = StockViewpage.get(Constants.EX_DIVIDEND_DATE);
		stocklist[number].y_Target_Est = StockViewpage.get(Constants.Y_TARGET_EST);

		stocklist[number].intraday_dates[0] = StockViewpage.get(Constants.INTRADAY_DATE_ONE);
		stocklist[number].intraday_dates[1] = StockViewpage.get(Constants.INTRADAY_DATE_TWO);
		stocklist[number].intraday_dates[2] = StockViewpage.get(Constants.INTRADAY_DATE_THREE);
		stocklist[number].intraday_dates[3] = StockViewpage.get(Constants.INTRADAY_DATE_FOUR);
		stocklist[number].intraday_dates[4] = StockViewpage.get(Constants.INTRADAY_DATE_FIVE);

		stocklist[number].intraday_values[0] = StockViewpage.get(Constants.INTRADAY_VALUE_ONE);
		stocklist[number].intraday_values[1] = StockViewpage.get(Constants.INTRADAY_VALUE_TWO);
		stocklist[number].intraday_values[2] = StockViewpage.get(Constants.INTRADAY_VALUE_THREE);
		stocklist[number].intraday_values[3] = StockViewpage.get(Constants.INTRADAY_VALUE_FOUR);
		stocklist[number].intraday_values[4] = StockViewpage.get(Constants.INTRADAY_VALUE_FIVE);

		stocklist[number].daily_dates[0] = StockViewpage.get(Constants.DAILY_DATE_ONE);
		stocklist[number].daily_dates[1] = StockViewpage.get(Constants.DAILY_DATE_TWO);
		stocklist[number].daily_dates[2] = StockViewpage.get(Constants.DAILY_DATE_THREE);
		stocklist[number].daily_dates[3] = StockViewpage.get(Constants.DAILY_DATE_FOUR);
		stocklist[number].daily_dates[4] = StockViewpage.get(Constants.DAILY_DATE_FIVE);

		stocklist[number].daily_values[0] = StockViewpage.get(Constants.DAILY_VALUE_ONE);
		stocklist[number].daily_values[1] = StockViewpage.get(Constants.DAILY_VALUE_TWO);
		stocklist[number].daily_values[2] = StockViewpage.get(Constants.DAILY_VALUE_THREE);
		stocklist[number].daily_values[3] = StockViewpage.get(Constants.DAILY_VALUE_FOUR);
		stocklist[number].daily_values[4] = StockViewpage.get(Constants.DAILY_VALUE_FIVE);

		stocklist[number].monthly_dates[0] = StockViewpage.get(Constants.MONTHLY_DATE_ONE);
		stocklist[number].monthly_dates[1] = StockViewpage.get(Constants.MONTHLY_DATE_TWO);
		stocklist[number].monthly_dates[2] = StockViewpage.get(Constants.MONTHLY_DATE_THREE);
		stocklist[number].monthly_dates[3] = StockViewpage.get(Constants.MONTHLY_DATE_FOUR);
		stocklist[number].monthly_dates[4] = StockViewpage.get(Constants.MONTHLY_DATE_FIVE);

		stocklist[number].monthly_values[0] = StockViewpage.get(Constants.MONTHLY_VALUE_ONE);
		stocklist[number].monthly_values[1] = StockViewpage.get(Constants.MONTHLY_VALUE_TWO);
		stocklist[number].monthly_values[2] = StockViewpage.get(Constants.MONTHLY_VALUE_THREE);
		stocklist[number].monthly_values[3] = StockViewpage.get(Constants.MONTHLY_VALUE_FOUR);
		stocklist[number].monthly_values[4] = StockViewpage.get(Constants.MONTHLY_VALUE_FIVE);

		saver.addStockAtPosition(stocklist[number], number);
	}

	private void updateButton(int number) {
		if(number == 1) {
			UpdateButtonText(stockviewbutton1, stocklist[number - 1].Stocksymbol, stocklist[number -1].Currentstockprice, stocklist[number - 1].Changefrompreviousclose, stocklist[number -1].ChangefrompreviousclosePrecentage);
		}else if(number == 2) {
			UpdateButtonText(stockviewbutton2, stocklist[number - 1].Stocksymbol, stocklist[number -1].Currentstockprice, stocklist[number - 1].Changefrompreviousclose, stocklist[number -1].ChangefrompreviousclosePrecentage);
		}else if(number == 3) {
			UpdateButtonText(stockviewbutton3, stocklist[number - 1].Stocksymbol, stocklist[number -1].Currentstockprice, stocklist[number - 1].Changefrompreviousclose, stocklist[number -1].ChangefrompreviousclosePrecentage);
		}else if(number == 4) {
			UpdateButtonText(stockviewbutton4, stocklist[number - 1].Stocksymbol, stocklist[number -1].Currentstockprice, stocklist[number - 1].Changefrompreviousclose, stocklist[number -1].ChangefrompreviousclosePrecentage);
		}else if(number == 5) {
			UpdateButtonText(stockviewbutton5, stocklist[number - 1].Stocksymbol, stocklist[number -1].Currentstockprice, stocklist[number - 1].Changefrompreviousclose, stocklist[number -1].ChangefrompreviousclosePrecentage);
		}else if(number == 6) {
			UpdateButtonText(stockviewbutton6, stocklist[number - 1].Stocksymbol, stocklist[number -1].Currentstockprice, stocklist[number - 1].Changefrompreviousclose, stocklist[number -1].ChangefrompreviousclosePrecentage);
		}else if(number == 7) {
			UpdateButtonText(stockviewbutton2, stocklist[number - 1].Stocksymbol, stocklist[number -1].Currentstockprice, stocklist[number - 1].Changefrompreviousclose, stocklist[number -1].ChangefrompreviousclosePrecentage);
		}else if(number == 8) {
			UpdateButtonText(stockviewbutton2, stocklist[number - 1].Stocksymbol, stocklist[number -1].Currentstockprice, stocklist[number - 1].Changefrompreviousclose, stocklist[number -1].ChangefrompreviousclosePrecentage);
		}else if(number == 9) {
			UpdateButtonText(stockviewbutton2, stocklist[number - 1].Stocksymbol, stocklist[number -1].Currentstockprice, stocklist[number - 1].Changefrompreviousclose, stocklist[number -1].ChangefrompreviousclosePrecentage);
		}else if(number == 10) {
			UpdateButtonText(stockviewbutton2, stocklist[number - 1].Stocksymbol, stocklist[number -1].Currentstockprice, stocklist[number - 1].Changefrompreviousclose, stocklist[number -1].ChangefrompreviousclosePrecentage);
		}
	}

	private void refreshOnClick() {
		AlphaVantageApiHelper apiConnection = new AlphaVantageApiHelper();

		for(int i=0; i < 10; i++) {

			if(!(stocklist[i].Stocksymbol == null)) {
				
				System.out.println("Refreshing stock: "+stocklist[i].Stocksymbol);
				HashMap<String, String> Homepage = apiConnection.getHomeScreenData(stocklist[i].Stocksymbol);
				HashMap<String, String> StockViewpage = apiConnection.getStockViewData(stocklist[i].Stocksymbol);
				storeStockData(Homepage, StockViewpage, i+1);
				updateButton(i+1);
				
			}else {
				continue;
			}


		}
	}

	public Stock getStockData(int number) {
		number = number - 1;
		return stocklist[number];
	}

	public void UpdateButtonText(JButton button, String StockSymbol, String Price, String Delta, String DeltaPercentage){
		String prompt = String.format("<html><span style='color:black;'>%s %s </span>"+
						"<span style='color:%s;'>(%s)(%s)</span></html>",
				StockSymbol, Price, Double.parseDouble(Delta) < 0 ? "red" : "green", Delta, DeltaPercentage);

		button.setText(prompt);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		if(debug) System.out.println("Running HomeScreen.initialize");
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 707, 543);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		for(int i = 0; i < 10; i++) {
			stocklist[i] = new Stock();
		}

		JPanel panel = new JPanel();
		JPanel panel_1 = new JPanel();
		Image addimage = new ImageIcon(this.getClass().getResource("/addimage_2.png")).getImage();

		/*
		 * add buttons
		 */
		addbutton1 = new JButton("");
		addbutton1.setBounds(10, 10, 323, 91);
		addbutton1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Type in the stock symbol you would like to add:";
				popup addpop = new popup(home, 1, true, promptstr);
				addpop.main(null);
				refreshOnClick();

			}
		});
		addbutton1.setBackground(new Color(255, 255, 255));
		addbutton1.setIcon(new ImageIcon(addimage));

		addbutton2 = new JButton("");
		addbutton2.setBounds(10, 107, 323, 92);
		addbutton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Type in the stock symbol you would like to add:";
				popup addpop = new popup(home, 2, true, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		addbutton2.setBackground(new Color(255, 255, 255));
		addbutton2.setIcon(new ImageIcon(addimage));

		addbutton3 = new JButton("");
		addbutton3.setBounds(10, 205, 323, 92);
		addbutton3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Type in the stock symbol you would like to add:";
				popup addpop = new popup(home, 3, true, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		addbutton3.setBackground(new Color(255, 255, 255));
		addbutton3.setIcon(new ImageIcon(addimage));


		addbutton4 = new JButton("");
		addbutton4.setBounds(10, 303, 323, 92);
		addbutton4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Type in the stock symbol you would like to add:";
				popup addpop = new popup(home, 4, true, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		addbutton4.setBackground(new Color(255, 255, 255));
		addbutton4.setIcon(new ImageIcon(addimage));

		addbutton5 = new JButton("");
		addbutton5.setBounds(10, 401, 323, 91);
		addbutton5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Type in the stock symbol you would like to add:";
				popup addpop = new popup(home, 5, true, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		addbutton5.setBackground(new Color(255, 255, 255));
		addbutton5.setIcon(new ImageIcon(addimage));

		addbutton6 = new JButton("");
		addbutton6.setBounds(10, 10, 324, 89);
		addbutton6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Type in the stock symbol you would like to add:";
				popup addpop = new popup(home, 6, true, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		addbutton6.setBackground(new Color(255, 255, 255));
		addbutton6.setIcon(new ImageIcon(addimage));

		addbutton7 = new JButton("");
		addbutton7.setBounds(10, 109, 324, 90);
		addbutton7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Type in the stock symbol you would like to add:";
				popup addpop = new popup(home, 7, true, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		addbutton7.setBackground(new Color(255, 255, 255));
		addbutton7.setIcon(new ImageIcon(addimage));

		addbutton8 = new JButton("");
		addbutton8.setBounds(10, 209, 324, 88);
		addbutton8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Type in the stock symbol you would like to add:";
				popup addpop = new popup(home, 8, true, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		addbutton8.setBackground(new Color(255, 255, 255));
		addbutton8.setIcon(new ImageIcon(addimage));

		addbutton9 = new JButton("");
		addbutton9.setBounds(10, 307, 324, 88);
		addbutton9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Type in the stock symbol you would like to add:";
				popup addpop = new popup(home, 9, true, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		addbutton9.setBackground(new Color(255, 255, 255));
		addbutton9.setIcon(new ImageIcon(addimage));

		addbutton10 = new JButton("");
		addbutton10.setBounds(10, 405, 324, 91);
		addbutton10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Type in the stock symbol you would like to add:";
				popup addpop = new popup(home, 10, true, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		addbutton10.setBackground(new Color(255, 255, 255));
		addbutton10.setIcon(new ImageIcon(addimage));


		/*
		 * remove buttons
		 */
		Image removeimage = new ImageIcon(this.getClass().getResource("/garbageicon4.jpg")).getImage();


		removebutton1 = new JButton("");
		removebutton1.setBounds(233, 10, 48, 91);
		removebutton1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				stockviewbutton1.setText("");
				stockviewbutton1.setVisible(false);
				addbutton1.setVisible(true);
				editbutton1.setVisible(false);
				removebutton1.setVisible(false);
				removeData(1);
			}
		});
		removebutton1.setIcon(new ImageIcon(removeimage));
		removebutton1.setBackground(new Color(255, 255, 255));
		removebutton1.setVisible(false);

		removebutton2 = new JButton("");
		removebutton2.setBounds(233, 107, 48, 92);
		removebutton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				stockviewbutton2.setText("");
				stockviewbutton2.setVisible(false);
				addbutton2.setVisible(true);
				editbutton2.setVisible(false);
				removebutton2.setVisible(false);
				removeData(2);
			}
		});
		removebutton2.setIcon(new ImageIcon(removeimage));
		removebutton2.setBackground(new Color(255, 255, 255));
		removebutton2.setVisible(false);

		removebutton3 = new JButton("");
		removebutton3.setBounds(233, 205, 48, 92);
		removebutton3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				stockviewbutton3.setText("");
				stockviewbutton3.setVisible(false);
				addbutton3.setVisible(true);
				editbutton3.setVisible(false);
				removebutton3.setVisible(false);
				removeData(3);

			}
		});
		removebutton3.setIcon(new ImageIcon(removeimage));
		removebutton3.setBackground(new Color(255, 255, 255));
		removebutton3.setVisible(false);

		removebutton4 = new JButton("");
		removebutton4.setBounds(233, 303, 48, 92);
		removebutton4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				stockviewbutton4.setText("");
				stockviewbutton4.setVisible(false);
				addbutton4.setVisible(true);
				editbutton4.setVisible(false);
				removebutton4.setVisible(false);
				removeData(4);
			}
		});
		removebutton4.setIcon(new ImageIcon(removeimage));
		removebutton4.setBackground(new Color(255, 255, 255));
		removebutton4.setVisible(false);

		removebutton5 = new JButton("");
		removebutton5.setBounds(233, 401, 48, 91);
		removebutton5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				stockviewbutton5.setText("");
				stockviewbutton5.setVisible(false);
				addbutton5.setVisible(true);
				editbutton5.setVisible(false);
				removebutton5.setVisible(false);
				removeData(5);
			}
		});
		removebutton5.setIcon(new ImageIcon(removeimage));
		removebutton5.setBackground(new Color(255, 255, 255));
		removebutton5.setVisible(false);

		removebutton6 = new JButton("");
		removebutton6.setBounds(222, 10, 59, 89);
		removebutton6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				stockviewbutton6.setText("");
				stockviewbutton6.setVisible(false);
				addbutton6.setVisible(true);
				editbutton6.setVisible(false);
				removebutton6.setVisible(false);
				removeData(6);
			}
		});
		removebutton6.setIcon(new ImageIcon(removeimage));
		removebutton6.setBackground(new Color(255, 255, 255));
		removebutton6.setVisible(false);

		removebutton7 = new JButton("");
		removebutton7.setBounds(222, 109, 59, 90);
		removebutton7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				stockviewbutton7.setText("");
				stockviewbutton7.setVisible(false);
				addbutton7.setVisible(true);
				editbutton7.setVisible(false);
				removebutton7.setVisible(false);
				removeData(7);
			}
		});
		removebutton7.setIcon(new ImageIcon(removeimage));
		removebutton7.setBackground(new Color(255, 255, 255));
		removebutton7.setVisible(false);

		removebutton8 = new JButton("");
		removebutton8.setBounds(222, 209, 59, 88);
		removebutton8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				stockviewbutton8.setText("");
				stockviewbutton8.setVisible(false);
				addbutton8.setVisible(true);
				editbutton8.setVisible(false);
				removebutton8.setVisible(false);
				removeData(8);
			}
		});
		removebutton8.setIcon(new ImageIcon(removeimage));
		removebutton8.setBackground(new Color(255, 255, 255));
		removebutton8.setVisible(false);

		removebutton9 = new JButton("");
		removebutton9.setBounds(222, 307, 59, 88);
		removebutton9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				stockviewbutton9.setText("");
				stockviewbutton9.setVisible(false);
				addbutton9.setVisible(true);
				editbutton9.setVisible(false);
				removebutton9.setVisible(false);
				removeData(9);
			}
		});
		removebutton9.setIcon(new ImageIcon(removeimage));
		removebutton9.setBackground(new Color(255, 255, 255));
		removebutton9.setVisible(false);

		removebutton10 = new JButton("");
		removebutton10.setBounds(222, 405, 59, 91);
		removebutton10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				stockviewbutton10.setText("");
				stockviewbutton10.setVisible(false);
				addbutton10.setVisible(true);
				editbutton10.setVisible(false);
				removebutton10.setVisible(false);
				removeData(10);
			}
		});
		removebutton10.setIcon(new ImageIcon(removeimage));
		removebutton10.setBackground(new Color(255, 255, 255));
		removebutton10.setVisible(false);


		/*
		 * edit buttons
		 */

		Image editimage = new ImageIcon(this.getClass().getResource("/editicon2.jpg")).getImage();

		editbutton1 = new JButton("");
		editbutton1.setBounds(276, 10, 57, 91);
		editbutton1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String promptstr = "Please type in the new stock symbol:";
				popup addpop = new popup(home, 1, false, promptstr);
				addpop.main(null);
				refreshOnClick();

			}
		});
		editbutton1.setIcon(new ImageIcon(editimage));
		editbutton1.setBackground(new Color(255, 255, 255));
		editbutton1.setVisible(false);


		editbutton2 = new JButton("");
		editbutton2.setBounds(276, 107, 57, 92);
		editbutton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Please type in the new stock symbol:";
				popup addpop = new popup(home, 2, false, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		editbutton2.setIcon(new ImageIcon(editimage));
		editbutton2.setBackground(new Color(255, 255, 255));
		editbutton2.setVisible(false);

		editbutton3 = new JButton("");
		editbutton3.setBounds(276, 205, 57, 92);
		editbutton3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Please type in the new stock symbol:";
				popup addpop = new popup(home, 3, false, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		editbutton3.setIcon(new ImageIcon(editimage));
		editbutton3.setBackground(new Color(255, 255, 255));
		editbutton3.setVisible(false);

		editbutton4 = new JButton("");
		editbutton4.setBounds(276, 303, 57, 92);
		editbutton4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Please type in the new stock symbol:";
				popup addpop = new popup(home, 4, false, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		editbutton4.setIcon(new ImageIcon(editimage));
		editbutton4.setBackground(new Color(255, 255, 255));
		editbutton4.setVisible(false);

		editbutton5 = new JButton("");
		editbutton5.setBounds(276, 401, 57, 91);
		editbutton5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Please type in the new stock symbol:";
				popup addpop = new popup(home, 5, false, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		editbutton5.setIcon(new ImageIcon(editimage));
		editbutton5.setBackground(new Color(255, 255, 255));
		editbutton5.setVisible(false);

		editbutton6 = new JButton("");
		editbutton6.setBounds(277, 10, 57, 89);
		editbutton6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Please type in the new stock symbol:";
				popup addpop = new popup(home, 6, false, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		editbutton6.setIcon(new ImageIcon(editimage));
		editbutton6.setBackground(new Color(255, 255, 255));
		editbutton6.setVisible(false);

		editbutton7 = new JButton("");
		editbutton7.setBounds(277, 109, 57, 90);
		editbutton7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Please type in the new stock symbol:";
				popup addpop = new popup(home,7, false, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		editbutton7.setIcon(new ImageIcon(editimage));
		editbutton7.setBackground(new Color(255, 255, 255));
		editbutton7.setVisible(false);

		editbutton8 = new JButton("");
		editbutton8.setBounds(277, 209, 57, 88);
		editbutton8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Please type in the new stock symbol:";
				popup addpop = new popup(home, 8, false, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		editbutton8.setIcon(new ImageIcon(editimage));
		editbutton8.setBackground(new Color(255, 255, 255));
		editbutton8.setVisible(false);

		editbutton9 = new JButton("");
		editbutton9.setBounds(277, 307, 57, 88);
		editbutton9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Please type in the new stock symbol:";
				popup addpop = new popup(home, 9, false, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		editbutton9.setIcon(new ImageIcon(editimage));
		editbutton9.setBackground(new Color(255, 255, 255));
		editbutton9.setVisible(false);

		editbutton10 = new JButton("");
		editbutton10.setBounds(277, 405, 57, 91);
		editbutton10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String promptstr = "Please type in the new stock symbol:";
				popup addpop = new popup(home, 10, false, promptstr);
				addpop.main(null);
				refreshOnClick();
			}
		});
		editbutton10.setIcon(new ImageIcon(editimage));
		editbutton10.setBackground(new Color(255, 255, 255));
		editbutton10.setVisible(false);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
								.addContainerGap())
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
		);


		/*
		 * stock view buttons
		 */
		stockviewbutton6 = new JButton("         New button");
		stockviewbutton6.setHorizontalAlignment(SwingConstants.LEFT);
		stockviewbutton6.setBounds(10, 10, 213, 89);
		stockviewbutton6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				refreshOnClick();
				StockViewPage viewpage = new StockViewPage(home, 5);
				viewpage.main(null);

			}
		});

		stockviewbutton7 = new JButton("         New button");
		stockviewbutton7.setHorizontalAlignment(SwingConstants.LEFT);
		stockviewbutton7.setBounds(10, 109, 213, 90);
		stockviewbutton7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				refreshOnClick();
				StockViewPage viewpage = new StockViewPage(home, 6);
				viewpage.main(null);
			}
		});

		stockviewbutton8 = new JButton("         New button");
		stockviewbutton8.setHorizontalAlignment(SwingConstants.LEFT);
		stockviewbutton8.setBounds(10, 209, 213, 88);
		stockviewbutton8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				refreshOnClick();
				StockViewPage viewpage = new StockViewPage(home, 7);
				viewpage.main(null);
			}
		});

		stockviewbutton9 = new JButton("         New button");
		stockviewbutton9.setHorizontalAlignment(SwingConstants.LEFT);
		stockviewbutton9.setBounds(10, 307, 213, 88);
		stockviewbutton9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				refreshOnClick();
				StockViewPage viewpage = new StockViewPage(home, 8);
				viewpage.main(null);
			}
		});

		stockviewbutton10 = new JButton("         New button");
		stockviewbutton10.setHorizontalAlignment(SwingConstants.LEFT);
		stockviewbutton10.setBounds(10, 405, 213, 91);
		stockviewbutton10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				refreshOnClick();
				StockViewPage viewpage = new StockViewPage(home, 9);
				viewpage.main(null);
			}
		});

		panel_1.setLayout(null);
		panel_1.add(addbutton7);
		panel_1.add(removebutton7);
		panel_1.add(editbutton7);
		panel_1.add(addbutton8);
		panel_1.add(removebutton8);
		panel_1.add(editbutton8);
		panel_1.add(addbutton9);
		panel_1.add(removebutton9);
		panel_1.add(editbutton9);
		panel_1.add(addbutton10);
		panel_1.add(removebutton10);
		panel_1.add(editbutton10);
		panel_1.add(addbutton6);
		panel_1.add(removebutton6);
		panel_1.add(editbutton6);
		panel_1.add(stockviewbutton6);
		panel_1.add(stockviewbutton7);
		panel_1.add(stockviewbutton8);
		panel_1.add(stockviewbutton9);
		panel_1.add(stockviewbutton10);

		/*
		 * stock view buttons
		 */
		stockviewbutton1 = new JButton("         New button");
		stockviewbutton1.setHorizontalAlignment(SwingConstants.LEFT);
		stockviewbutton1.setBounds(10, 10, 225, 91);
		stockviewbutton1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				refreshOnClick();
				StockViewPage viewpage = new StockViewPage(home, 0);
				viewpage.main(null);
			}
		});

		stockviewbutton2 = new JButton("         New button");
		stockviewbutton2.setHorizontalAlignment(SwingConstants.LEFT);
		stockviewbutton2.setBounds(10, 107, 225, 92);
		stockviewbutton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				refreshOnClick();
				StockViewPage viewpage = new StockViewPage(home, 1);
				viewpage.main(null);
			}
		});

		stockviewbutton3 = new JButton("         New button");
		stockviewbutton3.setHorizontalAlignment(SwingConstants.LEFT);
		stockviewbutton3.setBounds(10, 205, 225, 92);
		stockviewbutton3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				refreshOnClick();
				StockViewPage viewpage = new StockViewPage(home, 2);
				viewpage.main(null);
			}
		});

		stockviewbutton4 = new JButton("         New button");
		stockviewbutton4.setHorizontalAlignment(SwingConstants.LEFT);
		stockviewbutton4.setBounds(10, 303, 225, 92);
		stockviewbutton4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				refreshOnClick();
				StockViewPage viewpage = new StockViewPage(home, 3);
				viewpage.main(null);
			}
		});

		stockviewbutton5 = new JButton("         New button");
		stockviewbutton5.setHorizontalAlignment(SwingConstants.LEFT);
		stockviewbutton5.setBounds(10, 401, 225, 91);
		stockviewbutton5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				refreshOnClick();
				StockViewPage viewpage = new StockViewPage(home, 4);
				viewpage.main(null);
			}
		});

		panel.setLayout(null);
		panel.add(addbutton2);
		panel.add(removebutton2);
		panel.add(editbutton2);
		panel.add(addbutton1);
		panel.add(removebutton1);
		panel.add(editbutton1);
		panel.add(stockviewbutton1);
		panel.add(addbutton4);
		panel.add(removebutton4);
		panel.add(editbutton4);
		panel.add(addbutton5);
		panel.add(removebutton5);
		panel.add(editbutton5);
		panel.add(addbutton3);
		panel.add(removebutton3);
		panel.add(editbutton3);
		panel.add(stockviewbutton2);
		panel.add(stockviewbutton3);
		panel.add(stockviewbutton4);
		panel.add(stockviewbutton5);
		frame.getContentPane().setLayout(groupLayout);

		stockViewButtons[0] = stockviewbutton1;
		stockViewButtons[1] = stockviewbutton2;
		stockViewButtons[2] = stockviewbutton3;
		stockViewButtons[3] = stockviewbutton4;
		stockViewButtons[4] = stockviewbutton5;
		stockViewButtons[5] = stockviewbutton6;
		stockViewButtons[6] = stockviewbutton7;
		stockViewButtons[7] = stockviewbutton8;
		stockViewButtons[8] = stockviewbutton9;
		stockViewButtons[9] = stockviewbutton10;

		addButtons[0] = addbutton1;
		addButtons[1] = addbutton2;
		addButtons[2] = addbutton3;
		addButtons[3] = addbutton4;
		addButtons[4] = addbutton5;
		addButtons[5] = addbutton6;
		addButtons[6] = addbutton7;
		addButtons[7] = addbutton8;
		addButtons[8] = addbutton9;
		addButtons[9] = addbutton10;

		editButtons[0] = editbutton1;
		editButtons[1] = editbutton2;
		editButtons[2] = editbutton3;
		editButtons[3] = editbutton4;
		editButtons[4] = editbutton5;
		editButtons[5] = editbutton6;
		editButtons[6] = editbutton7;
		editButtons[7] = editbutton8;
		editButtons[8] = editbutton9;
		editButtons[9] = editbutton10;

		removeButtons[0] = removebutton1;
		removeButtons[1] = removebutton2;
		removeButtons[2] = removebutton3;
		removeButtons[3] = removebutton4;
		removeButtons[4] = removebutton5;
		removeButtons[5] = removebutton6;
		removeButtons[6] = removebutton7;
		removeButtons[7] = removebutton8;
		removeButtons[8] = removebutton9;
		removeButtons[9] = removebutton10;
	}

	public void updateButtonFromLayout() {
		//TODO: Add functionality to save new stocks when a new stock is added or edited or removed
		if(debug) System.out.println("Running HomeScreen.updateButtonFromLayout");
		if(!hasLoaded) {
			hasLoaded = true;
			for(int i = 0; i < stockViewButtons.length; i++) {
				Stock temp = saver.getStockAtPosition(i);

				if(temp.getStocksymbol() != null) {
					if(debug) System.out.println("Loading data for: " + temp.getStocksymbol() + " at position: " + (i + 1));

					HashMap<String, String> StockViewdata = AlphaVantageApiHelper.getStockViewData(temp.getStocksymbol());
					HashMap<String, String> homeScreenData = AlphaVantageApiHelper.getHomeScreenData(temp.getStocksymbol());

					UpdateButtonText(stockViewButtons[i],
							temp.getStocksymbol(),
							homeScreenData.get(Constants.CURRENT_VALUE),
							homeScreenData.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE),
							homeScreenData.get(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE));

					storeStockData(homeScreenData, StockViewdata, i + 1);

					addButtons[i].setVisible(false);
					editButtons[i].setVisible(true);
					removeButtons[i].setVisible(true);
					stockViewButtons[i].setVisible(true);
				}
			}
		}
	}
}
