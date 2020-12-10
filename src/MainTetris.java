import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

class Element{
	//중심점 x,y를 잡고 이것을 중심으로 +-으로 도형 표현할 것이다.
	int centerHeight; // 중심점 X좌표
	int centerWidth; // 중심점 Y좌표
	int colorNum;
	Element(int x, int y, int color){
		this.centerHeight = x;
		this.centerWidth = y;
		this.colorNum = color; // 지울까 고민중(현재 사용 X)
	}
}

class Shape{
	Element current[] = new Element[4];
	int height = 1; // row
	int width = MainTetris.formWidth/2; // column
	// x, y는 항상 도형의 좌측 최상단
	
	Shape(int shapeNum){
		switch (shapeNum) {
			//0~6 ㄱ ㅣ ㅁ ㄴ  어떤 모양이든 current[0]이 기준점(회전시 x,y / 내릴 떄나 옆으로 옮길때 x,y좌표 / colorNum 등
			case 0: // ㄱ 모양(세로로 긴)
				current[0] = new Element(height, width, 0);
				current[1] = new Element(height, width+1, 0);
				current[2] = new Element(height+1, width+1, 0);
				current[3] = new Element(height+2, width+1, 0);
				break;
			case 1: // ㅣ 모양
				current[0] = new Element(height, width, 1);
				current[1] = new Element(height+1, width, 1);
				current[2] = new Element(height+2, width, 1);
				current[3] = new Element(height+3, width, 1);
				break;
			case 2: // ㅁ 모양
				current[0] = new Element(height, width, 2);
				current[1] = new Element(height+1, width, 2);
				current[2] = new Element(height, width+1, 2);
				current[3] = new Element(height+1, width+1, 2);
				break;
			case 3: // ㄴ 모양 (가로로 긴)
				current[0] = new Element(height, width, 3);
				current[1] = new Element(height+1, width, 3);
				current[2] = new Element(height+1, width+1, 3);
				current[3] = new Element(height+1, width+2, 3);
				break;
			case 4: // z 모양
				current[0] = new Element(height, width, 4);
				current[1] = new Element(height, width+1, 4);
				current[2] = new Element(height+1, width+1, 4);
				current[3] = new Element(height+1, width+2, 4);
				break;
			case 5: // ㅗ모양
				current[0] = new Element(height, width, 5);
				current[1] = new Element(height+1, width-1, 5);
				current[2] = new Element(height+1, width, 5);
				current[3] = new Element(height+1, width+1, 5);
				break;
			case 6: // z' 모양
				current[0] = new Element(height, width, 6);
				current[1] = new Element(height, width+1, 6);
				current[2] = new Element(height+1, width, 6);
				current[3] = new Element(height+1, width-1, 6);
				break;
		}
	}
	public Element[] transferArray(){ // 랜덤 도형 넘버를 입력 받아서 모양배열을 구성한 뒤, 그리기 위해 배열을 리턴해준다.
		return current;
	}
}

public class MainTetris extends JFrame implements Runnable{
	// 1. GUI 화면구성
	// 2. GUI 메뉴 구성
	// 3. Thread
	// 4. 이벤트 처리
	// 5. 파일 입출력 -> 게임데이터 배열 저장
	// 6. 순서도 작성
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
	
	int gameScore = 0;
	
	int recordArray[][]; // 기록용 배열
	
	JButton b[][];
	JButton preview[][];
	
	int shapeNumber; // random함수로 0~6까지 나와서 makeShape함수의 변수로 사용
	Shape randomFigure; // makeShape의 결과물(Element 배열을 가지고 있다)
	Element[] eleNew; // randomFigure의 리턴값을 받을 Element 배열
	Color colorBox[] = {Color.red, Color.blue, Color.yellow, Color.gray, Color.pink, Color.green, Color.orange};

	JPanel main;
	JPanel sub;
	
	Thread tetris;
	
	JMenuBar menuBar;
	JMenu menu_game, menu_file, menu_guide;
	JMenuItem gameStart, gameExit, gameSave, gameLoad, gameTip;
	
	public MainTetris() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		main = new JPanel();
		sub = new JPanel();
		
		menu_game = new JMenu("게임");
		menuBar.add(menu_game);
		
		menu_file = new JMenu("파일");
		menuBar.add(menu_file);
		
		menu_guide = new JMenu("도움말");
		menuBar.add(menu_guide);
		
		gameStart = new JMenuItem("게임 시작하기");
		gameStart.addActionListener(myActionListener);
		
		gameExit = new JMenuItem("게임 종료하기");
		gameExit.addActionListener(myActionListener);
		
		gameSave = new JMenuItem("게임 저장하기");
		gameSave.addActionListener(myActionListener);
		
		gameLoad = new JMenuItem("게임 불러오기");
		gameLoad.addActionListener(myActionListener);
		
		gameTip = new JMenuItem("게임 도움말");
		gameTip.addActionListener(myActionListener);
		
		menu_game.add(gameStart);
		menu_game.add(gameExit);
		
		menu_file.add(gameSave);
		menu_file.add(gameLoad);
		
		menu_guide.add(gameTip);
		
		setSize(600, 1000); // JFrame 사이즈
		
		// 테트리스 판 세팅
		main.setLayout(new GridLayout(formHeight, formWidth));
		main.setSize(550, 1000); 
		
		// 미리보기 세팅
		//sub.setLayout(new GridLayout(4,4));
		sub.setSize(100, 100);
		
		getContentPane().add(main, BorderLayout.CENTER);
		getContentPane().add(sub);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 정상 종료
		
		b = new JButton[formHeight][formWidth];
		
		for(int row = 0 ; row < formHeight ; row++) {
			for(int col = 0 ; col < formWidth ; col++) {
				b[row][col] = new JButton();
				main.add(b[row][col]);
				JButton bj = b[row][col];
				bj.addKeyListener(new MyKeyListener());
				}
			}
		
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
			makeRecordArray();
			while(true) {
				if(needShape) { // 블럭이 필요한 경우 랜덤 생성
					shapeNumber = (int)Math.floor(Math.random()*7);// 0~6 
					randomFigure = makeShape(shapeNumber);
					eleNew = randomFigure.transferArray();
					needShape = false;
				}
				// 한줄 지우기 코드
				for(int row = 0 ; row < formHeight ; row++) {
					fullRow = true;
					for(int col = 0 ; col < formWidth ; col++) {
						if(recordArray[row][col] == -1) {
							fullRow = false;
						}	
					}
					if(fullRow) { // 해당 row가 모두 0이 아닐 경우
						for(int col = 0 ; col <formWidth ; col++) {
							recordArray[row][col] = -1; // 해당 row 값 0으로 만들기
							gameScore += 10; // 점수 더해주기
						}
						for(int tempRow = row ; tempRow >0 ; tempRow--) { // 한 줄씩 밑으로 내려주기
							recordArray[tempRow] = recordArray[tempRow-1];
						}
					}
				}
				
				System.out.println(gameScore);
				
				// 배경 reset 코드
				for(int row = 0 ; row < formHeight ; row++) {
					for(int col = 0 ; col < formWidth ; col++) {
						if(recordArray[row][col] == -1)
							b[row][col].setBackground(Color.white);
						else
							b[row][col].setBackground(colorBox[recordArray[row][col]]);
					}
				}
				
				// 그리기 코드
				for(int i = 0 ; i < 4 ; i++) {
					JButton jb = b[eleNew[i].centerHeight][eleNew[i].centerWidth];
					jb.setBackground(colorBox[shapeNumber]);
				}
				move();
				//eleNew = moveShape(eleNew, leftDirection);
				
				Thread.sleep(500);
			}
		} catch (Exception e) {
			System.out.println(e);
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
				else if(isDown) { // 두 칸씩
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
	
	public Element[] moveShape(Element[] currentElement, int direction) { // 코드 단순화하기
		// 1. 못 움직이는 경우 ( x나 y가 jFrame을 벗어나는 경우)
		// 2. 움직이는 경우 (좌표를 더해준다)
		boolean flag = false; // 충돌플래그 = true면 충돌
		
		Element [] updateElement = new Element[4];
		for(int i = 0 ; i < 4 ; i++) {
			updateElement[i] = new Element(0,0,currentElement[i].colorNum);
		}
		switch (direction) {
		case 0: // right
			System.out.println("오른쪽");
			for(int i = 0 ; i < 4 ; i++) {
				int tempHeight = currentElement[i].centerHeight; // 높이는 그대로
				int tempWidth = currentElement[i].centerWidth + 1; // 오른쪽 이동이므로 가로좌표 + 1
				// return용 배열 복사해두기
				updateElement[i].centerHeight = tempHeight;
				updateElement[i].centerWidth = tempWidth;

				if(tempWidth > formWidth - 1) { // 오른쪽 이동이므로 Width의 경계값을 넘어가면 flag를 변경한다.
					flag = true; // 충돌
					break;
				}
			}
			
			//경계는 넘지 않았지만 다른 도형에 닿은 경우, 이 도형은 끝나고 새로운 도형이 필요하다.
			if(!flag && checkShapetoShape(updateElement)) { // 경계체크
				flag = true;
				needShape = true;
			}
			
			if(flag == false) {
				return updateElement; // 이동이 가능하면 update배열을 리턴
			}
			else {
				// 왼쪽, 오른쪽은 움직이는것만 안되는것이고, recordArray에 기록하면 안됨.
				//addShapeToRecord(currentElement);
				return currentElement; // 이동이 불가능하면 기존 배열을 리턴
			}
		
		case 1: // left
			System.out.println("왼쪽");
			for(int i = 0 ; i < 4 ; i++) {
				int tempHeight = currentElement[i].centerHeight;
				int tempWidth = currentElement[i].centerWidth - 1; // 왼쪽 이동이므로 가로좌표 - 1
				// return용 배열 복사해두기
				updateElement[i].centerHeight = tempHeight;
				updateElement[i].centerWidth = tempWidth;

				if(tempWidth < 0) { // 왼쪽 이동이므로 0의 경계값을 넘어가면 flag를 변경한다.
					flag = true;
					break;
				}
			}
			
			//경계는 넘지 않았지만 다른 도형에 닿은 경우, 이 도형은 끝나고 새로운 도형이 필요하다.
			if(!flag && checkShapetoShape(updateElement)) { // 경계체크
				flag = true;
				needShape = true;
			}
			
			if(flag == false) {
				return updateElement; // 이동이 가능하면 update배열을 리턴
			}
			else {
				//왼쪽, 오른쪽은 움직이는것만 안되는것이고, recordArray에 기록하면 안됨.
				//addShapeToRecord(currentElement);
				return currentElement; // 이동이 불가능하면 기존 배열을 리턴
			}
			
		case 2: // down
			System.out.println("아래");
			for(int i = 0 ; i < 4 ; i++) {
				int tempHeight = currentElement[i].centerHeight + 1;  // 아래 이동이므로 세로좌표 + 1
				int tempWidth = currentElement[i].centerWidth;
				// return용 배열 복사해두기
				updateElement[i].centerHeight = tempHeight;
				updateElement[i].centerWidth = tempWidth;

				if(tempHeight > formHeight - 1) { // 아래 이동이므로 Height의 경계값을 넘어가면 flag를 변경한다.
					flag = true;
					needShape = true;
					break;
				}
			}
			
			if(!flag && checkShapetoShape(updateElement)) { // 경계체크
				flag = true;
				needShape = true;
			}
			
			if(flag == false) {
				return updateElement; // 이동이 가능하면 update배열을 리턴
			}
			else {
				addShapeToRecord(currentElement);
				return currentElement; // 이동이 불가능하면 기존 배열을 리턴
			}
			
		case 3: // rotation
			System.out.println("회전");
			int standardX = currentElement[0].centerHeight;
			int standardY = currentElement[0].centerWidth;
			for(int i = 0 ; i < 4 ; i++) { // 3번만 회전
				int tempHeight = standardX - currentElement[i].centerHeight; 
				int tempWidth = standardY - currentElement[i].centerWidth;
				// return용 배열 복사해두기
				updateElement[i].centerHeight = standardX + tempWidth; // x = y
				updateElement[i].centerWidth = standardY - tempHeight; // y = -x
	
				//updateElement[i].centerWidth > formWidth - 1 ||  || updateElement[i].centerWidth < 0
				if(updateElement[i].centerHeight > formHeight - 1) { // 회전이동이므로 모든 조건
					flag = true; // 충돌
					break;
				}
			}
			
			if(!flag && checkShapetoShape(updateElement)) { // 경계체크
				flag = true;
				needShape = true;
			}
			
			if(flag == false) {
				return updateElement; // 이동이 가능하면 update배열을 리턴
			}
			else {
				addShapeToRecord(currentElement);
				return currentElement; // 이동이 불가능하면 기존 배열을 리턴
			}
			
		} //switch 종료
		
		return updateElement;
	}
	
	public void makeRecordArray() { // record용 array 20*10배열 초기화
		recordArray = new int[formHeight][formWidth];
		for(int i = 0 ; i < formHeight ; i++) {
			for(int j = 0 ; j < formWidth ; j++) {
				recordArray[i][j] = -1;
			}
		}
	}
	public void addShapeToRecord(Element[] shape) { // 바닥에 닿았을시 array에 입력
		for(int i = 0 ; i < 4 ; i++) {
			recordArray[shape[i].centerHeight][shape[i].centerWidth] = shape[i].colorNum;
		}
	}
	public boolean checkShapetoShape(Element[] shape) {  // 나중에 여기에 경계값 check까지 넣어서 코드 단순화하기
		boolean checkFlag = false;
		for(int i = 0 ; i < 4 ; i++) {
			if( recordArray[shape[i].centerHeight][shape[i].centerWidth] != -1) {
				checkFlag = true;
				break;
			}
		}
		return checkFlag;
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
				start();
			}
			else if(e.getSource() == gameExit) {
				//게임 종료 코드
			}
			else if(e.getSource() == gameSave) {
				// 게임 저장 - 쓰레드 종료 후 파일 입력으로 recordArray 저장하기
			}
			else if(e.getSource() == gameLoad) {
				// 게임 불러오기 - 파일 출력으로 recordArray에 복사하기 -> 불러와서 그리기까지 해야한다.
			}
			else if(e.getSource() == gameTip) {
				 // 그냥 팝업으로 텍스트 띄워주기
			}
		}
		
	};
}