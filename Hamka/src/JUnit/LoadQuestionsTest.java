package JUnit;


import org.junit.Test;

import Model.Question;
import Model.SysData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import Utils.Difficulty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LoadQuestionsTest {
	
	@Test
	public void test() {
	
//		//read json into string after load
		SysData.getInstance().loadQuestions(null);
		HashMap<Difficulty, ArrayList<Question>> questions = SysData.getInstance().getQuestions();
		SysData.getInstance().saveQuestions(null);
	
//		//read json into output after save
		SysData.getInstance().loadQuestions(null);
		HashMap<Difficulty, ArrayList<Question>> questions2 = SysData.getInstance().getQuestions();


		
		//check if both question lists are equal
		assertTrue("Successful", questions.equals(questions2));
	}


}
