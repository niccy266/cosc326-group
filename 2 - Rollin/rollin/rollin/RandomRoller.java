package rollin;

import java.util.*;

public class RandomRoller extends rollin {

  public static final Random R = new Random();

  static int count = 0;

  public RandomRoller(int[] dice) {
    super(dice);
  }
  
  public int handleRoll(int roll) {
    int returned_die;
    returned_die = (count % 2) * 5;
    count++;
    // roll is the value of the 7th die
    return returned_die;
  }

}