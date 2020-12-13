package JUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

import Model.Tile;

public class UpgradeToQueenTest {

	@Test
	public void test() {

		Tile tile1 = new Tile(2, Color.black, 1, 3);
		Tile tile2 = new Tile(1, Color.white, 1, 0);
		Tile tile3 = new Tile(22, Color.black, 1, 1);
		Tile tile4 = new Tile(1, Color.black, 7, 7);
		Tile tile5 = new Tile(2, Color.black, 7, 7);
		Tile tile6 = new Tile(1, Color.black, 2,0);

		assertFalse(tile1.upgradeToQueen());
		assertFalse(tile2.upgradeToQueen());
		assertFalse(tile3.upgradeToQueen());
		assertFalse(tile4.upgradeToQueen());
		assertTrue(tile5.upgradeToQueen());
		assertTrue(tile6.upgradeToQueen());

	}

}
