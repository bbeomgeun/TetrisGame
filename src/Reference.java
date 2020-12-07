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
	int height = 0; // row
	int width = Reference.formWidth/2; // column
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


public class Reference extends JFrame implements KeyListener{
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
	static int rotation = 77;
	
	static boolean needShape = true;
	
	JButton b[][];
	int shapeNumber; // random함수로 0~6까지 나와서 makeShape함수의 변수로 사용
	Shape randomFigure; // makeShape의 결과물(Element 배열을 가지고 있다)
	Element[] eleNew; // randomFigure의 리턴값을 받을 Element 배열
	Color colorBox[] = {Color.red, Color.blue, Color.yellow, Color.gray, Color.pink, Color.green, Color.orange};
	
	public Shape makeShape(int shapeNumber) {
		Shape randomShape = new Shape(shapeNumber);
		
		return randomShape;
	}
	
	public Reference() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu_1 = new JMenu("파일저장");
		menuBar.add(menu_1);
		JMenu menu_2 = new JMenu("파일 불러오기");
		menuBar.add(menu_2);
		JMenu menu_3 = new JMenu("게임 종료");
		menuBar.add(menu_3);
		

		setLayout(new GridLayout(formHeight, formWidth));
		setSize(500, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 정상 종료
		
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
						
						// 임시 배경 reset 코드
						for(int row = 0 ; row < formHeight ; row++) {
							for(int col = 0 ; col < formWidth ; col++) {
								b[row][col].setBackground(Color.white);
							}
						}// 또한 테트리스판 지우는 코드 필요 ( 이것은 막 지울것이 아니라 20*10 배열에 테트리스데이터를 저장해두고 그것을 불러오는 형식으로 해야 바닥에 내려간 테트리스도 불러올수있겠다)
						
						// 그리기 코드
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
		// 1. 못 움직이는 경우 ( x나 y가 jFrame을 벗어나는 경우)
		// 2. 움직이는 경우 (좌표를 더해준다)
		boolean flag = true;
		
		Element [] updateElement = new Element[4];
		for(int i = 0 ; i < 4 ; i++) {
			updateElement[i] = new Element(0,0,0);
		}
		switch (direction) {
		case 0: // right
			for(int i = 0 ; i < 4 ; i++) {
				int tempHeight = currentElement[i].centerHeight; // 높이는 그대로
				int tempWidth = currentElement[i].centerWidth + 1; // 오른쪽 이동이므로 가로좌표 + 1
				// return용 배열 복사해두기
				updateElement[i].centerHeight = tempHeight;
				updateElement[i].centerWidth = tempWidth;

				if(tempWidth > formWidth - 1) { // 오른쪽 이동이므로 Width의 경계값을 넘어가면 flag를 변경한다.
					flag = false;
					break;
				}
			}
			if(flag == true) {
				return updateElement; // 이동이 가능하면 update배열을 리턴
			}
			else {
				return currentElement; // 이동이 불가능하면 기존 배열을 리턴
			}
		
		case 1: // left
			for(int i = 0 ; i < 4 ; i++) {
				int tempHeight = currentElement[i].centerHeight;
				int tempWidth = currentElement[i].centerWidth - 1; // 왼쪽 이동이므로 가로좌표 - 1
				// return용 배열 복사해두기
				updateElement[i].centerHeight = tempHeight;
				updateElement[i].centerWidth = tempWidth;

				if(tempWidth < 0) { // 왼쪽 이동이므로 0의 경계값을 넘어가면 flag를 변경한다.
					flag = false;
					break;
				}
			}
			if(flag == true) {
				return updateElement; // 이동이 가능하면 update배열을 리턴
			}
			else {
				return currentElement; // 이동이 불가능하면 기존 배열을 리턴
			}
			
		case 2: // down
			for(int i = 0 ; i < 4 ; i++) {
				int tempHeight = currentElement[i].centerHeight + 1;  // 아래 이동이므로 세로좌표 + 1
				int tempWidth = currentElement[i].centerWidth;
				// return용 배열 복사해두기
				updateElement[i].centerHeight = tempHeight;
				updateElement[i].centerWidth = tempWidth;

				if(tempHeight > formHeight - 1) { // 아래 이동이므로 Height의 경계값을 넘어가면 flag를 변경한다.
					flag = false;
					needShape = true;
					break;
				}
			}
			if(flag == true) {
				return updateElement; // 이동이 가능하면 update배열을 리턴
			}
			else {
				return currentElement; // 이동이 불가능하면 기존 배열을 리턴
			}
		}
		return updateElement;
	}
}

	