public abstract class Card {

    private int pointValue;

    public Card(int pointValue) {
        this.pointValue = pointValue;
    }

    public int readValue() {
        return pointValue;
    }

    public abstract void play(Player currentPlayer);
}
