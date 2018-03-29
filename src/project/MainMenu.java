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
	Customer ct = new Customer(); // 손님
	static JTextField idtext; // 아이디 입력필드
	JScrollPane scrollPane; // 스크롤팬
	JPanel backPanel; // 배경이미지를 입힐 패널

	CardLayout cardLayout = new CardLayout(); // 메뉴를 넣을 카드 레이아웃
	JPanel basePanel; // 메뉴가 들어갈 패널

	String columnNames[] = { "메뉴이름", "수량" }; // 주문테이블 항목이름
	JTable orderList; // 주문테이블

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
	JButton receiptBtn; // 영수증 버튼
	JLabel phoneComment; // "휴대폰 번호를 입력해 주세요" / 값 없을때 보임

	JLabel total; // 총액 보이는 라벨
	String memphone; // 폰번호
	int cashSum, cardSum, pointSum; // 캐시썸 / 카드썸 / 현금, 카드별 결제총액

	////////////////////////////////////////////////////////////////
	JButton makeBtn(String name, int x, int y, int w, int h) { // 상품별 버튼을 좀 더 쉽게 만들기 위해..
		JButton jb = new JButton(name); // 버튼이름 받아넣음
		jb.setBounds(x, y, w, h); // 버튼위치 받아넣음
		jb.setBackground(Color.white); // 버튼색은 하얀색
		jb.setVisible(true); // 버튼 보여라
		jb.addActionListener(new ActionListener() { // 버튼 리스너
			@Override
			public void actionPerformed(ActionEvent e) { // 누르면 작동
				if (0 != model.getRowCount()) { // 주문테이블에 항목이 있으면
					for (int idx = 0; idx < model.getRowCount(); idx++) { // 주문테이블에 모든 내역에 대해
						if (name.equals(model.getValueAt(idx, 0))) { // 주문테이블에 있는 같은 상품의
							int resCnt = (int) model.getValueAt(idx, 1); // 수량을 res에 넣음
							model.setValueAt(++resCnt, idx, 1); // 받아온 수량에 1을 더한값을 다시 넣음.
							System.out.println("이름 : " + model.getValueAt(idx, 0) + "수량 : " + model.getValueAt(idx, 1));
							return;
						} // << 주문리스트에 누른버튼과 같은이름 수량++
					}
				}
				model.addRow(new Object[] { name, 1 }); // 버튼 누르면 주문테이블에 버튼이름 같은거 없을때 이름과 수량(1) 항목 추가
			}
		});
		return jb; // 만든 버튼 리턴
	}

	class JPanelParent extends JPanel { // 카드레이아웃에 넣을 부모패널

		JPanel menu; // 이름은 메뉴

		public JPanel getPanel() {
			return this.menu; // 만들어진 메뉴패널 리턴
		}

	}

	class Menu01 extends JPanelParent { // 메뉴1 클래스

		// JPanel menu01;

		public Menu01() { // 메뉴1 생성자

			super.menu = new JPanel(); // 커피 메뉴판 패널 / 부모패널에서 상속받은 패널, 부모패널에 다 넣는다.

			// menu.setBounds(25, 200, 1000, 750);
			menu.setLayout(null); // 셋바운드로 다 찍을거니까 레이아웃 따위 없음
			// add(menu01);

			JLabel Americano = new JLabel(new ImageIcon("projectpicture/아메리카노.png")); // 아메리카노 라벨
			Americano.setBounds(50, 50, 100, 130); // 아메리카노 라벨 위치지정
			menu.add(Americano); // 아메리카노 라벨 부모패널에 추가
			Americano.setVisible(true); // 라벨 보여라!
			// menu.setBackground(new Color(255, 0, 0, 0));
			menu.setBackground(Color.WHITE);
			menu.add(makeBtn("아메리카노M", 270, 50, 110, 60));
			menu.add(makeBtn("아메리카노XL", 270, 120, 110, 60));
			menu.add(makeBtn("카페라떼M", 270, 190, 110, 60));
			menu.add(makeBtn("카페라떼XL", 270, 260, 110, 60));
			menu.add(makeBtn("카푸치노M", 270, 330, 110, 60));
			menu.add(makeBtn("카푸치노XL", 270, 400, 110, 60));

			JLabel Caffeelatte = new JLabel(new ImageIcon("projectpicture/카페라떼.png")); // 카페라떼 라벨
			Caffeelatte.setBounds(50, 195, 100, 130);
			Caffeelatte.setBackground(Color.green);
			menu.add(Caffeelatte);
			Caffeelatte.setVisible(true);

			JLabel Cafuchino = new JLabel(new ImageIcon("projectpicture/카푸치노.png")); // 카푸치노 라벨
			Cafuchino.setBackground(Color.red);
			Cafuchino.setBounds(50, 335, 100, 130);
			menu.add(Cafuchino);
			Cafuchino.setVisible(true);

			menu.add(makeBtn("카페모카M", 720, 50, 110, 60));
			menu.add(makeBtn("카페모카XL", 720, 120, 110, 60));
			menu.add(makeBtn("바닐라라떼M", 720, 190, 110, 60));
			menu.add(makeBtn("바닐라라떼XL", 720, 260, 110, 60));
			menu.add(makeBtn("카라멜라떼M", 720, 330, 110, 60));
			menu.add(makeBtn("카라멜라떼XL", 720, 400, 110, 60));

			JLabel Caffeemocca = new JLabel(new ImageIcon("projectpicture/카페모카.png")); // 카페모카 라벨
			Caffeemocca.setBounds(500, 50, 100, 130);
			menu.add(Caffeemocca);
			Caffeemocca.setVisible(true);

			JLabel Banillalattee = new JLabel(new ImageIcon("projectpicture/바닐라라떼.png")); // 바닐라라떼 라벨
			Banillalattee.setBounds(500, 195, 100, 130);
			menu.add(Banillalattee);
			Banillalattee.setVisible(true);

			JLabel Caramel = new JLabel(new ImageIcon("projectpicture/카라멜마키아또.png")); // 카라멜마키아또 라벨
			Caramel.setBounds(500, 335, 100, 130);
			menu.add(Caramel);
			Caramel.setVisible(true);

			menu.setName("커피");
			menu.setVisible(true);

		}

	}

	class Menu02 extends JPanelParent {

		// JPanel menu02;

		public Menu02() {
			super.menu = new JPanel();
			menu = new JPanel(); // 생과일주스 1 메뉴 패널
			menu.setLayout(null);
			menu.setBackground(Color.lightGray);

			JLabel juice1 = new JLabel();
			juice1.setBounds(50, 50, 700, 100);
			juice1.setVisible(true);
			menu.add(juice1);
			// menu.setBackground(new Color(255, 0, 0, 0));
			menu.setBackground(Color.WHITE);
			menu.add(makeBtn("바나나M", 270, 50, 110, 60));
			menu.add(makeBtn("바나나XL", 270, 120, 110, 60));
			menu.add(makeBtn("홍시M", 270, 190, 110, 60));
			menu.add(makeBtn("홍시XL", 270, 260, 110, 60));
			menu.add(makeBtn("파인애플M", 270, 330, 110, 60));
			menu.add(makeBtn("파인애플XL", 270, 400, 110, 60));

			JLabel Banana = new JLabel(new ImageIcon("projectpicture/바나나.png")); // 바나나 라벨
			Banana.setBounds(50, 050, 100, 130);
			menu.add(Banana);
			Banana.setVisible(true);

			JLabel hongshi = new JLabel(new ImageIcon("projectpicture/홍시.png")); // 홍시 라벨
			hongshi.setBounds(50, 195, 100, 130);
			menu.add(hongshi);
			hongshi.setVisible(true);

			JLabel fineapple = new JLabel(new ImageIcon("projectpicture/파인애플.png")); // 파인애플 라벨
			fineapple.setBounds(50, 335, 100, 130);
			menu.add(fineapple);
			fineapple.setVisible(true);

			JLabel choccobanana = new JLabel(new ImageIcon("projectpicture/초코바나나.png")); // 초코바나나 라벨
			choccobanana.setBounds(500, 50, 100, 130);
			menu.add(choccobanana);
			choccobanana.setVisible(true);

			JLabel kiwi = new JLabel(new ImageIcon("projectpicture/키위.png")); // 키위 라벨
			kiwi.setBounds(500, 195, 100, 130);
			menu.add(kiwi);
			kiwi.setVisible(true);
			JLabel tomato = new JLabel(new ImageIcon("projectpicture/토마토.png")); // 토마토 라벨
			tomato.setBounds(500, 335, 100, 130);
			menu.add(tomato);
			tomato.setVisible(true);

			menu.add(makeBtn("초코바나나M", 720, 50, 110, 60));
			menu.add(makeBtn("초코바나나XL", 720, 120, 110, 60));
			menu.add(makeBtn("키위M", 720, 190, 110, 60));
			menu.add(makeBtn("키위XL", 720, 260, 110, 60));
			menu.add(makeBtn("토마토M", 720, 330, 110, 60));
			menu.add(makeBtn("토마토XL", 720, 400, 110, 60));
			menu.setName("생과일주스1");
			menu.setVisible(true);
		}

	}

	class Menu03 extends JPanelParent {

		public Menu03() {

			super.menu = new JPanel(); // 생과일주스 2 메뉴 패널
			menu.setBounds(25, 200, 1000, 750);
			menu.setLayout(null);
			menu.setBackground(Color.pink);
			// add(menu);

			JLabel juice2 = new JLabel();
			juice2.setBounds(50, 50, 700, 100);
			juice2.setVisible(true);
			menu.add(juice2);

			JLabel ddalba = new JLabel(new ImageIcon("projectpicture/딸기바나나.png")); // 딸바 라벨
			ddalba.setBounds(50, 65, 100, 110);
			menu.add(ddalba);
			// menu.setBackground(new Color(255, 0, 0, 0));
			menu.setBackground(Color.WHITE);

			/////////////////////////////////////////// 카테고리 패널에 이름

			menu.setName("생과일주스2");

			menu.add(makeBtn("딸바M", 270, 050, 110, 60));
			menu.add(makeBtn("딸바XL", 270, 120, 110, 60));
			menu.add(makeBtn("오렌지M", 270, 190, 110, 60));
			menu.add(makeBtn("오렌지XL", 270, 260, 110, 60));
			menu.add(makeBtn("망고M", 270, 330, 110, 60));
			menu.add(makeBtn("망고XL", 270, 400, 110, 60));

			JLabel orange = new JLabel(new ImageIcon("projectpicture/오렌지.png")); // 오렌지 라벨
			orange.setBounds(50, 205, 100, 110);
			menu.add(orange);

			JLabel manggo = new JLabel(new ImageIcon("projectpicture/망바.png")); // 망고 라벨
			manggo.setBounds(50, 345, 100, 110);
			menu.add(manggo);

			JLabel strawberry = new JLabel(new ImageIcon("projectpicture/딸기.png")); // 딸기 라벨
			strawberry.setBounds(500, 65, 100, 110);
			menu.add(strawberry);

			menu.add(makeBtn("생딸기M", 720, 50, 110, 60));
			menu.add(makeBtn("생딸기XL", 720, 120, 110, 60));
			menu.add(makeBtn("망바M", 720, 190, 110, 60));
			menu.add(makeBtn("망바XL", 720, 260, 110, 60));
			menu.add(makeBtn("자몽M", 720, 330, 110, 60));
			menu.add(makeBtn("자몽XL", 720, 400, 110, 60));

			ImageIcon mb = new ImageIcon("projectpicture/망바.png");
			JLabel mangba = new JLabel(mb); // 망바 라벨
			mangba.setBounds(500, 205, 100, 110);
			menu.add(mangba);

			ImageIcon jm = new ImageIcon("projectpicture/자몽.png");
			JLabel jamong = new JLabel(jm); // 자몽 라벨
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
		/* CardLyaout 시작한당 */

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

		/* CardLyaout 끝낸당 */

		JPanel cart = new JPanel(); // 카테고리 패널
		cart.setBounds(500, 70, 500, 100);
		add(cart);
		cart.setLayout(new GridLayout(1, 3, 10, 5));

		cart.setBackground(new Color(255, 0, 0, 0));

		String[] ct = { "커피", "생과일주스 1", "생과일주스 2" };

		ButtonGroup bg = new ButtonGroup();
		/////////////////////////////////////////////////////// 카테고리 버튼

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
		JLabel main = new JLabel(logo); // 로고
		main.setBounds(25, 30, 400, 145);
		add(main);

		// 핸드폰 번호 입력 안내 라벨
		phoneComment = new JLabel("핸드폰 번호를 입력해주세요");
		phoneComment.setBounds(1050, 70, 275, 30);
		phoneComment.setBackground(Color.white);
		add(phoneComment);

		// 핸드폰 번호 받는 텍스트 필드
		idtext = new JTextField();
		idtext.setBounds(1050, 70, 275, 100);
		idtext.setBackground(Color.RED);
		idtext.setForeground(Color.WHITE);
		idtext.setFont(new Font("맑은 고딕", Font.ITALIC | Font.BOLD, 40));
		add(idtext);
		idtext.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (!((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || (e.getKeyChar() == KeyEvent.VK_BACK_SPACE)
						|| (e.getKeyChar() == KeyEvent.VK_ENTER))) {
					JOptionPane.showMessageDialog(null, "숫자만 입력해 주시기 바랍니다.");
					e.consume();
					return;
				}
			}
		});

		JPanel pay = new JPanel(); // 계산 패널
		pay.setBounds(1050, 620, 400, 130);
		add(pay);
		pay.setLayout(new GridLayout(1, 3, 10, 10));
		pay.setBackground(new Color(255, 0, 0, 0));
		paybuttonCard = new JButton(new ImageIcon("projectpicture/card.png"));
		paybuttonCard.setBackground(Color.white);
		paybuttonCard.setBorderPainted(false);
		pay.add(paybuttonCard);
		paybuttonCard.setVisible(true);
		paybuttonCard.addActionListener(new payCardCashAction("카드"));
		paybuttonCard.setEnabled(false);

		paybuttonCash = new JButton(new ImageIcon("projectpicture/cash.png"));
		paybuttonCash.setBackground(Color.white);
		paybuttonCash.setBorderPainted(false);
		pay.add(paybuttonCash);
		paybuttonCash.setVisible(true);
		paybuttonCash.addActionListener(new payCardCashAction("현금"));
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

		///////////////////////////////////////////////////////////////////// 주문 목록
		orderList = new JTable(model); // 우측 주문 목록
		orderList.getTableHeader().setReorderingAllowed(false);
		orderList.getTableHeader().setResizingAllowed(false);
		// orderList.setBounds(1050, 200, 400, 350);
		JScrollPane jp = new JScrollPane(orderList);
		jp.setBounds(1050, 200, 400, 350);
		orderList.setBackground(Color.pink);
		orderList.setFont(new Font("맑은고딕", Font.PLAIN, 17));
		// orderList.setLayout(null);
		add(jp);
		// jp.setVisible(true);
		// orderList.setVisible(true);

		// 선택 삭제 버튼
		JButton deleteMenu = new JButton("선택삭제");
		deleteMenu.setBounds(1300, 550, 150, 50);
		deleteMenu.addActionListener(new RemoveAction());
		add(deleteMenu);

		JPanel receiptJp = new JPanel(); // 영수증 패널
		receiptJp.setBounds(1050, 800, 270, 100);
		receiptJp.setLayout(null);
		add(receiptJp);
		total = new JLabel("0"); // 라벨로 바꿀 것 ( 결제 금액)
		total.setBounds(20, 15, 200, 70);
		total.setBackground(Color.black);
		receiptJp.add(total);

		// 영수증 버튼
		receiptBtn = new JButton(new ImageIcon("projectpicture/receipt.png"));
		// receiptBtn.setBounds(270, 0, 128, 128);
		receiptBtn.setBounds(1320, 780, 128, 128);

		receiptBtn.setBackground(Color.white);
		receiptBtn.addActionListener(new ReceiptAction());
		receiptBtn.setBorderPainted(false);
		// receiptJp.add(receiptBtn);
		// 잠시 위치 바꿔놓음
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

	// 카드결제, 현금결제 눌렀을떄 동작
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
			if (name.equals("카드")) { // 카드결제 버튼 선택시
				paySum = cs.getCashSum(); // 금액 가져옴.
				cardSum += paySum; // 카드합에 추가한다.
			} else if (name.equals("현금")) {// 현금결제 버튼 누르면
				paySum = cs.getCashSum(); // 금액 가져옴.
				cashSum += paySum; // 현금합에 추가한다.
			} // 포인트결제는 따로 하니까 없앰.

			System.out.println(name + "캐시썸:" + cashSum + "카드썸:" + cardSum + "포인트썸:" + pointSum + "겟캐시썸:" + paySum);
			total.setText("" + ct.total); // 총액에 차감반영된 금액을 넣음.
			// ct.
			if (ct.total == 0) { // 결제 완료시
				total.setText("결제완료"); // 총액 >> "결제완료"
				receiptBtn.setEnabled(true); // 결제 끝났으니 영수증 버튼 활성화
				paybuttonCash.setEnabled(false);
				paybuttonCard.setEnabled(false);
			}
		}
	}

	// 계산버튼
	public class payAction implements ActionListener {
		@Override // 버튼을 누르면 주문목록의 주문이 한번에 DB 갔다와서 total에 업데이트
		public void actionPerformed(ActionEvent e) {
			if (idtext.getText().length() == 11) {
				if (idtext.getText().length() != 0) {
					if (!idtext.getText().substring(0, 3).equals("010")) {
						JOptionPane.showMessageDialog(null, "010으로 시작해야합니다.");
					} else {
						ct.order(model);
						// 테이블 내용을 주세요 메소드에 넘겨줌
						total.setText("" + ct.total);
						// 총액 텍스트필드에 주세요로 계산된 총액을 출력
						receiptBtn.setEnabled(false);
						paybuttonCash.setEnabled(true);
						paybuttonCard.setEnabled(true);
//						paybuttonPoint.setEnabled(true);
						if (idtext.getText().equals("")) {
							paybuttonPoint.setEnabled(false);
						} else {// 건환수정. 살아나라
							paybuttonPoint.setEnabled(true);
						} // 끝.
					}
				}
			} else if (idtext.getText().length() == 0) {
				ct.order(model);
				// 테이블 내용을 주세요 메소드에 넘겨줌
				total.setText("" + ct.total);
				// 총액 텍스트필드에 주세요로 계산된 총액을 출력
				receiptBtn.setEnabled(false);
				paybuttonCash.setEnabled(true);
				paybuttonCard.setEnabled(true);
				if (idtext.getText().equals("")) {
					paybuttonPoint.setEnabled(false);
				} else {// 건환수정. 살아나라
					paybuttonPoint.setEnabled(true);
				} // 끝.
			} else {
				JOptionPane.showMessageDialog(null, "올바른 형식의 핸드폰 번호를 입력해주십시오");
				idtext.setText("");
			}
		}
	}

	// 멤버십 결제 버튼 누르면 동작 // 건환수정.
	public class payPointAction implements ActionListener {
		int paySum = 0;

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(idtext.getText() + "메인.페이포인트액션");
			Membership ms = new Membership(ct);
			paySum = ms.getPointSum(); // 금액 가져옴.
			pointSum += paySum; // 포인트합에 추가한다.
			System.out.println("캐시썸:" + cashSum + "카드썸:" + cardSum + "포인트썸:" + pointSum + "겟캐시썸:" + paySum);
			total.setText("" + ct.total); // 총액에 차감반영된 금액을 넣음.
			if (ct.total == 0) { // 결제 완료시
				total.setText("결제완료"); // 총액 >> "결제완료"
				receiptBtn.setEnabled(true); // 글제 끝났으니 영수증 버튼 활성화
			}
		}
	}// 끝.

	// 영수증 출력 버튼
	public class ReceiptAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// 메인 메뉴 객체를
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
			super("JIACY 영수증");
			setBounds(200, 200, 350, 600);
			// 패널
			receiptPanel = new JPanel();
			receiptPanel.setLayout(null);
			receiptPanel.setBounds(0, 0, 350, 500);
			add(receiptPanel);
			title1 = new JLabel("JIACY 이신협점"); // 예쁜 글씨로 바꾸고 가운데 정렬하기
			title1.setBounds(0, 10, 350, 20);
			receiptPanel.add(title1);

			title2 = new JLabel("Casher: 김성광. 김동훈. 류건환. 류지아. "); // 예쁜 글씨로 바꾸고 가운데 정렬하기
			title2.setBounds(0, 30, 350, 20);
			receiptPanel.add(title2);
			for (int i = 0; i < orderList.getRowCount(); i++) {
				jt.append("이름 : " + orderList.getValueAt(i, 0) + "   \t수량 : " + orderList.getValueAt(i, 1) + "\n");
				// 건환수정
				ct.updateCnt("" + orderList.getValueAt(i, 0), (int) orderList.getValueAt(i, 1));
			}
			jt.append("_________________________________" + "\n");
			jt.append("현금 : " + cashSum + "\n");
			jt.append("카드 : " + cardSum + "\n");
			jt.append("포인트 : " + pointSum + "\n");
			ct.payKind(cashSum, cardSum, pointSum);
			jt.setEditable(false);
			jt.setForeground(Color.BLUE);
			jt.setFont(new Font("맑은고딕", Font.BOLD, 17));

			
			receiptPanel.add(jsp);
			jsp.setBounds(0, 80, 330, 360);
			jt.setBackground(Color.pink);

			bottom = new JLabel("JIACY_wifi:쟈씨_5G"); // 예쁜 글씨로 바꾸고 가운데 정렬하기
			bottom.setBounds(0, 450, 350, 20);
			receiptPanel.add(bottom);

			bottom = new JLabel("JIACY_wifi_pw:가랏삐까!"); // 예쁜 글씨로 바꾸고 가운데 정렬하기
			bottom.setBounds(0, 470, 350, 20);
			receiptPanel.add(bottom);
			// 총계를 보여준다
			int total = cashSum + cardSum;
			receiptTotal = new JLabel("총액:" + total); // 예쁜 글씨로 바꾸고 가운데 정렬하기
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