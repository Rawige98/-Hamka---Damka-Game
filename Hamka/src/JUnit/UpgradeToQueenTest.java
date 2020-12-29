package JUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

import Model.BlackSoldier;
import Model.BlackTile;
import Model.Tile;
import Model.WhiteSoldier;

public class UpgradeToQueenTest {

	@Test
	public void test() {

		Tile tile1 = new BlackTile( 1, 3);
		Tile tile2 = new WhiteSoldier(1, 0);
		Tile tile3 = new BlackSoldier(1, 1);
		tile3.setValue(22);
		Tile tile4 = new WhiteSoldier(7, 7);
		Tile tile5 = new BlackSoldier( 7, 7);
		Tile tile6 = new WhiteSoldier(2,0);

		assertFalse(tile1.upgradeToQueen());
		assertFalse(tile2.upgradeToQueen());
		assertFalse(tile3.upgradeToQueen());
		assertFalse(tile4.upgradeToQueen());
		assertTrue(tile5.upgradeToQueen());
		assertTrue(tile6.upgradeToQueen());

	}

}
