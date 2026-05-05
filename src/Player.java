import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int numPoints;
    private boolean isFrozen;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<Card>();
        numPoints = 5;
        isFrozen = false;
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public void takeTurn() {
        if (isFrozen) {
            System.out.println("Player '" + name + "' is frozen! Skipping turn.");
            return;
        }

        // play a random card
        int randomCardIndex = Rand.randomInt(0, hand.size());
        Card chosenCard = hand.get(randomCardIndex);
        chosenCard.play(this);

        // discard the card that was just played
        hand.remove(randomCardIndex);

        Input.waitForUserToPressEnter("Press Enter to continue...");
    }
}
