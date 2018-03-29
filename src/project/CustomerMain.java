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
			// 도킹
			con = DriverManager.getConnection("jdbc:oracle:thin:@" + url, id, pw);
			con.setAutoCommit(true);
			System.out.println("연결성공 ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 주세요 메소드

	void order(DefaultTableModel model) { // 하나씩
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
			System.out.println("총계:" + total);

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

	// 카드, 현금, 포인트 결제
	// 카드 및 현금 결제 금액의 5%를 포인트 적립
	void payCardCash(int pay) {
		String memPhone = MainMenu.idtext.getText();
		int updatePoint = 0;
		// 결제한 금액만큼 총액에서 빼준다
		total -= pay;
		// if (memPhone != null)
		// 포인트 5%적립
		point += pay * 0.05;

		try {
			// 폰번호에 맞는 포인트를 DB에서 가져온다
			stmt = con.createStatement();
			sql = "select money from member where phone = '" + memPhone + "'";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			// System.out.println(rs.next());

			// 폰번호가 없다면 DB에 insert한다
			if (memPhone.equals(""))
				return;
			if (!rs.next()) {
				sql = "insert into member (phone) values ( '" + memPhone + "')";
				System.out.println(sql);
				rs = stmt.executeQuery(sql);

				// 새 포인트를 updatePoint에 넣어준다
				updatePoint = point;
			} else {
				// 폰번호가 있다면 기존 포인트 새포인트를 더해 updatePoint에 더한다
				updatePoint = rs.getInt("money") + point;
			}
			sql = "select money from member where phone = '" + memPhone + "'";
			rs = stmt.executeQuery(sql);
			System.out.println(rs.next());
			System.out.println(sql);
			System.out.println("updatePoint:" + updatePoint);

			// 포인트를 더한 만큼 DB에 포인트 업데이트
			sql = "update member set money = " + updatePoint + " where phone ='" + memPhone + "'";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);

			System.out.println("총계:" + total);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// if (total == 0)
			// close();
		}
	}

	/* 건환 수정 */
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

	/* 끝 */
	// 포인트로 결제한다
	void payPoint(int pay, String memPhone) {
		int updatePoint;
		total -= pay;
		System.out.println("paypoint");
		try {
			// 폰번호에 맞는 포인트를 DB에서 가져온다
			stmt = con.createStatement();
			sql = "select money from member where phone = '" + memPhone + "'";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			System.out.println(rs.next());
			// 폰번호가 없다면 DB에 insert한다
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

			// 기존 포인트에서 사용한 포인트를 뺸다
			updatePoint = rs.getInt("money");
			updatePoint -= pay;
			System.out.println("updatePoint:" + updatePoint);

			// 포인트를 더한 만큼 DB에 포인트 업데이트
			sql = "update member set money = " + updatePoint + " where phone ='" + memPhone + "'";
			rs = stmt.executeQuery(sql);
			System.out.println(sql);
			System.out.println("총계:" + total);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// if (total == 0)
			// close();
		}
	}

	// 폰번호를 받아 포인트를 조회해서 리턴한다
	int getPoint() {
		int money = 0;
		String memPhone = MainMenu.idtext.getText();
		try {
			// 폰번호에 맞는 포인트를 DB에서 가져온다
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

	// 건환수정
	void payKind(int cash, int card, int point) {
		try {
			stmt = con.createStatement();
			sql = "update paykind set money = (money+" + cash + ") where kind = '현금' ";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			sql = "update paykind set money = (money+" + card + ") where kind = '카드' ";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			sql = "update paykind set money = (money+" + point + ") where kind = '포인트' ";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// 끝
}

public class CustomerMain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// String payResult = "";
		// Customer ct = new Customer();

		// 주문시 호출

	}
}
