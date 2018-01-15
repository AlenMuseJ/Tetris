package facade;

import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.IOException;

import configuration.Context;

public class KeyController {
		public static int mode = 0;
		
		private static String rotate_path = "rotate.txt";
		private static String left_path = "left.txt";
		private static String right_path = "right.txt";
		private static String down_path = "down.txt";
		
		private static String conf_rotate;
		private static String conf_left;
		private static String conf_right;
		private static String conf_down;
		
		public static void keyPressed(KeyEvent e, Tetramino currentTetramino, Field field) {
						
			Context c = new Context();
			conf_rotate = read(rotate_path);
			c.setExpr(conf_rotate);
			mode = c.interpret();
			
			if (mode == 0 || mode == -1) 
			{
				if(e.getKeyCode() == KeyEvent.VK_UP)
					currentTetramino.rotateTetramino();
			}
			else
				if (e.getKeyCode() == mode)
					currentTetramino.rotateTetramino();
			
			conf_right = read(right_path);
			c.setExpr(conf_right);
			mode = c.interpret();
			
			if (mode == 0 || mode == -1) 
			{			
				if(e.getKeyCode() == KeyEvent.VK_RIGHT)
					currentTetramino.setDeltaX(1);
			}
			else if (e.getKeyCode() == mode)
				currentTetramino.setDeltaX(1);
			
			conf_left = read(left_path);
			c.setExpr(conf_left);
			mode = c.interpret();
			
			if (mode == 0 || mode == -1) 
			{			
				if(e.getKeyCode() == KeyEvent.VK_LEFT)
					currentTetramino.setDeltaX(-1);
			}
			else if (e.getKeyCode() == mode)
				currentTetramino.setDeltaX(-1);
			
			conf_down = read(down_path);
			c.setExpr(conf_down);
			mode = c.interpret();
			
			if (mode == 0 || mode == -1) 
			{			
				if(e.getKeyCode() == KeyEvent.VK_DOWN)
					currentTetramino.speedUp();
			}
			else if (e.getKeyCode() == mode)
				currentTetramino.speedUp();	
			
			if (e.getKeyChar() == KeyEvent.VK_SPACE)
				field.setGamePaused(!field.getGamePaused());
			if (e.getKeyChar() == KeyEvent.VK_ESCAPE)
				System.exit(1);
			if (e.getKeyChar() == KeyEvent.VK_ENTER)
				field.startGame();
			if (e.getKeyChar() == KeyEvent.VK_SLASH)
				LevelTracker.speedUp();
			if (e.getKeyChar() == KeyEvent.VK_MINUS)
				LevelTracker.speedDown();
		}
		
		public static String read(String path)
		{
			String str = "";
			int c;
			try(FileReader reader = new FileReader(path))
	        {
				while((c=reader.read())!=-1){	                 
	                str+="" + (char)c;
	            } 
	            
	        }
	        catch(IOException ex){	             
	            System.out.println(ex.getMessage());
	        } 
			return str;
		}

}
