import java.util.*;
/**
 * A poker hand is a list of cards, which can be of some "kind" (pair, straight, etc.)
 * 
 */
public class Hand implements Comparable<Hand> {

    public enum Kind {HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, 
        FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH}

    private final List<Card> cards;
    /**
     * Create a hand from a string containing all cards (e,g, "5C TD AH QS 2D")
     */
    public Hand(String c) {
        cards = new ArrayList<Card>();
        String handRep[] = c.split("\\s+");
        for (int i = 0; i < handRep.length; i++) {
            Card card = new Card(handRep[i]);
            cards.add(card);
        }
    }

    /**
     * @returns true if the hand has n cards of the same rank
	 * e.g., "TD 9C 9H 8C 7D" returns True for n=2 and n=3, and False for n=1 and n=4
     */ 


    protected boolean hasNKind(int n) {	//English: see if a given amount exists in cards, return true
    	List<Card.Rank> ranks = new ArrayList<Card.Rank>(cards.size()); //all previously checked ranks
    	int count; // because one of each card will always be found 
    	for(int i = 0; i< cards.size(); i++){//iterate through hand
    		if(!containsRank(ranks, cards.get(i).getRank())){ //used to ensure each card rank is only checked once
    			 ranks.add(cards.get(i).getRank());	//get rank of first card in local set, store in list of previous cards
    			 count = 1;	//first card of this type is found
    			 for(int j = i+1; j< cards.size(); j++) //compare with every card starting AFTER THIS ONE!!
    				 if (cards.get(i).getRank() == cards.get(j).getRank())
    					 count++;
    			 if (count == n)	//found n of the same rank
    				 return true;
    		}
    	}
    	return false; // This number of matches does not exist
    }
    /**
     * Side function as part of hasNKind
     */
    public boolean containsRank(List<Card.Rank> ranks, Card.Rank newRank){
        for (int i = 0; i<ranks.size(); i++)
            if (ranks.get(i) == newRank)
                return true;
        return false;
    }

    
    
    
    /**
	 * Optional: you may skip this one. If so, just make it return False
     * @returns true if the hand has two pairs
     */
    public boolean isTwoPair() {
    	List<Card.Rank> ranks = new ArrayList<Card.Rank>(cards.size()); //all previously checked ranks
    	int count; // because one of each card will always be found 
    	int numPairs=0; //iterate when a new pair is found
    	for(int i = 0; i< cards.size(); i++){//iterate through hand
    		if(!containsRank(ranks, cards.get(i).getRank())){ //used to ensure each card rank is only checked once
    			 ranks.add(cards.get(i).getRank());	//get rank of first card in local set, store in list of previous cards
    			 count = 1;	//first card of this type is found
    			 for(int j = i+1; j< cards.size(); j++) //compare with every card starting AFTER THIS ONE!!
    				 if (cards.get(i).getRank() == cards.get(j).getRank())
    					 count++;
    			 if (count == 2)	//found n of the same rank
    				 numPairs++;
    		}
    	}
    	if (numPairs == 2)  //two pairs have been found
    			return true;
    	return false; // This number of matches does not exist
    }
    
    /**
     * @returns true if the hand is a straight 
     * cards appear in sequential order
     */
    public boolean isStraight() {
    	/*inefficient, but a TA instructed that I could not change the internals of Card to add a comparator
         so I decided to find a straight without sorting */
        int numIterated = 1;
        Card lowCard = new Card("AH");
        for (int i = 0; i < cards.size(); i++) // get lowest card
            if (lowCard.getRank().ordinal() > cards.get(i).getRank().ordinal())
                lowCard = cards.get(i);    //this just finds the lowest value
        for (char i = 1; i < cards.size(); i++)
            for(char j = 0; j <cards.size(); j++)
                if(cards.get(j).getRank().ordinal() == lowCard.getRank().ordinal()+i)
                    numIterated++;
        if(numIterated == cards.size())     //everything worked out
            return true;
        else if (numIterated == cards.size()-1)     //it might be 2-5 atm, look for an ace
            if(lowCard.getRank() == Card.Rank.DEUCE)
                for (char i = 0; i < cards.size(); i++) //just look for ace
                    if(cards.get(i).getRank() == Card.Rank.ACE)
                        return true;
        return false;

    }

    /**
     * @returns true if the hand is a flush
     * All cards of one suit
     */
    public boolean isFlush() {
    	Card.Suit mainSuit = cards.get(0).getSuit();
    	for(int i = 1; i<cards.size();i++)
    		if(mainSuit != cards.get(i).getSuit())
    			return false;
        return true;
    }
    
    @Override
    public int compareTo(Hand h) {
        //ordinal value is the inherent value assigned to an enum based on their declared order
        if(kind().ordinal() < h.kind().ordinal())
            return -1;
        if(kind().ordinal() == h.kind().ordinal())
            return 0;
        if(kind().ordinal() > h.kind().ordinal())
            return 1;
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
    /** Internal main, for testing purposes
    */
    public static void main(String[] args){
        Poker game = new Poker();
        game.addHand(new Hand("2H 3H 4H 5H AD"));
        game.addHand(new Hand("TH JH QH KH AH"));
        game.addHand(new Hand("5H 5D 5C 5S 6D"));
        game.addHand(new Hand("6H 6S 6C AC AS"));

        System.out.print("The winning hand is a ");
        switch (game.bestHand().kind()){
            case STRAIGHT_FLUSH:
                System.out.println("Straight Flush");
                break;
            case FOUR_OF_A_KIND:
                System.out.println("Four Of A Kind");
                break;
            case FULL_HOUSE:
                System.out.println("Full House");
                break;
            case FLUSH:
                System.out.println("Flush");
                break;
            case STRAIGHT:
                System.out.println("Straight");
                break;
            case THREE_OF_A_KIND:
                System.out.println("Three Of A Kind");
                break;
            case TWO_PAIR:
                System.out.println("Two Pair");
                break;
            case PAIR:
                System.out.println("Pair");
                break;
            case HIGH_CARD:
                System.out.println("High Card");
                break;
        }

    }
}