package dev.memoryleakfix.continuation.mixin;

import dev.memoryleakfix.continuation.audit.VanillaFixLedger;
import dev.memoryleakfix.continuation.config.ConfigLoader;
import dev.memoryleakfix.continuation.config.ConfigSnapshot;
import dev.memoryleakfix.continuation.config.FeatureId;
import dev.memoryleakfix.continuation.diagnostics.DiagnosticLog;
import dev.memoryleakfix.continuation.version.MinecraftVersion;
import dev.memoryleakfix.continuation.version.MinecraftVersionDetector;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MemoryLeakFixMixinPlugin implements IMixinConfigPlugin {

    private ConfigSnapshot configSnapshot;
    private MinecraftVersion currentVersion;

    @Override
    public void onLoad(String mixinPackage) {
        this.configSnapshot = ConfigLoader.getOrLoad();
        this.currentVersion = MinecraftVersionDetector.detectVersion();
        DiagnosticLog.info("MemoryLeakFixMixinPlugin initialized for Minecraft " + currentVersion.getRawString());
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
        if (currentVersion == null) {
            currentVersion = MinecraftVersionDetector.detectVersion();
        }

        FeatureId feature = resolveFeature(mixinClassName);
        if (feature != null) {
            if (VanillaFixLedger.isFixedInVanilla(feature, currentVersion)) {
                DiagnosticLog.info("Skipping mixin [" + mixinClassName + "] because feature [" + feature.getKey()
                        + "] was fixed in Vanilla (current version: " + currentVersion.getRawString() + ")");
                return false;
            }

            if (!VanillaFixLedger.shouldApplyForVersion(feature, currentVersion)) {
                DiagnosticLog.debug("Skipping mixin [" + mixinClassName + "] out of affected range for " + currentVersion.getRawString());
                return false;
            }

            return configSnapshot.isEnabled(feature);
        }

        return true;
    }

    private FeatureId resolveFeature(String mixinClassName) {
        if (mixinClassName == null) {
            return null;
        }
        if (mixinClassName.contains(".mixin.biome.BiomeTemperatureCacheMixin") || mixinClassName.contains("biomeTemperature")) {
            return FeatureId.BIOME_TEMPERATURE;
        }
        if (mixinClassName.contains(".mixin.target.ClientTargetReferenceMixin") || mixinClassName.contains("clientTarget")) {
            return FeatureId.CLIENT_TARGET;
        }
        if (mixinClassName.contains(".mixin.screenshot.HugeScreenshotBufferMixin") || mixinClassName.contains("hugeScreenshot")) {
            return FeatureId.SCREENSHOT_IMAGE;
        }
        if (mixinClassName.contains(".mixin.entity.CombatTrackerLeakMixin") || mixinClassName.contains("combatTracker")) {
            return FeatureId.COMBAT_TRACKER;
        }
        if (mixinClassName.contains(".mixin.screen.ScreenWorldReferenceMixin") || mixinClassName.contains("screenWorldReference")) {
            return FeatureId.SCREEN_WORLD_REFERENCE;
        }
        if (mixinClassName.contains("entityMemoriesLeak")) {
            return FeatureId.ENTITY_MEMORIES;
        }
        if (mixinClassName.contains("tagKeyLeak")) {
            return FeatureId.TAG_KEY;
        }
        if (mixinClassName.contains("drownedNavigationLeak")) {
            return FeatureId.DROWNED_NAVIGATION;
        }
        if (mixinClassName.contains("readResourcesLeak") || mixinClassName.contains("textureUtil")) {
            return FeatureId.TEXTURE_UTIL;
        }
        if (mixinClassName.contains("customPayloadPacket")) {
            return FeatureId.PACKET_PAYLOAD;
        }
        return null;
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
