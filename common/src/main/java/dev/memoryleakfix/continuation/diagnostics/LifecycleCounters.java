package dev.memoryleakfix.continuation.diagnostics;

import java.util.concurrent.atomic.AtomicLong;

public final class LifecycleCounters {

    private static final AtomicLong BIOME_TEMPERATURE_CACHES_CLEARED = new AtomicLong(0);
    private static final AtomicLong TARGET_REFERENCES_CLEARED = new AtomicLong(0);
    private static final AtomicLong SCREENSHOT_BUFFERS_FREED = new AtomicLong(0);
    private static final AtomicLong TOTAL_CLEANUP_ACTIONS = new AtomicLong(0);

    private LifecycleCounters() {
    }

    public static long incrementBiomeTemperatureCleans() {
        TOTAL_CLEANUP_ACTIONS.incrementAndGet();
        return BIOME_TEMPERATURE_CACHES_CLEARED.incrementAndGet();
    }

    public static long incrementTargetCleans() {
        TOTAL_CLEANUP_ACTIONS.incrementAndGet();
        return TARGET_REFERENCES_CLEARED.incrementAndGet();
    }

    public static long incrementScreenshotBufferFreed() {
        TOTAL_CLEANUP_ACTIONS.incrementAndGet();
        return SCREENSHOT_BUFFERS_FREED.incrementAndGet();
    }

    public static long getBiomeTemperatureCleans() {
        return BIOME_TEMPERATURE_CACHES_CLEARED.get();
    }

    public static long getTargetCleans() {
        return TARGET_REFERENCES_CLEARED.get();
    }

    public static long getScreenshotBuffersFreed() {
        return SCREENSHOT_BUFFERS_FREED.get();
    }

    public static long getTotalCleanupActions() {
        return TOTAL_CLEANUP_ACTIONS.get();
    }

    public static void resetAll() {
        BIOME_TEMPERATURE_CACHES_CLEARED.set(0);
        TARGET_REFERENCES_CLEARED.set(0);
        SCREENSHOT_BUFFERS_FREED.set(0);
        TOTAL_CLEANUP_ACTIONS.set(0);
    }
}
