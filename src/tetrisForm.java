import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class tetrisForm extends JPanel implements KeyListener{
	final int ROWS = 20;
	final int COLS = 10;
	MyRect rects[][];
	int blockShape[][][]  = new int[][][]{
//		{	{1, 1, 0, 0},
//			{1, 1, 0, 0}	}, // ㅁ
//		
//		{	{0, 2, 0, 0},
//			{2, 2, 2, 0}}, // ㅗ
//		
//		{	{3, 3, 0, 0},
//			{0, 3, 3, 0}}, // z
//		
//		{	{0, 4, 4, 0},
//			{4, 4, 0, 0}}, // z'
//		
//		{	{5, 5, 5, 0},
//			{5, 0, 0, 0}}, // ㄱ'
//		
//		{	{6, 6, 6, 0},
//			{0, 0, 6, 0}}, // ㄱ
//		
//		{	{7, 7, 7, 7},
//			{0, 0, 0, 0}}, // ㅡ
		{{0, 0}, {0, 0}, {0, 0}, {0, 0}},
	    {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},//Z'
	    {{0, -1}, {0, 0}, {1, 0}, {1, 1}},//Z
	    {{0, -1}, {0, 0}, {0, 1}, {0, 2}},//ㄱ'
	    {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},//ㅏ
	    {{0, 0}, {1, 0}, {0, 1}, {1, 1}},//ㅁ
	    {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},//ㄴ
	    {{1, -1}, {0, -1}, {0, 0}, {0, 1}}//
	};
	
	public tetrisForm() {
		rects = new MyRect[ROWS][COLS];
		for(int i = 0 ; i < ROWS ; i++) {
			for(int j = 0 ; j < COLS; j++) {
//				if(i==0 || i==ROWS-1 || j==0 || j==COLS-1) {
//					rects[i][j] = new MyRect(j*51, i*51, 50, 50, Color.blue);
//				}
//				else {
					rects[i][j] = new MyRect(j*51, i*51, 50, 50, Color.WHITE); // 이미 RECTS 객체로 생성되어있다.
				//}
			}
		}
	}
	
	public void makeBlock() {
//		int x = (int)Math.floor(Math.random()*7);// 0~6
//		int selectedBlock[][] = blockShape[x];
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);		
		Color colorBox[] = {Color.red, Color.blue, Color.yellow, Color.gray, Color.pink, Color.green, Color.orange};
		int x = (int)Math.floor(Math.random()*7);// 0~6
		int selectedBlock[][] = blockShape[x];
		
		for(int i = 0; i < 4; i++) {
			rects[selectedBlock[i][0]][selectedBlock[i][0]].setC(colorBox[x]);
		}
		
		for(int i = 0 ; i < ROWS ; i++) {
			for(int j = 0 ; j < COLS; j++) {
				rects[i][j].draw(g);
			}
		}
		addKeyListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
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
//		}
//		int input = e.getKeyCode();
//		if(input==40) {
//			for(int i=0; i<4; i++) {
//			  	  rects[shape[i][0]] [shape[i][1]].setC(Color.yellow);
//			  	  shape[i][0]+=1;
//			    }
//			
//		}else if(input==38) {
//			for(int i=0; i<4; i++) {
//			  	  rects[shape[i][0]] [shape[i][1]].setC(Color.yellow);
//			  	  shape[i][0]-=1;
//			    }
//		}else if(input==37) {
//			for(int i=0; i<4; i++) {
//			  	  rects[shape[i][0]] [shape[i][1]].setC(Color.yellow);
//			  	  shape[i][1]-=1;
//			    }
//		}else if(input==39) {
//			for(int i=0; i<4; i++) {
//			  	  rects[shape[i][0]] [shape[i][1]].setC(Color.yellow);
//			  	  shape[i][1]+=1;
//			    }
//		}
		
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
