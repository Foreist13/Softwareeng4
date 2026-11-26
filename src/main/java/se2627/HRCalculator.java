package se2627;

public class HRCalculator {

    public static int calculateMaxHR(int age) {
        if (age < 0 || age > 120) {
            throw new IllegalArgumentException("Age out of bounds");
        }
        return 220 - age;
    }

    public static String getWorkoutZone(int age, int bpm) {
        if (bpm <= 0) {
            throw new IllegalArgumentException("BPM must be positive");
        }
        int maxHR = calculateMaxHR(age);

        // compare bpm / maxHR against each threshold using integers,
        // so exact boundaries (e.g. 60%) are not affected by rounding
        long scaledBpm = 100L * bpm;
        if (scaledBpm < 50L * maxHR) {
            return "Resting";
        } else if (scaledBpm < 60L * maxHR) {
            return "Recovery";
        } else if (scaledBpm < 70L * maxHR) {
            return "Fat Burn";
        } else if (scaledBpm < 80L * maxHR) {
            return "Aerobic";
        } else if (scaledBpm < 90L * maxHR) {
            return "Anaerobic";
        } else {
            return "VO2 Max";
        }
    }
}
