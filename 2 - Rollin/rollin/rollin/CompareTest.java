package rollin;

import java.util.*;

public class CompareTest {

    public static final Random R = new Random();
    public static final int testCount = 1000;
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
            Rollin roller = new NicoRoller(dice);
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
            // System.out.println("number of rolls: " + count + " run: " + (rollNo + 1) +"\n");
            sum += count;
            rolls = rolls[rollNo];
        }
        System.out.println("\naverage number of rolls: " + ((float) sum/testCount));
        System.out.println(Arrays.toString(rolls));
    }
}
