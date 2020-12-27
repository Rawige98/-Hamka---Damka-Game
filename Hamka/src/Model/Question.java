package Model;

import java.util.ArrayList;

import Utils.Consts;
import Utils.Difficulty;
import Utils.E_Teams;



public class Question {

	private String text;
	private ArrayList<String> answers;
	private int rightAnswer;
	private Difficulty difficulty;
	private E_Teams team;

	public Question( String text, Difficulty difficulty, int rightAnswer,E_Teams team) {
		super();
		this.text = text;
		this.answers = new ArrayList<String>();
		this.rightAnswer = rightAnswer;
		this.difficulty = difficulty;
		this.team=team;
	}
	
	
	
    /**
     * Add a possible answer to the question.
     * check if the answer is not already exists in answers arraylist.
     * @param answerToAdd
     * @return true if the answer added successfully,false otherwise.
     */
	public boolean addAnswer(String answerToAdd){
		
		if(answerToAdd!=null && !this.answers.contains(answerToAdd)){
			this.answers.add(answerToAdd);
			return true;
		}
		return false;
	}

    /**
     * Remove a possible answer to the question.
     * check if the answer is exists in answers arraylist.
     * @param answerToRemove
     * @return true if the answer removed successfully,false otherwise.
     */
	public boolean removeAnswer(String answerToRemove){
		
		if(answerToRemove!=null && this.answers.contains(answerToRemove)){
			this.answers.remove(answerToRemove);
			return true;
		}
		return false;
	}

	/******************** Getters And Setters ******************************/


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ArrayList<String> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}

	public int getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(int rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
	

	public E_Teams getTeam() {
		return team;
	}


	public void setTeam(E_Teams team) {
		this.team = team;
	}

	public int checkAnswer(int answer) {
		if(answer == this.rightAnswer) {
			if(difficulty == Difficulty.EASY)
				return Consts.EASY_RIGHT;
			else if(difficulty == Difficulty.MEDIUM)
				return Consts.MEDUIM_RIGHT;
			else
				return Consts.HARD_RIGHT;
		}else {
			if(difficulty == Difficulty.EASY)
				return Consts.EASY_WRONG;
			else if(difficulty == Difficulty.MEDIUM)
				return Consts.MEDUIM_WRONG;
			else
				return Consts.HARD_WRONG;
		}
	}
	
	@Override
	public String toString() {
	   return text+" ?- level :"+difficulty+ "\n"+ answers.get(0)+"\n"+answers.get(1)+"\n"
			   +answers.get(2)+"\n"+answers.get(3)+"\nTeam:"+team+"\n----------------------------------------";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + ((difficulty == null) ? 0 : difficulty.hashCode());
		result = prime * result + rightAnswer;
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		Question other = (Question) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (difficulty != other.difficulty)
			return false;
		if (rightAnswer != other.rightAnswer)
			return false;
		if (team != other.team)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}








}
