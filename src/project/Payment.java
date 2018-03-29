package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import project.Cash.buttonAction;

class Cash extends JFrame {
	Customer ct;
	JDialog paymentWindowWindow;
	JTextField sum;
	JButton tt;
	JButton pay;
	int cashSum;

	public Cash(Customer ct, String name) {
		this.ct = ct;
		paymentWindowWindow = new JDialog(new JFrame(), name, true); // 현금
		paymentWindowWindow.setBounds(100, 100, 500, 500);
		paymentWindowWindow.setLayout(null);

		// 결제버튼 누르면 닫힘
		sum = new JTextField(); // 숫자받을거
		sum.setBounds(50, 50, 400, 200);
		sum.setText("0");
		sum.addKeyListener(new KeyAdapter() { // 키숫자만 받기, 값이 total이상이면 total보여주기
			@Override
			public void keyReleased(KeyEvent e) { // 후에정규식?
				if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9')) {
					System.out.println("숫자아님");
					if (!(e.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
						System.out.println("백스페이스 아님");
						if (!(e.getKeyChar() == KeyEvent.VK_ENTER)) {
							System.out.println("엔터도 아님");
							e.consume(); // 키 이벤트시 타이핑한 내용 삭제
							System.out.println(e.getKeyChar());
							JOptionPane.showMessageDialog(null, "숫자만 입력하시기 바랍니다."); // 메시지창
							return;
						}
					}
				} else if (!sum.getText().equals("")) { // 아무것도 안적혀있을때 null아니고 ""이다
					if (Integer.parseInt(sum.getText()) > ct.total) { // 텍스트필드내용 가져와서 숫자화
						e.consume();
						sum.setText(ct.total + "");
					}
				}
			}
		});
		paymentWindowWindow.add(sum);

		tt = new JButton("전체"); // 총액 그대로 받아서 텍스트 필드로 가져다 줄거
		tt.setBounds(50, 350, 150, 100);
		paymentWindowWindow.add(tt);
		tt.addActionListener(new buttonAction());

		pay = new JButton("결제"); // 금액있고 결제 누르면 닫히고 총액반영
		pay.setBounds(300, 350, 150, 100);
		paymentWindowWindow.add(pay);
		pay.addActionListener(new buttonAction1());

		paymentWindowWindow.setVisible(true);
	}

	class buttonAction implements ActionListener { // 전체 버튼
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int total = ct.getTotal(); // 변수에 총액가져오기
			sum.setText(String.valueOf(total)); // 총액 텍스트에 삽입
		}
	}

	class buttonAction1 implements ActionListener { // 결제 버튼
		int payCash;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (sum.getText() == null) { // 텍스트 아무내용없으면 0
				payCash = 0;
			} else
				payCash = Integer.parseInt(sum.getText()); // 텍스트필드에 있는 값을 가지고 와서 숫자화

			cashSum = payCash;
			ct.payCardCash(payCash);
			System.out.println(payCash + "원 만큼 총액에서 차감");

			paymentWindowWindow.dispose(); // frame 안보이게
		}
	}

	int getCashSum() {
		return cashSum;
	}
}

// 멤버십 결제
class Membership extends JFrame {
	Customer ct;
	String id;
	JDialog member;
	JButton chk;
	JLabel saved;
	JTextField useSv;
	JButton ttCellPh;
	JButton payCellPh;
	String point;
	int pointSum;

	public Membership(Customer ct) {
		this.ct = ct;
		this.id = MainMenu.idtext.getText();
		int phone = ct.getPoint();
		point = String.valueOf(phone);

		member = new JDialog(new JFrame(), "멤버쉽", true); // 멤버쉽
		member.setBounds(100, 100, 500, 500);
		member.setLayout(null);

		saved = new JLabel("적립금:" + point); // 적립금 보여줄거//

		// 시간남으면 아래 텍스트필드따라 후에 변동할 적립금보여주기
		saved.setBounds(50, 50, 400, 100);
		member.add(saved);

		useSv = new JTextField(); // 적립금사용 금액 받을거 //적립금보다 높은 숫자 입력불가
		useSv.setText("0");
		useSv.setBounds(50, 150, 400, 100);
		useSv.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9' || e.getKeyChar() == KeyEvent.VK_BACK_SPACE
						|| e.getKeyChar() == KeyEvent.VK_ENTER)) { // char0~9

					JOptionPane.showMessageDialog(null, "숫자만 입력하시기 바랍니다."); // 메시지창
					e.consume(); // 키 이벤트시 타이핑한 내용 삭제
					return;
				}
				if (!useSv.getText().equals("")) { // 아무것도 안적혀있을때 null아니고 ""이다
					if ((Integer.parseInt(useSv.getText()) > Integer.parseInt(point)
							|| Integer.parseInt(useSv.getText()) > ct.total)) { // 텍스트필드내용 가져와서 숫자화
						e.consume();
						useSv.setText("" + Math.min(Integer.parseInt(point), ct.total));
					}
				}
			}
		});
		member.add(useSv);

		ttCellPh = new JButton("전체"); // 전체 그대로 적립금 텍스트필드로 가져다 줄거
										// 적립금 전체사용으로 만들고 총액보다 적립금 많으면 총액까지만되게

		ttCellPh.addActionListener(new buttonAction());
		ttCellPh.setBounds(50, 350, 150, 100);
		member.add(ttCellPh);
		payCellPh = new JButton("결제"); // 적립금은 차감, 총액에 결과 반영, 다이얼로그 닫기
		payCellPh.setBounds(300, 350, 150, 100);
		member.add(payCellPh);
		payCellPh.addActionListener(new buttonAction2());

		member.setVisible(true);
	}

	int getPointSum() {
		return pointSum;
	}

	class buttonAction implements ActionListener { // 전체 버튼
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int total = Math.min(Integer.parseInt(point), ct.total); // 변수에 총액가져오기
			useSv.setText(String.valueOf(total)); // 총액 텍스트에 삽입
		}
	}

	class buttonAction2 implements ActionListener { // 적립금 결제 버튼

		@Override
		public void actionPerformed(ActionEvent e) {
			int pay; // 숫자변수
			pay = Integer.parseInt(useSv.getText()); // 텍스트필드에 있는 값을 가지고 와서 숫자화

			pointSum = pay;
			ct.payPoint(pay, id);
			System.out.println(pay + "원 만큼 총액에서 차감");
			member.dispose();
			// System.exit(0);
		}
	}

	
	// 메뉴판 화면에서
	class chkAction implements ActionListener { // 핸드폰 번호받아서 적립금 조회하여 보여줌

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String memphone;
			int sav = 100;
			memphone = id; // 핸드폰번호받기

			sav = ct.getPoint();

			// saved = new JLabel("적립금: " + sav + " point"); //라벨에 적립금 보여주기
			saved.setText("적립금: " + sav + " point");
		}
	}
}

public class Payment {

	public static void main(String[] args) {
		// Customer ct = new Customer();

	}

}