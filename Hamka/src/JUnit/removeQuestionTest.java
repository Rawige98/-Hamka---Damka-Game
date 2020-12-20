package JUnit;

import org.junit.Test;

import Model.Question;
import Model.SysData;
import Utils.Difficulty;
import Utils.E_Teams;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

public class removeQuestionTest {

	@Test
	public void test() {

		SysData.getInstance().loadQuestions(null);
		HashMap<Difficulty, ArrayList<Question>> questions = SysData.getInstance().getQuestions();
		ArrayList<Question> arrayList = questions.get(Difficulty.EASY);

		Question question = null;
		for (Question q : arrayList) {
			if (q.getText().equals("q1"))

			{
				question = q;
			}

		}
		Question q2 = new Question("q5", Difficulty.HARD, 2, E_Teams.Wolf);
        Question q3= new Question("q",Difficulty.EASY,3,E_Teams.Wolf);
		//assertFalse(SysData.getInstance().removeQuestion(q2));
		assertTrue(SysData.getInstance().removeQuestion(question));
		assertFalse(SysData.getInstance().removeQuestion(q3));
		

	}

}
