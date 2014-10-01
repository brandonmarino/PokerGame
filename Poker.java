import java.util.*;
/**
 * Poker distributes the hands and can determine the winner
 * 
 * @original author babak 
 * @appended by: Brandon Marino
 * @version 1.0
 */
public class Poker {

    private Collection<Hand> hands;

    /**
     * Create a new game of poker (empty at first)
     */
    public Poker(){
        hands = new ArrayList<Hand>();
    }

    /**
     * Add a new hand
     */
    public void addHand(Hand h) {
        hands.add(h);
    }
    
    /**
     * @return the best hand 
     */
    public Hand bestHand(){
       return Collections.max(hands);
    }
}