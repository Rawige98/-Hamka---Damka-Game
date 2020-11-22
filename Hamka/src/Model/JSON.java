package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import Controller.Logger;
import Model.Answer;
import Model.Question;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Iterator;
/**
 * This class handle the reading/saving questions from the JSON file.
 *
 */

public class JSON {
  
     //-------------------------------------------------------------------
	 //-----------------------------fields--------------------------------
	 //-------------------------------------------------------------------
		
	 /**instance object for the class.*/
	 private static JSON instance;
		
	 /**question's JSON file path.*/
	 private static String quesJsonPath = "/JSON/questions.json";
	 private static String originalPath = quesJsonPath;
		
	 /**for question id number*/
	 private static int autoQuestionNumber = 1;
	 //-------------------------------------------------------------------
	 //-------------------------constructors------------------------------
	 //-------------------------------------------------------------------
	 /**
	  * empty constructor.
	  */
		private JSON() {
		}

	 /**
	  * singleton instance getter.
	  * @return instance of SysData.
	  */
	  public static JSON getInstance() {
		if (instance == null) {
			instance = new JSON();
		}

		return instance;
	  }
	  //-------------------------------------------------------------------
	  //-------------------------functionality-----------------------------
	  //-------------------------------------------------------------------
		
		
		
		
	  //-------------------------------------------------------------------
	  //----------------------------utility--------------------------------
	  //-------------------------------------------------------------------
	  /**
	   * This method for reading the questions from JSON File
	   * @return reading questions from file
	   */
	   @SuppressWarnings("unchecked")
	   public HashMap<Difficulty , List<Question>> loadQuestions(String externalPath) {
			HashMap<Difficulty , List<Question>> questions =new HashMap<Difficulty , List<Question>>();
			if (externalPath !=null) {
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
				//get the questions data.
				while (quesIterator.hasNext()) {
					
					JSONObject q = quesIterator.next();
				    
					//get question's number
					int code = Integer.valueOf(q.get("id").toString());
					
	                //get question's content
					String text=(String)q.get("question");
	                
					//get correct answer's number.
					int correctAnswerNum=Integer.valueOf(q.get("correct_ans").toString());
				
					//get question's difficulty level.
					Difficulty  level=getQuestionLevel(Integer.valueOf(q.get("level").toString()));
					
					//get question's created team name.
					E_Teams team=E_Teams.valueOf((String)q.get("team"));
					//create an new object of the question.
					Question questionToAdd=new Question(code,text,level,correctAnswerNum,team);
				
					//get question's answers.
					JSONArray ansArray = (JSONArray) q.get("answers");
				
					for (int i = 0; i < ansArray.size(); i++) {
						String a=(String) ansArray.get(i);
						//add answers to the queston's answers array.
						questionToAdd.addAnswer(new Answer(a));
					}
					
					questionToAdd.setId(code);
					// Add the question to questions map.according the the question level.
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
				return null;
			}
			resetPathToDefault();
			return questions;
		}
	    @SuppressWarnings({ "unchecked", "resource" })
		public void saveQuestions(Map<Difficulty ,List<Question>> questionsToSave,String externalPath){
		//	HashMap<Difficulty , List<Question>> questions =new HashMap<Difficulty , List<Question>>();
			if (externalPath !=null) {
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
			for (Map.Entry<Difficulty , List<Question>> list : questionsToSave.entrySet()) {
				 if (list.getValue() == null)
						continue;

				// get each question from the list
				for (Question q : list.getValue()) {
					JSONObject ja = new JSONObject();
						
					// get all answers
					JSONArray answers = new JSONArray();
					for (Answer a : q.getAnswers()){
						answers.add(a.getText());
					}

					// put fields in the object
					ja.put("id", q.getId());
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
			FileWriter file = new FileWriter("./bin"+originalPath);
		    file.write(toWrite.toJSONString());
		    file.flush();
		   Logger.log("Question JSON was saved");
	    }
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			resetPathToDefault();
	    }


		/**
		 * Helper method to define question's difficulty level
		 */
		static Difficulty  getQuestionLevel(int level) {
			if (level == 1)
				return Difficulty .EASY;
			else if (level == 2)
				return Difficulty .MEDIUM;
			else if (level == 3)
				return Difficulty .HARD;

			return Difficulty .MEDIUM;
		}


		
		public static int getNextQuestionNumber(){
	      return autoQuestionNumber;
		}
	    
		public void addQuestionNum() {
			autoQuestionNumber++;
		}
		private void resetPathToDefault() {
			quesJsonPath = originalPath;
			System.out.println("Restting JSON Path: " + quesJsonPath);
		}
	    

	
}
