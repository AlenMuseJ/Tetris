package facade;

public class LevelTracker {
	private static int speed;
	
	private static long lastTime;
	
	public static void setSpeed(int s)
	{
		lastTime = System.currentTimeMillis();
		speed = s;
	}
	
	public static int getLevel()
	{
		return (6 - speed/200);
	}
	
	
	public static int getSpeed()
	{
		return speed;
	}
	
	public static void speedUp()
	{
		if (speed > 200 && (System.currentTimeMillis() - lastTime > 3000))
			{speed -=200;
			lastTime = System.currentTimeMillis();}
	}
	
	public static void speedDown()
	{
		if (speed < 1000 && (System.currentTimeMillis() - lastTime > 3000))
		{speed +=200;
		lastTime = System.currentTimeMillis();}
	}
}
