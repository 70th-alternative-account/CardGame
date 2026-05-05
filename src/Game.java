import java.util.ArrayList;

public class Game {

    // ----------- Settings ----------- //

    // Player settings
    private int startingHandSize;
    private float playerChancesOfPlayingCard; // % chance (0-1) that a player plays a card from their hand

    // Deck settings
    private int totalNumberOfCards;
    private float pointCardChances; // % chance (from 0-1) of generating a point card
    private float attackCardChances; // % chance (from 0-1) of generating an attack card
    private float freezeCardChances; // % chance (from 0-1) of generating a freeze card
    //private float thiefCardChances; // thief card chances are the leftovers

    private float chancesOfDamageCardBeingInDamageDeck; // % chance of a generated damage card being added to the damage-only deck

    // -------- End of Settings ------- //


    // --------- Game Objects --------- //

    private ArrayList<Player> players;
    private ArrayList<Card> mixedDeck; // contains a mix of all types of cards
    private ArrayList<DealsDamage> damageDeck; // contains only cards that implement DealsDamage

    // ------ End of Game Objects ----- //



    public Game() {
        // Player settings
        startingHandSize = 3;
        playerChancesOfPlayingCard = 0.5f; // 50% play card, 50% draw card

        // Deck settings
        totalNumberOfCards = 30;
        chancesOfDamageCardBeingInDamageDeck = 0.2f;

        pointCardChances = 0.5f; // must be between 0 and 1
        attackCardChances = 0.25f; // must be between 0 and 1
        freezeCardChances = 0.15f; // must be between 0 and 1

        // thief card chances should be positive based on the math, but check just to be safe
        float thiefCardChances = 1f - (pointCardChances + attackCardChances + freezeCardChances);
        if (thiefCardChances < 0f) {
            System.out.println("ERROR: Card chances are not all positive.");
            return;
        }

        // Game objects
        players = new ArrayList<Player>();
        mixedDeck = new ArrayList<Card>();
        damageDeck = new ArrayList<DealsDamage>();


        // Generate the decks
        for (int i = 0; i < totalNumberOfCards; i++) {

            float randomValue = Rand.random(); // 0.0 -> 0.999...

            // % chance of creating a point card
            if (randomValue < pointCardChances) {
                mixedDeck.add(new PointCard());
            }

            // % chance of creating an attack card
            else if (randomValue < pointCardChances + attackCardChances) {
                AttackCard newAttackCard = new AttackCard();

                if (Rand.random() < chancesOfDamageCardBeingInDamageDeck) {
                    damageDeck.add(newAttackCard);
                } else {
                    mixedDeck.add(newAttackCard);
                }
            }

            // % chance of creating a freeze card
            else if (randomValue < pointCardChances + attackCardChances + freezeCardChances) {
                FreezeCard newFreezeCard = new FreezeCard();

                if (Rand.random() < chancesOfDamageCardBeingInDamageDeck) {
                    damageDeck.add(newFreezeCard);
                } else {
                    mixedDeck.add(newFreezeCard);
                }
            }

            // % chance of creating a thief card
            else {
                mixedDeck.add(new ThiefCard());
            }
        }
    }

    public void registerPlayer(Player player) {
        players.add(player);
    }

    public void run() {

        // deal cards to each player
        int cardsAdded = 0;
        while (cardsAdded < startingHandSize) {
            for (int i = 0; i < players.size(); i++) {
                int randomCardIndex = Rand.randomInt(0, mixedDeck.size());
                mixedDeck.remove(randomCardIndex);
                Card randomCard = mixedDeck.get(randomCardIndex);
                players.get(i).addCardToHand(randomCard);
            }
            cardsAdded += 1;
        }

        // game loop -- loop as long as either deck has cards
        while (mixedDeck.size() > 0 || damageDeck.size() > 0) {
            if (Rand.random() < playerChancesOfPlayingCard) {
                // play a card

            }
            else {
                // draw a card

            }
        }

    }

}
