import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Reference extends JFrame{
	// 1. GUI ȭ�鱸��
	// 2. GUI �޴� ����
	// 3. Thread
	// 4. �̺�Ʈ ó��
	// 5. ���� ����� -> ���ӵ����� �迭 ����
	// 6. ������ �ۼ�
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
						//�������� �ڵ�
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