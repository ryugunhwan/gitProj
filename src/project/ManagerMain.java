package project;

import java.awt.Color;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.UnknownKeyException;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

class tab1 extends JPanel {
	Statement stmt = null;
	ResultSet rs = null;
	Connection con = null;
	String sql = null;
	String sql2 = null;
	String url = "localhost:1521:xe", id = "hr", pw = "hr";

	public tab1() {
		String menu = "";
		int count = 0;

		// Table�� �����, JScrollPane�� JTable �ֱ�

		/////////////////////////////////////////// ��Ʈ
		JFreeChart chart = new JFreeChart(new PiePlot3D(createDataSet()));
		chart.setBackgroundPaint(Color.white);
		ChartPanel cp2 = new ChartPanel(chart);
		// cp2.setPreferredSize(new Dimension(400, 300));
		cp2.setBounds(10, 10, 500, 400);
		add(cp2);

		setBounds(0, 0, 1000, 500);
		setLayout(null);

		setVisible(true);
	}

	// ������ ����
	private PieDataset createDataSet() {
		DefaultPieDataset dataset = new DefaultPieDataset();

		try {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// ��ŷ
			con = DriverManager.getConnection("jdbc:oracle:thin:@" + url, id, pw);
			con.setAutoCommit(true);
			stmt = con.createStatement();

			// sql = "select (select count from juicy where menu = '�Ƹ޸�ī��M') + (select count
			// from juicy where menu = '�Ƹ޸�ī��XL') as result from dual";

			sql = "select sum(count) from juicy where cate = 'Ŀ��'";
			rs = stmt.executeQuery(sql);
			rs.next();
			int totalNum = rs.getInt("sum(count)");
			System.out.println("Ŀ�� totalNum :" + totalNum);

			sql2 = "select * from juicy where cate = 'Ŀ��'";
			rs = stmt.executeQuery(sql2);

			while (rs.next()) {
				int i = rs.getInt("count") / totalNum;
				System.out.println("i:" + rs.getInt("count"));
				Double menuCount = new Double(Math.round((rs.getInt("count") / (double) totalNum) * 100));
				// System.out.println("d:"+Math.round((rs.getInt("count")/(double)totalNum)*100));
				String menuName = rs.getString("menu").substring(0,
						rs.getString("menu").indexOf("M") & rs.getString("menu").indexOf("X"));
				// System.out.println(values);
				try {
					dataset.setValue(menuName,(double) dataset.getValue(menuName) + menuCount);
				} catch (UnknownKeyException e) {
					dataset.setValue(menuName, menuCount);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataset;
	}

	// ��Ʈ�����
	private JFreeChart createChart(PieDataset dataset) {
		JFreeChart jfreechart = ChartFactory.createPieChart("������Ʈ", dataset, false, false, false);
		PiePlot plot = (PiePlot) jfreechart.getPlot();
		plot.setNoDataMessage("No data available");
		plot.setExplodePercent(0, 0.1);
		plot.setCircular(true);
		plot.setStartAngle(180);
		plot.setForegroundAlpha(0.6F);
		return jfreechart;
	}
}

class tab2 extends JPanel {
	Statement stmt = null;
	ResultSet rs = null;
	Connection con = null;
	String sql = null;
	String sql2 = null;
	String url = "localhost:1521:xe", id = "hr", pw = "hr";

	public tab2() {
		String menu = "";
		int count = 0;

		// Table�� �����, JScrollPane�� JTable �ֱ�

		/////////////////////////////////////////// ��Ʈ
		JFreeChart chart = new JFreeChart(new PiePlot3D(createDataSet()));
		chart.setBackgroundPaint(Color.white);
		ChartPanel cp2 = new ChartPanel(chart);
		// cp2.setPreferredSize(new Dimension(400, 300));
		cp2.setBounds(10, 10, 500, 400);
		add(cp2);

		setBounds(0, 0, 1000, 500);
		setLayout(null);

		setVisible(true);
	}

	// ������ ����
	private PieDataset createDataSet() {
		DefaultPieDataset dataset = new DefaultPieDataset();

		try {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// ��ŷ
			con = DriverManager.getConnection("jdbc:oracle:thin:@" + url, id, pw);
			con.setAutoCommit(true);
			stmt = con.createStatement();

			// sql = "select (select count from juicy where menu = '�Ƹ޸�ī��M') + (select count
			// from juicy where menu = '�Ƹ޸�ī��XL') as result from dual";

			sql = "select sum(count) from juicy where cate = '�������ֽ�1'";
			rs = stmt.executeQuery(sql);
			rs.next();
			int totalNum = rs.getInt("sum(count)");
			System.out.println("�������ֽ�1 totalNum :" + totalNum);
			sql2 = "select * from juicy where cate = '�������ֽ�1'";
			rs = stmt.executeQuery(sql2);

			while (rs.next()) {

				// map�� �޴� �̸��� ���ٸ�,
				/*
				 * for (Map.Entry<Integer, String> entry : map.entrySet() ) {
				 * System.out.println(entry.getKey() + " " + entry.getValue()); }
				 */
				int i = rs.getInt("count") / totalNum;
				System.out.println("i:" + rs.getInt("count"));
				Double menuCount = new Double(Math.round((rs.getInt("count") / (double) totalNum) * 100));
				// System.out.println("d:"+Math.round((rs.getInt("count")/(double)totalNum)*100));
				String menuName = rs.getString("menu").substring(0,
						rs.getString("menu").indexOf("M") & rs.getString("menu").indexOf("X"));
				// System.out.println(values);
				try {
					dataset.setValue(menuName, (double) dataset.getValue(menuName) + menuCount);
				} catch (UnknownKeyException e) {
					dataset.setValue(menuName, menuCount);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataset;
	}

	// ��Ʈ�����
	private JFreeChart createChart(PieDataset dataset) {
		JFreeChart jfreechart = ChartFactory.createPieChart("������Ʈ", dataset, false, false, false);
		PiePlot plot = (PiePlot) jfreechart.getPlot();
		plot.setNoDataMessage("No data available");
		plot.setExplodePercent(0, 0.1);
		plot.setCircular(true);
		plot.setStartAngle(180);
		plot.setForegroundAlpha(0.6F);
		// plot.setSectionPaint(1, Color.black);
		return jfreechart;
	}
}

class tab3 extends JPanel {
	Statement stmt = null;
	ResultSet rs = null;
	Connection con = null;
	String sql = null;
	String sql2 = null;
	String url = "localhost:1521:xe", id = "hr", pw = "hr";

	public tab3() {
		String menu = "";
		int count = 0;

		// Table�� �����, JScrollPane�� JTable �ֱ�

		/////////////////////////////////////////// ��Ʈ
//		PiePlot3D pp3d = new PiePlot3D(createDataSet());
		
		JFreeChart chart = new JFreeChart(new PiePlot3D(createDataSet()));
		chart.setBackgroundPaint(Color.white);
		
		ChartPanel cp2 = new ChartPanel(chart);
		// cp2.setPreferredSize(new Dimension(400, 300));
		cp2.setBounds(10, 10, 500, 400);
		add(cp2);

		setBounds(0, 0, 1000, 500);
		setLayout(null);
		setVisible(true);
	}

	// ������ ����
	private PieDataset createDataSet() {
		DefaultPieDataset dataset = new DefaultPieDataset();

		try {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// ��ŷ
			con = DriverManager.getConnection("jdbc:oracle:thin:@" + url, id, pw);
			con.setAutoCommit(true);
			stmt = con.createStatement();

			sql = "select sum(count) from juicy where cate = '�������ֽ�2'";
			rs = stmt.executeQuery(sql);
			rs.next();
			int totalNum = rs.getInt("sum(count)");
			System.out.println("�������ֽ�2 totalNum :" + totalNum);

			sql2 = "select * from juicy where cate = '�������ֽ�2'";
			rs = stmt.executeQuery(sql2);

			while (rs.next()) {

				int i = rs.getInt("count") / totalNum;
				System.out.println("i:" + rs.getInt("count"));
				Double menuCount = new Double(Math.round((rs.getInt("count") / (double) totalNum) * 100));
				// System.out.println("d:"+Math.round((rs.getInt("count")/(double)totalNum)*100));
				String menuName = rs.getString("menu").substring(0,
						rs.getString("menu").indexOf("M") & rs.getString("menu").indexOf("XL"));
				// System.out.println(values);
				try {
					dataset.setValue(menuName, (double) dataset.getValue(menuName) + menuCount);
				} catch (UnknownKeyException e) {
					dataset.setValue(menuName, menuCount);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataset;
	}
}

class tab4 extends JPanel {
	Statement stmt = null;
	ResultSet rs = null;
	Connection con = null;
	String sql = null;
	String sql2 = null;
	String url = "localhost:1521:xe", id = "hr", pw = "hr";

	public tab4() {
		String menu = "";
		int count = 0;

		/////////////////////////////////////////// ��Ʈ
		JFreeChart chart;
		try {
			chart = new JFreeChart(new PiePlot3D(createDataSet()));
			chart.setBackgroundPaint(Color.white);
			ChartPanel cp2 = new ChartPanel(chart);
			// cp2.setPreferredSize(new Dimension(400, 300));
			cp2.setBounds(10, 10, 500, 400);
			add(cp2);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		setBounds(0, 0, 1000, 500);
		setLayout(null);
		setVisible(true);
	}

	PieDataset createDataSet() throws SQLException {
		DefaultPieDataset dataset = new DefaultPieDataset();

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// ��ŷ
		con = DriverManager.getConnection("jdbc:oracle:thin:@" + url, id, pw);
		con.setAutoCommit(true);
		stmt = con.createStatement();
		sql = "select * from paykind";
		rs = stmt.executeQuery(sql);

		while (rs.next()) {
			dataset.setValue(rs.getString("kind") + "=" + rs.getInt("money"), rs.getInt("money"));
		}

		return dataset;
	}

	JFreeChart createChart(PieDataset dataset) {
		JFreeChart payKind = ChartFactory.createPieChart("�������", dataset, false, false, false);
		PiePlot plot = (PiePlot) payKind.getPlot();
		plot.setNoDataMessage("���ɵ����;���");
		plot.setCircular(true);
		plot.setStartAngle(180);

		return payKind;
	}
}

class tablej extends JPanel {
	Statement stmt = null;
	ResultSet rs = null;
	Connection con = null;
	JTable table;
	JPanel tablePanel;
	String columnNames[] = { "�޴��̸�", "����" }; // �ֹ����̺� �׸��̸�
	String url = "localhost:1521:xe", id = "hr", pw = "hr";
	String sql;

	tablej() {
		DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column >= 0) {
					return false;
				} else {
					return true;
				}
			}
		};

		table = new JTable(model);
		table.setBounds(700, 70, 250, 370);
		add(table);

		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(700, 70, 250, 370);
		add(scroll);
		table.setVisible(true);
		// DB ����
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// ��ŷ
			con = DriverManager.getConnection("jdbc:oracle:thin:@" + url, id, pw);
			con.setAutoCommit(true);
			System.out.println("���Ἲ�� ");

			stmt = con.createStatement();
			sql = "select * from juicy";
			rs = stmt.executeQuery(sql);
			// DB���� �� �پ� �����ͼ� model�� �� �� �� �ִ´�.
			while (rs.next()) {
				model.addRow(new Object[] { rs.getString("menu"), rs.getInt("count") });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class ManagerMain {
	static JFrame jf;
	public static void main(String[] args) {
		jf = new JFrame("�� ������ ������");
		jf.setLayout(new GridLayout(0, 2));
		JTabbedPane jtab = new JTabbedPane();
		tab1 jp1 = new tab1();
		tab2 jp2 = new tab2();
		tab3 jp3 = new tab3();
		tab4 jp4 = new tab4();
		tablej tb = new tablej();

		jtab.add("Ŀ�� �Ǹź���", jp1);
		jtab.add("�ֽ�1 �Ǹź���", jp2);
		jtab.add("�ֽ�2 �Ǹź���", jp3);
		jtab.add("������ĺ� �Ǹž�", jp4);

		jf.setLocation(700, 400);
		jf.setSize(1000, 500);
		jf.add(jtab);
		jf.add(tb);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
}