import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

class Element{
	//�߽��� x,y�� ��� �̰��� �߽����� +-���� ���� ǥ���� ���̴�.
	int centerX; // �߽��� X��ǥ
	int centerY; // �߽��� Y��ǥ
	int colorNum;
	Element(int x, int y, int color){
		this.centerX = x;
		this.centerY = y;
		this.colorNum = color;
	}
}

class Shape{
	Element current[] = new Element[4];
	int height = 0; // row
	int width = Reference.formWidth/2; // column
	// x, y�� �׻� ������ ���� �ֻ��
	
	Shape(int shapeNum){
		switch (shapeNum) {
			//0~6 �� �� �� ��  � ����̵� current[0]�� ������(ȸ���� x,y / ���� ���� ������ �ű涧 x,y��ǥ / colorNum ��
			case 0: // �� ���(���η� ��)
				current[0] = new Element(height, width, 0);
				current[1] = new Element(height, width+1, 0);
				current[2] = new Element(height+1, width+1, 0);
				current[3] = new Element(height+2, width+1, 0);
				break;
			case 1: // �� ���
				current[0] = new Element(height, width, 1);
				current[1] = new Element(height+1, width, 1);
				current[2] = new Element(height+1, width, 1);
				current[3] = new Element(height+1, width, 1);
				break;
			case 2: // �� ���
				current[0] = new Element(height, width, 2);
				current[1] = new Element(height+1, width, 2);
				current[2] = new Element(height, width+1, 2);
				current[3] = new Element(height+1, width+1, 2);
				break;
			case 3: // �� ��� (���η� ��)
				current[0] = new Element(height, width, 3);
				current[1] = new Element(height+1, width, 3);
				current[2] = new Element(height+1, width+1, 3);
				current[3] = new Element(height+1, width+2, 3);
				break;
			case 4: // z ���
				current[0] = new Element(height, width, 4);
				current[1] = new Element(height, width+1, 4);
				current[2] = new Element(height+1, width+1, 4);
				current[3] = new Element(height+1, width+2, 4);
				break;
			case 5: // �Ǹ��
				current[0] = new Element(height, width, 5);
				current[1] = new Element(height+1, width-1, 5);
				current[2] = new Element(height+1, width, 5);
				current[3] = new Element(height+1, width+1, 5);
				break;
			case 6: // z' ���
				current[0] = new Element(height, width, 6);
				current[1] = new Element(height, width+1, 6);
				current[2] = new Element(height+1, width, 6);
				current[3] = new Element(height+1, width-1, 6);
				break;
		}
	}

	public Element[] transferArray(){ // ���� ���� �ѹ��� �Է� �޾Ƽ� ���迭�� ������ ��, �׸��� ���� �迭�� �������ش�.
		return current;
	}
}


public class Reference extends JFrame implements KeyListener{
	// 1. GUI ȭ�鱸��
	// 2. GUI �޴� ����
	// 3. Thread
	// 4. �̺�Ʈ ó��
	// 5. ���� ����� -> ���ӵ����� �迭 ����
	// 6. ������ �ۼ�
	static int formHeight = 20;
	static int formWidth = 10;
	JButton b[][];
	
	Color colorBox[] = {Color.red, Color.blue, Color.yellow, Color.gray, Color.pink, Color.green, Color.orange};
	
	public Shape makeShape(int shapeNumber) {
		Shape randomShape = new Shape(shapeNumber);
		
		return randomShape;
	}
	
	public Reference() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu_1 = new JMenu("��������");
		menuBar.add(menu_1);
		JMenu menu_2 = new JMenu("���� �ҷ�����");
		menuBar.add(menu_2);
		JMenu menu_3 = new JMenu("���� ����");
		menuBar.add(menu_3);
		

		setLayout(new GridLayout(formHeight, formWidth));
		setSize(500, 1000);
		b = new JButton[formHeight][formWidth];
		
		for(int row = 0 ; row < formHeight ; row++) {
			for(int col = 0 ; col < formWidth ; col++) {
				b[row][col] = new JButton();
				add(b[row][col]);
				JButton bj = b[row][col];
				}
			}
		setVisible(true);
		
		int shapeNumber = (int)Math.floor(Math.random()*7);// 0~6 
		
		for(int i = 0 ; i < 4 ; i++) {
			Shape randomFigure = makeShape(shapeNumber);
			Element[] eleNew = randomFigure.transferArray();
			JButton jb = b[eleNew[i].centerX][eleNew[i].centerY];
			jb.setBackground(colorBox[shapeNumber]);
		}
		
		
//		for(int row = 0 ; row < formHeight ; row++) {
//			for(int col = 0 ; col < formWidth ; col++) {
//				b[row][col] = new JButton();
//				add(b[row][col]);
//				JButton bj = b[row][col];
//				
//				
//				b[row][col].addActionListener(new ActionListener() {
//					
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						bj.setBackground(Color.DARK_GRAY);
//						
//					}
//				});
//			}
//		}

//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					while(true) {
//						int shapeNumber = (int)Math.floor(Math.random()*7);// 0~6 
//						
//						for(int i = 0 ; i < 4 ; i++) {
//							Shape randomFigure = makeShape(shapeNumber);
//							Element[] eleNew = randomFigure.transferArray();
//							JButton jb = b[eleNew[i].centerX][eleNew[i].centerY];
//							jb.setBackground(colorBox[shapeNumber]);
//						}
//								Thread.sleep(1000);
//								repaint();
//						//�������� �ڵ�
//					}
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//				
//			}
//		}).run();
	}


	public static void main(String[] args) {
		new Reference();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP: // UP
			break;
			
		case KeyEvent.VK_DOWN:
			break;
		
		case KeyEvent.VK_LEFT:
			break;
			
		case KeyEvent.VK_RIGHT:
			break;
			
		case KeyEvent.VK_SPACE:
			break;
		}
		
	repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}

	