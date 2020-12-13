package JUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Model.Board;
import Model.Player;

public class ValidateMoveTest {

	@Test
	public void test() {

		Board board1 = new Board();
		board1.getMyBoard()[0][0].setValue(11);
		board1.getMyBoard()[1][1].setValue(0);

		assertTrue(board1.validateIDs(true, 0, 0, 1, 1, false));
		assertFalse(board1.validateIDs(false, 0, 0, 1, 1, false));

		Board board2 = new Board();

		board2.getMyBoard()[0][4].setValue(22);
		board2.getMyBoard()[1][3].setValue(0);

		assertTrue(board2.validateIDs(false, 4, 0, 3, 1, false));
		assertFalse(board2.validateIDs(true, 4, 0, 3, 1, false));

		board2.getMyBoard()[1][3].setValue(11);
		assertFalse(board2.validateIDs(false, 4, 0, 3, 1, false));

	}

}
