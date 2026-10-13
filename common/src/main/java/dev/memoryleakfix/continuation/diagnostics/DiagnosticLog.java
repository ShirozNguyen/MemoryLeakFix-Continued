package dev.memoryleakfix.continuation.diagnostics;

import dev.memoryleakfix.continuation.config.ConfigLoader;

public final class DiagnosticLog {

    private static final String PREFIX = "[MemoryLeakFix:Continued] ";

    private DiagnosticLog() {
    }

    public static void info(String message) {
        System.out.println(PREFIX + message);
    }

    public static void warn(String message) {
        System.err.println(PREFIX + "[WARN] " + message);
    }

    public static void error(String message, Throwable t) {
        System.err.println(PREFIX + "[ERROR] " + message);
        if (t != null) {
            t.printStackTrace();
        }
    }

    public static void debug(String message) {
        if (ConfigLoader.getOrLoad().isDebugLogging()) {
            System.out.println(PREFIX + "[DEBUG] " + message);
        }
    }
}
