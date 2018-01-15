package facade;

import java.awt.image.BufferedImage;

import base.SpriteLoader;

public abstract class TetraminoBuilder {
	protected Tetramino tetramino;
	protected BufferedImage blocks = SpriteLoader.loadImage("/blocks.png");
	protected int blockSize = 30;
	
	public Tetramino getTetramino()
	{
		return tetramino;
	}
	
	public void createNewTetramino() 
	{ 
		tetramino = new Tetramino(); 
	}
	
	public abstract void setCoords();
    public abstract void setBlock();
    public abstract void setColor();
}
