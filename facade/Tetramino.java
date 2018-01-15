package facade;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tetramino {
	
	private BufferedImage block;	
	private int[][] coords;
	
	private int color;	
	private int x, y; 	
	
	private long time, lastTime;	
	private static int speed, falldown = 50;	
	private int delay;
	
	private int[][] reference;	
	private int deltaX;
	
	private Field field;
	
	private boolean collision = false, moveX = false;
	
	public Tetramino()
	{
		deltaX = 0;
		x = 4;
		y = 0;
		speed = LevelTracker.getSpeed();
		delay = speed;
		time = 0;
		lastTime = System.currentTimeMillis();
	}
	
	public void update()
	{
		moveX = true;
		time += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if (collision)
		{
			for(int row = 0; row < coords.length; row ++)
			{
				for(int col = 0; col < coords[0].length; col ++)
				{
					if(coords[row][col] != 0)
						field.getField()[y + row][x + col] = color;
				}
			}
			checkLine();
			field.addScore();
			field.setCurrentTetramino();
		}
		
		if(!(x + deltaX + coords[0].length > 10) && !(x + deltaX < 0))
		{
			
			for(int row = 0; row < coords.length; row++)
			{
				for(int col = 0; col < coords[row].length; col ++)
				{
					if(coords[row][col] != 0)
					{
						if(field.getField()[y + row][x + deltaX + col] != 0)
						{
							moveX = false;
						}
						
					}
				}
			}
			
			if(moveX)
				x += deltaX;
			
		}
		
		if(!(y + 1 + coords.length > 20))
		{
			
			for(int row = 0; row < coords.length; row++)
			{
				for(int col = 0; col < coords[row].length; col ++)
				{
					if(coords[row][col] != 0)
					{
						
						if(field.getField()[y + 1 + row][x +  col] != 0)
						{
							collision = true;
						}
					}
				}
			}
			if(time > delay)
				{
					y++;
					time = 0;
				}
		}
		else
		{
			collision = true;
		}
		
		deltaX = 0;
	}
	
	public void render(Graphics g)
	{
		
		for(int row = 0; row < coords.length; row ++)
		{
			for(int col = 0; col < coords[0].length; col ++)
			{
				if(coords[row][col] != 0)
				{
					g.drawImage(block, col*30 + x*30, row*30 + y*30, null);	
				}
			}		
		}
		
		for(int row = 0; row < reference.length; row ++)
		{
			for(int col = 0; col < reference[0].length; col ++)
			{
				if(reference[row][col] != 0)
				{
					g.drawImage(block, col*30 + 320, row*30 + 160, null);	
				}	
				
			}
				
		}

	}
	
	private void checkLine(){
		int size = field.getField().length - 1;
		
		for(int i = field.getField().length - 1; i > 0; i--)
		{
			int count = 0;
			for(int j = 0; j < field.getField()[0].length; j++)
			{
				if(field.getField()[i][j] != 0)
					count++;
				
				field.getField()[size][j] = field.getField()[i][j];
			}
			if(count < field.getField()[0].length)
				size --;
		}
	}
	
	public void rotateTetramino()
	{
		
		int[][] rotatedShape = null;
		
		rotatedShape = trans(coords);
		
		rotatedShape = reverse(rotatedShape);
		
		if((x + rotatedShape[0].length > 10) || (y + rotatedShape.length > 20))
		{
			return;
		}
		
		for(int row = 0; row < rotatedShape.length; row++)
		{
			for(int col = 0; col < rotatedShape[row].length; col ++)
			{
				if(rotatedShape[row][col] != 0)
				{
					if(field.getField()[y + row][x + col] != 0)
					{
						return;
					}
				}
			}
		}
		coords = rotatedShape;
	}

	
    public int[][] trans(int[][] matrix){
        int[][] temp = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                temp[j][i] = matrix[i][j];
        return temp;
    }


	public int[][] reverse(int[][] matrix){	
		int middle = matrix.length/2;
			
		for(int i = 0; i < middle; i++)
		{
			int[] temp = matrix[i];
			
			matrix[i] = matrix[matrix.length - i - 1];
			matrix[matrix.length - i - 1] = temp;
		}
		
		return matrix;
		
	}
	
	public void makeDisappear()
	{
		for(int row = 0; row < coords.length; row ++)
			for(int col = 0; col < coords[0].length; col++)
			{
				coords[row][col] = 0;
			}				
	}
		
	public int getColor(){
		return color;
	}
	
	public void setDeltaX(int deltaX){
		this.deltaX = deltaX;
	}
	
	public void speedUp(){
		delay = falldown;
	}
	
	public void speedDown(){
		delay = speed;
	}
	
	public BufferedImage getBlock(){
		return block;
	}
	
	public int[][] getCoords(){
		return coords;
	}
	
	public void setCoords(int[][] coords){
		this.coords = coords;
	}
	
	public void setBlock(BufferedImage block){
		this.block = block;
	}
	
	public void setColor(int color){
		this.color = color;
	}
	
	public void setField(Field field) {
	this.field = field;
	}
	
	public void setReference(int[][] reference) {
		this.reference = reference;
		}
	
	public Field getField() {
		return field;
		}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}