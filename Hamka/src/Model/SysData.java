package Model;

import java.util.ArrayList;

public class SysData {
	
	private ArrayList<Game> games ;
	private ArrayList<Question> questions;
	private ArrayList<Game> pausedGames ;
	
/******************************Constructor***********************************************/
	
	public SysData(ArrayList<Game> games, ArrayList<Question> questions, ArrayList<Game> pausedGames) {
		super();
		this.games = new ArrayList<Game>();
		this.questions = new ArrayList<Question>();
		this.pausedGames = new ArrayList<Game>();
	}
	
/*****************************Getters and Setters ******************************************/
	public ArrayList<Game> getGames() {
		return games;
	}

	public void setGames(ArrayList<Game> games) {
		this.games = games;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public ArrayList<Game> getPausedGames() {
		return pausedGames;
	}

	public void setPausedGames(ArrayList<Game> pausedGames) {
		this.pausedGames = pausedGames;
	}
	/*****************************************hashCode + equals**********************/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((games == null) ? 0 : games.hashCode());
		result = prime * result + ((pausedGames == null) ? 0 : pausedGames.hashCode());
		result = prime * result + ((questions == null) ? 0 : questions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysData other = (SysData) obj;
		if (games == null) {
			if (other.games != null)
				return false;
		} else if (!games.equals(other.games))
			return false;
		if (pausedGames == null) {
			if (other.pausedGames != null)
				return false;
		} else if (!pausedGames.equals(other.pausedGames))
			return false;
		if (questions == null) {
			if (other.questions != null)
				return false;
		} else if (!questions.equals(other.questions))
			return false;
		return true;
	}
	

	
	
	
	
	

}
