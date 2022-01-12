package rollin;

import java.util.*;

public class CompareTest {

    public static long seed;
    public static Random R = new Random();
    public static Scanner scan;
    public static final int testCount = 100000;
    public static Rollin[] rollers;
    public static boolean input = false; // whether to use system input to get dice rolls
    public static int record = -1; // whether to use system input to get dice rolls
    public static int[] dice;

    public static void main(String[] args) {
        String rollerList = "";
        try {
            rollerList = parseArgs(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        test(rollerList);
    }

    public static String parseArgs(String[] args) {
        seed = R.nextLong();

        String rollerList = "";
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "s":
                    System.out.println("Added PreferSimilarRoller");
                    rollerList += "s";
                    break;
                case "l":
                    System.out.println("Added Limit3SimilarRoller");
                    rollerList += "l";
                    break;
                case "c":
                    System.out.println("Added ScaledSimilarRoller");
                    rollerList += "c";
                    break;
                case "e":
                    System.out.println("Added EndRoller");
                    rollerList += "e";
                    break;
                case "h":
                    System.out.println("Run CompareTest in terminal with arguments");
                    System.out.println("-s use PreferSimilarRoller");
                    System.out.println("-l use Limit3SimilarRoller");
                    System.out.println("-c use ScaledSimilarRoller");
                    System.out.println("-e use EndRoller");
                    System.out.println("-r pass a seed for the psuedorandom number generator, give seed as argument");
                    System.out.println("-i use System.in to pass custom dice rolls");
                    System.out.println("-o output a run to file, defaults to output.dice. argument is run to record");
                    System.out.println("-f specify output file");
                    System.out.println("-h show this message");
                    System.exit(0);
                    break;
                case "i":
                    System.err.println("Using System.in to get dice rolls");
                    input = true;
                    scan = new Scanner(System.in);
                    break;
                case "o":
                    record = Integer.parseInt(args[i++]);
                    System.err.println("Recording run " + record);
                    break;
                case "f":
                    System.err.println("Outputting run %s");
                    break;
                case "r":
                    seed = Integer.parseInt(args[i++]);
                    break;

                default:
                    break;
            }
        }

        return rollerList;
    }

    public static void test(String rollerList) {
        System.out.println("Using seed " + seed + " for RNG");
        R.setSeed(seed);
        rollers = new Rollin[rollerList.length()];
        int[] sums = new int[rollerList.length()];
        boolean[] isComplete = new boolean[rollerList.length()];

        int[] longestRun = new int[rollers.length];
        int[] runLength = new int[rollers.length];
        int testLength = testCount;
        for (int runNo = 0; runNo < testCount; runNo++) {
            dice = startDice();

            for (int i = 0; i < rollerList.length(); i++) {
                switch (rollerList.charAt(i)) {
                    case 's':
                        rollers[i] = new PreferSimilarRoller(dice);
                        break;
                    case 'l':
                        rollers[i] = new Limit3SimilarRoller(dice);
                        break;
                    case 'c':
                        rollers[i] = new ScaledSimilarRoller(dice);
                        break;
                    case 'e':
                        rollers[i] = new EndRoller(dice);
                        break;
                }
            }

            int counts[] = new int[rollers.length];
            isComplete = new boolean[rollers.length];
            if (input) {
                try {
                    while (!allTrue(isComplete) && scan.hasNext()) {
                        int roll = scan.nextInt();
                        parseRoll(roll, isComplete, sums, counts);
                    }
                    if (!scan.hasNext()) {
                        testLength = runNo;
                        break; // ends test if there is no more input data
                    }
                } catch (NumberFormatException e) {
                    System.err.println(e.getMessage());
                    System.exit(1);
                }
            } else {
                while (allTrue(isComplete)) {
                    int roll = R.nextInt(6) + 1;
                    parseRoll(roll, isComplete, sums, counts);
                }
            }

            for (int i = 0; i < rollers.length; i++) {
                if (counts[i] > runLength[i]) {
                    longestRun[i] = runNo;
                    runLength[i] = counts[i];
                }
                sums[i] += counts[i];
            }

            // System.out.println("number of rolls: " + count + " run: " + (rollNo + 1)
            // +"\n");
        }

        int average;
        for (int i = 0; i < rollerList.length(); i++) {
            switch (rollerList.charAt(i)) {
                case 's':
                    average = sums[i] / testLength;
                    System.out.print("PreferSimilarRoller scored an average of " + average);
                    break;
                case 'l':
                    average = sums[i] / testLength;
                    System.out.print("Limit3SimilarRoller scored an average of " + average);
                    break;
                case 'c':
                    average = sums[i] / testLength;
                    System.out.print("ScaledSimilarRoller scored an average of " + average);
                    break;
                case 'e':
                    average = sums[i] / testLength;
                    System.out.print("EndRoller scored an average of " + average);
                    break;
            }
        }
    }

    public static int[] startDice() {
        int[] dice = new int[6];
        if (input) {
            try {
                for (int i = 0; i < dice.length; i++) {
                    dice[i] = scan.nextInt();
                }
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        } else {
            for (int i = 0; i < dice.length; i++) {
                dice[i] = R.nextInt(6) + 1;
            }
        }
        Arrays.sort(dice);

        return dice;
    }

    public static boolean allTrue(boolean[] arr) {
        for (boolean b : arr) {
            if (!b)
                return false;
        }
        return true;
    }

    public static void parseRoll(int roll, boolean[] isComplete, int[] sums, int[] counts) {
        int d;

        for (int i = 0; i < rollers.length; i++) {
            // load the next roller
            dice = rollers[i].getDice();

            if (!isComplete[i]) {
                if (rollers[i].isComplete()) {
                    isComplete[i] = true;
                } else {
                    d = rollers[i].handleRoll(roll);

                    // System.out.println("Rolled " + roll + " used at " + i);
                    if (0 <= d && d < 6) {
                        dice[d] = roll;
                    }
                    Arrays.sort(dice);

                    // System.out.println(Arrays.toString(dice));
                    rollers[i].setDice(dice);
                    counts[i]++;
                }
            }
        }
    }
}
