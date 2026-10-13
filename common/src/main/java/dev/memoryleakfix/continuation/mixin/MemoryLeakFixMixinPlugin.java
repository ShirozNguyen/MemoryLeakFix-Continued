package dev.memoryleakfix.continuation.mixin;

import dev.memoryleakfix.continuation.config.ConfigLoader;
import dev.memoryleakfix.continuation.config.ConfigSnapshot;
import dev.memoryleakfix.continuation.config.FeatureId;
import dev.memoryleakfix.continuation.diagnostics.DiagnosticLog;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MemoryLeakFixMixinPlugin implements IMixinConfigPlugin {

    private ConfigSnapshot configSnapshot;

    @Override
    public void onLoad(String mixinPackage) {
        this.configSnapshot = ConfigLoader.getOrLoad();
        DiagnosticLog.info("MemoryLeakFixMixinPlugin loaded configuration.");
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (configSnapshot == null) {
            configSnapshot = ConfigLoader.getOrLoad();
        }

        if (mixinClassName.contains(".mixin.biome.BiomeTemperatureCacheMixin")) {
            return configSnapshot.isEnabled(FeatureId.BIOME_TEMPERATURE);
        }
        if (mixinClassName.contains(".mixin.target.ClientTargetReferenceMixin")) {
            return configSnapshot.isEnabled(FeatureId.CLIENT_TARGET);
        }
        if (mixinClassName.contains(".mixin.screenshot.HugeScreenshotBufferMixin")) {
            return configSnapshot.isEnabled(FeatureId.SCREENSHOT_IMAGE);
        }
        if (mixinClassName.contains(".mixin.entity.CombatTrackerLeakMixin")) {
            return true;
        }
        if (mixinClassName.contains(".mixin.screen.ScreenWorldReferenceMixin")) {
            return configSnapshot.isEnabled(FeatureId.SCREEN_WORLD_REFERENCE);
        }

        if (mixinClassName.contains("entityMemoriesLeak") ||
            mixinClassName.contains("tagKeyLeak") ||
            mixinClassName.contains("drownedNavigationLeak") ||
            mixinClassName.contains("readResourcesLeak")) {
            DiagnosticLog.debug("Skipping obsolete upstream mixin patched in Vanilla: " + mixinClassName);
            return false;
        }

        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
