package jstock.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jstock.core.StockDayDataFrame;
import jstock.utils.ChartUtils;
import jstock.utils.DataUtils;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;

public class JStockFrame extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_year;
	private JTextField textField_code;
	private static String[] markets = { "sh", "sz", "hk", "us" };
	private static String[] periods = { "day", "week", "month" };
	private StockDayDataFrame stockDayDataFrame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					JStockFrame frame = new JStockFrame();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JStockFrame()
	{
		setType(Type.UTILITY);
		setTitle("JStock");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 526, 755);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField_year = new JTextField();
		textField_year.setToolTipText("\u91C7\u7528\"YYYY\"\u7684\u683C\u5F0F\uFF0C\u59822018");
		textField_year.setBounds(134, 201, 86, 24);
		contentPane.add(textField_year);
		textField_year.setColumns(10);

		textField_code = new JTextField();
		textField_code.setToolTipText(
				"\u6CAA\u6DF1\u4E3A6\u4F4D\uFF0C\u6E2F\u80A1\u4E3A5\u4F4D\uFF0C\u7F8E\u80A1\u4E3A\u82F1\u6587\u5927\u5199\u5B57\u6BCD");
		textField_code.setBounds(134, 98, 86, 24);
		contentPane.add(textField_code);
		textField_code.setColumns(10);

		JComboBox<String> comboBox_period = new JComboBox<>();
		comboBox_period.setModel(
				new DefaultComboBoxModel<String>(new String[] { "   \u6BCF\u5929", "   \u6BCF\u5468", "   \u6BCF\u6708" }));
		comboBox_period.setSelectedIndex(0);
		comboBox_period.setMaximumRowCount(3);
		comboBox_period.setBounds(134, 252, 86, 24);
		contentPane.add(comboBox_period);

		JComboBox<String> comboBox_market = new JComboBox<>();
		comboBox_market.setModel(new DefaultComboBoxModel<String>(
				new String[] { "   \u4E0A\u6D77", "   \u6DF1\u5733", "   \u9999\u6E2F", "   \u7F8E\u56FD" }));
		comboBox_market.setSelectedIndex(0);
		comboBox_market.setMaximumRowCount(4);
		comboBox_market.setBounds(134, 150, 86, 24);
		contentPane.add(comboBox_market);

		JLabel lblNewLabel = new JLabel("\u80A1\u7968\u4EE3\u7801");
		lblNewLabel.setToolTipText(
				"\u6CAA\u6DF1\u4E3A6\u4F4D\uFF0C\u6E2F\u80A1\u4E3A5\u4F4D\uFF0C\u7F8E\u80A1\u4E3A\u82F1\u6587\u5927\u5199\u5B57\u6BCD");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(44, 101, 72, 18);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("\u5E74\u4EFDYYYY");
		lblNewLabel_1.setToolTipText("\u91C7\u7528\"YYYY\"\u7684\u683C\u5F0F\uFF0C\u59822018");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(44, 204, 72, 18);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("\u80A1\u7968\u5E02\u573A");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(44, 153, 72, 18);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("\u6570\u636E\u5468\u671F");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setBounds(48, 255, 72, 18);
		contentPane.add(lblNewLabel_3);

		JButton btnNewButton = new JButton("\u7ED8\u5236k\u7EBF");
		JButton btnNewButton_1 = new JButton("\u67E5\u627E");
		JLabel lblNewLabel_5 = new JLabel("\u67E5\u627E\u6210\u529F\uFF01");
		lblNewLabel_5.setForeground(Color.RED);
		lblNewLabel_5.setBounds(363, 156, 90, 18);
		lblNewLabel_5.setVisible(false);

		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				ChartUtils.paintKLineData(stockDayDataFrame);
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(true);
			}
		});
		btnNewButton.setBounds(352, 200, 101, 27);
		contentPane.add(btnNewButton);

		btnNewButton_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String code = textField_code.getText();
				String market = markets[comboBox_market.getSelectedIndex()];
				String year = textField_year.getText();
				String period = periods[comboBox_period.getSelectedIndex()];
				try
				{
					stockDayDataFrame = new StockDayDataFrame(code, market, year, period);
					btnNewButton.setEnabled(true);
					btnNewButton_1.setEnabled(false);
					lblNewLabel_5.setText("查找成功！");
					lblNewLabel_5.setVisible(true);
					Object[][] model = DataUtils.getTableModel(stockDayDataFrame);
					table.setModel(
							new DefaultTableModel(model, new String[] { "日期", "开盘", "最高", "最低", "收盘", "成交量", "涨跌幅" }));
				}
				catch (Exception e)
				{
					lblNewLabel_5.setText("查找失败！");
					lblNewLabel_5.setVisible(true);
				}

			}
		});
		btnNewButton_1.setBounds(363, 119, 72, 27);
		contentPane.add(btnNewButton_1);

		JLabel lblNewLabel_4 = new JLabel(
				"\u6B22\u8FCE\u4F7F\u7528JStock\u80A1\u7968\u6570\u636E\u67E5\u8BE2\u548C\u7ED8\u56FE\u7CFB\u7EDF");
		lblNewLabel_4.setFont(new Font("宋体", Font.BOLD, 20));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(32, 0, 446, 94);
		contentPane.add(lblNewLabel_4);
		contentPane.add(lblNewLabel_5);

		table = new JTable();
		table.setSurrendersFocusOnKeystroke(true);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setFillsViewportHeight(true);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setBounds(0, 0, 420, 1);
		contentPane.add(table);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(41, 333, 423, 321);
		contentPane.add(scrollPane);

	}
}
