package se2627;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HRCalculatorTest {

    @Test
    public void testValidMaxHR() {
        var result = HRCalculator.calculateMaxHR(25);
        assertEquals(195, result);
    }

    @Test
    public void testInvalidMaxHR_ageTooHigh() {
        assertThrows(IllegalArgumentException.class,
            () -> HRCalculator.calculateMaxHR(500));
    }

    @Test
    public void testInvalidMaxHR_ageTooLow() {
        assertThrows(IllegalArgumentException.class,
            () -> HRCalculator.calculateMaxHR(-1));
    }

    @ParameterizedTest
    @CsvSource({"0, 220", "120, 100"})
    public void testMaxHR_ageBoundaries(int age, int expectedMaxHR) {
        assertEquals(expectedMaxHR, HRCalculator.calculateMaxHR(age));
    }

    // age 20 -> max HR 200, so 1% of max HR = 2 BPM:
    // 50% = 100, 60% = 120, 70% = 140, 80% = 160, 90% = 180
    @ParameterizedTest(name = "age {0}, {1} BPM -> {2}")
    @CsvSource({
        "20,   1, Resting",
        "20,  99, Resting",
        "20, 100, Recovery",
        "20, 119, Recovery",
        "20, 120, Fat Burn",
        "20, 139, Fat Burn",
        "20, 140, Aerobic",
        "20, 159, Aerobic",
        "20, 160, Anaerobic",
        "20, 179, Anaerobic",
        "20, 180, VO2 Max",
        "20, 250, VO2 Max",
        "40, 150, Anaerobic",
    })
    public void testWorkoutZone(int age, int bpm, String expectedZone) {
        assertEquals(expectedZone, HRCalculator.getWorkoutZone(age, bpm));
    }

    @ParameterizedTest(name = "age {0}, {1} BPM -> exception")
    @CsvSource({
        "20,    0",
        "20,  -10",
        "-1,  150",
        "121, 150",
    })
    public void testWorkoutZone_invalidInput(int age, int bpm) {
        assertThrows(IllegalArgumentException.class,
            () -> HRCalculator.getWorkoutZone(age, bpm));
    }
}
