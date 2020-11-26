import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class practice extends JFrame{

	public practice() {
		setTitle("¿¬½À");
		setSize(550, 670);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		MyPanel panel = new MyPanel();
		add(panel);
		this.addKeyListener(panel);
		this.requestFocus();
		
		setVisible(true);
	
	}
	
	public static void main(String[] args) {
		new practice();
	}
}
