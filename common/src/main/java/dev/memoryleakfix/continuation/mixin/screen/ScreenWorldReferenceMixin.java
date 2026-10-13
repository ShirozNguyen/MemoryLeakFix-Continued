package dev.memoryleakfix.continuation.mixin.screen;

import dev.memoryleakfix.continuation.cleaner.screen.ScreenWorldReferenceCleanHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class ScreenWorldReferenceMixin {

    @Inject(
            method = "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V",
            at = @At("TAIL")
    )
    private void memoryLeakFix$onClearLevel(Screen screen, CallbackInfo ci) {
        ScreenWorldReferenceCleanHandler.onLevelCleared(screen);
    }
}
