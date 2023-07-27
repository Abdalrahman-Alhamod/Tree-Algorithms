import java.util.Locale;

public class ExecutionTimeCalculator {
    private static long startTime;

    private static long executionTime;

    public static void start() {
        startTime = System.nanoTime();
    }

    public static void stop() {
        executionTime = System.nanoTime() - startTime;
    }

    public static long getExecutionTime() {
        return executionTime;
    }

    public static String getFormattedExecutionTime() {
        double executionTimeSeconds = executionTime / 1_000_000_000.0; // Convert nanoseconds to seconds
        return String.format(Locale.US, "%.6f s", executionTimeSeconds);
    }
}
