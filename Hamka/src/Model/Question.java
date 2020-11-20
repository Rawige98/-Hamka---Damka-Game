package Model;

import java.util.ArrayList;

public class Question {

	private int id;
	private String text;
	private ArrayList<String> answers;
	private String rightAnswer;
	private Difficulty difficulty;

	public Question(int id, String text, ArrayList<String> answers, String rightAnswer, Difficulty difficulty) {
		super();
		this.id = id;
		this.text = text;
		this.answers = answers;
		this.rightAnswer = rightAnswer;
		this.difficulty = difficulty;
	}

	public boolean addAnswer(String answer, boolean isCorrect) {
		if (answer != null) {
			answers.add(answer);
			if (isCorrect)
				setRightAnswer(answer);
			return true;
		}

		return false;
	}

	public boolean removeAnswer(String answer) {
		if (!answers.contains(answer))
			return false;
		else {
			if (this.rightAnswer.equals(answer))
				setRightAnswer("");
			answers.remove(answer);
			return true;
		}
	}

	/******************** Getters And Setters ******************************/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", text=" + text + ", answers=" + answers + ", rightAnswer=" + rightAnswer
				+ ", difficulty=" + difficulty + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + ((difficulty == null) ? 0 : difficulty.hashCode());
		result = prime * result + id;
		result = prime * result + ((rightAnswer == null) ? 0 : rightAnswer.hashCode());
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
		if (id != other.id)
			return false;
		if (rightAnswer == null) {
			if (other.rightAnswer != null)
				return false;
		} else if (!rightAnswer.equals(other.rightAnswer))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}
