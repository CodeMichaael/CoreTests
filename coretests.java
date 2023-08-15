package me.michael

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class TestingConsole {

    public static void main(String[] args) {
        console();
    }

    public static void console() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("(Core Testing Console) Enter a valid file path: ");
        String filePath = scanner.nextLine();
        System.out.print("(Core Testing Console) Would you like to log the results? (y/n): ");
        String logChoice = scanner.nextLine();
        boolean log = logChoice.equalsIgnoreCase("y");

        boolean success = newTestingSet(filePath, log);

        if (success) {
            System.out.println("Code execution passed!");
        } else {
            System.out.println("Code execution failed.");
        }
    }

    public static boolean newTestingSet(String filePath, boolean log) {
        boolean success = true; // Assume success initially

        try {
            String code = Files.readString(Paths.get(filePath));
            executeCode(code);
        } catch (IOException e) {
            success = false;
            logError(e, log);
        }

        return success;
    }

    public static void executeCode(String code) {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("js"); // Use JavaScript engine

        try {
            engine.eval(code);
        } catch (ScriptException e) {
            throw new RuntimeException("Code execution failed.", e);
        }
    }

    public static void logError(Exception e, boolean log) {
        System.err.println("(Error): " + e.getClass().getSimpleName() + ": " + e.getMessage() + " [" + new Date() + "]");

        if (log) {
            String logFileName = "Error_" + new Date().getTime() + ".txt";
            String logMessage = "(Error): " + e.getClass().getSimpleName() + ": " + e.getMessage();

            try {
                Files.write(Paths.get(logFileName), logMessage.getBytes());
                System.out.println("Logged Error Successfully.");
            } catch (IOException ex) {
                System.err.println("Failed to log error: " + ex.getMessage());
            }
        }
    }
}
