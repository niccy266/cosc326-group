package rollin;

import java.util.*;

public class RandomRoller extends rollin {

  public static final Random R = new Random();

  public RandomRoller(int[] dice) {
    super(dice);
  }
  
  public int handleRoll(int roll) {
    return (roll % 2) * 5;
  }

}