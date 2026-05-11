import java.util.ArrayList;

public class EventChaos extends Event {

    public static void play(ArrayList<Player> players) {

        Input.waitForUserToPressEnter("Uh oh.");

        EventFreeze.play(players);
        EventStorm.play(players);

    }
}
