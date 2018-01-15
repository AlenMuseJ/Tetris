package base;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Timer;

public class StartPage extends JPanel implements MouseListener, MouseMotionListener{
	
	private static final long serialVersionUID = 1L;
	private int mouseX, mouseY;
	private Rectangle bounds;
	private boolean leftClick = false;
	private BufferedImage upPicture, downPicture, play;
	private Window window;
	private BufferedImage[] playButton = new BufferedImage[2];
	private Timer timer;
	
	
	public StartPage(Window window){
			upPicture = SpriteLoader.loadImage("/up.png");
			downPicture = SpriteLoader.loadImage("/down.png");
			play = SpriteLoader.loadImage("/play.png");

			timer = new Timer(1000/60, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
			
		});
		timer.start();
		mouseX = 0;
		mouseY = 0;
		
		playButton[0] = play.getSubimage(0, 0, 100, 80);
		playButton[1] = play.getSubimage(100, 0, 100, 80);
		
		bounds = new Rectangle(Window.WIDTH/2 - 50, Window.HEIGHT/2 - 80, 100, 80);
		this.window = window;		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(leftClick && bounds.contains(mouseX, mouseY))
			window.startTetris();
			
		g.setColor(Color.WHITE);
		
		g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		
		g.drawImage(upPicture, Window.WIDTH/2 - upPicture.getWidth()/2, Window.HEIGHT/2 - upPicture.getHeight()/2 - 180, null);
		g.drawImage(downPicture, Window.WIDTH/2 - downPicture.getWidth()/2,
				Window.HEIGHT/2 - downPicture.getHeight()/2 + 130, null);
		
		if(bounds.contains(mouseX, mouseY))
			g.drawImage(playButton[1], Window.WIDTH/2 - 50, Window.HEIGHT/2 - 80, null);
		else
			g.drawImage(playButton[0], Window.WIDTH/2 - 50, Window.HEIGHT/2 - 80, null);	
	}

	@Override
	public void mouseClicked(MouseEvent e) {	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftClick = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftClick = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}	
}