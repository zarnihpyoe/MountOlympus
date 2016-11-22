package phyo;

import java.awt.event.MouseEvent;

import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;

public class FoundationController extends SolitaireReleasedAdapter{

	PileView to;


	public FoundationController(MountOlympus theGame, PileView pv) {
		super(theGame);
		this.to = pv;
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		Container c = theGame.getContainer();

		Widget draggingWidget = c.getActiveDraggingObject();
		if(draggingWidget == Container.getNothingBeingDragged()) {
			System.err.println ("FoundationController::mouseReleased() unexpectedly found nothing being dragged.");
			c.releaseDraggingObject();		
			return;
		}
		
		Widget fromWidget = c.getDragSource();
		if(fromWidget == null) {
			System.err.println ("FoundationController::mouseReleased() somehow no dragSource in container.");
			c.releaseDraggingObject();		
			return;
		}
		
		Pile dest = (Pile) to.getModelElement();
		//ColumnView colView = (ColumnView) fromWidget;
		Column src = (Column) fromWidget.getModelElement();
		//ColumnView draggedColView = (ColumnView) draggingWidget;
		Column cardBeingDragged = (Column) draggingWidget.getModelElement();
		if(cardBeingDragged.count() != 1) {
			System.err.println ("FoundationController::mouseReleased() can't move more than one card to the foundation.");
			fromWidget.returnWidget(draggingWidget);
			c.releaseDraggingObject();
			return;
		}
		
		Move m = new ToFoundationMove(src, dest, cardBeingDragged.get());
		if(m.doMove(theGame)) {
			theGame.pushMove(m);
		} else {
			// Bug here: the card doesn't go back to the source
			fromWidget.returnWidget(draggingWidget);
			src.push(cardBeingDragged);
			System.out.println("Rejected: go back to your place");
		}
		
		c.releaseDraggingObject();
		c.repaint();
	}



}
