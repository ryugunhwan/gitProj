package project;

//2018. 03. 19 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

class Customer {

	int total, card, cash, point;
	int totalChk;
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	String sql = null;
	String url = "localhost:1521:xe", id = "hr", pw = "hr";
	ResultSet resultSet;
	Map<String, Integer> orderList = new HashMap<>();

	public Customer() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// ��ŷ
			con = DriverManager.getConnection("jdbc:oracle:thin:@" + url, id, pw);
			con.setAutoCommit(true);
			System.out.println("���Ἲ�� ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �ּ��� �޼ҵ�

	void order(DefaultTableModel model) { // �ϳ���
		int price;
		int sum;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(new Date());

		try {
			stmt = con.createStatement();
			total = 0;
			for (int i = 0; i < model.getRowCount(); i++) {
				sql = "select price from juicy where menu='" + model.getValueAt(i, 0) + "'";
				System.out.println(sql);
				rs = stmt.executeQuery(sql);

				System.out.println(rs.next());
				System.out.println(rs.getInt("price"));

				price = rs.getInt("price");
				sum = price * (int) model.getValueAt(i, 1);
				System.out.println("menu:" + model.getValueAt(i, 0) + "/sum:" + sum);
				total += sum;

			}
			System.out.println("�Ѱ�:" + total);

			try {
				sql = "insert into paydate values('" + str + "', " + total + ")";
				rs = stmt.executeQuery(sql);
			} catch (SQLIntegrityConstraintViolationException ee) {
				sql = "update paydate set price = price+"+total+" where pdate='" + str + "'";
				rs = stmt.executeQuery(sql);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void close() {
		if (rs != null)
			try {
				rs.close();
			} catch (Exception e2) {
			}
		if (stmt != null)
			try {
				stmt.close();
			} catch (Exception e2) {
			}
		if (con != null)
			try {
				con.close();
			} catch (Exception e2) {
			}
	}

	// ī��, ����, ����Ʈ ����
	// ī�� �� ���� ���� �ݾ��� 5%�� ����Ʈ ����
	void payCardCash(int pay) {
		String memPhone = MainMenu.idtext.getText();
		int updatePoint = 0;
		// ������ �ݾ׸�ŭ �Ѿ׿��� ���ش�
		total -= pay;
		// if (memPhone != null)
		// ����Ʈ 5%����
		point += pay * 0.05;

		try {
			// ����ȣ�� �´� ����Ʈ�� DB���� �����´�
			stmt = con.createStatement();
			sql = "select money from member where phone = '" + memPhone + "'";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			// System.out.println(rs.next());

			// ����ȣ�� ���ٸ� DB�� insert�Ѵ�
			if (memPhone.equals(""))
				return;
			if (!rs.next()) {
				sql = "insert into member (phone) values ( '" + memPhone + "')";
				System.out.println(sql);
				rs = stmt.executeQuery(sql);

				// �� ����Ʈ�� updatePoint�� �־��ش�
				updatePoint = point;
			} else {
				// ����ȣ�� �ִٸ� ���� ����Ʈ ������Ʈ�� ���� updatePoint�� ���Ѵ�
				updatePoint = rs.getInt("money") + point;
			}
			sql = "select money from member where phone = '" + memPhone + "'";
			rs = stmt.executeQuery(sql);
			System.out.println(rs.next());
			System.out.println(sql);
			System.out.println("updatePoint:" + updatePoint);

			// ����Ʈ�� ���� ��ŭ DB�� ����Ʈ ������Ʈ
			sql = "update member set money = " + updatePoint + " where phone ='" + memPhone + "'";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);

			System.out.println("�Ѱ�:" + total);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// if (total == 0)
			// close();
		}
	}

	/* ��ȯ ���� */
	void updateCnt(String name, int cnt) {
		try {
			stmt = con.createStatement();
			sql = "update juicy set count = count+" + cnt + " where menu = '" + name + "'";
			// sql = "select money from member where phone = '" + "" + "'";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* �� */
	// ����Ʈ�� �����Ѵ�
	void payPoint(int pay, String memPhone) {
		int updatePoint;
		total -= pay;
		System.out.println("paypoint");
		try {
			// ����ȣ�� �´� ����Ʈ�� DB���� �����´�
			stmt = con.createStatement();
			sql = "select money from member where phone = '" + memPhone + "'";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			System.out.println(rs.next());
			// ����ȣ�� ���ٸ� DB�� insert�Ѵ�
			try {
				if (!rs.next()) {
					sql = "insert into member values ( '" + memPhone + "' , 0)";
					System.out.println(sql);
					rs = stmt.executeQuery(sql);
				}
			} catch (SQLIntegrityConstraintViolationException e1) {

			}
			sql = "select money from member where phone = '" + memPhone + "'";
			rs = stmt.executeQuery(sql);
			System.out.println(rs.next());
			System.out.println(sql);

			// ���� ����Ʈ���� ����� ����Ʈ�� �A��
			updatePoint = rs.getInt("money");
			updatePoint -= pay;
			System.out.println("updatePoint:" + updatePoint);

			// ����Ʈ�� ���� ��ŭ DB�� ����Ʈ ������Ʈ
			sql = "update member set money = " + updatePoint + " where phone ='" + memPhone + "'";
			rs = stmt.executeQuery(sql);
			System.out.println(sql);
			System.out.println("�Ѱ�:" + total);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// if (total == 0)
			// close();
		}
	}

	// ����ȣ�� �޾� ����Ʈ�� ��ȸ�ؼ� �����Ѵ�
	int getPoint() {
		int money = 0;
		String memPhone = MainMenu.idtext.getText();
		try {
			// ����ȣ�� �´� ����Ʈ�� DB���� �����´�
			stmt = con.createStatement();
			sql = "select money from member where phone = '" + memPhone + "'";
			try {
				if (!rs.next()) {
					sql = "insert into member values ( '" + memPhone + "' , 0)";
					System.out.println(sql);
					rs = stmt.executeQuery(sql);
				}
			} catch (SQLIntegrityConstraintViolationException e) {

			} catch (NullPointerException e) {

			}
			sql = "select money from member where phone = '" + memPhone + "'";
			rs = stmt.executeQuery(sql);
			System.out.println(sql);
			rs.next();
			money = rs.getInt("money");
			System.out.println("rs.getInt(money):" + money);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// if (total == 0)
			// close();
		}
		return money;
	}

	int getTotal() {
		return total;
	}

	// ��ȯ����
	void payKind(int cash, int card, int point) {
		try {
			stmt = con.createStatement();
			sql = "update paykind set money = (money+" + cash + ") where kind = '����' ";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			sql = "update paykind set money = (money+" + card + ") where kind = 'ī��' ";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			sql = "update paykind set money = (money+" + point + ") where kind = '����Ʈ' ";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// ��
}

public class CustomerMain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// String payResult = "";
		// Customer ct = new Customer();

		// �ֹ��� ȣ��

	}
}
