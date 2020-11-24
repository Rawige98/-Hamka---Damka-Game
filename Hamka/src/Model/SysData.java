package Model;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Controller.Logger;
import Utils.Difficulty;
import Utils.E_Teams;

public class SysData {

	private static SysData SysData;
	private static HashMap<Difficulty, ArrayList<Question>> questions;
	private static ArrayList<Game> games;
	private static ArrayList<Game> pausedGames;
	private static String quesJsonPath = "/JSON/question_json.json"; // .txt
	private static String originalPath = quesJsonPath;

	public static SysData getInstance() {
		if (SysData == null)
			SysData = new SysData();
		return SysData;
	}

//************************************** constructor*********************************************************************
	private SysData() {

		questions = new HashMap<Difficulty, ArrayList<Question>>();
		games = new ArrayList<Game>();
		pausedGames = new ArrayList<Game>();
	}

//*********************************getters and setters********************************************************************
	public static HashMap<Difficulty, ArrayList<Question>> getQuestions() {
		return questions;
	}

	public static void setQuestions(HashMap<Difficulty, ArrayList<Question>> Questions) {
		questions = Questions;
	}

	public static ArrayList<Game> getGames() {
		return games;
	}

	public static void setGames(ArrayList<Game> Games) {
		games = Games;
	}

	public static ArrayList<Game> getPausedGames() {
		return pausedGames;
	}

	public static void setPausedGames(ArrayList<Game> PausedGames) {
		pausedGames = PausedGames;
	}
	// *******************************************loadQuestions************************************************************************

	@SuppressWarnings("unchecked")
	public boolean loadQuestions(String externalPath) {

		if (externalPath != null) {
			quesJsonPath = externalPath;
		}
		Logger.log("Reading questions form path: " + quesJsonPath);
		JSONParser parser = new JSONParser();

		try {
			// get question's JSON file
			InputStream is = getClass().getResourceAsStream(originalPath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			Object obj = parser.parse(reader);
			JSONObject jo = (JSONObject) obj;

			// convert question's JSON file to array .
			JSONArray quesArray = (JSONArray) jo.get("questions");

			// iterate over the values (questions).
			Iterator<JSONObject> quesIterator = quesArray.iterator();
			System.out.println(quesIterator.toString());
			// get the questions data.
			while (quesIterator.hasNext()) {

				JSONObject q = quesIterator.next();

				// get question's content
				String text = (String) q.get("question");

				// get correct answer's number.
				int correctAnswerNum = Integer.valueOf(q.get("correct_ans").toString());

				// get question's difficulty level.
				Difficulty level = getQuestionLevel(Integer.valueOf(q.get("level").toString()));

				// get question's created team name.
				E_Teams team = E_Teams.valueOf((String) q.get("team"));
				// create an new object of the question.
				Question questionToAdd = new Question(text, level, correctAnswerNum, team);

				// get question's answers.
				JSONArray ansArray = (JSONArray) q.get("answers");

				for (int i = 0; i < ansArray.size(); i++) {
					String a = (String) ansArray.get(i);
					// add answers to the queston's answers array.
					questionToAdd.addAnswer(a);
				}

				// Add the question to questions according to the question level.
				if (!questions.containsKey(questionToAdd.getDifficulty())) {
					questions.put(questionToAdd.getDifficulty(), new ArrayList<Question>());
					questions.get(questionToAdd.getDifficulty()).add(questionToAdd);

				} else {
					questions.get(questionToAdd.getDifficulty()).add(questionToAdd);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			resetPathToDefault();
			return false;
		}
		resetPathToDefault();
		return true;
	}

//***********************************************SaveQuestions****************************************************************************
	@SuppressWarnings({ "unchecked", "resource" })
	public void saveQuestions(String externalPath) {

		if (externalPath != null) {
			quesJsonPath = externalPath;
		}
		Logger.log("Reading questions form path: " + quesJsonPath);
		try {
			JSONParser parser = new JSONParser();

			// get question's JSON file
			InputStream is = getClass().getResourceAsStream(originalPath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			Object obj = parser.parse(reader);
			JSONObject jo = (JSONObject) obj;
			jo.clear();
			Logger.log("Saving questions from path: " + quesJsonPath);
			JSONArray JSONquestions = new JSONArray();
			JSONObject toWrite = new JSONObject();

			// go over all questions and add every question to json file
			for (ArrayList<Question> list : questions.values()) {
				if (list == null)
					continue;

				// get each question from the ArrayList
				for (Question q : list) {
					JSONObject ja = new JSONObject();

					// get all answers
					JSONArray answers = new JSONArray();
					for (String a : q.getAnswers()) {
						answers.add(a);
					}

					// put fields in the object
					ja.put("question", q.getText());
					ja.put("correct_ans", q.getRightAnswer());
					ja.put("level", q.getDifficulty());
					ja.put("team", q.getTeam().toString());
					ja.put("answers", answers);

					// add the object to json array
					JSONquestions.add(ja);
				}

				// add json array to object question
				toWrite.put("questions", JSONquestions);
			}
			// write the JSONObject to .json file
			FileWriter file = new FileWriter("./bin" + originalPath);
			file.write(toWrite.toJSONString());
			file.flush();
			Logger.log("Question JSON was saved");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		resetPathToDefault();
	}

//************************************************add Question****************************************************************************
	public void addQuestion(Question question) {
		ArrayList<Question> myArray = questions.get(question.getDifficulty());
		if (myArray == null) {
			myArray = new ArrayList<Question>();
			myArray.add(question);
		} else if (!myArray.contains(question)) {
			myArray.add(question);
		}
		questions.put(question.getDifficulty(), myArray);

	}

//************************************************remove question***************************************************************************
	public boolean removeQuestion(Question question) {
		ArrayList<Question> myArray = questions.get(question.getDifficulty());
		if (myArray.contains(question)) {
			questions.get(question.getDifficulty()).remove(question);
			return true;
		}
		return false;
	}

//*********************************************Edit Question******************************************************************************
	public boolean editQuestion(Question question, Question newQuestion) {
		if (removeQuestion(question)) {
			addQuestion(newQuestion);
			return true;
		}
		return false;

	}

//***********************************************popQuestion*****************************************************************************
	public Question popQuestion() {
		Object[] diff = questions.keySet().toArray();
		Difficulty key = (Difficulty) diff[new Random().nextInt(diff.length)];
		ArrayList<Question> myArray = questions.get(key);
		Question q = myArray.get(new Random().nextInt(myArray.size()));
		return q;
	}

//****************************************************************************************************************************	
	static Difficulty getQuestionLevel(int level) { // Helper method to define question's difficulty level
		if (level == 1)
			return Difficulty.EASY;
		else if (level == 2)
			return Difficulty.MEDIUM;
		else if (level == 3)
			return Difficulty.HARD;

		return Difficulty.MEDIUM;
	}

	private void resetPathToDefault() {
		quesJsonPath = originalPath;
		System.out.println("Restting JSON Path: " + quesJsonPath);
	}

}
