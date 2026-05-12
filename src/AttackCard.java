import java.util.ArrayList;

public class AttackCard extends Card implements DealsDamage {

    private final int damage;

    public AttackCard() {

        // Attack card settings
        int minDamage = 5;
        int maxDamage = 8;
        this.damage = Rand.randomInt(minDamage, maxDamage + 1);

        // Points gained from playing this card
        int minPoints = 3;
        int maxPoints = 4;
        int pointValue = Rand.randomInt(minPoints, maxPoints + 1);

        super(pointValue);
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        currentPlayer.addPoints(super.getPointValue());

        System.out.println(currentPlayer.getName() + " played " + this);
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getNumPoints() + " points.");

        // choose a target player (and not the current player)
        if (allPlayers.size() < 2) {
            System.out.println("Error: No other players for the AttackCard to damage.");
            return;
        }

        Player otherPlayer = currentPlayer.selectAnotherPlayer(allPlayers);
        doDamage(currentPlayer, otherPlayer);
    }

    @Override
    public void doDamage(Player currentPlayer, Player playerToDamage) {
        playerToDamage.removePoints(damage);
        System.out.println("\n" + currentPlayer.getName() + " did " + damage + " damage to " + playerToDamage.getName() + ".");
        System.out.println(playerToDamage.getName() + " now has " + playerToDamage.getNumPoints() + " points.");
    }

    @Override
    public String toString() {
        return "Attack Card {point value: " + super.getPointValue() + ", damage: " + damage + "}";
    }
}
