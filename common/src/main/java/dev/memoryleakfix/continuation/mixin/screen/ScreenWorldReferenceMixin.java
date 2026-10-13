package dev.memoryleakfix.continuation.mixin.screen;

import dev.memoryleakfix.continuation.cleaner.screen.ScreenWorldReferenceCleanHandler;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class ScreenWorldReferenceMixin {

    @Inject(
            method = "clearLevel()V",
            at = @At("TAIL"),
            require = 0
    )
    private void memoryLeakFix$onClearLevel(CallbackInfo ci) {
        ScreenWorldReferenceCleanHandler.onLevelCleared(null);
    }
}
