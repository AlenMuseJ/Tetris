package facade;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class ScoreJournal implements IScoreJournal {
	private static String path = "recordTable.txt";
	private static String path2 = "record.txt";
	private int maxScore = 0;
	private int score = 0;
	
	private boolean isRecord = false;
	
	private int previousMaxScore = 0;
	
	
	/* (non-Javadoc)
	 * @see facade.IScoreJournal#getScore()
	 */
	@Override
	public int getScore()
	{
		return score;
	}
	
	/* (non-Javadoc)
	 * @see facade.IScoreJournal#startOver()
	 */
	@Override
	public void startOver()
	{
		score = 0;
		maxScore = 0;
	}
	
	public ScoreJournal()
	{
		score = 0;
		previousMaxScore = readMax();
		isRecord = false;
	}
	
	private int readMax()
	{
		int max = 0;
		
		 try(FileReader reader = new FileReader(path2))
	        {
			 String str = "";
	            int c;
	            while((c=reader.read())!=-1){	
	            	str+=(c-48);
	            } 
	          max = Integer.parseInt(str);
	        }
	        catch(IOException ex){	             
	            System.out.println(ex.getMessage());
	        }  
		
		return max;
	}
	
	/* (non-Javadoc)
	 * @see facade.IScoreJournal#addScore(int)
	 */
	@Override
	public void addScore(int points)
	{
		maxScore = maxScore + points;
		score = maxScore;
	}
	
	/* (non-Javadoc)
	 * @see facade.IScoreJournal#checkScore()
	 */
	@Override
	public boolean checkScore()
	{
		if (isRecord == false)
		if (maxScore > previousMaxScore) {
			isRecord =  true;
		}
		newGame();
		return isRecord;
	}
	
	private void newGame()
	{
		Date date = new Date();
		try(FileWriter writer = new FileWriter(path, true))
        {
			if (maxScore > previousMaxScore)
			{
            writer.append(maxScore + " " + date.toString() + "\t");	             
            writer.flush();
            previousMaxScore = maxScore;
            establishNewMax(maxScore);
            score = maxScore;
            maxScore = 0;
			}
        }
        catch(IOException ex){             
            System.out.println(ex.getMessage());
        } 
	}
	
	private void establishNewMax(int max)
	{
		try(FileWriter writer = new FileWriter(path2, false))
        {
            writer.append(maxScore + "");	             
            writer.flush();
        }
        catch(IOException ex){
             
            System.out.println(ex.getMessage());
        } 
	}
	
}
