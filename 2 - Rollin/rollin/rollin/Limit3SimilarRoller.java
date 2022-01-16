package rollin;

import java.util.*;

public class Limit3SimilarRoller extends Rollin {

  public static final Random R = new Random();

  static int count = 0;
  static int vals[] = new int[7];
  static int working[] = new int[6];
  static int diceVals[] = new int[6];
  static int numCount[] = new int[6];

  public Limit3SimilarRoller(int[] dice) {
    super(dice);
  }
  public int handleRoll(int roll) {
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 6; j++) {
        working[j] = dice[j];
      }

      if (i != 6) {
        working[i] = roll;
      }

      if (new Limit3SimilarRoller(working).isComplete()) {
        return i;
      }

      vals[i] = calculateValues(roll);
    }
    ;

    int best = 0;
    int bestVal = vals[0];
    for (int i = 1; i < 7; i++) {
      if (bestVal < vals[i]) {
        best = i;
        bestVal = vals[i];
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
    val += (numCount[num] > 2) ? 2 : numCount[num] - 1;

    return val;
  }

}