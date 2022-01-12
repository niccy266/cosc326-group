package rollin;

import java.util.*;

public class EndRoller extends Rollin {

    public static final Random R = new Random();

    static int count = 0;

    public EndRoller(int[] dice) {
        super(dice);
    }

    public int handleRoll(int roll) {
        int returned_die;
        // get rid of any 1s or 6s
        if (dice[0] == 1) {
            returned_die = 0;
        } else if (dice[5] == 6) {
            returned_die = 5;
            // then pick something from the ends
        } else if ((roll == 1) || (roll == 6)) {
            // unless you've rolled a 1 or 6
            // in which case skip
            returned_die = 6;
        } else {
            // snip off the end
            returned_die = (count % 2) * 5;
        }
        count++;
        // roll is the value of the 7th die
        return returned_die;
    }

}