package phyo;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Pile;
import ks.launcher.Main;

public class TestDealNineMove extends TestCase {

	MountOlympus theGame;
	GameWindow gw;

	@Override
	protected void setUp() {
		theGame = new MountOlympus();
		gw = Main.generateWindow(theGame, Deck.OrderByRank);
	}

	@Override
	protected void tearDown() {
		gw.dispose();
	}

	public void testDealNineMove() {
		Card[] cards = new Card[9];
		for(int i=1; i<=9; i++) {
			cards[i-1] = theGame.deck.peek(theGame.deck.count()-i);
		}

		DealNineMove m = new DealNineMove(theGame.deck, theGame.tableau);
		assertTrue(m.valid(theGame));

		// executed the move
		m.doMove(theGame);
		// checking the cards count from deck
		assertEquals(88-9, theGame.deck.count());
		// checking the cards count from theGame
		assertEquals(88-9, theGame.getNumLeft().getValue());
		// checking the top cards in the tableau
		for(int i=0; i<9; i++) {
			assertEquals(cards[i], theGame.tableau[i].peek());
		}

		// executed the undo move
		m.undo(theGame);
		assertEquals(88, theGame.deck.count());
		assertEquals(88, theGame.getNumLeft().getValue());
	}
}
