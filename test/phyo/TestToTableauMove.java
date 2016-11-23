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
	
	public void testToTableauColMove() {
		Column dragged = new Column();
		dragged.add(theGame.tableau[0].get());
		
		// move 3 club to the last column
		ToTableauMove m = new ToTableauMove(theGame.tableau[0], theGame.tableau[8], dragged, 1);
		m.doMove(theGame);
		
		// move 3 diamond to the foundation
		ToFoundationMove d3ToFound = new ToFoundationMove(theGame.tableau[1], 
				theGame.aceFoundation[1], theGame.tableau[1].get());
		d3ToFound.doMove(theGame);
		
		// deal second time
		DealNineMove deal2 = new DealNineMove(theGame.deck, theGame.tableau);
		assertTrue(deal2.valid(theGame));
		deal2.doMove(theGame);
		
		// move 5 diamond to the foundation
		ToFoundationMove d5ToFound = new ToFoundationMove(theGame.tableau[0], 
				theGame.aceFoundation[1], theGame.tableau[1].get());
		d5ToFound.doMove(theGame);

		// move 5 diamond to the foundation
		ToFoundationMove d7ToFound = new ToFoundationMove(theGame.tableau[8], 
				theGame.aceFoundation[1], theGame.tableau[8].get());
		d7ToFound.doMove(theGame);
		
		// move 5, 3 clubs to the 8th column
		Column from = theGame.tableau[8];
		Column to = theGame.tableau[7];
		Column draggedCol = new Column();
		draggedCol.add(from.peek(0));
		draggedCol.add(from.peek(1));
		from.removeAll();
		ToTableauMove valColMove = new ToTableauMove(from, to, draggedCol, 2);
		assertTrue(valColMove.valid(theGame));
		
		valColMove.doMove(theGame);
	}
}
