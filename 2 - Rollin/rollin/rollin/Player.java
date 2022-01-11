package rollin;

public class Player extends rollin {}

  public static void main(String[] args) {
    int[] dice = new int[6];
    for (int i = 0; i < dice.length; i++) {
      dice[i] = R.nextInt(6) + 1;
    }
    Arrays.sort(dice);
    rollin roller = new RandomRoller(dice);
    System.out.println(Arrays.toString(dice));
    while (!roller.isComplete()) {
      int roll = R.nextInt(6) + 1;
      int i = roller.handleRoll(roll);
      System.out.println("Rolled " + roll + " used at " + i);
      if (0 <= i && i < 6) {
        dice[i] = roll;
      }
      Arrays.sort(dice);
      System.out.println(Arrays.toString(dice));
      roller.setDice(dice);
    }
  }

  }

  public Player() {

  }

  public int handleRoll(int roll) {
    int returned_die;
    // get rid of any 1s or 5s
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