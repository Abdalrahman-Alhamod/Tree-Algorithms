import java.util.Locale;

/**
 * The ExecutionTimeCalculator class provides utility methods
 * to calculate and format the execution time of a piece of code.
 * It uses the System.nanoTime() method to measure the time in nanoseconds.
 * The class supports calculating and formatting execution time in seconds.
 * The formatted execution time is printed in the "0.001232 s"
 * format using the Locale.US to ensure a dot as the decimal separator.
 */
public class ExecutionTimeCalculator {

    // The starting time of the execution.
    private static long startTime;

    // The total execution time in nanoseconds.
    private static long executionTime;

    /**
     * Starts measuring the execution time.
     * This method sets the start time of the execution to the current system time in nanoseconds.
     */
    public static void start() {
        startTime = System.nanoTime();
    }

    /**
     * Stops measuring the execution time.
     * This method calculates the total execution time in nanoseconds
     * by subtracting the start time from the current system time.
     */
    public static void stop() {
        executionTime = System.nanoTime() - startTime;
    }

    /**
     * Gets the total execution time in nanoseconds.
     *
     * @return The total execution time in nanoseconds.
     */
    public static long getExecutionTime() {
        return executionTime;
    }

    /**
     * Gets the formatted execution time in seconds.
     * The execution time is converted from nanoseconds to seconds and formatted with six decimal places (0.001232 s).
     *
     * @return The formatted execution time in seconds (e.g., "0.001232 s").
     */
    public static String getFormattedExecutionTime() {
        double executionTimeSeconds = executionTime / 1_000_000_000.0; // Convert nanoseconds to seconds
        return String.format(Locale.US, "%.6f s", executionTimeSeconds);
    }

    /**
     * Prints the formatted execution time in seconds to the console.
     * This method calls getFormattedExecutionTime()
     * to get the formatted execution time and then prints it to the console.
     */
    public static void printExecutionTime() {
        System.out.println("Execution Time: " + getFormattedExecutionTime());
    }
}
