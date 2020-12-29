package JUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Model.Board;
import Model.Player;
import Model.ValidateID;

public class ValidateMoveTest {

	@Test
	public void test() {

		Board board1 = new Board();
		board1.getMyBoard()[0][0].setValue(11);
		board1.getMyBoard()[1][1].setValue(0);
		ValidateID v=new ValidateID( 0, 0, 1, 1,board1,true, false);
		ValidateID vv=new ValidateID( 0, 0, 1, 1,board1,false, false);

		assertTrue(v.validateIDs());
		assertFalse(vv.validateIDs());

		Board board2 = new Board();

		board2.getMyBoard()[0][4].setValue(22);
		board2.getMyBoard()[1][3].setValue(0);
		ValidateID v2=new ValidateID( 4, 0, 3, 1,board2,false, false);
		ValidateID v3=new ValidateID( 4, 0, 3, 1,board2,true, false);

		assertTrue(v2.validateIDs());
		assertFalse(v3.validateIDs());

		board2.getMyBoard()[1][3].setValue(11);
		ValidateID v4=new ValidateID( 4, 0, 3, 1,board2,false, false);

		assertFalse(v4.validateIDs());

	}

}
