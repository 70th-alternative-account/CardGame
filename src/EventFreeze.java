import java.util.ArrayList;

public class EventFreeze extends Event {

    public static void play(ArrayList<Player> players) {

        Player playerToFreeze = players.get(Rand.randomInt(0, players.size()));

        playerToFreeze.freeze();
        System.out.println(playerToFreeze.getName() + "was frozen!");

    }
}
