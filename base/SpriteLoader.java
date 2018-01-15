package base;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class SpriteLoader {
	
	public static BufferedImage loadImage(String path){
		try 
		{
			return ImageIO.read(SpriteLoader.class.getResource(path));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;	
	}
}