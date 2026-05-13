import java.util.ArrayList;

public class WindCard extends Card {
	public WindCard() {
		super(Rand.randomInt(2,3));
	}
	
	public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
		currentPlayer.addPoints(super.getPointValue());
		
		System.out.println(currentPlayer.getName() + " played " + this);
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getNumPoints() + " points.");
        
        // Shuffle both decks
        // Need to access mixedDeck and damageDeck somehow
        
	}
	
	@Override
	public String toString() {
		return "Wind Card {point value: " + super.getPointValue() + "}";
	}
}
