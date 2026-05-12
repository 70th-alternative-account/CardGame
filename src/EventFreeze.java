import java.util.ArrayList;

public class EventFreeze extends Event {

    public static void play(ArrayList<Player> players) {

        System.out.println("\nThe lands grow colder...");

        // choose player to freeze
        Player playerToFreeze = players.get(Rand.randomInt(0, players.size()));

        // freeze player
        playerToFreeze.freeze();
        System.out.println(playerToFreeze.getName() + " was frozen!");

    }
}
