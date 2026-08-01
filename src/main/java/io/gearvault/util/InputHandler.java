package io.gearvault.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Scanner;

public class InputHandler {

    // ----------------------------------------------------------------
    // Set DEBUG = true to read input from the test file instead of
    // the command line. Flip back to false for normal operation.
    // ----------------------------------------------------------------
    private static final boolean DEBUG = false;
    private static final String DEBUG_FILE = "C:\\Users\\jarob\\Documents\\Java\\Claude\\SpringBootAutoWarehouseCLI\\data\\test_input.txt";
    private static final int DEBUG_DELAY_MS = 1000;

    private static final Scanner consoleScanner = new Scanner(System.in);
    private static BufferedReader debugReader = null;
    private static int stepCounter = 0;

    static {
        if (DEBUG) {
            try {
                debugReader = new BufferedReader(new FileReader(DEBUG_FILE));
            } catch (IOException e) {
                System.err.println("[InputHandler] Could not open debug file: " + DEBUG_FILE);
                System.err.println("[InputHandler] Falling back to console input.");
            }
        }
    }

    // ----------------------------------------------------------------
    // Core read — returns the next meaningful line from the active
    // source, skipping blank lines and comment lines (# prefix).
    // ----------------------------------------------------------------
    private static String nextLine() {
        if (DEBUG && debugReader != null) {
            return nextDebugLine();
        }
        return consoleScanner.nextLine();
    }

    private static String nextDebugLine() {
        try {
            String line;
            while ((line = debugReader.readLine()) != null) {
                String trimmed = line.trim();
                // Skip blank lines and comment lines
                if (trimmed.contains("--- TEST")) {
                    stepCounter++;
                    continue;
                }
                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }
                System.out.println("[DEBUG - STEP #" + stepCounter + "] Input: " + trimmed);
                sleep();
                return trimmed;
            }
            // End of file — fall back to console so the app does not crash
            System.out.println("[DEBUG] End of test input file. Switching to console.");
            debugReader.close();
            debugReader = null;
            return consoleScanner.nextLine();
        } catch (IOException e) {
            System.err.println("[InputHandler] Error reading debug file: " + e.getMessage());
            return consoleScanner.nextLine();
        }
    }

    // ----------------------------------------------------------------
    // Public API — mirrors the original interface exactly
    // ----------------------------------------------------------------

    public static String getStringInput() {
        return nextLine();
    }

    public static int getIntegerInput() {
        while (true) {
            String line = nextLine();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number:");
            }
        }
    }

    public static double getDoubleInput() {
        while (true) {
            String line = nextLine();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number:");
            }
        }
    }

    public static LocalDate getDateInput() {
        while (true) {
            String line = nextLine();
            try {
                return LocalDate.parse(line);
            } catch (Exception e) {
                System.out.println("Invalid date. Please use format yyyy-MM-dd:");
            }
        }
    }

    public static boolean getBooleanInput() {
        return "Y".equalsIgnoreCase(nextLine());
    }

    public static void closeInput() {
        consoleScanner.close();
        if (debugReader != null) {
            try {
                debugReader.close();
            } catch (IOException e) {
                System.err.println("[InputHandler] Error closing debug reader: " + e.getMessage());
            }
        }
    }

    public static String formatAsMoney(double value) {
        DecimalFormat df = new DecimalFormat("0.00");
        return "$" + df.format(value);
    }

    // ----------------------------------------------------------------
    // Helpers
    // ----------------------------------------------------------------

    private static void sleep() {
        try {
            Thread.sleep(InputHandler.DEBUG_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}