package Model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import Utils.Difficulty;
import Utils.E_Teams;
import Utils.JsonParser;

public class SysData {
	
	private static SysData SysData;
	private HashMap<Difficulty, ArrayList<Question>> questions;
	private ArrayList<Game> games;
	private ArrayList<Game> pausedGames;
	private ArrayList<String> rules;

	private String gameJsonPath="src/JSON/game_json.txt";

	private String quesJsonPath = "src/JSON/question_json.txt"; // .txt
	private String originalPath = quesJsonPath;

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
		rules = new ArrayList<>();
	}

//*********************************getters and setters********************************************************************
	public HashMap<Difficulty, ArrayList<Question>> getQuestions() {
		return questions;
	}

	public void setQuestions(HashMap<Difficulty, ArrayList<Question>> Questions) {
		questions = Questions;
	}

	public ArrayList<Game> getGames() {
		return games;
	}

	public void setGames(ArrayList<Game> Games) {
		games = Games;
	}

	public ArrayList<Game> getPausedGames() {
		return pausedGames;
	}

	public void setPausedGames(ArrayList<Game> PausedGames) {
		pausedGames = PausedGames;
	}
	
	public ArrayList<String> getRules() {
		return rules;
	}
	
	public void setRules(ArrayList<String> rules) {
		this.rules = rules;
	}
	// *******************************************loadQuestions************************************************************************

	@SuppressWarnings("unchecked")
	public  boolean loadQuestions(String externalPath) {

		if (externalPath != null) {
			quesJsonPath = externalPath;
		}
		//Logger.log("Reading questions form path: " + quesJsonPath);
		JSONParser parser = new JSONParser();

		try {
			// get question's JSON file
			FileInputStream fis = new FileInputStream(originalPath);

			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			Object obj = parser.parse(reader);
			JSONObject jo = (JSONObject) obj;

			// convert question's JSON file to array .
			JSONArray quesArray = (JSONArray) jo.get("questions");

			// iterate over the values (questions).
			Iterator<JSONObject> quesIterator = quesArray.iterator();
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
		
		try {
			JSONParser parser = new JSONParser();

			// get question's JSON file
			FileInputStream fis = new FileInputStream(originalPath);

			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			Object obj = parser.parse(reader);
			JSONObject jo = (JSONObject) obj;
			jo.clear();
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
			FileWriter file = new FileWriter(originalPath);
			file.write(toWrite.toJSONString());
			file.flush();
			System.out.println("Question JSON was saved");
			
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
//*****************************************************************************************************************************	
	private void resetPathToDefault() {
		quesJsonPath = originalPath;
		System.out.println("Restting JSON Path: " + quesJsonPath);
	}
	
	
	
	
	public boolean loadPausedGames() {
		// TODO Auto-generated catch block
		try {
			String file = "src/JSON/pausedGames_json.txt";
			String json = readFileAsString(file);

			List<Game> games = JsonParser.getInstance().parseToList(json, new Game());
			pausedGames.clear();
			pausedGames.addAll(games);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			resetPathToDefault();
			return false;
		}

	}

	public static String readFileAsString(String file) throws Exception {
		return new String(Files.readAllBytes(Paths.get(file)));
	}
	
	
	
	public boolean writePausedGames() {
		FileWriter writer = null;
		try {
			String filePath = "src/JSON/pausedGames_json.txt";
			writer = new FileWriter(filePath);
			String parsedListToJson = JsonParser.getInstance().parseListToJsonArray(pausedGames, new Game());
			writer.write(parsedListToJson);
			return true;
		}

		catch (Exception e) {
			e.printStackTrace();
			resetPathToDefault();
			return false;
		}finally {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean loadFinishedGames() {
		// TODO Auto-generated method stub
//		FileWriter writer = null;
//		try {
//			String filePath = "src/JSON/finishedGames_json.txt";
//			writer = new FileWriter(filePath);
//			String parsedListToJson = JsonParser.getInstance().parseListToJsonArray(pausedGames, new Game());
//			writer.write(parsedListToJson);
//			return true;
//		}
//
//		catch (Exception e) {
//			e.printStackTrace();
//			resetPathToDefault();
			return false;
//		}finally {
//			try {
//				writer.flush();
//				writer.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}

	public boolean writeRules() {
		// TODO Auto-generated method stub
		FileWriter writer = null;
		try {
			String filePath = "src/JSON/rules_json.txt";
			writer = new FileWriter(filePath);
			String parsedListToJson = JsonParser.getInstance().parseListToJsonArray(rules, new String());
			writer.write(parsedListToJson);
			return true;
		}

		catch (Exception e) {
			e.printStackTrace();
			resetPathToDefault();
			return false;
		}finally {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public boolean loadRules() {
		// TODO Auto-generated method stub
		try {
			String file = "src/JSON/rules_json.txt";
			String json = readFileAsString(file);
//			System.out.println(json);
			List<String> rules = JsonParser.getInstance().parseToList(json, new String());
			this.rules.clear();
			this.rules.addAll(rules);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			resetPathToDefault();
			return false;
		}
	}
	
	
	

}
