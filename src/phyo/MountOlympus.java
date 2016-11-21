package phyo;

import java.awt.Dimension;

import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;
import ks.common.model.Stack;
import ks.common.view.CardImages;
import ks.common.view.ColumnView;
import ks.common.view.DeckView;
import ks.common.view.IntegerView;
import ks.common.view.PileView;
import ks.launcher.Main;

public class MountOlympus extends Solitaire {
	
	public static final int HALF_FOUNDATION = 8;
	public static final int TABLEAU = 9;
	
	// Models
	MultiDeck deck;
	Pile[] aceFoundation = new Pile[HALF_FOUNDATION];
	Pile[] deuceFoundation = new Pile[HALF_FOUNDATION];
	Column[] tableau = new Column[TABLEAU];
	
	// Views
	DeckView deckView;
	PileView[] aceFoundationView = new PileView[HALF_FOUNDATION];
	PileView[] deuceFoundationView = new PileView[HALF_FOUNDATION];
	ColumnView[] tableauView = new ColumnView[TABLEAU];
	IntegerView scoreView, numLeftView;
	

	@Override
	public String getName() {
		return "Mount Olympus";
	}

	@Override
	public boolean hasWon() {
		return getScore().getValue() == 88;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension (1280, 768);
	}

	@Override
	public void initialize() {
		initModel();
		initView();
		initControllers();
	}

	private void initModel() {
		// Initialize deck
		deck = new MultiDeck(2);
		deck.create(seed);
		model.addElement(deck);
		// removing aces and twos for the foundation
		removeAcesTwos();
		
		// Initialize Foundation
		for(int i=0; i<HALF_FOUNDATION; i++) {
			// add ace piles
			Card ace = new Card(Card.ACE, getSuit(i));
			Pile acePile = new Pile();
			acePile.add(ace);
			aceFoundation[i] = acePile;
			model.addElement(acePile);
			
			// add deuce piles
			Card two = new Card(Card.TWO, getSuit(i));
			Pile deucePile = new Pile();
			deucePile.add(two);
			deuceFoundation[i] = deucePile;
			model.addElement(deucePile);
		}
		
		// Initialize Tableau
		for(int i=0; i<TABLEAU; i++) {
			Column column = new Column();
			tableau[i] = column;
			model.addElement(column);
		}
		
		updateNumberCardsLeft(88);		// # of cards left before initial dealt
		updateScore(0);		// initial score 0
	}
	
	
	private int getSuit(int pos) {
		switch(pos%4) {
			case 0: return Card.SPADES;
			case 1: return Card.CLUBS;
			case 2: return Card.DIAMONDS;
			case 3: return Card.HEARTS;
			default: return -1;
		}
	}

	private void initView() {
		CardImages ci = getCardImages();
		int w = ci.getWidth();
		int h = ci.getHeight();
		
		// initialize deck
		deckView = new DeckView(deck);
		deckView.setBounds(20, 20, ci.getWidth(), ci.getHeight());
		container.addWidget(deckView);
		
		// initialize foundation
		for(int i=0; i<HALF_FOUNDATION; i++) {
			// adding ace widgets
			PileView acePV = new PileView(aceFoundation[i]);
			acePV.setBounds(40+ (i+1)*(20+w), 20, w, h);
			aceFoundationView[i] = acePV;
			container.addWidget(acePV);
			
			// adding deuce widgets
			PileView deucePV = new PileView(deuceFoundation[i]);
			deucePV.setBounds(40+ (i+1)*(20+w), 40+h, w, h);
			deuceFoundationView[i] = deucePV;
			container.addWidget(deucePV);
		}
		
		// initialize tableau
		for(int i=0; i<TABLEAU; i++) {
			ColumnView colView = new ColumnView(tableau[i]);
			tableauView[i] = colView;
			container.addWidget(colView);
		}
		// setting the position of the ColumnViews
		tableauView[0].setBounds((int)(60+0.5*w), 240+2*h, w, 80*h);
		tableauView[1].setBounds((int)(80+1.5*w), 200+2*h, w, 80*h);
		tableauView[2].setBounds((int)(100+2.5*w), 160+2*h, w, 80*h);
		tableauView[3].setBounds((int)(120+3.5*w), 120+2*h, w, 80*h);
		tableauView[4].setBounds((int)(140+4.5*w), 80+2*h, w, 80*h);
		tableauView[5].setBounds((int)(160+5.5*w), 120+2*h, w, 80*h);
		tableauView[6].setBounds((int)(180+6.5*w), 160+2*h, w, 80*h);
		tableauView[7].setBounds((int)(200+7.5*w), 200+2*h, w, 80*h);
		tableauView[8].setBounds((int)(220+8.5*w), 240+2*h, w, 80*h);
		
		// initialize scoreView
		scoreView = new IntegerView(getScore());
		scoreView.setBounds(240+9*w, 20, 100, 60);
		scoreView.setFontSize(14);
		container.addWidget(scoreView);
		
		// initialize numLeftView
		numLeftView=  new IntegerView(getNumLeft());
		numLeftView.setBounds(20, 40+h, 100, 60);
		numLeftView.setFontSize(14);
		container.addWidget(numLeftView);
	}

	private void initControllers() {
		
		// installing controller to DeckView
		deckView.setMouseAdapter(new DealNineController(this, deck, tableau));
		deckView.setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
		
		scoreView.setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
		numLeftView.setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));

	}
	
	private void removeAcesTwos() {
		Stack newDeck = new Stack();
		for(int i=0; i<104; i++) {
			Card c = deck.get();
			if(!(c.getRank()==Card.ACE || c.getRank()==Card.TWO)) {
				newDeck.add(c);
			}
		}
		deck.push(newDeck);
	}

	public static void main(String args[]) {
		// Seed is to ensure we get the same initial cards every time.
		// Here the seed is to "order by suit."
		Main.generateWindow(new MountOlympus(), Deck.OrderByRank);
	}

}
