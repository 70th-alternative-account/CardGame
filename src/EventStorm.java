import java.util.ArrayList;

public class EventStorm extends Event {

    public static void play(ArrayList<Player> players) {

        System.out.println("\nA storm approaches...");

        // choose player to freeze
        int i = Rand.randomInt(0, players.size());
        Player playerToFreeze = players.get(i);

        // freeze player
        playerToFreeze.freeze();
        System.out.println(playerToFreeze.getName() + " was frozen!");


        // choose player to damage
        i = Rand.randomInt(0, players.size());
        Player playerToDamage = players.get(i);

        // damage player
        int minDamage = 4;
        int maxDamage = 6;
        int damage = Rand.randomInt(minDamage, maxDamage);

        playerToDamage.removePoints(damage);
        System.out.println(playerToDamage.getName() + " sustained " + damage + " damage.");
        System.out.println(playerToDamage.getName() + " now has " + playerToDamage.getNumPoints() + " points.");


    }
}
