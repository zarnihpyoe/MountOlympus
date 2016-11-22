package phyo;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.launcher.Main;

public class TestToTableauMove extends TestCase {
	
	MountOlympus theGame;
	GameWindow gw;

	@Override
	protected void setUp() {
		theGame = new MountOlympus();
		gw = Main.generateWindow(theGame, Deck.OrderByRank);
		// deal 9 cards
		DealNineMove deal = new DealNineMove(theGame.deck, theGame.tableau);
		deal.doMove(theGame);
	}

	@Override
	protected void tearDown() {
		gw.dispose();
	}

	public void testToTableauValidMove() {
		Column from = theGame.tableau[0];
		Column to = theGame.tableau[8];
		Column dragged = new Column();
		dragged.add(from.get());
		
		ToTableauMove m = new ToTableauMove(from, to, dragged, 1);
		assertTrue(m.valid(theGame));
		
		m.doMove(theGame);
		assertEquals(0, from.count());
		assertEquals(2, to.count());
		
		m.undo(theGame);
		assertEquals(1, from.count());
		assertEquals(1, to.count());
	}
	
	public void testToTableauInvalidMove() {
		Column from = theGame.tableau[8];
		Column to = theGame.tableau[0];
		Column dragged = new Column();
		dragged.add(from.get());
		
		ToTableauMove m = new ToTableauMove(from, to, dragged, 1);
		assertTrue(!m.valid(theGame));
	}
}
