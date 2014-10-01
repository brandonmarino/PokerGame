import java.util.*;
/**
 * A poker hand is a list of cards, which can be of some "kind" (pair, straight, etc.)
 * 
 */
public class Hand implements Comparable<Hand> {

    public enum Kind {HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, 
        FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH}

    private final List<Card> cards;
    public static final char MAX_CARD = 5;
    /**
     * Create a hand from a string containing all cards (e,g, "5C TD AH QS 2D")
     */
    public static void main(String[] args){
    	Hand hand = new Hand("7D 5C 6H 8C 8D");
    	if (hand.isTwoPair())
    		System.out.println("Yup");
    	else 
    		System.out.println("No");
    }
    /**
     * @returns true if the hand has n cards of the same rank
	 * e.g., "TD 9C 9H 8C 7D" returns True for n=2 and n=3, and False for n=1 and n=4
     */ 
    public boolean containsRank(List<Card.Rank> ranks, Card.Rank newRank){
    	for (int i = 0; i<ranks.size(); i++)
    		if (ranks.get(i) == newRank)
    			return true;
    	return false;
    }
    protected boolean hasNKind(int n) {	//English: see if a given amount exists in cards, return true
    	List<Card.Rank> ranks = new ArrayList<Card.Rank>(MAX_CARD); //all previously checked ranks
    	int count; // because one of each card will always be found 
    	for(int i = 0; i< MAX_CARD; i++){//iterate through hand
    		if(!containsRank(ranks, cards.get(i).getRank())){ //used to ensure each card rank is only checked once
    			 ranks.add(cards.get(i).getRank());	//get rank of first card in local set, store in list of previous cards
    			 count = 1;	//first card of this type is found
    			 for(int j = i+1; j< MAX_CARD; j++) //compare with every card starting AFTER THIS ONE!!
    				 if (cards.get(i).getRank() == cards.get(j).getRank())
    					 count++;
    			 if (count == n)	//found n of the same rank
    				 return true;
    		}
    	}
    	return false; // This number of matches does not exist
    }
    
    public Hand(String c) {
    	cards = new ArrayList<Card>(MAX_CARD);
        String handRep[] = c.split("\\s+");
        for (int i = 0; i < handRep.length; i++) {
            Card card = new Card(handRep[i]);
            cards.add(card);
        }
    }
    
    
    
    /**
	 * Optional: you may skip this one. If so, just make it return False
     * @returns true if the hand has two pairs
     */
    public boolean isTwoPair() {
    	List<Card.Rank> ranks = new ArrayList<Card.Rank>(MAX_CARD); //all previously checked ranks
    	int count; // because one of each card will always be found 
    	int numPairs=0;
    	for(int i = 0; i< MAX_CARD; i++){//iterate through hand
    		if(!containsRank(ranks, cards.get(i).getRank())){ //used to ensure each card rank is only checked once
    			 ranks.add(cards.get(i).getRank());	//get rank of first card in local set, store in list of previous cards
    			 count = 1;	//first card of this type is found
    			 for(int j = i+1; j< MAX_CARD; j++) //compare with every card starting AFTER THIS ONE!!
    				 if (cards.get(i).getRank() == cards.get(j).getRank())
    					 count++;
    			 if (count == 2)	//found n of the same rank
    				 numPairs++;
    		}
    	}
    	if (numPairs == 2)
    			return true;
    	return false; // This number of matches does not exist
    }
    
    /**
     * @returns true if the hand is a straight 
     * cards appear in sequential order
     */
    public boolean isStraight() {
    	//find lowest card value, ignore ace for now
    	char low = 0;
    	char sequential_number = 0;
    	char numberChecked = 0;
    	for(int i = 0; i< MAX_CARD; i++){
    		
    	}
    	if (low==2)//find ACE
    		for(int i = 0; i< MAX_CARD; i++){
        		if (cards.get(i).getRank() == Card.Rank.ACE){
        			sequential_number++;
        		}
        	}
    	//find card of sequential order
    	
    	for(int i = 0; i< MAX_CARD; i++)
    		for(int j = 0; j < MAX_CARD; j++)
    			if (rankToChar(cards.get(j).getRank()) == low+1){
    				sequential_number++;
    				low++;
    				j=MAX_CARD;
    			}
        return false;
    }
     /** Numerical Value
      */
    public char rankToChar(Card.Rank rank){
    	char charRank = 0;
    	switch (rank){
    		case DEUCE:
    			charRank = 2;
    			break;
    		case THREE:
    			charRank = 3;
    			break;
    		case FOUR:
    			charRank = 4;
    			break;
    		case FIVE:
    			charRank = 5;
    			break;
    		case SIX:
    			charRank = 6;
    			break;
    		case SEVEN:
    			charRank = 7;
    			break;
    		case EIGHT:
    			charRank = 8;
    			break;
    		case NINE:
    			charRank = 9;
    			break;
    		case JACK:
    			charRank = 10;
    			break;
    		case QUEEN:
    			charRank = 11;
    			break;
    		case KING:
    			charRank = 12;
    			break;
    		case ACE:
    			charRank = 13;
    			break;
    	}
    	return charRank;
    }
    /**
     * @returns true if the hand is a flush
     * All cards of one suit
     */
    public boolean isFlush() {
    	Card.Suit mainSuit = cards.get(0).getSuit();
    	for(int i = 1; i<MAX_CARD;i++)
    		if(mainSuit != cards.get(i).getSuit())
    			return false;
        return true;
    }
    
    @Override
    public int compareTo(Hand h) {
    	Kind primaryHand = this.kind();
    	Kind secondaryHand = h.kind();
    	//fill rest with checking which one has the best hand by it's rank
        //hint: delegate!
		//and don't worry about breaking ties
    	return 0;
    }
    
    /**
	 * This method is already implemented and could be useful! 
     * @returns the "kind" of the hand: flush, full house, etc.
     */
    public Kind kind() {
        if (isStraight() && isFlush()) return Kind.STRAIGHT_FLUSH;
        else if (hasNKind(4)) return Kind.FOUR_OF_A_KIND; 
        else if (hasNKind(3) && hasNKind(2)) return Kind.FULL_HOUSE;
        else if (isFlush()) return Kind.FLUSH;
        else if (isStraight()) return Kind.STRAIGHT;
        else if (hasNKind(3)) return Kind.THREE_OF_A_KIND;
        else if (isTwoPair()) return Kind.TWO_PAIR;
        else if (hasNKind(2)) return Kind.PAIR; 
        else return Kind.HIGH_CARD;
    }

}