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
		paymentWindowWindow = new JDialog(new JFrame(), name, true); // ����
		paymentWindowWindow.setBounds(100, 100, 500, 500);
		paymentWindowWindow.setLayout(null);

		// ������ư ������ ����
		sum = new JTextField(); // ���ڹ�����
		sum.setBounds(50, 50, 400, 200);
		sum.setText("0");
		sum.addKeyListener(new KeyAdapter() { // Ű���ڸ� �ޱ�, ���� total�̻��̸� total�����ֱ�
			@Override
			public void keyReleased(KeyEvent e) { // �Ŀ����Խ�?
				if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9')) {
					System.out.println("���ھƴ�");
					if (!(e.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
						System.out.println("�齺���̽� �ƴ�");
						if (!(e.getKeyChar() == KeyEvent.VK_ENTER)) {
							System.out.println("���͵� �ƴ�");
							e.consume(); // Ű �̺�Ʈ�� Ÿ������ ���� ����
							System.out.println(e.getKeyChar());
							JOptionPane.showMessageDialog(null, "���ڸ� �Է��Ͻñ� �ٶ��ϴ�."); // �޽���â
							return;
						}
					}
				} else if (!sum.getText().equals("")) { // �ƹ��͵� ������������ null�ƴϰ� ""�̴�
					if (Integer.parseInt(sum.getText()) > ct.total) { // �ؽ�Ʈ�ʵ峻�� �����ͼ� ����ȭ
						e.consume();
						sum.setText(ct.total + "");
					}
				}
			}
		});
		paymentWindowWindow.add(sum);

		tt = new JButton("��ü"); // �Ѿ� �״�� �޾Ƽ� �ؽ�Ʈ �ʵ�� ������ �ٰ�
		tt.setBounds(50, 350, 150, 100);
		paymentWindowWindow.add(tt);
		tt.addActionListener(new buttonAction());

		pay = new JButton("����"); // �ݾ��ְ� ���� ������ ������ �Ѿ׹ݿ�
		pay.setBounds(300, 350, 150, 100);
		paymentWindowWindow.add(pay);
		pay.addActionListener(new buttonAction1());

		paymentWindowWindow.setVisible(true);
	}

	class buttonAction implements ActionListener { // ��ü ��ư
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int total = ct.getTotal(); // ������ �Ѿװ�������
			sum.setText(String.valueOf(total)); // �Ѿ� �ؽ�Ʈ�� ����
		}
	}

	class buttonAction1 implements ActionListener { // ���� ��ư
		int payCash;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (sum.getText() == null) { // �ؽ�Ʈ �ƹ���������� 0
				payCash = 0;
			} else
				payCash = Integer.parseInt(sum.getText()); // �ؽ�Ʈ�ʵ忡 �ִ� ���� ������ �ͼ� ����ȭ

			cashSum = payCash;
			ct.payCardCash(payCash);
			System.out.println(payCash + "�� ��ŭ �Ѿ׿��� ����");

			paymentWindowWindow.dispose(); // frame �Ⱥ��̰�
		}
	}

	int getCashSum() {
		return cashSum;
	}
}

// ����� ����
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

		member = new JDialog(new JFrame(), "�����", true); // �����
		member.setBounds(100, 100, 500, 500);
		member.setLayout(null);

		saved = new JLabel("������:" + point); // ������ �����ٰ�//

		// �ð������� �Ʒ� �ؽ�Ʈ�ʵ���� �Ŀ� ������ �����ݺ����ֱ�
		saved.setBounds(50, 50, 400, 100);
		member.add(saved);

		useSv = new JTextField(); // �����ݻ�� �ݾ� ������ //�����ݺ��� ���� ���� �ԷºҰ�
		useSv.setText("0");
		useSv.setBounds(50, 150, 400, 100);
		useSv.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9' || e.getKeyChar() == KeyEvent.VK_BACK_SPACE
						|| e.getKeyChar() == KeyEvent.VK_ENTER)) { // char0~9

					JOptionPane.showMessageDialog(null, "���ڸ� �Է��Ͻñ� �ٶ��ϴ�."); // �޽���â
					e.consume(); // Ű �̺�Ʈ�� Ÿ������ ���� ����
					return;
				}
				if (!useSv.getText().equals("")) { // �ƹ��͵� ������������ null�ƴϰ� ""�̴�
					if ((Integer.parseInt(useSv.getText()) > Integer.parseInt(point)
							|| Integer.parseInt(useSv.getText()) > ct.total)) { // �ؽ�Ʈ�ʵ峻�� �����ͼ� ����ȭ
						e.consume();
						useSv.setText("" + Math.min(Integer.parseInt(point), ct.total));
					}
				}
			}
		});
		member.add(useSv);

		ttCellPh = new JButton("��ü"); // ��ü �״�� ������ �ؽ�Ʈ�ʵ�� ������ �ٰ�
										// ������ ��ü������� ����� �Ѿ׺��� ������ ������ �Ѿױ������ǰ�

		ttCellPh.addActionListener(new buttonAction());
		ttCellPh.setBounds(50, 350, 150, 100);
		member.add(ttCellPh);
		payCellPh = new JButton("����"); // �������� ����, �Ѿ׿� ��� �ݿ�, ���̾�α� �ݱ�
		payCellPh.setBounds(300, 350, 150, 100);
		member.add(payCellPh);
		payCellPh.addActionListener(new buttonAction2());

		member.setVisible(true);
	}

	int getPointSum() {
		return pointSum;
	}

	class buttonAction implements ActionListener { // ��ü ��ư
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int total = Math.min(Integer.parseInt(point), ct.total); // ������ �Ѿװ�������
			useSv.setText(String.valueOf(total)); // �Ѿ� �ؽ�Ʈ�� ����
		}
	}

	class buttonAction2 implements ActionListener { // ������ ���� ��ư

		@Override
		public void actionPerformed(ActionEvent e) {
			int pay; // ���ں���
			pay = Integer.parseInt(useSv.getText()); // �ؽ�Ʈ�ʵ忡 �ִ� ���� ������ �ͼ� ����ȭ

			pointSum = pay;
			ct.payPoint(pay, id);
			System.out.println(pay + "�� ��ŭ �Ѿ׿��� ����");
			member.dispose();
			// System.exit(0);
		}
	}

	
	// �޴��� ȭ�鿡��
	class chkAction implements ActionListener { // �ڵ��� ��ȣ�޾Ƽ� ������ ��ȸ�Ͽ� ������

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String memphone;
			int sav = 100;
			memphone = id; // �ڵ�����ȣ�ޱ�

			sav = ct.getPoint();

			// saved = new JLabel("������: " + sav + " point"); //�󺧿� ������ �����ֱ�
			saved.setText("������: " + sav + " point");
		}
	}
}

public class Payment {

	public static void main(String[] args) {
		// Customer ct = new Customer();

	}

}