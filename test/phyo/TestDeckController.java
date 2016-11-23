package phyo;

import java.awt.event.MouseEvent;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;

public class TestDeckController extends KSTestCase {

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

	public void testPressLogic() {
		// create mouse press at (0,0) within the deckview; should deal card
		MouseEvent press = this.createPressed(theGame, theGame.deckView, 0, 0);
		theGame.deckView.getMouseManager().handleMouseEvent(press);
		Column[] tableau = theGame.tableau;
		assertEquals("3C", tableau[0].peek().toString());
		assertEquals("3D", tableau[1].peek().toString());
		assertEquals("3H", tableau[2].peek().toString());
		assertEquals("3S", tableau[3].peek().toString());
		assertEquals("4C", tableau[4].peek().toString());
		assertEquals("4D", tableau[5].peek().toString());
		assertEquals("4H", tableau[6].peek().toString());
		assertEquals("4S", tableau[7].peek().toString());
		assertEquals("5C", tableau[8].peek().toString());
	}
}
