package phyo;

import ks.common.games.Solitaire;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Stack;

public class ToTableauMove extends Move {

	Column from, to, columnBeingDragged;
	int numCards;
	
	public ToTableauMove(Column from, Column to, Column columnBeingDragged, int numCards) {
		super();
		this.from = from;
		this.to = to;
		this.columnBeingDragged = columnBeingDragged;
		this.numCards = numCards;
	}

	@Override
	public boolean doMove(Solitaire game) {
		// Validate
		if (!valid(game)) { return false; }
		
		// Execute
		if (columnBeingDragged == null) {
			from.select(numCards);
			Stack st = from.getSelected();
			to.push(st);
		} else {
			to.push (columnBeingDragged);
		}
		
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		// validation
		if(to.count() <= numCards) { return false; }
		// execution
		to.select(numCards);
		from.push(to.getSelected());
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		Column targetColumn;
		if(columnBeingDragged == null) {
			targetColumn = new Column();
			for(int i=numCards; i>0; i--) {
				targetColumn.add(from.peek(from.count()-i));
			}
		} else {
			targetColumn = columnBeingDragged;
		}
		
		// can't move to empty Column according to the game rules
		if(to.empty()) { return false; }
		
		// if the base card of the column is the same suit and has -2 rank compared to the top card of the source column, make it valid
		if(to.peek().sameSuit(targetColumn.peek(0)) &&
				(to.peek().getRank() == 2+ targetColumn.peek(0).getRank())) {
			return true;
		}
		return false;
	}

}
