import java.util.ArrayList;

public class EventChaos extends Event {

    public static void play(ArrayList<Player> players) {

        Input.waitForUserToPressEnter("\nuh oh");

        EventFreeze.play(players);
        EventStorm.play(players);

    }
}
