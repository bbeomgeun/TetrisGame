import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;

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
	int height = 1; // row
	int width = MainTetris.formWidth/2; // column
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

public class MainTetris extends JFrame implements Runnable{
	static int formHeight = 20;
	static int formWidth = 10;
	static int rightDirection = 0;
	static int leftDirection = 1;
	static int downDirection = 2;
	static int rotationDirection = 3;
	
	static boolean isLeft = false;
	static boolean isRight = false;
	static boolean isDown = false;
	static boolean isRotation = false;
	
	static boolean needShape = true;
	
	static boolean fullRow = false;
	
	static boolean gameEnd = false;
	
	int gameScore = 0;
	
	int recordArray[][]; // ��Ͽ� �迭
	
	JButton b[][];
	JButton preview[][];
	
	int shapeNumber; // random�Լ��� 0~6���� ���ͼ� makeShape�Լ��� ������ ���
	Shape randomFigure; // makeShape�� �����(Element �迭�� ������ �ִ�)
	Element[] eleNew; // randomFigure�� ���ϰ��� ���� Element �迭
	Color colorBox[] = {Color.red, Color.blue, Color.yellow, Color.gray, Color.pink, Color.green, Color.orange};

	JPanel main;
	JPanel sub;
	
	Thread tetris;
	
	JMenuBar menuBar;
	JMenu menu_game, menu_file, menu_guide;
	JMenuItem gameStart, gameExit, programExit, gameSave, gameLoad, gameTip;
	
	public MainTetris() {
		// �޴� ����
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		main = new JPanel();
		sub = new JPanel();
		
		menu_game = new JMenu("����");
		menuBar.add(menu_game);
		
		menu_file = new JMenu("����");
		menuBar.add(menu_file);
		
		menu_guide = new JMenu("����");
		menuBar.add(menu_guide);
		
		gameStart = new JMenuItem("���� �����ϱ�");
		gameStart.addActionListener(myActionListener);
		
		gameExit = new JMenuItem("���� �����ϱ�");
		gameExit.addActionListener(myActionListener);
		
		programExit = new JMenuItem("���α׷� �����ϱ�");
		programExit.addActionListener(myActionListener);
		
		gameSave = new JMenuItem("���� �����ϱ�");
		gameSave.addActionListener(myActionListener);
		
		gameLoad = new JMenuItem("���� �ҷ�����");
		gameLoad.addActionListener(myActionListener);
		
		gameTip = new JMenuItem("���� ����");
		gameTip.addActionListener(myActionListener);
		
		menu_game.add(gameStart);
		menu_game.add(gameExit);
		menu_game.add(programExit);
		
		menu_file.add(gameSave);
		menu_file.add(gameLoad);
		
		menu_guide.add(gameTip);
		
		setSize(600, 1000); // JFrame ������
		
		// ��Ʈ���� �� ����
		main.setLayout(new GridLayout(formHeight, formWidth));
		main.setSize(550, 1000); 
		
		// �̸����� ����
		//sub.setLayout(new GridLayout(4,4));
		//sub.setSize(100, 100);
		
		getContentPane().add(main);
//		getContentPane().add(sub);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���� ����
		
		b = new JButton[formHeight][formWidth];
		
		for(int row = 0 ; row < formHeight ; row++) {
			for(int col = 0 ; col < formWidth ; col++) {
				b[row][col] = new JButton();
				main.add(b[row][col]);
				JButton bj = b[row][col];
				bj.addKeyListener(new MyKeyListener());
				}
			}
		makeRecordArray();
		drawBackGround();
		
//		preview = new JButton[4][4];
//		
//		for(int r = 0 ; r < 4 ; r++) {
//			for(int c = 0 ; c < 4 ; c++) {
//				preview[r][c] = new JButton();
//				sub.add(preview[r][c]);
//			}
//		}
		
		setVisible(true);
		}

	public static void main(String[] args) {
		new MainTetris();
	}
	
	public void start() {
		tetris = new Thread(this);
		tetris.start();
	}

	public void run() {
		try {
			while(!gameEnd) {
				if(needShape) { // ���� �ʿ��� ��� ���� ����
					shapeNumber = (int)Math.floor(Math.random()*7);// 0~6 
					randomFigure = makeShape(shapeNumber);
					eleNew = randomFigure.transferArray();
					needShape = false;
					if(checkShapetoShape(eleNew)) {
						drawCurrentShape(); // ���� ��ġ�°� �����ְ� ����
						gameEnd = true;
						JOptionPane.showMessageDialog(null, "Game Over!\n"
								+ "�� ���� �������� ������ ���̸� ���� �ſ�.", "��Ʈ����", JOptionPane.ERROR_MESSAGE);
						resetRecordArray();
						drawBackGround();
						break;
					}
				}
				// ���� ����� �ڵ�
				eraseFullRow();
				
				System.out.println(gameScore);
				
				// ��� reset �ڵ�
				drawBackGround();
				
				// �׸��� �ڵ�
				drawCurrentShape();
				
				// ���⿡ ���缭 ���� �����̴� �ڵ� / default�� downDirection
				move();
				
				Thread.sleep(500);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void eraseFullRow() {
		for(int row = 0 ; row < formHeight ; row++) {
			fullRow = true;
			for(int col = 0 ; col < formWidth ; col++) {
				if(recordArray[row][col] == -1) {
					fullRow = false;
				}	
			}
			if(fullRow) { // �ش� row�� ��� 0�� �ƴ� ���
				for(int col = 0 ; col <formWidth ; col++) {
					recordArray[row][col] = -1; // �ش� row �� 0���� �����
				}
				gameScore += 10; // ���� �����ֱ�
				for(int tempRow = row ; tempRow >0 ; tempRow--) { // �� �پ� ������ �����ֱ�
					recordArray[tempRow] = recordArray[tempRow-1];
				}
			}
		}
	}
	
	public void drawBackGround() {
		for(int row = 2 ; row < formHeight ; row++) {
			for(int col = 0 ; col < formWidth ; col++) {
				if(recordArray[row][col] == -1)
					b[row][col].setBackground(Color.white);
				else
					b[row][col].setBackground(colorBox[recordArray[row][col]]);
			}
		}
		for(int i = 0 ; i <= 1 ; i++) {
			for(int j = 0 ; j < formWidth ; j++) {
				b[i][j].setBackground(Color.black);
			}
		}
	}
	
	public void drawCurrentShape() {
		for(int i = 0 ; i < 4 ; i++) {
			JButton jb = b[eleNew[i].centerHeight][eleNew[i].centerWidth];
			jb.setBackground(colorBox[shapeNumber]);
		}
	}
		
	public void move() {
			if(isLeft) {
				eleNew = moveShape(eleNew, leftDirection);
				isLeft = false;
			}
			else if(isRight) {
				eleNew = moveShape(eleNew, rightDirection);
				isRight = false;
				}
			else if(isDown) { // �� ĭ��
				eleNew = moveShape(eleNew, downDirection);
				eleNew = moveShape(eleNew, downDirection);
				isDown = false;
			}
			else if(isRotation) {
				eleNew = moveShape(eleNew, rotationDirection);
				isRotation = false;
			}
			else
				eleNew = moveShape(eleNew, downDirection);
		}
	
	public Shape makeShape(int shapeNumber) {
		Shape randomShape = new Shape(shapeNumber);
		
		return randomShape;
	}
	
	public void makingNewShape() {
		shapeNumber = (int)Math.floor(Math.random()*7);// 0~6 
		randomFigure = makeShape(shapeNumber);
	}
	
	public Element[] moveShape(Element[] currentElement, int direction) { // �ڵ� �ܼ�ȭ�ϱ�
		// 1. �� �����̴� ��� ( x�� y�� jFrame�� ����� ���)
		// 2. �����̴� ��� (��ǥ�� �����ش�)
		boolean flag = false; // �浹�÷��� = true�� �浹
		boolean rotationFlag = false;
		
		Element [] updateElement = new Element[4];
		for(int i = 0 ; i < 4 ; i++) {
			updateElement[i] = new Element(0,0,currentElement[i].colorNum);
		}
		switch (direction) {
		case 0: // right
			System.out.println("������");
			for(int i = 0 ; i < 4 ; i++) {
				int tempHeight = currentElement[i].centerHeight; // ���̴� �״��
				int tempWidth = currentElement[i].centerWidth + 1; // ������ �̵��̹Ƿ� ������ǥ + 1
				// return�� �迭 �����صα�
				updateElement[i].centerHeight = tempHeight;
				updateElement[i].centerWidth = tempWidth;

				if(tempWidth > formWidth - 1) { // ������ �̵��̹Ƿ� Width�� ��谪�� �Ѿ�� flag�� �����Ѵ�.
					flag = true; // �浹
					break;
				}
			}
			
			//���� ���� �ʾ����� �ٸ� ������ ���� ���, �� ������ ������ ���ο� ������ �ʿ��ϴ�.
			if(!flag && checkShapetoShape(updateElement)) { // ���üũ
				flag = true;
				needShape = true;
			}
			
			if(flag == false) {
				return updateElement; // �̵��� �����ϸ� update�迭�� ����
			}
			else {
				// ����, �������� �����̴°͸� �ȵǴ°��̰�, recordArray�� ����ϸ� �ȵ�.
				//addShapeToRecord(currentElement);
				return currentElement; // �̵��� �Ұ����ϸ� ���� �迭�� ����
			}
		
		case 1: // left
			System.out.println("����");
			for(int i = 0 ; i < 4 ; i++) {
				int tempHeight = currentElement[i].centerHeight;
				int tempWidth = currentElement[i].centerWidth - 1; // ���� �̵��̹Ƿ� ������ǥ - 1
				// return�� �迭 �����صα�
				updateElement[i].centerHeight = tempHeight;
				updateElement[i].centerWidth = tempWidth;

				if(tempWidth < 0) { // ���� �̵��̹Ƿ� 0�� ��谪�� �Ѿ�� flag�� �����Ѵ�.
					flag = true;
					break;
				}
			}
			
			//���� ���� �ʾ����� �ٸ� ������ ���� ���, �� ������ ������ ���ο� ������ �ʿ��ϴ�.
			if(!flag && checkShapetoShape(updateElement)) { // ���üũ
				flag = true;
				needShape = true;
			}
			
			if(flag == false) {
				return updateElement; // �̵��� �����ϸ� update�迭�� ����
			}
			else {
				//����, �������� �����̴°͸� �ȵǴ°��̰�, recordArray�� ����ϸ� �ȵ�.
				//addShapeToRecord(currentElement);
				return currentElement; // �̵��� �Ұ����ϸ� ���� �迭�� ����
			}
			
		case 2: // down
			System.out.println("�Ʒ�");
			for(int i = 0 ; i < 4 ; i++) {
				int tempHeight = currentElement[i].centerHeight + 1;  // �Ʒ� �̵��̹Ƿ� ������ǥ + 1
				int tempWidth = currentElement[i].centerWidth;
				// return�� �迭 �����صα�
				updateElement[i].centerHeight = tempHeight;
				updateElement[i].centerWidth = tempWidth;

				if(tempHeight > formHeight - 1) { // �Ʒ� �̵��̹Ƿ� Height�� ��谪�� �Ѿ�� flag�� �����Ѵ�.
					flag = true;
					needShape = true;
					break;
				}
			}
			
			if(!flag && checkShapetoShape(updateElement)) { // ���üũ
				flag = true;
				needShape = true;
			}
			
			if(flag == false) {
				return updateElement; // �̵��� �����ϸ� update�迭�� ����
			}
			else {
				addShapeToRecord(currentElement);
				return currentElement; // �̵��� �Ұ����ϸ� ���� �迭�� ����
			}
			
		case 3: // rotation
			System.out.println("ȸ��");
			int standardX = currentElement[0].centerHeight;
			int standardY = currentElement[0].centerWidth;
			for(int i = 0 ; i < 4 ; i++) { // 3���� ȸ��
				int tempHeight = standardX - currentElement[i].centerHeight; 
				int tempWidth = standardY - currentElement[i].centerWidth;
				// return�� �迭 �����صα�
				updateElement[i].centerHeight = standardX + tempWidth; // x = y
				updateElement[i].centerWidth = standardY - tempHeight; // y = -x
	
				if(updateElement[i].centerHeight > formHeight - 1)
						{
					flag = true; // �浹
					break;
				}
				if(updateElement[i].centerWidth > formWidth - 1 ||
						updateElement[i].centerWidth < 0) {
					 rotationFlag = true;
					 break;
				 }
			}
			
			if(rotationFlag == true) { //ȸ���ϴٰ� ���� ���� �ε��������� �迭�� ������� �ʰ� �׳� ���� �迭�� ��ȯ
				return currentElement;
			}
			else { // ���� rotation������ �迭index������ checkShapetoShape���� �˻��Ҷ� ���� �Ѿ�� �����
				if(!flag && checkShapetoShape(updateElement)) { // ���üũ
					flag = true;
					needShape = true;
				}
				
				if(flag == false && rotationFlag == false) {
					return updateElement; // �̵��� �����ϸ� update�迭�� ����
				}
				else if(flag == true && rotationFlag == false){ // �⺻ flag == true
					addShapeToRecord(currentElement);
					return currentElement; // �̵��� �Ұ����ϸ� ���� �迭�� ����
				}
			}
			
		} //switch ����
		
		return updateElement;
	}
	
	public void makeRecordArray() { // record�� array 20*10�迭 �ʱ�ȭ
		recordArray = new int[formHeight][formWidth];
		for(int i = 0 ; i < formHeight ; i++) {
			for(int j = 0 ; j < formWidth ; j++) {
				recordArray[i][j] = -1;
			}
		}
	}
	public void resetRecordArray() {
		for(int i = 0 ; i < formHeight ; i++) {
			for(int j = 0 ; j < formWidth ; j++) {
				recordArray[i][j] = -1;
			}
		}
	}
	
	public void addShapeToRecord(Element[] shape) { // �ٴڿ� ������� array�� �Է�
		for(int i = 0 ; i < 4 ; i++) {
			recordArray[shape[i].centerHeight][shape[i].centerWidth] = shape[i].colorNum;
		}
	}
	public boolean checkShapetoShape(Element[] shape) {  // ���߿� ���⿡ ��谪 check���� �־ �ڵ� �ܼ�ȭ�ϱ�
		boolean checkFlag = false;
		for(int i = 0 ; i < 4 ; i++) {
			if( recordArray[shape[i].centerHeight][shape[i].centerWidth] != -1) {
				checkFlag = true;
				break;
			}
		}
		return checkFlag;
	}
	
	public void saveRecordArray() {
		String output = "C:\\homework\\tetrisResult.txt"; // c\:homework ����
		File file = new File(output);
		try {
			PrintWriter pw = new PrintWriter(file);
			for(int i = 0 ; i < formHeight ; i++) {
				for(int j = 0 ; j < formWidth ; j++) {
					pw.print(recordArray[i][j]+ " ");
				}
				pw.println("");
			}
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadRecordArray() {
		String output = "C:\\homework\\tetrisResult.txt"; // c\:homework ����
		File file = new File(output);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String str;
			for(int i = 0 ; i < formHeight ; i++) {
				str = br.readLine();
				String [] splitNum = str.split(" ");
				for(int j = 0 ; j < formWidth ; j++) {	
					if(str==null)
						break;
					recordArray[i][j] = Integer.parseInt(splitNum[j]);	
				}
				System.out.println("");
			}	
		}catch (IOException e) {
		e.printStackTrace();
		}
	}
	
	class MyKeyListener extends KeyAdapter{
		@Override
		public void keyTyped(KeyEvent e) {
		
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP: // UP
				break;
				
			case KeyEvent.VK_DOWN:
				System.out.println("pressed" + e.getKeyCode());
				isDown = true;
				break;
			
			case KeyEvent.VK_LEFT:
				System.out.println("pressed" + e.getKeyCode());
				isLeft = true;
				break;
				
			case KeyEvent.VK_RIGHT:
				System.out.println("pressed" + e.getKeyCode());
				isRight = true;
				break;
				
			case KeyEvent.VK_SPACE:
				System.out.println("pressed" + e.getKeyCode());
				isRotation = true;
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP: // UP
				break;
				
			case KeyEvent.VK_DOWN:
				System.out.println("released" + e.getKeyCode());
				break;
			
			case KeyEvent.VK_LEFT:
				System.out.println("released" + e.getKeyCode());
				break;
				
			case KeyEvent.VK_RIGHT:
				System.out.println("released" + e.getKeyCode());
				break;
				
			case KeyEvent.VK_SPACE:
				System.out.println("released" + e.getKeyCode());
				break;
			}
		}
	}
	
	ActionListener myActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == gameStart) {
				needShape = true;
				fullRow = false;
				gameEnd = false;
				start();
			}
			else if(e.getSource() == gameExit) {
				gameEnd = true;
				tetris = null; // thread�� null���� �־��ֱ�.
				resetRecordArray();
				drawBackGround();
			}
			else if(e.getSource() == programExit) { // ���� â�� ����
				setVisible(false);
				dispose();
				System.exit(0);
			}
			else if(e.getSource() == gameSave) {
				gameEnd = true; // ������ ���߰�
				saveRecordArray();
			}
			else if(e.getSource() == gameLoad) {
				// ���� �ҷ����� - ���� ������� recordArray�� �����ϱ� -> �ҷ��ͼ� �׸������ �ؾ��Ѵ�.
				gameEnd = true; // ������ ���߰�
				loadRecordArray(); // recordArray�� ���� �Ϸ�
				drawBackGround();
			}
			else if(e.getSource() == gameTip) {
				JOptionPane.showMessageDialog(null, "��Ʈ���� ���� ����\n�⺻ ����Ű : ����,������,�Ʒ� ����Ű + ȸ�� : �����̽���"
						+ "\n**�޴� ����**"
						+ "\n - ���� ���� : �� ��Ʈ������ ���۵˴ϴ�"
						+ "\n - ���� ���� : �������� ��Ʈ������ ����˴ϴ�"
						+ "\n - ���α׷� ���� : ���� â�� ����˴ϴ�"
						+ "\n - ���� ���� : �������� ��Ʈ������ ��ϵ˴ϴ�."
						+ "\n - ���� �ҷ����� : �����ߴ� ��Ʈ������ �ҷ��ɴϴ�."
						+ "\n ** ���� ���� �� �ҷ����� ���� ���� ������ �ٽ� ������ �̾ ������ ����˴ϴ� **"
						, "��Ʈ���� ����", JOptionPane.PLAIN_MESSAGE);
			}
		}
	};
}
