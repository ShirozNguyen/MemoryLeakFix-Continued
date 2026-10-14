package dev.memoryleakfix.continuation;

import dev.memoryleakfix.continuation.config.ConfigLoader;
import dev.memoryleakfix.continuation.config.ConfigSnapshot;
import dev.memoryleakfix.continuation.diagnostics.DiagnosticLog;

public final class MemoryLeakFixCommon {

    public static final String MOD_ID = "memoryleakfix";
    public static final String MOD_NAME = "Memory Leak Fix: Continued";
    public static final String VERSION = "1.0.8";

    private static boolean initialized = false;

    private MemoryLeakFixCommon() {
    }

    public static synchronized void init() {
        if (initialized) {
            return;
        }
        initialized = true;

        DiagnosticLog.info("Initializing " + MOD_NAME + " v" + VERSION);
        ConfigSnapshot config = ConfigLoader.getOrLoad();
        DiagnosticLog.debug("Loaded configuration: " + config);
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
