package Controller;

import Model.Question;
import Model.SysData;

public class PopQ {
	SysData sys = SysData.getInstance();
	
	
	public Question popQuestion() {
		
		//sys.loadQuestions(null);
		return sys.popQuestion();
	}

}
