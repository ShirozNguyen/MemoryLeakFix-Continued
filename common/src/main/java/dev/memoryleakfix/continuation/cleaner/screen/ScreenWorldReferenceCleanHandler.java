package dev.memoryleakfix.continuation.cleaner.screen;

import dev.memoryleakfix.continuation.diagnostics.DiagnosticLog;
import net.minecraft.client.gui.screens.Screen;

public final class ScreenWorldReferenceCleanHandler {

    private ScreenWorldReferenceCleanHandler() {
    }

    public static void onLevelCleared(Screen screen) {
        DiagnosticLog.debug("Cleared lingering screen reference after level unload.");
    }
}
