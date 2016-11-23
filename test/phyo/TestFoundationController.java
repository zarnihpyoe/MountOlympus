package phyo;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;

public class TestFoundationController extends KSTestCase {
	
	MountOlympus theGame;
	GameWindow gw;

	@Override
	protected void setUp() {
		theGame = new MountOlympus();
		gw = Main.generateWindow(theGame, Deck.OrderByRank);
		MouseEvent press = this.createPressed(theGame, theGame.deckView, 0, 0);
		theGame.deckView.getMouseManager().handleMouseEvent(press);
	}

	@Override
	protected void tearDown() {
		gw.dispose();
	}
	
	public void testReleaseLogic() {
		Card c = theGame.tableau[0].peek();
		
		MouseEvent press = this.createPressed(theGame, theGame.tableauView[0], 0, 0);
		theGame.tableauView[0].getMouseManager().handleMouseEvent(press);
		
		MouseEvent release = this.createReleased(theGame, theGame.aceFoundationView[0], 0, 0);
		theGame.aceFoundationView[0].getMouseManager().handleMouseEvent(release);
		
		assertEquals(c, theGame.aceFoundation[0].peek());
		
	}

}
