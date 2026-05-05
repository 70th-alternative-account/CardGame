public class PointCard extends Card {

    public PointCard() {

        // Attack card settings
        int minPoints = 4;
        int maxPoints = 7;

        int pointValue = Rand.randomInt(minPoints, maxPoints + 1);

        super(pointValue);
    }

    @Override
    public void play(Player currentPlayer) {

    }
}
