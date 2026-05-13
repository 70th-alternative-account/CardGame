import java.util.ArrayList;

public class Game {

    // ----------- Settings ----------- //

    // Player settings
    private int startingHandSize;

    private float playerChancesOfPlayingCard; // % chance (0-1) that a player plays a card from their hand
    private float playerChancesOfDrawingFromMixedDeck; // % chance (0-1) that a player draws from the mixed deck
    // private float playerChancesOfDrawingFromDamageDeck; // damage deck chances are the leftovers of the other chances

    // Deck settings
    private int totalNumberOfCards;
    //private float pointCardChances; // point card chances are the leftovers of the other chances
    private float attackCardChances; // % chance (from 0-1) of generating an attack card
    private float freezeCardChances; // % chance (from 0-1) of generating a freeze card
    private float thiefCardChances; // % chance (from 0-1) of generating a thief card
    private float windCardChances; // % chance (from 0-1) of generating a wind card

    private float chancesOfDamageCardBeingInDamageDeck; // % chance of a generated damage card being added to the damage-only deck

    // -------- End of Settings ------- //


    // --------- Game Objects --------- //

    private ArrayList<Player> players;
    private ArrayList<Card> mixedDeck; // contains a mix of all types of cards
    private ArrayList<DealsDamage> damageDeck; // contains only cards that implement DealsDamage

    // ------ End of Game Objects ----- //



    public Game() {
        // Set game settings
        setGameSettings();

        // Game objects
        players = new ArrayList<Player>();
        mixedDeck = new ArrayList<Card>();
        damageDeck = new ArrayList<DealsDamage>();

        // Generate the decks
        generateDecks();
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
                Card randomCard = mixedDeck.get(randomCardIndex);
                mixedDeck.remove(randomCardIndex);
                players.get(i).addCardToHand(randomCard);
            }
            cardsAdded += 1;
        }

        int currentPlayerIndex = -1; // will increase to 0 when the loop starts
        Player currentPlayer;

        // game loop -- loop as long as either deck has cards

        while (mixedDeck.size() > 0 || damageDeck.size() > 0) {

            // switch to next player
            currentPlayerIndex += 1;
            if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0;
            }
            currentPlayer = players.get(currentPlayerIndex);

            System.out.println("\n# cards remaining in Mixed deck: " + mixedDeck.size() + ".");
            System.out.println("# cards remaining in Damage deck: " + damageDeck.size() + ".\n");

            System.out.println("It's " + currentPlayer.getName() + "'s turn.\n");
            currentPlayer.displayStatus();
            Input.waitForUserToPressEnter("\nPress Enter to play " + currentPlayer.getName() + "'s turn.");
            System.out.println();

            // check if the player should be skipped
            if (currentPlayer.isFrozen()) {
                System.out.println(currentPlayer.getName() + " is frozen! Skipping turn.");
                currentPlayer.unfreeze();
                System.out.println("\n" + "----- ----- ----- ----- ----- ----- ----- -----");
                continue; // skips the rest of the body of the loop, and returns to the start of the loop
            }

            // generate a random value to choose a random action
            float randomValue = Rand.random();

            // 1. play a card from player's hand
            if (randomValue < playerChancesOfPlayingCard && currentPlayer.hasCardsInHand()) {
                currentPlayer.playRandomCardFromHand(players);
            }

            // 2. OR draw a card from mixed deck (but don't play it yet)
            else if (damageDeck.size() == 0 || (mixedDeck.size() > 0 && randomValue < playerChancesOfPlayingCard + playerChancesOfDrawingFromMixedDeck)) {
                Object drawnObject = drawRandomCard(mixedDeck);
                Card drawnCard = (Card)drawnObject;
                currentPlayer.addCardToHand(drawnCard);

                System.out.println(currentPlayer.getName() + " drew a " + drawnCard + " from the Mixed deck.");
            }

            // 3. OR draw a card from damage deck and use its damage effect immediately, without getting points
            else {
                Object drawnObject = drawRandomCard(damageDeck);
                DealsDamage damageCard = (DealsDamage)drawnObject;

                System.out.println(currentPlayer.getName() + " drew a " + damageCard + " from the Damage deck.");

                // pick a random player (but not oneself) to apply the damage card to
                Player otherPlayer = currentPlayer.selectAnotherPlayer(players);

                damageCard.doDamage(currentPlayer, otherPlayer);
                if (damageCard instanceof AppliesFreeze) {
                    AppliesFreeze freezeCard = (AppliesFreeze)damageCard;
                    freezeCard.freeze(currentPlayer, otherPlayer);
                }
            }

            Input.waitForUserToPressEnter("\nPress Enter to end " + currentPlayer.getName() + "'s turn.");

            System.out.println("\n" + "----- ----- ----- ----- ----- ----- ----- -----");
        }

        // End game: determine which Player had the most points
        declareWinner();
    }

    // Randomly selects a reference (Card or DealsDamage) from an ArrayList (mixedDeck or damageDeck).
    // Removes the randomly selected reference from the specified ArrayList.
    // Returns the selected reference as an Object (because we don't know what type the ArrayList stores).
    private Object drawRandomCard(ArrayList arrayList) {
        int randomCardIndex = Rand.randomInt(0, arrayList.size());
        Object randomCard = arrayList.remove(randomCardIndex);
        return randomCard;
    }

    // Initializes the settings fields.
    private void setGameSettings() {
        // Player settings
        startingHandSize = 3;
        playerChancesOfPlayingCard = 0.5f; // 50% play card, 25% draw card from mixed, 25% draw card from damage deck and play immediately
        playerChancesOfDrawingFromMixedDeck = 0.25f;
        float playerChancesOfDrawingFromDamageDeck = 1f - (playerChancesOfPlayingCard + playerChancesOfDrawingFromMixedDeck);
        if (playerChancesOfDrawingFromDamageDeck < 0f) {
            System.out.println("ERROR: Chances of different player actions are not all positive.");
        }


        // Deck settings
        totalNumberOfCards = 20;
        chancesOfDamageCardBeingInDamageDeck = 0.4f;

        // all chances must be between 0 and 1
        attackCardChances = 0.25f;
        freezeCardChances = 0.2f;
        thiefCardChances = 0.1f;
        windCardChances = 0.05f;
        //pointCardChances = 0.4f; // chance of card not being any of the above

        // point card chances should be positive based on the math, but check just to be safe
        float pointCardChances = 1f - (attackCardChances + freezeCardChances + thiefCardChances + windCardChances);
        if (pointCardChances < 0f) {
            System.out.println("ERROR: Card chances are not all positive.");
        }
    }

    // Populates the two ArrayLists with random Cards, according to the settings.
    private void generateDecks() {
        for (int i = 0; i < totalNumberOfCards; i++) {

            Card card = generateCard();

            if (card instanceof DealsDamage && Rand.random() < chancesOfDamageCardBeingInDamageDeck) {
                damageDeck.add((DealsDamage) card);
            } else {
                mixedDeck.add(card);
            }

        }
    }

    // Generates a new card using the game's settings.
    public Card generateCard() {

        float randomValue = Rand.random();
        Card card;

        // AWESOME CODE !!

        if (randomValue < attackCardChances) {
            card = new AttackCard(); // % chance of creating an attack card
        }

        else if (randomValue < attackCardChances + freezeCardChances) {
            card = new FreezeCard(); // % chance of creating a freeze card
        }

        else if (randomValue < attackCardChances + freezeCardChances + thiefCardChances) {
            card = new ThiefCard(); // % chance of creating a thief card
        }

        else if (randomValue < attackCardChances + freezeCardChances + thiefCardChances + windCardChances) {
            card = new WindCard(); // % chance of creating a wind card
        }

        else {
            card = new PointCard(); // default card (point card) chances
        }

        return card;
    }

    private void declareWinner() {
        int highestScore = 0;
        Player playerWithHighestScore = null;

        System.out.println("\nFinal Scoreboard:");
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            System.out.println(p.getName() + ": " + p.getNumPoints());

            // update highest score tracker
            if (p.getNumPoints() >= highestScore) {
                highestScore = p.getNumPoints();
                playerWithHighestScore = p;
            }
        }

        System.out.println("Player '" + playerWithHighestScore.getName() + "' wins!");
    }
}
