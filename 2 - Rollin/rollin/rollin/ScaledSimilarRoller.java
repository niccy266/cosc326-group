package rollin;

import java.util.*;

public class ScaledSimilarRoller extends Rollin {

  public static final Random R = new Random();

  static int count = 0;
  static float vals[] = new float[7];
  static int working[] = new int[6];
  static int diceVals[] = new int[6];
  static int numCount[] = new int[6];

  public ScaledSimilarRoller(int[] dice) {
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

      if (new ScaledSimilarRoller(working).isComplete()) {
        return i;
      }

      vals[i] = calculateValues(roll);
    }
    ;

    int best = 0;
    float bestVal = vals[0];
    for (int i = 1; i < 7; i++) {
      if (bestVal < vals[i]) {
        best = i;
        bestVal = vals[i];
      }
    }

    return best;
  }

  public static float calculateValues(int roll) {
    for (int j = 0; j < 6; j++) {
      numCount[j] = 0;
    }
    for (int j = 0; j < 6; j++) {
      numCount[working[j] - 1]++;
    }

    float val = 0;
    for (int j = 0; j < 6; j++) {
      val += diceValue(j);
    }

    return val;
  }

  public static float diceValue(int i) {
    int num = working[i] - 1;
    float val = 0;
    if (!(num - 1 < 0 || num + 1 > 5) && ((numCount[num - 1] != 0 || numCount[num + 1] != 0)))
      val++;
    if (!(num - 2 < 0) && (numCount[num - 2] != 0 || numCount[num - 1] != 0))
      val++;
    if (!(num + 2 > 5) && (numCount[num + 1] != 0 || numCount[num + 2] != 0))
      val++;
    val += ((float) numCount[num] - 1) / 2;

    return val;
  }

}