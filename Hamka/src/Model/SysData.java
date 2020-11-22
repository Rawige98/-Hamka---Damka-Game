package Model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Controller.Logger;


public class SysData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**instance object for the class.*/
	transient private static SysData sysData;
	
	/**hashMap stores the questions for each level.*/
	private HashMap<Difficulty, List<Question>> questions ;
	transient private Game currentGame;
	transient private User currentUser;
	private Map<Integer, Game> gameData;
	private List<User> playerData;
	
//	private ArrayList<Game> games ;
//	private ArrayList<Game> pausedGames ;
	
/******************************Constructor***********************************************/

	public static SysData getInstance() {
		if (sysData == null) {
			sysData = importData();
		}
		return sysData;
	}
	
	
	/**
	 * private c'tor for singleton use.
	 */
	private SysData() {
		sysData = this;
		playerData = new ArrayList<>();
		this.questions= JSON.getInstance().loadQuestions(null);
		//tileSet = new LinkedList<>();
		gameData = new HashMap<>();
		Logger.log("Finished creating new instance of DB");
		currentGame = null;

	}

	//-------------------------------------------------------------------
	//-------------------------functionality-----------------------------
	//-------------------------------------------------------------------
	/**
	 * add a new question to json file
	 * @param questionToAdd
	 * @return
	 */
	public boolean addQuestion(Question questionToAdd){
		if(questionToAdd==null) return false;
		List<Question> qList=this.questions.get(questionToAdd.getDifficulty());
		if(qList==null)
			qList=new ArrayList<Question>();
		if(qList.add(questionToAdd)){
	      JSON.getInstance().saveQuestions(this.questions,null);
		  return true;}
		
		return false;
	}
    
	/**
	 * remove a new question to json file
	 * @param questionToAdd
	 * @return
	 */
	public boolean removeQuestion(Question questionToRemove){
		if(questionToRemove==null) return false;
		List<Question> qList=this.questions.get(questionToRemove.getDifficulty());
		if(qList==null)return false;
	    int index=qList.indexOf(questionToRemove);
	    if(index>-1){
		 qList.remove(index);	
	      JSON.getInstance().saveQuestions(this.questions,null);
		  return true;
	    }
	    return false;
	
	}
	
	public void closeGame() {

	}
	
	public String login(String nickname){
		if (getCurrentUser() == null)
			setCurrentUser(new User(verifyPlayer(nickname)));
		else
			verifyPlayer(nickname);
		return nickname;
		
		
	}
	
	public void setCurrentUser(User currentPlayer) {
		this.currentUser = currentPlayer;
	}
	public void login(String nickname, Boolean force) {
		this.currentUser = null;
		login(nickname);
		
	}
	
	public String verifyPlayer(String nickname) {
		if (playerData == null)
			playerData = new ArrayList<>();
		if (!playerData.contains(new User(nickname))) {
			Logger.log("Added player " + nickname + " to database.");
			playerData.add(new User(nickname, E_UserType.PLAYER));
//			exportData();
		}

		return playerData.get(playerData.indexOf(new User(nickname))).getNickName();
	}
	
	//-------------------------------------------------------------------
		//----------------------------utility--------------------------------
		//-------------------------------------------------------------------
		/**
		 * Seraialize all relevant data
		 *
		 * @return
		 */
		private static SysData importData() {
			try {
				String fileName = "SysData.cer";
				FileInputStream input = new FileInputStream(fileName);
				ObjectInputStream objInput = new ObjectInputStream(input);
				SysData Data = (SysData) objInput.readObject();
				Logger.log("Successfully imported Data.cer");
				Data.questions = JSON.getInstance().loadQuestions(null);
				
				objInput.close();

				return Data;
			} catch (Exception e) {
				Logger.log("Failed to import database");
				return new SysData();
			}
		}
		/**
		 * Deserialize data
		 */
		public static void exportData() {
			try {
				String fileName = "SysData.cer";
				FileOutputStream output = new FileOutputStream(fileName);
				ObjectOutputStream objoutput = new ObjectOutputStream(output);
				objoutput.writeObject(sysData);
				objoutput.close();
				Logger.log("Exported Data to \"" + fileName + "\"");

			} catch (Exception e) {
				System.err.println("Could not export database\n" + e.toString());

			}
		}
		public boolean AdminLogin(String username,String password) {
			// TODO Auto-generated method stub
			if(username.equals("Admin") && password.equals("Admin"))
				return true;
			return false;
		}
		
		public Map<Difficulty, List<Question>> getQuestions() {
			return questions;
		}
		public boolean editQuestion(Question qBefore, Question qAfter) {
			// TODO Auto-generated method stub
			if (removeQuestion(qBefore))
				if (addQuestion(qAfter))
					return true;

			return false;
		}
		public int getNextQuestionNum() {
			// TODO Auto-generated method stub
			int num=0;
			for(List<Question> list:questions.values())
				num+=list.size();
			return num++;
		}
		
		public Difficulty getQuestionLevel(int level) {
			// TODO Auto-generated method stub
			return   JSON.getQuestionLevel(level);
		}
		public User getCurrentUser() {
			return currentUser;
		}


	
	
	
	
	

}
