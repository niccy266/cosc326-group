package rollin;

import java.util.*;

public class CompareTest {

    public static final Random R = new Random();
    public static final int testCount = 1000000;
    public static int[] rolls = new int[testCount];

    public static void main(String[] args) {
        int sum = 0;
        for (int rollNo = 0; rollNo < testCount; rollNo++) {
            int[] dice = new int[6];
            for (int i = 0; i < dice.length; i++) {
                dice[i] = R.nextInt(6) + 1;
            }
            Arrays.sort(dice);

            // change this to the roller class to test
            Rollin roller = new GroupofFiveRoller(dice);
            //System.out.println(Arrays.toString(dice));

            int count = 0;
            while (!roller.isComplete()) {
                int roll = R.nextInt(6) + 1;
                int i = roller.handleRoll(roll);
                count++;
                //System.out.println("Rolled " + roll + " used at " + i);
                if (0 <= i && i < 6) {
                    dice[i] = roll;
                }
                Arrays.sort(dice);
                //System.out.println(Arrays.toString(dice));
                roller.setDice(dice);
            }
            //System.out.println("number of rolls: " + count + " run: " + (rollNo + 1) + "\n");
            sum += count;
            rolls[rollNo] = count;
        }
        System.out.println("\naverage number of rolls: " + ((float) sum / testCount));
        int maxIndex = getIndexOfLargest(rolls);
        //System.out.println(Arrays.toString(rolls));
        Arrays.sort(rolls);
        int lt = 0, lq = 0, uq = 0, ut = 0;
        for (int i = 0; i < rolls.length / 4; i++) {
            lt += rolls[i];
            lq += rolls[i + rolls.length / 4];
            uq += rolls[i + 2 * rolls.length / 4];
            ut += rolls[i + 3 * rolls.length / 4];
        }
        System.out.println("lt: " + (float) 4 * lt / testCount + " lq: " + (float) 4 * lq / testCount + "uq: "
                + (float) 4 * uq / testCount + " ut: " + (float) 4 * ut / testCount);
        // System.out.println("mode:" + mode(rolls));
        System.out.println("max" + rolls[testCount - 1] + " index: " + (maxIndex + 1));
        
    }

    public static int mode(int a[]) {
        int maxValue = 0, maxCount = 0;

        for (int i = 0; i < a.length; ++i) {
            int count = 0;
            for (int j = 0; j < a.length; ++j) {
                if (a[j] == a[i])
                    ++count;
            }
            if (count > maxCount) {
                maxCount = count;
                maxValue = a[i];
            }
        }

        return maxValue;
    }

    public static int getIndexOfLargest(int[] array) {
        int largest = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[largest])
                largest = i;
        }
        return largest; // position of the first largest found
    }
}
