import javax.swing.JFrame;

public class practice extends JFrame{
//jFrame�ȿ� ���� ������ �ִ� ������� ����
	public practice() {
		setTitle("����");
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
