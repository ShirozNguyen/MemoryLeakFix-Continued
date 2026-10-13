package dev.memoryleakfix.continuation.cleaner.entity;

import dev.memoryleakfix.continuation.diagnostics.DiagnosticLog;

import java.util.List;

public final class CombatTrackerCleanHandler {

    private CombatTrackerCleanHandler() {
    }

    public static void cleanCombatEntries(List<?> entries) {
        if (entries != null && !entries.isEmpty()) {
            DiagnosticLog.debug("Cleared " + entries.size() + " lingering combat entries.");
            entries.clear();
        }
    }
}
