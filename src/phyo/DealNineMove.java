package phyo;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.MultiDeck;

public class DealNineMove extends Move{
	
	MultiDeck deck;
	Column[] tableau;

	public DealNineMove(MultiDeck deck, Column[] tableau) {
		super();
		this.deck = deck;
		this.tableau = tableau;
	}

	@Override
	public boolean doMove(Solitaire game) {
		if(!valid(game)) { return false; }
		
		int cardsToDeal = tableau.length;
		// if remaining cards is less than # of tableau, just deal what it has
		if(deck.count() < cardsToDeal) {
			cardsToDeal = deck.count();
		}
		
		for(int i=0; i<cardsToDeal; i++) {
			Card c = deck.get();
			tableau[i].add(c);
			game.updateNumberCardsLeft(-1);
		}
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		// validation
		for(Column col : tableau) {
			if(col.empty()) { return false; }
		}
		// add back the cards from the last column
		for(int i=tableau.length-1; i>=0; i--) {
			deck.add(tableau[i].get());
			game.updateNumberCardsLeft(+1);
		}
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return !(deck.count()==0);
	}

}
