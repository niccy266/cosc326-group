package rollin;

import java.util.*;

public class WilliamRoller extends Rollin {

  public static final Random R = new Random();

  static int count = 0;
  static int values[] = new int[7];
  static int working[] = new int[6];
  static int diceValue[] = new int[6];
  static int numCount[] = new int[6];

  public WilliamRoller(int[] dice) {
    super(dice);
  }
         
  public int handleRoll(int roll) {
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 6; j++) {
        working[j] = dice[j];
      }
      // iterates through and sees if a solution can be found where one of the dies can
      // be replaced by the roll to be complete
      if (i != 6) {
        working[i] = roll;
      }

      if (new WilliamRoller(working).isComplete()) {
        return i;
      }

      values[i] = calculateValues(roll);
    }
    ;

    int best = 0;
    int bestVal = values[0];
    for (int i = 1; i < 7; i++) {
      if (bestVal < values[i]) {
        best = i;
        bestVal = values[i];
      }
    }

    return best;
  }

  public static int calculateValues(int roll) {
    for (int j = 0; j < 6; j++) {
      numCount[j] = 0;
    }
    for (int j = 0; j < 6; j++) {
      numCount[working[j] - 1]++;
    }

    int val = 0;
    for (int j = 0; j < 6; j++) {
      val += diceValue(j);
    }

    return val;
  }

  public static int diceValue(int i) {
    int num = working[i] - 1;
    int val = 0;
    if (!(num - 1 < 0 || num + 1 > 5) && ((numCount[num - 1] != 0 || numCount[num + 1] != 0)))
      val++;
    if (!(num - 2 < 0) && (numCount[num - 2] != 0 || numCount[num - 1] != 0))
      val++;
    if (!(num + 2 > 5) && (numCount[num + 1] != 0 || numCount[num + 2] != 0))
      val++;
    val += (numCount[num] - 1 > 2) ? 2 : numCount[num] - 1;

    return val;
  }
}