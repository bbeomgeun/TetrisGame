import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

class Element{
	//�߽��� x,y�� ��� �̰��� �߽����� +-���� ���� ǥ���� ���̴�.
	int centerHeight; // �߽��� X��ǥ
	int centerWidth; // �߽��� Y��ǥ
	int colorNum;
	Element(int x, int y, int color){
		this.centerHeight = x;
		this.centerWidth = y;
		this.colorNum = color; // ����� �����(���� ��� X)
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
				current[2] = new Element(height+2, width, 1);
				current[3] = new Element(height+3, width, 1);
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
	static int rightDirection = 0;
	static int leftDirection = 1;
	static int downDirection = 2;
	static int rotation = 77;
	
	static boolean needShape = true;
	
	JButton b[][];
	int shapeNumber; // random�Լ��� 0~6���� ���ͼ� makeShape�Լ��� ������ ���
	Shape randomFigure; // makeShape�� �����(Element �迭�� ������ �ִ�)
	Element[] eleNew; // randomFigure�� ���ϰ��� ���� Element �迭
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���� ����
		
		this.addKeyListener(new KeyAdapter() {
			
		});
		
		b = new JButton[formHeight][formWidth];
		
		for(int row = 0 ; row < formHeight ; row++) {
			for(int col = 0 ; col < formWidth ; col++) {
				b[row][col] = new JButton();
				add(b[row][col]);
				JButton bj = b[row][col];
				bj.addKeyListener(this);
				}
			}
		setVisible(true);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while(true) {
						if(needShape) {
							shapeNumber = (int)Math.floor(Math.random()*7);// 0~6 
							randomFigure = makeShape(shapeNumber);
							eleNew = randomFigure.transferArray();
							needShape = false;
						}
						
						// �ӽ� ��� reset �ڵ�
						for(int row = 0 ; row < formHeight ; row++) {
							for(int col = 0 ; col < formWidth ; col++) {
								b[row][col].setBackground(Color.white);
							}
						}// ���� ��Ʈ������ ����� �ڵ� �ʿ� ( �̰��� �� ������� �ƴ϶� 20*10 �迭�� ��Ʈ���������͸� �����صΰ� �װ��� �ҷ����� �������� �ؾ� �ٴڿ� ������ ��Ʈ������ �ҷ��ü��ְڴ�)
						
						// �׸��� �ڵ�
						for(int i = 0 ; i < 4 ; i++) {
							JButton jb = b[eleNew[i].centerHeight][eleNew[i].centerWidth];
							jb.setBackground(colorBox[shapeNumber]);
						}
						eleNew = moveShape(eleNew, downDirection);
						
						Thread.sleep(500);
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				
			}
		}).run();
	}


	public static void main(String[] args) {
		new Reference();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP: // UP
			break;
			
		case KeyEvent.VK_DOWN:
			moveShape(eleNew, downDirection);
			break;
		
		case KeyEvent.VK_LEFT:
			moveShape(eleNew, leftDirection);
			break;
			
		case KeyEvent.VK_RIGHT:
			moveShape(eleNew, rightDirection);
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
	
	public void makingNewShape() {
		shapeNumber = (int)Math.floor(Math.random()*7);// 0~6 
		randomFigure = makeShape(shapeNumber);
	}
	
	public Element[] moveShape(Element[] currentElement, int direction) {
		// 1. �� �����̴� ��� ( x�� y�� jFrame�� ����� ���)
		// 2. �����̴� ��� (��ǥ�� �����ش�)
		boolean flag = true;
		
		Element [] updateElement = new Element[4];
		for(int i = 0 ; i < 4 ; i++) {
			updateElement[i] = new Element(0,0,0);
		}
		switch (direction) {
		case 0: // right
			for(int i = 0 ; i < 4 ; i++) {
				int tempHeight = currentElement[i].centerHeight; // ���̴� �״��
				int tempWidth = currentElement[i].centerWidth + 1; // ������ �̵��̹Ƿ� ������ǥ + 1
				// return�� �迭 �����صα�
				updateElement[i].centerHeight = tempHeight;
				updateElement[i].centerWidth = tempWidth;

				if(tempWidth > formWidth - 1) { // ������ �̵��̹Ƿ� Width�� ��谪�� �Ѿ�� flag�� �����Ѵ�.
					flag = false;
					break;
				}
			}
			if(flag == true) {
				return updateElement; // �̵��� �����ϸ� update�迭�� ����
			}
			else {
				return currentElement; // �̵��� �Ұ����ϸ� ���� �迭�� ����
			}
		
		case 1: // left
			for(int i = 0 ; i < 4 ; i++) {
				int tempHeight = currentElement[i].centerHeight;
				int tempWidth = currentElement[i].centerWidth - 1; // ���� �̵��̹Ƿ� ������ǥ - 1
				// return�� �迭 �����صα�
				updateElement[i].centerHeight = tempHeight;
				updateElement[i].centerWidth = tempWidth;

				if(tempWidth < 0) { // ���� �̵��̹Ƿ� 0�� ��谪�� �Ѿ�� flag�� �����Ѵ�.
					flag = false;
					break;
				}
			}
			if(flag == true) {
				return updateElement; // �̵��� �����ϸ� update�迭�� ����
			}
			else {
				return currentElement; // �̵��� �Ұ����ϸ� ���� �迭�� ����
			}
			
		case 2: // down
			for(int i = 0 ; i < 4 ; i++) {
				int tempHeight = currentElement[i].centerHeight + 1;  // �Ʒ� �̵��̹Ƿ� ������ǥ + 1
				int tempWidth = currentElement[i].centerWidth;
				// return�� �迭 �����صα�
				updateElement[i].centerHeight = tempHeight;
				updateElement[i].centerWidth = tempWidth;

				if(tempHeight > formHeight - 1) { // �Ʒ� �̵��̹Ƿ� Height�� ��谪�� �Ѿ�� flag�� �����Ѵ�.
					flag = false;
					needShape = true;
					break;
				}
			}
			if(flag == true) {
				return updateElement; // �̵��� �����ϸ� update�迭�� ����
			}
			else {
				return currentElement; // �̵��� �Ұ����ϸ� ���� �迭�� ����
			}
		}
		return updateElement;
	}
}

	