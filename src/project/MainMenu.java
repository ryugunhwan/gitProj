package project;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

public class MainMenu extends JFrame {
	Customer ct = new Customer(); // �մ�
	static JTextField idtext; // ���̵� �Է��ʵ�
	JScrollPane scrollPane; // ��ũ����
	JPanel backPanel; // ����̹����� ���� �г�

	CardLayout cardLayout = new CardLayout(); // �޴��� ���� ī�� ���̾ƿ�
	JPanel basePanel; // �޴��� �� �г�

	String columnNames[] = { "�޴��̸�", "����" }; // �ֹ����̺� �׸��̸�
	JTable orderList; // �ֹ����̺�

	JButton paybuttonPoint;
	JButton paybuttonCash;
	JButton paybuttonCard;

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
	JButton receiptBtn; // ������ ��ư
	JLabel phoneComment; // "�޴��� ��ȣ�� �Է��� �ּ���" / �� ������ ����

	JLabel total; // �Ѿ� ���̴� ��
	String memphone; // ����ȣ
	int cashSum, cardSum, pointSum; // ĳ�ý� / ī��� / ����, ī�庰 �����Ѿ�

	////////////////////////////////////////////////////////////////
	JButton makeBtn(String name, int x, int y, int w, int h) { // ��ǰ�� ��ư�� �� �� ���� ����� ����..
		JButton jb = new JButton(name); // ��ư�̸� �޾Ƴ���
		jb.setBounds(x, y, w, h); // ��ư��ġ �޾Ƴ���
		jb.setBackground(Color.white); // ��ư���� �Ͼ��
		jb.setVisible(true); // ��ư ������
		jb.addActionListener(new ActionListener() { // ��ư ������
			@Override
			public void actionPerformed(ActionEvent e) { // ������ �۵�
				if (0 != model.getRowCount()) { // �ֹ����̺� �׸��� ������
					for (int idx = 0; idx < model.getRowCount(); idx++) { // �ֹ����̺� ��� ������ ����
						if (name.equals(model.getValueAt(idx, 0))) { // �ֹ����̺� �ִ� ���� ��ǰ��
							int resCnt = (int) model.getValueAt(idx, 1); // ������ res�� ����
							model.setValueAt(++resCnt, idx, 1); // �޾ƿ� ������ 1�� ���Ѱ��� �ٽ� ����.
							System.out.println("�̸� : " + model.getValueAt(idx, 0) + "���� : " + model.getValueAt(idx, 1));
							return;
						} // << �ֹ�����Ʈ�� ������ư�� �����̸� ����++
					}
				}
				model.addRow(new Object[] { name, 1 }); // ��ư ������ �ֹ����̺� ��ư�̸� ������ ������ �̸��� ����(1) �׸� �߰�
			}
		});
		return jb; // ���� ��ư ����
	}

	class JPanelParent extends JPanel { // ī�巹�̾ƿ��� ���� �θ��г�

		JPanel menu; // �̸��� �޴�

		public JPanel getPanel() {
			return this.menu; // ������� �޴��г� ����
		}

	}

	class Menu01 extends JPanelParent { // �޴�1 Ŭ����

		// JPanel menu01;

		public Menu01() { // �޴�1 ������

			super.menu = new JPanel(); // Ŀ�� �޴��� �г� / �θ��гο��� ��ӹ��� �г�, �θ��гο� �� �ִ´�.

			// menu.setBounds(25, 200, 1000, 750);
			menu.setLayout(null); // �¹ٿ��� �� �����Ŵϱ� ���̾ƿ� ���� ����
			// add(menu01);

			JLabel Americano = new JLabel(new ImageIcon("projectpicture/�Ƹ޸�ī��.png")); // �Ƹ޸�ī�� ��
			Americano.setBounds(50, 50, 100, 130); // �Ƹ޸�ī�� �� ��ġ����
			menu.add(Americano); // �Ƹ޸�ī�� �� �θ��гο� �߰�
			Americano.setVisible(true); // �� ������!
			// menu.setBackground(new Color(255, 0, 0, 0));
			menu.setBackground(Color.WHITE);
			menu.add(makeBtn("�Ƹ޸�ī��M", 270, 50, 110, 60));
			menu.add(makeBtn("�Ƹ޸�ī��XL", 270, 120, 110, 60));
			menu.add(makeBtn("ī���M", 270, 190, 110, 60));
			menu.add(makeBtn("ī���XL", 270, 260, 110, 60));
			menu.add(makeBtn("īǪġ��M", 270, 330, 110, 60));
			menu.add(makeBtn("īǪġ��XL", 270, 400, 110, 60));

			JLabel Caffeelatte = new JLabel(new ImageIcon("projectpicture/ī���.png")); // ī��� ��
			Caffeelatte.setBounds(50, 195, 100, 130);
			Caffeelatte.setBackground(Color.green);
			menu.add(Caffeelatte);
			Caffeelatte.setVisible(true);

			JLabel Cafuchino = new JLabel(new ImageIcon("projectpicture/īǪġ��.png")); // īǪġ�� ��
			Cafuchino.setBackground(Color.red);
			Cafuchino.setBounds(50, 335, 100, 130);
			menu.add(Cafuchino);
			Cafuchino.setVisible(true);

			menu.add(makeBtn("ī���īM", 720, 50, 110, 60));
			menu.add(makeBtn("ī���īXL", 720, 120, 110, 60));
			menu.add(makeBtn("�ٴҶ��M", 720, 190, 110, 60));
			menu.add(makeBtn("�ٴҶ��XL", 720, 260, 110, 60));
			menu.add(makeBtn("ī����M", 720, 330, 110, 60));
			menu.add(makeBtn("ī����XL", 720, 400, 110, 60));

			JLabel Caffeemocca = new JLabel(new ImageIcon("projectpicture/ī���ī.png")); // ī���ī ��
			Caffeemocca.setBounds(500, 50, 100, 130);
			menu.add(Caffeemocca);
			Caffeemocca.setVisible(true);

			JLabel Banillalattee = new JLabel(new ImageIcon("projectpicture/�ٴҶ��.png")); // �ٴҶ�� ��
			Banillalattee.setBounds(500, 195, 100, 130);
			menu.add(Banillalattee);
			Banillalattee.setVisible(true);

			JLabel Caramel = new JLabel(new ImageIcon("projectpicture/ī��ḶŰ�ƶ�.png")); // ī��ḶŰ�ƶ� ��
			Caramel.setBounds(500, 335, 100, 130);
			menu.add(Caramel);
			Caramel.setVisible(true);

			menu.setName("Ŀ��");
			menu.setVisible(true);

		}

	}

	class Menu02 extends JPanelParent {

		// JPanel menu02;

		public Menu02() {
			super.menu = new JPanel();
			menu = new JPanel(); // �������ֽ� 1 �޴� �г�
			menu.setLayout(null);
			menu.setBackground(Color.lightGray);

			JLabel juice1 = new JLabel();
			juice1.setBounds(50, 50, 700, 100);
			juice1.setVisible(true);
			menu.add(juice1);
			// menu.setBackground(new Color(255, 0, 0, 0));
			menu.setBackground(Color.WHITE);
			menu.add(makeBtn("�ٳ���M", 270, 50, 110, 60));
			menu.add(makeBtn("�ٳ���XL", 270, 120, 110, 60));
			menu.add(makeBtn("ȫ��M", 270, 190, 110, 60));
			menu.add(makeBtn("ȫ��XL", 270, 260, 110, 60));
			menu.add(makeBtn("���ξ���M", 270, 330, 110, 60));
			menu.add(makeBtn("���ξ���XL", 270, 400, 110, 60));

			JLabel Banana = new JLabel(new ImageIcon("projectpicture/�ٳ���.png")); // �ٳ��� ��
			Banana.setBounds(50, 050, 100, 130);
			menu.add(Banana);
			Banana.setVisible(true);

			JLabel hongshi = new JLabel(new ImageIcon("projectpicture/ȫ��.png")); // ȫ�� ��
			hongshi.setBounds(50, 195, 100, 130);
			menu.add(hongshi);
			hongshi.setVisible(true);

			JLabel fineapple = new JLabel(new ImageIcon("projectpicture/���ξ���.png")); // ���ξ��� ��
			fineapple.setBounds(50, 335, 100, 130);
			menu.add(fineapple);
			fineapple.setVisible(true);

			JLabel choccobanana = new JLabel(new ImageIcon("projectpicture/���ڹٳ���.png")); // ���ڹٳ��� ��
			choccobanana.setBounds(500, 50, 100, 130);
			menu.add(choccobanana);
			choccobanana.setVisible(true);

			JLabel kiwi = new JLabel(new ImageIcon("projectpicture/Ű��.png")); // Ű�� ��
			kiwi.setBounds(500, 195, 100, 130);
			menu.add(kiwi);
			kiwi.setVisible(true);
			JLabel tomato = new JLabel(new ImageIcon("projectpicture/�丶��.png")); // �丶�� ��
			tomato.setBounds(500, 335, 100, 130);
			menu.add(tomato);
			tomato.setVisible(true);

			menu.add(makeBtn("���ڹٳ���M", 720, 50, 110, 60));
			menu.add(makeBtn("���ڹٳ���XL", 720, 120, 110, 60));
			menu.add(makeBtn("Ű��M", 720, 190, 110, 60));
			menu.add(makeBtn("Ű��XL", 720, 260, 110, 60));
			menu.add(makeBtn("�丶��M", 720, 330, 110, 60));
			menu.add(makeBtn("�丶��XL", 720, 400, 110, 60));
			menu.setName("�������ֽ�1");
			menu.setVisible(true);
		}

	}

	class Menu03 extends JPanelParent {

		public Menu03() {

			super.menu = new JPanel(); // �������ֽ� 2 �޴� �г�
			menu.setBounds(25, 200, 1000, 750);
			menu.setLayout(null);
			menu.setBackground(Color.pink);
			// add(menu);

			JLabel juice2 = new JLabel();
			juice2.setBounds(50, 50, 700, 100);
			juice2.setVisible(true);
			menu.add(juice2);

			JLabel ddalba = new JLabel(new ImageIcon("projectpicture/����ٳ���.png")); // ���� ��
			ddalba.setBounds(50, 65, 100, 110);
			menu.add(ddalba);
			// menu.setBackground(new Color(255, 0, 0, 0));
			menu.setBackground(Color.WHITE);

			/////////////////////////////////////////// ī�װ� �гο� �̸�

			menu.setName("�������ֽ�2");

			menu.add(makeBtn("����M", 270, 050, 110, 60));
			menu.add(makeBtn("����XL", 270, 120, 110, 60));
			menu.add(makeBtn("������M", 270, 190, 110, 60));
			menu.add(makeBtn("������XL", 270, 260, 110, 60));
			menu.add(makeBtn("����M", 270, 330, 110, 60));
			menu.add(makeBtn("����XL", 270, 400, 110, 60));

			JLabel orange = new JLabel(new ImageIcon("projectpicture/������.png")); // ������ ��
			orange.setBounds(50, 205, 100, 110);
			menu.add(orange);

			JLabel manggo = new JLabel(new ImageIcon("projectpicture/����.png")); // ���� ��
			manggo.setBounds(50, 345, 100, 110);
			menu.add(manggo);

			JLabel strawberry = new JLabel(new ImageIcon("projectpicture/����.png")); // ���� ��
			strawberry.setBounds(500, 65, 100, 110);
			menu.add(strawberry);

			menu.add(makeBtn("������M", 720, 50, 110, 60));
			menu.add(makeBtn("������XL", 720, 120, 110, 60));
			menu.add(makeBtn("����M", 720, 190, 110, 60));
			menu.add(makeBtn("����XL", 720, 260, 110, 60));
			menu.add(makeBtn("�ڸ�M", 720, 330, 110, 60));
			menu.add(makeBtn("�ڸ�XL", 720, 400, 110, 60));

			ImageIcon mb = new ImageIcon("projectpicture/����.png");
			JLabel mangba = new JLabel(mb); // ���� ��
			mangba.setBounds(500, 205, 100, 110);
			menu.add(mangba);

			ImageIcon jm = new ImageIcon("projectpicture/�ڸ�.png");
			JLabel jamong = new JLabel(jm); // �ڸ� ��
			jamong.setBounds(500, 345, 100, 110);
			menu.add(jamong);

			menu.setVisible(true);
			ddalba.setVisible(true);
			orange.setVisible(true);
			manggo.setVisible(true);
			strawberry.setVisible(true);
			mangba.setVisible(true);
			jamong.setVisible(true);

		}

	}

	public MainMenu() {
		ImageIcon background = new ImageIcon("projectpicture/background.GIF");

		backPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(background.getImage(), 0, 0, this);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		scrollPane = new JScrollPane(backPanel);
		setContentPane(backPanel);

		setBounds(0, 0, 1500, 1000);
		// setBackground(Color.white);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		/* CardLyaout �����Ѵ� */

		basePanel = new JPanel();
		basePanel.setBounds(25, 400, 1000, 650);
		add(basePanel);
		basePanel.setLayout(cardLayout);
		basePanel.setBackground(new Color(255, 0, 0, 0));
		Menu01 menu01 = new Menu01();
		Menu02 menu02 = new Menu02();
		Menu03 menu03 = new Menu03();
		menu01.setBackground(new Color(255, 0, 0, 0));
		menu02.setBackground(new Color(255, 0, 0, 0));
		menu03.setBackground(new Color(255, 0, 0, 0));

		// basePanel.add("Menu01", new TestPanel(Color.BLACK).getPanel());
		// basePanel.add("Menu02", new TestPanel(Color.WHITE).getPanel());

		basePanel.add("Menu01", menu01.getPanel());
		basePanel.add("Menu02", menu02.getPanel());
		basePanel.add("Menu03", menu03.getPanel());

		// ************** NENU 1

		// Menu03 menu1 = new Menu03();
		// add(menu1.getPanel());

		/* CardLyaout ������ */

		JPanel cart = new JPanel(); // ī�װ� �г�
		cart.setBounds(500, 70, 500, 100);
		add(cart);
		cart.setLayout(new GridLayout(1, 3, 10, 5));

		cart.setBackground(new Color(255, 0, 0, 0));

		String[] ct = { "Ŀ��", "�������ֽ� 1", "�������ֽ� 2" };

		ButtonGroup bg = new ButtonGroup();
		/////////////////////////////////////////////////////// ī�װ� ��ư

		JToggleButton categoryBtn1 = new JToggleButton("" + ct[0]);
		cart.add(categoryBtn1);
		bg.add(categoryBtn1);
		categoryBtn1.setSelected(true);

		JToggleButton categoryBtn2 = new JToggleButton("" + ct[1]);
		cart.add(categoryBtn2);
		bg.add(categoryBtn2);

		JToggleButton categoryBtn3 = new JToggleButton("" + ct[2]);
		cart.add(categoryBtn3);
		bg.add(categoryBtn3);

		categoryBtn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(basePanel, "Menu01");
			}
		});

		categoryBtn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(basePanel, "Menu02");
			}
		});

		categoryBtn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(basePanel, "Menu03");
			}
		});

		ImageIcon logo = new ImageIcon("projectpicture/JIACY2.png");
		JLabel main = new JLabel(logo); // �ΰ�
		main.setBounds(25, 30, 400, 145);
		add(main);

		// �ڵ��� ��ȣ �Է� �ȳ� ��
		phoneComment = new JLabel("�ڵ��� ��ȣ�� �Է����ּ���");
		phoneComment.setBounds(1050, 70, 275, 30);
		phoneComment.setBackground(Color.white);
		add(phoneComment);

		// �ڵ��� ��ȣ �޴� �ؽ�Ʈ �ʵ�
		idtext = new JTextField();
		idtext.setBounds(1050, 70, 275, 100);
		idtext.setBackground(Color.RED);
		idtext.setForeground(Color.WHITE);
		idtext.setFont(new Font("���� ���", Font.ITALIC | Font.BOLD, 40));
		add(idtext);
		idtext.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (!((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || (e.getKeyChar() == KeyEvent.VK_BACK_SPACE)
						|| (e.getKeyChar() == KeyEvent.VK_ENTER))) {
					JOptionPane.showMessageDialog(null, "���ڸ� �Է��� �ֽñ� �ٶ��ϴ�.");
					e.consume();
					return;
				}
			}
		});

		JPanel pay = new JPanel(); // ��� �г�
		pay.setBounds(1050, 620, 400, 130);
		add(pay);
		pay.setLayout(new GridLayout(1, 3, 10, 10));
		pay.setBackground(new Color(255, 0, 0, 0));
		paybuttonCard = new JButton(new ImageIcon("projectpicture/card.png"));
		paybuttonCard.setBackground(Color.white);
		paybuttonCard.setBorderPainted(false);
		pay.add(paybuttonCard);
		paybuttonCard.setVisible(true);
		paybuttonCard.addActionListener(new payCardCashAction("ī��"));
		paybuttonCard.setEnabled(false);

		paybuttonCash = new JButton(new ImageIcon("projectpicture/cash.png"));
		paybuttonCash.setBackground(Color.white);
		paybuttonCash.setBorderPainted(false);
		pay.add(paybuttonCash);
		paybuttonCash.setVisible(true);
		paybuttonCash.addActionListener(new payCardCashAction("����"));
		paybuttonCash.setEnabled(false);

		paybuttonPoint = new JButton(new ImageIcon("projectpicture/point.png"));
		paybuttonPoint.setBackground(Color.white);
		paybuttonPoint.setBorderPainted(false);
		pay.add(paybuttonPoint);
		paybuttonPoint.setVisible(true);
		paybuttonPoint.addActionListener(new payPointAction());
		paybuttonPoint.setEnabled(false);
		ImageIcon payEndBtn = new ImageIcon("projectpicture/payEnd.gif");
		JButton payEnd = new JButton(payEndBtn);
		payEnd.setBounds(1350, 70, 100, 100);
		payEnd.addActionListener(new payAction());
		add(payEnd);

		///////////////////////////////////////////////////////////////////// �ֹ� ���
		orderList = new JTable(model); // ���� �ֹ� ���
		orderList.getTableHeader().setReorderingAllowed(false);
		orderList.getTableHeader().setResizingAllowed(false);
		// orderList.setBounds(1050, 200, 400, 350);
		JScrollPane jp = new JScrollPane(orderList);
		jp.setBounds(1050, 200, 400, 350);
		orderList.setBackground(Color.pink);
		orderList.setFont(new Font("�������", Font.PLAIN, 17));
		// orderList.setLayout(null);
		add(jp);
		// jp.setVisible(true);
		// orderList.setVisible(true);

		// ���� ���� ��ư
		JButton deleteMenu = new JButton("���û���");
		deleteMenu.setBounds(1300, 550, 150, 50);
		deleteMenu.addActionListener(new RemoveAction());
		add(deleteMenu);

		JPanel receiptJp = new JPanel(); // ������ �г�
		receiptJp.setBounds(1050, 800, 270, 100);
		receiptJp.setLayout(null);
		add(receiptJp);
		total = new JLabel("0"); // �󺧷� �ٲ� �� ( ���� �ݾ�)
		total.setBounds(20, 15, 200, 70);
		total.setBackground(Color.black);
		receiptJp.add(total);

		// ������ ��ư
		receiptBtn = new JButton(new ImageIcon("projectpicture/receipt.png"));
		// receiptBtn.setBounds(270, 0, 128, 128);
		receiptBtn.setBounds(1320, 780, 128, 128);

		receiptBtn.setBackground(Color.white);
		receiptBtn.addActionListener(new ReceiptAction());
		receiptBtn.setBorderPainted(false);
		// receiptJp.add(receiptBtn);
		// ��� ��ġ �ٲ����
		add(receiptBtn);
		receiptBtn.setVisible(true);
		setVisible(true);
		cart.setVisible(true);
		main.setVisible(true);
		menu02.setVisible(true);
		menu03.setVisible(true);
		idtext.setVisible(true);
		payEnd.setVisible(true);
		pay.setVisible(true);
		receiptJp.setVisible(true);
		total.setVisible(true);

	}

	public class RemoveAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int row = orderList.getSelectedRow();
			if (row == -1) {
				return;
			}
			model.removeRow(row);
		}
	}

	// ī�����, ���ݰ��� �������� ����
	public class payCardCashAction implements ActionListener {
		int paySum = 0;
		String name;

		public payCardCashAction(String name) {
			this.name = name;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			Cash cs = new Cash(ct, name);

			// paySum = cs.getCashSum();
			if (name.equals("ī��")) { // ī����� ��ư ���ý�
				paySum = cs.getCashSum(); // �ݾ� ������.
				cardSum += paySum; // ī���տ� �߰��Ѵ�.
			} else if (name.equals("����")) {// ���ݰ��� ��ư ������
				paySum = cs.getCashSum(); // �ݾ� ������.
				cashSum += paySum; // �����տ� �߰��Ѵ�.
			} // ����Ʈ������ ���� �ϴϱ� ����.

			System.out.println(name + "ĳ�ý�:" + cashSum + "ī���:" + cardSum + "����Ʈ��:" + pointSum + "��ĳ�ý�:" + paySum);
			total.setText("" + ct.total); // �Ѿ׿� �����ݿ��� �ݾ��� ����.
			// ct.
			if (ct.total == 0) { // ���� �Ϸ��
				total.setText("�����Ϸ�"); // �Ѿ� >> "�����Ϸ�"
				receiptBtn.setEnabled(true); // ���� �������� ������ ��ư Ȱ��ȭ
				paybuttonCash.setEnabled(false);
				paybuttonCard.setEnabled(false);
			}
		}
	}

	// ����ư
	public class payAction implements ActionListener {
		@Override // ��ư�� ������ �ֹ������ �ֹ��� �ѹ��� DB ���ٿͼ� total�� ������Ʈ
		public void actionPerformed(ActionEvent e) {
			if (idtext.getText().length() == 11) {
				if (idtext.getText().length() != 0) {
					if (!idtext.getText().substring(0, 3).equals("010")) {
						JOptionPane.showMessageDialog(null, "010���� �����ؾ��մϴ�.");
					} else {
						ct.order(model);
						// ���̺� ������ �ּ��� �޼ҵ忡 �Ѱ���
						total.setText("" + ct.total);
						// �Ѿ� �ؽ�Ʈ�ʵ忡 �ּ���� ���� �Ѿ��� ���
						receiptBtn.setEnabled(false);
						paybuttonCash.setEnabled(true);
						paybuttonCard.setEnabled(true);
//						paybuttonPoint.setEnabled(true);
						if (idtext.getText().equals("")) {
							paybuttonPoint.setEnabled(false);
						} else {// ��ȯ����. ��Ƴ���
							paybuttonPoint.setEnabled(true);
						} // ��.
					}
				}
			} else if (idtext.getText().length() == 0) {
				ct.order(model);
				// ���̺� ������ �ּ��� �޼ҵ忡 �Ѱ���
				total.setText("" + ct.total);
				// �Ѿ� �ؽ�Ʈ�ʵ忡 �ּ���� ���� �Ѿ��� ���
				receiptBtn.setEnabled(false);
				paybuttonCash.setEnabled(true);
				paybuttonCard.setEnabled(true);
				if (idtext.getText().equals("")) {
					paybuttonPoint.setEnabled(false);
				} else {// ��ȯ����. ��Ƴ���
					paybuttonPoint.setEnabled(true);
				} // ��.
			} else {
				JOptionPane.showMessageDialog(null, "�ùٸ� ������ �ڵ��� ��ȣ�� �Է����ֽʽÿ�");
				idtext.setText("");
			}
		}
	}

	// ����� ���� ��ư ������ ���� // ��ȯ����.
	public class payPointAction implements ActionListener {
		int paySum = 0;

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(idtext.getText() + "����.��������Ʈ�׼�");
			Membership ms = new Membership(ct);
			paySum = ms.getPointSum(); // �ݾ� ������.
			pointSum += paySum; // ����Ʈ�տ� �߰��Ѵ�.
			System.out.println("ĳ�ý�:" + cashSum + "ī���:" + cardSum + "����Ʈ��:" + pointSum + "��ĳ�ý�:" + paySum);
			total.setText("" + ct.total); // �Ѿ׿� �����ݿ��� �ݾ��� ����.
			if (ct.total == 0) { // ���� �Ϸ��
				total.setText("�����Ϸ�"); // �Ѿ� >> "�����Ϸ�"
				receiptBtn.setEnabled(true); // ���� �������� ������ ��ư Ȱ��ȭ
			}
		}
	}// ��.

	// ������ ��� ��ư
	public class ReceiptAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// ���� �޴� ��ü��
			Receipt rc = new Receipt();
			model.setNumRows(0);
			idtext.setText("");
			receiptBtn.setEnabled(false);

		}
	}

	public static void main(String[] args) {
		Play("music/goodMusic.wav");
		new MainMenu();
	}

	void InitSum() {
		cashSum = 0;
		cardSum = 0;
		pointSum = 0;
	}

	public static void Play(String fileName) {
		try {
			File file = new File(fileName);
			AudioInputStream ais = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.stop();
			clip.open(ais);
			clip.start();
		} catch (Exception ex) {
		}
	}

	class Receipt extends JFrame {
		JDialog receiptWindow;
		JPanel receiptPanel;
		JTextArea jt = new JTextArea();
		JScrollPane jsp = new JScrollPane(jt);
		JLabel receiptTotal, title1, title2, bottom;
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		public Receipt() {
			super("JIACY ������");
			setBounds(200, 200, 350, 600);
			// �г�
			receiptPanel = new JPanel();
			receiptPanel.setLayout(null);
			receiptPanel.setBounds(0, 0, 350, 500);
			add(receiptPanel);
			title1 = new JLabel("JIACY �̽�����"); // ���� �۾��� �ٲٰ� ��� �����ϱ�
			title1.setBounds(0, 10, 350, 20);
			receiptPanel.add(title1);

			title2 = new JLabel("Casher: �輺��. �赿��. ����ȯ. ������. "); // ���� �۾��� �ٲٰ� ��� �����ϱ�
			title2.setBounds(0, 30, 350, 20);
			receiptPanel.add(title2);
			for (int i = 0; i < orderList.getRowCount(); i++) {
				jt.append("�̸� : " + orderList.getValueAt(i, 0) + "   \t���� : " + orderList.getValueAt(i, 1) + "\n");
				// ��ȯ����
				ct.updateCnt("" + orderList.getValueAt(i, 0), (int) orderList.getValueAt(i, 1));
			}
			jt.append("_________________________________" + "\n");
			jt.append("���� : " + cashSum + "\n");
			jt.append("ī�� : " + cardSum + "\n");
			jt.append("����Ʈ : " + pointSum + "\n");
			ct.payKind(cashSum, cardSum, pointSum);
			jt.setEditable(false);
			jt.setForeground(Color.BLUE);
			jt.setFont(new Font("�������", Font.BOLD, 17));

			
			receiptPanel.add(jsp);
			jsp.setBounds(0, 80, 330, 360);
			jt.setBackground(Color.pink);

			bottom = new JLabel("JIACY_wifi:��_5G"); // ���� �۾��� �ٲٰ� ��� �����ϱ�
			bottom.setBounds(0, 450, 350, 20);
			receiptPanel.add(bottom);

			bottom = new JLabel("JIACY_wifi_pw:�����߱�!"); // ���� �۾��� �ٲٰ� ��� �����ϱ�
			bottom.setBounds(0, 470, 350, 20);
			receiptPanel.add(bottom);
			// �Ѱ踦 �����ش�
			int total = cashSum + cardSum;
			receiptTotal = new JLabel("�Ѿ�:" + total); // ���� �۾��� �ٲٰ� ��� �����ϱ�
			receiptTotal.setBounds(0, 500, 350, 100);
			receiptPanel.add(receiptTotal);

			int rowNum = model.getRowCount();
			for (int idx = 0; idx < rowNum; idx++) {
				String first = (String) model.getValueAt(idx, 0);
				Integer second = (Integer) model.getValueAt(idx, 1);
				model.addRow(new Object[] { first, second });
			}
			InitSum();
			setVisible(true);
			System.out.println("cashSum:" + cashSum);
			System.out.println("cardSum:" + cardSum);
			System.out.println("pointSum:" + pointSum);
		}
	}
}