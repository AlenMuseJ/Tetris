package facade;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

import base.SpriteLoader;
import base.Window;
import tetraminoBuilders.*;

public class Field extends JPanel implements KeyListener, MouseListener, MouseMotionListener{
	
	private static final long serialVersionUID = 1L;
	
	private Field currentField;
	
	ScoreJournal sj = new ScoreJournal();
	
	private TetraminoBuilder tetraminoBuilder;
	
	private BufferedImage blocks, background, pause, playAgain, nextlvl;	
	private final int boardHeight = 20, boardWidth = 10;	
	private final int blockSize = 30;

	private int[][] field = new int[boardHeight][boardWidth];

	private Tetramino[] tetraminos = new Tetramino[7];
	private TetraminoBuilder[] builders = {new IBuilder(), 
			new TBuilder(), new LBuilder(), new JBuilder(), new SBuilder(),
			new ZBuilder(), new OBuilder()};
	private static Tetramino currentTetramino, nextTetramino;
	
	private Timer looper;	
	private int FPS = 60;	
	private int delay = 1000/FPS;
	
	private int mouseX, mouseY;	
	private boolean leftClick = false;
	private boolean rightClick = false;
	private Rectangle pauseBounds, playAgainBounds, nextlvlBounds;	
	private boolean gamePaused = false;	
	private boolean gameOver = false;
	
	public Field(){

		blocks = SpriteLoader.loadImage("/blocks.png");
		playAgain = SpriteLoader.loadImage("/again.png");
		pause = SpriteLoader.loadImage("/pause.png");
		background = SpriteLoader.loadImage("/background.png");
		nextlvl = SpriteLoader.loadImage("/nextlvl.png");
		
		mouseX = 0;
		mouseY = 0;
		
		pauseBounds = new Rectangle(350, 500, pause.getWidth(), pause.getHeight() + pause.getHeight()/2);
		playAgainBounds = new Rectangle(350, 500 - playAgain.getHeight() - 20,playAgain.getWidth(),
				playAgain.getHeight() + playAgain.getHeight()/2);
		nextlvlBounds = new Rectangle(350, 500 - nextlvl.getHeight() - 100, 
				nextlvl.getWidth(), 
				nextlvl.getHeight() + nextlvl.getHeight()/2);
		
		looper = new Timer(delay, new GameLooper());
		
		constructTetraminos(this);
		currentField = this;
	}
	
	/*Organize in time*/
	private Timer buttonLapse = new Timer(300, new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			buttonLapse.stop();
		}});

	class GameLooper implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			update();
			repaint();
		}		
	}

	public void startGame(){
		LevelTracker.setSpeed(1000);
		stopGame();
		sj.startOver();
		setNextTetramino();
		setCurrentTetramino();
		gameOver = false;
		looper.start();	
	}
	public void stopGame(){	
		for(int row = 0; row < field.length; row++)
		{
			for(int col = 0; col < field[row].length; col ++)
			{
				field[row][col] = 0;
			}
		}
		looper.stop();
	}
	
	/*Render all the field*/
	public void paintComponent(Graphics g){
		super.paintComponent(g);	
		g.drawImage(background, 0, 0, null);
		
		for(int row = 0; row < field.length; row++)
			for(int col = 0; col < field[row].length; col ++)		
				if(field[row][col] != 0)			
					g.drawImage(blocks.getSubimage((field[row][col] - 1)*blockSize,
							0, blockSize, blockSize), col*blockSize, row*blockSize, null);							
		
		for(int row = 0; row < nextTetramino.getCoords().length; row ++)
			for(int col = 0; col < nextTetramino.getCoords()[0].length; col ++)
				if(nextTetramino.getCoords()[row][col] != 0)
					g.drawImage(nextTetramino.getBlock(), col*30 + 320, row*30 + 50, null);	
		
		currentTetramino.render(g);
		
		if(pauseBounds.contains(mouseX, mouseY))
			g.drawImage(pause.getScaledInstance(pause.getWidth() + 2, pause.getHeight() + 2, BufferedImage.SCALE_DEFAULT)
					, pauseBounds.x + 2, pauseBounds.y + 2, null);
		else
			g.drawImage(pause, pauseBounds.x, pauseBounds.y, null);
		
		if(playAgainBounds.contains(mouseX, mouseY))
			g.drawImage(playAgain.getScaledInstance(playAgain.getWidth() + 2, playAgain.getHeight() + 2,
					BufferedImage.SCALE_DEFAULT), playAgainBounds.x + 2, playAgainBounds.y + 2, null);
		else
			g.drawImage(playAgain, playAgainBounds.x, playAgainBounds.y, null);
		
		if(nextlvlBounds.contains(mouseX, mouseY))
			g.drawImage(nextlvl.getScaledInstance(nextlvl.getWidth() + 2, nextlvl.getHeight() + 2,
					BufferedImage.SCALE_DEFAULT), nextlvlBounds.x + 2, nextlvlBounds.y + 2, null);
		else
			g.drawImage(nextlvl, nextlvlBounds.x, nextlvlBounds.y, null);
		
		if(gameOver)
		{
			boolean record = sj.checkScore();
			String gameOverString = "*** GAME OVER ***";
			if (record)
				gameOverString = "NEW RECORD: " + sj.getScore() + "!";
			g.setColor(Color.BLACK);
			g.setFont(new Font("Georgia", Font.BOLD, 30));
			g.drawString(gameOverString, 30, Window.HEIGHT/2);
		}	
		g.setColor(Color.BLACK);
		
		g.setFont(new Font("Georgia", Font.BOLD, 20));
		
		g.drawString("SCORE", Window.WIDTH - 125, Window.HEIGHT/2 - 30);
		g.drawString(sj.getScore() +"", Window.WIDTH - 125, Window.HEIGHT/2);
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(new Color(0, 0, 0, 100));
		
		for(int i = 0; i <= boardHeight; i++)
		{
			g2d.drawLine(0, i*blockSize, boardWidth*blockSize, i*blockSize);
		}
		for(int j = 0; j <= boardWidth; j++)
		{
			g2d.drawLine(j*blockSize, 0, j*blockSize, boardHeight*30);
		}
	}
	
	/*Clicking buttons: pause, play again, next level*/
	private void update(){ 	
		if(pauseBounds.contains(mouseX, mouseY) && leftClick && !buttonLapse.isRunning() && !gameOver)
		{
			buttonLapse.start();
			gamePaused = !gamePaused;
		}
		
		if(playAgainBounds.contains(mouseX, mouseY) && leftClick)
			startGame();
		
		if (nextlvlBounds.contains(mouseX, mouseY) && leftClick)
			{
				LevelTracker.speedUp();
			}
				
		if (nextlvlBounds.contains(mouseX, mouseY) && rightClick) {
			LevelTracker.speedDown();
		}
		
		if(gamePaused || gameOver)
		{
			return;
		}
		currentTetramino.update();
	}

	public void setNextTetramino(){
		int index = (int)(Math.random()*tetraminos.length);
		
		tetraminoBuilder = builders[index];
		constructTetramino();
		nextTetramino = tetraminoBuilder.getTetramino();
		nextTetramino.setField(currentField);
	}
	
	public void setCurrentTetramino(){
		currentTetramino = nextTetramino;
		setNextTetramino();
		
		for(int row = 0; row < currentTetramino.getCoords().length; row ++)
		{
			for(int col = 0; col < currentTetramino.getCoords()[0].length; col ++)
			{
				if(currentTetramino.getCoords()[row][col] != 0)
				{
					if(field[currentTetramino.getY() + row][currentTetramino.getX() + col] != 0)
						gameOver = true;
				}
			}		
		}		
	}

	public void addScore(){
		int points = 1*LevelTracker.getLevel();
		sj.addScore(points);
	}
	
	/*creating Tetraminos*/
	private void constructTetraminos(Field field)
	{
		Tetramino[] tetraminos = new Tetramino[7];
		for (int i = 0; i < 7; ++i) {
			tetraminoBuilder = builders[i];
			constructTetramino(); 
			tetraminos[i] = tetraminoBuilder.getTetramino();
			tetraminos[i].setField(field);
		}
		this.tetraminos = tetraminos;
	}
	
	private void constructTetramino() {
	       tetraminoBuilder.createNewTetramino();
	       tetraminoBuilder.setBlock();
	       tetraminoBuilder.setColor();
	       tetraminoBuilder.setCoords();
	}
	
	/*Setters and getters*/
	public void setGamePaused(boolean b){
		gamePaused = b;
	}
	
	public boolean getGamePaused(){
		return gamePaused;
	}

	
	public int[][] getField(){
		return field;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		KeyController.keyPressed(e, currentTetramino, currentField);
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e){
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

	@Override
	public void mouseClicked(MouseEvent e) {	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftClick = true;
		if(e.getButton() == MouseEvent.BUTTON3)
			rightClick = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftClick = false;
		if(e.getButton() == MouseEvent.BUTTON3)
			rightClick = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {	
	}

	@Override
	public void mouseExited(MouseEvent e) {	
	}
}