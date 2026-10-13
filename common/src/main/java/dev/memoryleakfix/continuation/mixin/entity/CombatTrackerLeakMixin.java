package dev.memoryleakfix.continuation.mixin.entity;

import dev.memoryleakfix.continuation.cleaner.entity.CombatTrackerCleanHandler;
import net.minecraft.world.damagesource.CombatTracker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CombatTracker.class)
public abstract class CombatTrackerLeakMixin {

    @Shadow
    @Final
    private List<?> entries;

    @Shadow
    private boolean inCombat;

    @Inject(
            method = "recheckStatus",
            at = @At("TAIL")
    )
    private void memoryLeakFix$onRecheckStatus(CallbackInfo ci) {
        if (!this.inCombat && this.entries != null && !this.entries.isEmpty()) {
            CombatTrackerCleanHandler.cleanCombatEntries(this.entries);
        }
    }
}
