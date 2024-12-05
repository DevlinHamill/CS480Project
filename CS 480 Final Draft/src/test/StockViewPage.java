package test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class StockViewPage extends JFrame{

	private JFrame frame;
	public static StockViewPage window;
	public static Stock currentstock;
	private static int number;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new StockViewPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public StockViewPage() {
		initialize();
	}
	
	/**
	 * Create the application.
	 * @param stocklist 
	 * @param home 
	 */
	public StockViewPage(HomeScreen home, int number) {
		this.currentstock = home.stocklist[number];
		
		initialize();
	}

	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 972, 494);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		String StockSymbol = currentstock.Stocksymbol;
		String Price = currentstock.Currentstockprice;
		String Delta = currentstock.Changefrompreviousclose;
		String DeltaPercentage = currentstock.ChangefrompreviousclosePrecentage;
		
		String prompt = String.format("<html><span style='color:black;'>%s (%s) %s </span>"+
				"<span style='color:%s;'>(%s)(%s)</span></html>",
				currentstock.StockName, StockSymbol, Price, Double.parseDouble(Delta) < 0 ? "red" : "green", Delta, DeltaPercentage);
		
		JLabel TitleLabel = new JLabel("");
		TitleLabel.setBounds(10, 10, 849, 30);
		TitleLabel.setText(prompt);
				
		TitleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		
		DefaultCategoryDataset data = new DefaultCategoryDataset();
        data.addValue(Double.parseDouble(currentstock.intraday_values[0]), "Stock Symbol", currentstock.intraday_dates[0]);
        data.addValue(Double.parseDouble(currentstock.intraday_values[1]), "Stock Symbol", currentstock.intraday_dates[1]);
        data.addValue(Double.parseDouble(currentstock.intraday_values[2]), "Stock Symbol", currentstock.intraday_dates[2]);
        data.addValue(Double.parseDouble(currentstock.intraday_values[3]), "Stock Symbol", currentstock.intraday_dates[3]);
        data.addValue(Double.parseDouble(currentstock.intraday_values[4]), "Stock Symbol", currentstock.intraday_dates[4]);
       
        
        JFreeChart chart = ChartFactory.createLineChart(
                "Stock data (Intraday)",  	//graph title
                "Stock date",  	//x value title      
                "Stock Price", 	// y value title   
                data,        	//data being displayed
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
		
    
        ValueAxis yAxis = chart.getCategoryPlot().getRangeAxis();
        double[] yValues = new double[] {
                Double.parseDouble(currentstock.intraday_values[0]),
                Double.parseDouble(currentstock.intraday_values[1]),
                Double.parseDouble(currentstock.intraday_values[2]),
                Double.parseDouble(currentstock.intraday_values[3]),
                Double.parseDouble(currentstock.intraday_values[4])
        };

        
        double minY = Arrays.stream(yValues).min().getAsDouble();
        double maxY = Arrays.stream(yValues).max().getAsDouble();

        yAxis.setRange(minY, maxY);

        
		ChartPanel panel_1 = new ChartPanel((chart));
		panel_1.setBounds(10, 46, 938, 196);
		
		panel_1.setRangeZoomable(true);
		panel_1.setDomainZoomable(true);
		panel_1.setMouseZoomable(true);
		
		JLabel prevcloselabel = new JLabel("Previous Close: "+currentstock.Previous_Close);
		prevcloselabel.setBounds(10, 248, 196, 33);
		prevcloselabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel openlabel = new JLabel("Open: "+currentstock.Open);
		openlabel.setBounds(10, 289, 196, 35);
		openlabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel bidlabel = new JLabel("Bid: "+currentstock.Bid);
		bidlabel.setBounds(10, 330, 196, 35);
		bidlabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel asklabel = new JLabel("Ask:"+currentstock.Ask);
		asklabel.setBounds(10, 371, 196, 35);
		asklabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel dayRangeLabel = new JLabel("Day's Range: "+currentstock.DaysRange);
		dayRangeLabel.setBounds(212, 248, 218, 33);
		dayRangeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel MarketCapLabel = new JLabel("Market Cap (IntraDay): "+currentstock.MarketCap_intraday);
		MarketCapLabel.setBounds(436, 248, 204, 35);
		MarketCapLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		
		JLabel EarningsDateLabel = new JLabel("Earnings Date: "+ currentstock.Earnings_Date);
		EarningsDateLabel.setBounds(652, 248, 296, 35);
		EarningsDateLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel weekRangeLabel = new JLabel("52 Week Range: "+currentstock.FiftyTwo_WeekRange);
		weekRangeLabel.setBounds(212, 289, 202, 35);
		weekRangeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel VolumeLabel = new JLabel("Volume: "+ currentstock.Volume);
		VolumeLabel.setBounds(212, 330, 218, 35);
		VolumeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel AvgVolumeLabel = new JLabel("Avg. Volume: "+currentstock.AvgVolume);
		AvgVolumeLabel.setBounds(212, 371, 218, 35);
		AvgVolumeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel BetaLabel = new JLabel("Beta (5Y Monthly): "+currentstock.Beta_5Y_Monthly);
		BetaLabel.setBounds(436, 289, 196, 35);
		BetaLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel PERationLabel = new JLabel("PE Ration (TTM): "+currentstock.PERatio_TTM);
		PERationLabel.setBounds(436, 330, 210, 35);
		PERationLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel EPSTTMRatio = new JLabel("EPS (TTM): "+currentstock.EPS_TTM);
		EPSTTMRatio.setBounds(436, 371, 210, 35);
		EPSTTMRatio.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel ForwardDivYieldLabel = new JLabel("Forward Div & Yield: "+currentstock.Forward_Dividend_and_Yield);
		ForwardDivYieldLabel.setBounds(652, 289, 296, 35);
		ForwardDivYieldLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel ExDivDateLabel = new JLabel("Ex-Div Date: "+currentstock.Ex_Dividend_Date);
		ExDivDateLabel.setBounds(652, 330, 296, 35);
		ExDivDateLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel TargetEstLabel = new JLabel("1y Target Est: "+currentstock.y_Target_Est);
		TargetEstLabel.setBounds(652, 371, 296, 35);
		TargetEstLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JButton BackButton = new JButton("Close");
		BackButton.setBounds(10, 424, 144, 21);
		BackButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		
		JButton IntraDayButton = new JButton("Intraday chart");
		IntraDayButton.setBounds(565, 424, 122, 21);
		IntraDayButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				data.clear();
		        data.addValue(Double.parseDouble(currentstock.intraday_values[0]), "Stock Symbol", currentstock.intraday_dates[0]);
		        data.addValue(Double.parseDouble(currentstock.intraday_values[1]), "Stock Symbol", currentstock.intraday_dates[1]);
		        data.addValue(Double.parseDouble(currentstock.intraday_values[2]), "Stock Symbol", currentstock.intraday_dates[2]);
		        data.addValue(Double.parseDouble(currentstock.intraday_values[3]), "Stock Symbol", currentstock.intraday_dates[3]);
		        data.addValue(Double.parseDouble(currentstock.intraday_values[4]), "Stock Symbol", currentstock.intraday_dates[4]);
		       
		    
		        JFreeChart chart = ChartFactory.createLineChart(
		                "Stock data (Intraday)",  	//graph title
		                "Stock date",  	//x value title      
		                "Stock Price", 	// y value title   
		                data,        	//data being displayed
		                PlotOrientation.VERTICAL,
		                true,
		                true,
		                false
		        );
		        
		        ValueAxis yAxis = chart.getCategoryPlot().getRangeAxis();
		        double[] yValues = new double[] {
		                Double.parseDouble(currentstock.intraday_values[0]),
		                Double.parseDouble(currentstock.intraday_values[1]),
		                Double.parseDouble(currentstock.intraday_values[2]),
		                Double.parseDouble(currentstock.intraday_values[3]),
		                Double.parseDouble(currentstock.intraday_values[4])
		        };

		        
		        double minY = Arrays.stream(yValues).min().getAsDouble();
		        double maxY = Arrays.stream(yValues).max().getAsDouble();

		        yAxis.setRange(minY, maxY);
		        
		        panel_1.setChart(chart);
		        
			}
		});
		
		JButton DailyButton = new JButton("Daily Graph");
		DailyButton.setBounds(695, 424, 124, 21);
		DailyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				data.clear();
		        data.addValue(Double.parseDouble(currentstock.daily_values[0]), "Stock Symbol", currentstock.daily_dates[0]);
		        data.addValue(Double.parseDouble(currentstock.daily_values[1]), "Stock Symbol", currentstock.daily_dates[1]);
		        data.addValue(Double.parseDouble(currentstock.daily_values[2]), "Stock Symbol", currentstock.daily_dates[2]);
		        data.addValue(Double.parseDouble(currentstock.daily_values[3]), "Stock Symbol", currentstock.daily_dates[3]);
		        data.addValue(Double.parseDouble(currentstock.daily_values[4]), "Stock Symbol", currentstock.daily_dates[4]);
		       
		    
		        JFreeChart chart = ChartFactory.createLineChart(
		                "Stock data (Daily)",  	//graph title
		                "Stock date",  	//x value title      
		                "Stock Price", 	// y value title   
		                data,        	//data being displayed
		                PlotOrientation.VERTICAL,
		                true,
		                true,
		                false
		        );
		        
		        ValueAxis yAxis = chart.getCategoryPlot().getRangeAxis();
		        double[] yValues = new double[] {
		                Double.parseDouble(currentstock.daily_values[0]),
		                Double.parseDouble(currentstock.daily_values[1]),
		                Double.parseDouble(currentstock.daily_values[2]),
		                Double.parseDouble(currentstock.daily_values[3]),
		                Double.parseDouble(currentstock.daily_values[4])
		        };

		        
		        double minY = Arrays.stream(yValues).min().getAsDouble();
		        double maxY = Arrays.stream(yValues).max().getAsDouble();

		        yAxis.setRange(minY, maxY);
		        
		        panel_1.setChart(chart);
			}
		});
		
		JButton MonthlyButton = new JButton("Monthly Graph");
		MonthlyButton.setBounds(824, 424, 124, 21);
		MonthlyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				data.clear();
		        data.addValue(Double.parseDouble(currentstock.monthly_values[0]), "Stock Symbol", currentstock.monthly_dates[0]);
		        data.addValue(Double.parseDouble(currentstock.monthly_values[1]), "Stock Symbol", currentstock.monthly_dates[1]);
		        data.addValue(Double.parseDouble(currentstock.monthly_values[2]), "Stock Symbol", currentstock.monthly_dates[2]);
		        data.addValue(Double.parseDouble(currentstock.monthly_values[3]), "Stock Symbol", currentstock.monthly_dates[3]);
		        data.addValue(Double.parseDouble(currentstock.monthly_values[4]), "Stock Symbol", currentstock.monthly_dates[4]);
		       
		    
		        JFreeChart chart = ChartFactory.createLineChart(
		                "Stock data (Monthly)",  	//graph title
		                "Stock date",  	//x value title      
		                "Stock Price", 	// y value title   
		                data,        	//data being displayed
		                PlotOrientation.VERTICAL,
		                true,
		                true,
		                false
		        );
		        
		        ValueAxis yAxis = chart.getCategoryPlot().getRangeAxis();
		        double[] yValues = new double[] {
		                Double.parseDouble(currentstock.monthly_values[0]),
		                Double.parseDouble(currentstock.monthly_values[1]),
		                Double.parseDouble(currentstock.monthly_values[2]),
		                Double.parseDouble(currentstock.monthly_values[3]),
		                Double.parseDouble(currentstock.monthly_values[4])
		        };

		        
		        double minY = Arrays.stream(yValues).min().getAsDouble();
		        double maxY = Arrays.stream(yValues).max().getAsDouble();

		        yAxis.setRange(minY, maxY);
		        
		        panel_1.setChart(chart);
			}
		});
		panel.setLayout(null);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 724, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 194, Short.MAX_VALUE)
		);
		panel_1.setLayout(gl_panel_1);
		panel.add(panel_1);
		panel.add(TitleLabel);
		panel.add(asklabel);
		panel.add(bidlabel);
		panel.add(openlabel);
		panel.add(prevcloselabel);
		panel.add(VolumeLabel);
		panel.add(PERationLabel);
		panel.add(ExDivDateLabel);
		panel.add(dayRangeLabel);
		panel.add(MarketCapLabel);
		panel.add(EarningsDateLabel);
		panel.add(weekRangeLabel);
		panel.add(BetaLabel);
		panel.add(ForwardDivYieldLabel);
		panel.add(BackButton);
		panel.add(AvgVolumeLabel);
		panel.add(EPSTTMRatio);
		panel.add(TargetEstLabel);
		panel.add(IntraDayButton);
		panel.add(DailyButton);
		panel.add(MonthlyButton);
	}
}
