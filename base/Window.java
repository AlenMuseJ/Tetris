package base;
import javax.swing.JFrame;

import facade.Field;

public class Window{
	public static final int WIDTH = 460, HEIGHT = 635;
	
	private Field field;
	private StartPage title;
	private JFrame window;
	
	public Window(){
		
		window = new JFrame("Tetris");
		window.setSize(WIDTH, HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setResizable(false);	
		
		field = new Field();
		title = new StartPage(this);
		
		window.addKeyListener(field);
		window.addMouseMotionListener(title);
		window.addMouseListener(title);
		
		window.add(title);
		
		window.setVisible(true);
	}
	public void startTetris(){
		window.remove(title);
		window.addMouseMotionListener(field);
		window.addMouseListener(field);
		window.add(field);
		field.startGame();
		window.revalidate();
	}
	
	public static void main(String[] args) {
		new Window();
	}

}