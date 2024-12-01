package test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		frame.setBounds(100, 100, 758, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		
		JLabel TitleLabel = new JLabel(currentstock.StockName + "("+ currentstock.Stocksymbol+")"
				+ "("+currentstock.Changefrompreviousclose+")"+"("+currentstock.ChangefrompreviousclosePrecentage+")");
				
		TitleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		
		DefaultCategoryDataset data = new DefaultCategoryDataset();
        data.addValue(1, "Stock Symbol", "Day 1");
        data.addValue(4.6, "Stock Symbol", "Day 2");
        data.addValue(3, "Stock Symbol", "Day 3");
        data.addValue(5, "Stock Symbol", "Day 4");
        data.addValue(6, "Stock Symbol", "Day 5");

        JFreeChart chart = ChartFactory.createLineChart(
                "Stock data",  	//graph title
                "Stock date",  	//x value title      
                "Stock Price", 	// y value title   
                data        	//data being displayed
        );
		
		ChartPanel panel_1 = new ChartPanel((chart));
       
		
		JLabel prevcloselabel = new JLabel("Previous Close: "+currentstock.Previous_Close);
		prevcloselabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel openlabel = new JLabel("Open: "+currentstock.Open);
		openlabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel bidlabel = new JLabel("Bid: "+currentstock.Bid);
		bidlabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel asklabel = new JLabel("Ask:"+currentstock.Ask);
		asklabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel dayRangeLabel = new JLabel("Day's Range: "+currentstock.DaysRange);
		dayRangeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel MarketCapLabel = new JLabel("Market Cap (IntraDay): "+currentstock.MarketCap_intraday);
		MarketCapLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		
		JLabel EarningsDateLabel = new JLabel("Earnings Date: "+ currentstock.Earnings_Date);
		EarningsDateLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel weekRangeLabel = new JLabel("52 Week Range: "+currentstock.FiftyTwo_WeekRange);
		weekRangeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel VolumeLabel = new JLabel("Volume: "+ currentstock.Volume);
		VolumeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel AvgVolumeLabel = new JLabel("Avg. Volume: "+currentstock.AvgVolume);
		AvgVolumeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel BetaLabel = new JLabel("Beta (5Y Monthly): "+currentstock.Beta_5Y_Monthly);
		BetaLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel PERationLabel = new JLabel("PE Ration (TTM): "+currentstock.PERatio_TTM);
		PERationLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel EPSTTMRatio = new JLabel("EPS (TTM): "+currentstock.EPS_TTM);
		EPSTTMRatio.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel ForwardDivYieldLabel = new JLabel("Forward Div & Yield: "+currentstock.Forward_Dividend_and_Yield);
		ForwardDivYieldLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel ExDivDateLabel = new JLabel("Ex-Div Date: "+currentstock.Ex_Dividend_Date);
		ExDivDateLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel TargetEstLabel = new JLabel("1y Target Est: "+currentstock.y_Target_Est);
		TargetEstLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JButton BackButton = new JButton("Close");
		BackButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
						.addComponent(TitleLabel, GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(asklabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
								.addComponent(bidlabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
								.addComponent(openlabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
								.addComponent(prevcloselabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(VolumeLabel, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(PERationLabel, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(ExDivDateLabel, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(dayRangeLabel, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(MarketCapLabel, GroupLayout.PREFERRED_SIZE, 159, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(EarningsDateLabel, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(weekRangeLabel, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(BetaLabel, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(ForwardDivYieldLabel, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(74)
											.addComponent(BackButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addComponent(AvgVolumeLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(EPSTTMRatio, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(TargetEstLabel, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)))
							.addGap(8)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(TitleLabel, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(prevcloselabel, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
							.addComponent(dayRangeLabel, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
						.addComponent(MarketCapLabel, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(EarningsDateLabel, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(openlabel, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(weekRangeLabel, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(BetaLabel, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(ForwardDivYieldLabel, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(bidlabel, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(VolumeLabel, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(PERationLabel, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(ExDivDateLabel, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(TargetEstLabel, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(EPSTTMRatio, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(AvgVolumeLabel, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(asklabel, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(BackButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(12))
		);
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
		panel.setLayout(gl_panel);
	}
}
