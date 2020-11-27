import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class practice extends JFrame{
//jFrame안에 만든 도형을 넣는 방식으로 진행
	public practice() {
		setTitle("연습");
		setSize(550, 670);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		tetrisForm tetris = new tetrisForm();
		add(tetris);
		this.addKeyListener(tetris);
		this.requestFocus();
		
		setVisible(true);
	
	}
	
	public static void main(String[] args) {
		new practice();
	}
}
