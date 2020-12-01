import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Reference extends JFrame{
	// 1. GUI 화면구성
	// 2. GUI 메뉴 구성
	// 3. Thread
	// 4. 이벤트 처리
	// 5. 파일 입출력 -> 게임데이터 배열 저장
	// 6. 순서도 작성
	JButton b[][];
	public Reference() {
		setLayout(new GridLayout(20, 10));
		b = new JButton[20][10];
		for(int i = 0 ; i < 20 ; i++) {
			for(int j = 0 ; j < 10 ; j++) {
				b[i][j] = new JButton();
				add(b[i][j]);
				JButton bj = b[i][j];
				b[i][j].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						bj.setBackground(Color.DARK_GRAY);
						
					}
				});
				if(i == 0)
					b[i][j].setBackground(Color.blue);
			}
		}
		setVisible(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while(true) {
						Thread.sleep((1000);
						//떨어지는 코드
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		}).run();
	}


	public static void main(String[] args) {
		new Reference();
	}
}