package phyo;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Pile;
import ks.launcher.Main;

public class TestToFoundationMove extends TestCase {
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

	public void testToFoundationMove() {

		Column from = theGame.tableau[0];
		Pile to = theGame.aceFoundation[0];
		// use get instead of peek because dragging indeed change the column
		Card draggedCard = from.get();

		ToFoundationMove m = new ToFoundationMove(from, to, draggedCard);
		assertTrue(m.valid(theGame));		// check valid move

		// executed move
		m.doMove(theGame);
		assertEquals(0, from.count());
		assertEquals(2, to.count());
		assertEquals(draggedCard, to.peek());
		assertEquals(1, theGame.getScoreValue());

		// executed the undo move
		m.undo(theGame);
		assertEquals(1, from.count());
		assertEquals(1, to.count());
		assertEquals(draggedCard, from.peek());
		assertEquals(0, theGame.getScoreValue());
	}

}
