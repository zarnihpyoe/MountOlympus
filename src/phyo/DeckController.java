package phyo;

import java.awt.event.MouseEvent;

import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.games.Solitaire;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.MultiDeck;

public class DeckController extends SolitaireReleasedAdapter {
	
	MultiDeck deck;
	Column[] tableau;
	
	public DeckController(Solitaire theGame, MultiDeck deck, Column[] tableau) {
		super(theGame);
		this.deck = deck;
		this.tableau = tableau;
	}
	
	@Override
	public void mousePressed(MouseEvent me) {
		Move m = new DealNineMove(deck, tableau);
		if (m.doMove(theGame)) {
			theGame.pushMove(m);
			theGame.refreshWidgets();
		}
	}
	
	

}
