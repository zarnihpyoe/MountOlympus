package phyo;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;

public class ToFoundationMove extends Move {

	Column from;
	Pile to;
	Card cardBeingDragged;
	
	public ToFoundationMove(Column from, Pile to, Card cardBeingDragged) {
		super();
		this.from = from;
		this.to = to;
		this.cardBeingDragged = cardBeingDragged;
	}

	@Override
	public boolean doMove(Solitaire game) {
		if(!valid(game)) { return false; }
		
		if(cardBeingDragged != null) {
			to.add(cardBeingDragged);
		} else {
			to.add(from.get());
		}
		game.updateScore(1);
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		// validation
		if(to.count() <= 1) { return false;	}
		// execution
		from.add(to.get());
		game.updateScore(-1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		Card top = to.peek();
		Card c = cardBeingDragged;
		return (c.sameSuit(top) &&
				c.getRank() == 2+top.getRank());
	}

}
