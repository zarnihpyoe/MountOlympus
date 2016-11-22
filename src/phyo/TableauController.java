package phyo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.Widget;

public class TableauController extends MouseAdapter{

	MountOlympus theGame;
	ColumnView src;
	
	public TableauController(MountOlympus theGame, ColumnView cv) {
		super();
		this.theGame = theGame;
		this.src = cv;
	}
	
	@Override
	public void mousePressed(MouseEvent me) {
		Container c = theGame.getContainer();
		
		/** Return if there is no card to be chosen. */
		Column from = (Column) src.getModelElement();
		if (from.count() == 0) {
			return;
		}
		
		ColumnView draggedColView = src.getColumnView(me);
		if(draggedColView == null) {
			return;
		}
		
		Column draggedCol = (Column) draggedColView.getModelElement();
		
		// verify all the cards being dragged have same suit and are in the interval of two
		if(!isValidColumnToDrag(draggedCol)) {
			from.push(draggedCol);		// put the dragged column back to the source column
			return;
		}
		
		
		// now we know that everything is valid, so we prepare to add the draggedCol to the container
		Widget w = c.getActiveDraggingObject();
		if (w != Container.getNothingBeingDragged()) {
			System.err.println ("TableauController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
			return;
		}
		
		c.setActiveDraggingObject (draggedColView, me);
		c.setDragSource (src);
		src.redraw();
	}
	
	
	
	
	@Override
	public void mouseReleased(MouseEvent me) {
		Container c = theGame.getContainer();
		
		/** Return if there is no card being dragged chosen. */
		Widget w = c.getActiveDraggingObject();
		if(w == Container.getNothingBeingDragged()) {
			c.releaseDraggingObject();
			return;
		}
		
		Widget fromWidget = c.getDragSource();
		if (fromWidget == null) {
			System.err.println ("TableauController::mouseReleased(): somehow no dragSource in container.");
			c.releaseDraggingObject();
			return;
		}
		
		Column to = (Column) src.getModelElement();
		Column from = (Column) fromWidget.getModelElement();
		Column columnBeingDragged = (Column) w.getModelElement();
		
		if(columnBeingDragged == null) {
			System.err.println ("TableauController::mouseReleased(): somehow ColumnView model element is null.");
			return;
		}
		
		if(fromWidget == src) {
			to.push(columnBeingDragged);		// simply push back to the same column
		} else {
			Move m = new ToTableauMove(from, to, columnBeingDragged, columnBeingDragged.count());
			
			if(m.doMove(theGame)) {
				theGame.pushMove(m);
			} else {
				from.push(columnBeingDragged);
			}
		}
		c.releaseDraggingObject();
		c.repaint();
		
	}
	
	
	
	
	
	
	
	private boolean isValidColumnToDrag(Column draggedCol) {
		
		for(int i=1; i<draggedCol.count(); i++) {
			Card pre = draggedCol.peek(i-1);
			Card cur = draggedCol.peek(i);
			if(!(pre.sameSuit(cur) && pre.getRank() == 2+ cur.getRank())) {
				return false;
			}
		}
		return true;
	}

}
