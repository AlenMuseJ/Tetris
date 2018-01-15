package facade;

public interface IScoreJournal {

	int getScore();

	void startOver();

	void addScore(int points);

	boolean checkScore();

}