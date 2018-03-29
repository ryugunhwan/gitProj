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

		// Table을 만들고, JScrollPane에 JTable 넣기

		/////////////////////////////////////////// 차트
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

	// 데이터 세팅
	private PieDataset createDataSet() {
		DefaultPieDataset dataset = new DefaultPieDataset();

		try {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// 도킹
			con = DriverManager.getConnection("jdbc:oracle:thin:@" + url, id, pw);
			con.setAutoCommit(true);
			stmt = con.createStatement();

			// sql = "select (select count from juicy where menu = '아메리카노M') + (select count
			// from juicy where menu = '아메리카노XL') as result from dual";

			sql = "select sum(count) from juicy where cate = '커피'";
			rs = stmt.executeQuery(sql);
			rs.next();
			int totalNum = rs.getInt("sum(count)");
			System.out.println("커피 totalNum :" + totalNum);

			sql2 = "select * from juicy where cate = '커피'";
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

	// 차트만들기
	private JFreeChart createChart(PieDataset dataset) {
		JFreeChart jfreechart = ChartFactory.createPieChart("파이차트", dataset, false, false, false);
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

		// Table을 만들고, JScrollPane에 JTable 넣기

		/////////////////////////////////////////// 차트
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

	// 데이터 세팅
	private PieDataset createDataSet() {
		DefaultPieDataset dataset = new DefaultPieDataset();

		try {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// 도킹
			con = DriverManager.getConnection("jdbc:oracle:thin:@" + url, id, pw);
			con.setAutoCommit(true);
			stmt = con.createStatement();

			// sql = "select (select count from juicy where menu = '아메리카노M') + (select count
			// from juicy where menu = '아메리카노XL') as result from dual";

			sql = "select sum(count) from juicy where cate = '생과일주스1'";
			rs = stmt.executeQuery(sql);
			rs.next();
			int totalNum = rs.getInt("sum(count)");
			System.out.println("생과일주스1 totalNum :" + totalNum);
			sql2 = "select * from juicy where cate = '생과일주스1'";
			rs = stmt.executeQuery(sql2);

			while (rs.next()) {

				// map에 메뉴 이름이 없다면,
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

	// 차트만들기
	private JFreeChart createChart(PieDataset dataset) {
		JFreeChart jfreechart = ChartFactory.createPieChart("파이차트", dataset, false, false, false);
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

		// Table을 만들고, JScrollPane에 JTable 넣기

		/////////////////////////////////////////// 차트
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

	// 데이터 세팅
	private PieDataset createDataSet() {
		DefaultPieDataset dataset = new DefaultPieDataset();

		try {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// 도킹
			con = DriverManager.getConnection("jdbc:oracle:thin:@" + url, id, pw);
			con.setAutoCommit(true);
			stmt = con.createStatement();

			sql = "select sum(count) from juicy where cate = '생과일주스2'";
			rs = stmt.executeQuery(sql);
			rs.next();
			int totalNum = rs.getInt("sum(count)");
			System.out.println("생과일주스2 totalNum :" + totalNum);

			sql2 = "select * from juicy where cate = '생과일주스2'";
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

		/////////////////////////////////////////// 차트
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
		// 도킹
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
		JFreeChart payKind = ChartFactory.createPieChart("결제방식", dataset, false, false, false);
		PiePlot plot = (PiePlot) payKind.getPlot();
		plot.setNoDataMessage("가능데이터없음");
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
	String columnNames[] = { "메뉴이름", "수량" }; // 주문테이블 항목이름
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
		// DB 접속
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 도킹
			con = DriverManager.getConnection("jdbc:oracle:thin:@" + url, id, pw);
			con.setAutoCommit(true);
			System.out.println("연결성공 ");

			stmt = con.createStatement();
			sql = "select * from juicy";
			rs = stmt.executeQuery(sql);
			// DB에서 한 줄씩 가져와서 model에 한 줄 씩 넣는다.
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
		jf = new JFrame("쟈씨 관리자 페이지");
		jf.setLayout(new GridLayout(0, 2));
		JTabbedPane jtab = new JTabbedPane();
		tab1 jp1 = new tab1();
		tab2 jp2 = new tab2();
		tab3 jp3 = new tab3();
		tab4 jp4 = new tab4();
		tablej tb = new tablej();

		jtab.add("커피 판매비중", jp1);
		jtab.add("주스1 판매비중", jp2);
		jtab.add("주스2 판매비중", jp3);
		jtab.add("결제방식별 판매액", jp4);

		jf.setLocation(700, 400);
		jf.setSize(1000, 500);
		jf.add(jtab);
		jf.add(tb);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
}