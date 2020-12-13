package JUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Model.Game;
import Model.Player;

public class ValidateMoveInputTest {

	@Test
	public void test() {

		String move1 = "123456";
		String move2 = "((12";
		String move3 = "(12,4)";
		String move4 = "(1,1)";
		String move5 = "(123)";
		Game game = new Game();
		assertFalse(game.validateMoveInput(move1));
		assertFalse(game.validateMoveInput(move2));
		assertFalse(game.validateMoveInput(move3));
		assertTrue(game.validateMoveInput(move4));
		assertFalse(game.validateMoveInput(move5));
        

	}
}
