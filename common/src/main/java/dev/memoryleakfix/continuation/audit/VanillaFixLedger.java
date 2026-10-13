package dev.memoryleakfix.continuation.audit;

import dev.memoryleakfix.continuation.config.FeatureId;
import dev.memoryleakfix.continuation.version.MinecraftVersion;
import dev.memoryleakfix.continuation.version.MinecraftVersionRange;

import java.util.EnumMap;
import java.util.Map;

public final class VanillaFixLedger {

    private static final Map<FeatureId, MinecraftVersionRange> AFFECTED_RANGES = new EnumMap<>(FeatureId.class);

    static {
        AFFECTED_RANGES.put(FeatureId.DROWNED_NAVIGATION, MinecraftVersionRange.between("1.16.0", "1.16.5"));
        AFFECTED_RANGES.put(FeatureId.TAG_KEY, MinecraftVersionRange.between("1.18.0", "1.18.2"));
        AFFECTED_RANGES.put(FeatureId.ENTITY_MEMORIES, MinecraftVersionRange.between("1.16.0", "1.19.3"));
        AFFECTED_RANGES.put(FeatureId.TEXTURE_UTIL, MinecraftVersionRange.between("1.16.0", "1.19.3"));
        AFFECTED_RANGES.put(FeatureId.PACKET_PAYLOAD, MinecraftVersionRange.between("1.16.0", "1.20.1"));
        AFFECTED_RANGES.put(FeatureId.BIOME_TEMPERATURE, MinecraftVersionRange.between("1.16.0", "1.20.4"));
        AFFECTED_RANGES.put(FeatureId.CLIENT_TARGET, MinecraftVersionRange.between("1.16.0", "1.20.4"));
        AFFECTED_RANGES.put(FeatureId.SCREENSHOT_IMAGE, MinecraftVersionRange.between("1.16.0", "1.20.4"));
        AFFECTED_RANGES.put(FeatureId.COMBAT_TRACKER, MinecraftVersionRange.from("1.16.0"));
        AFFECTED_RANGES.put(FeatureId.SCREEN_WORLD_REFERENCE, MinecraftVersionRange.from("1.16.0"));
    }

    private VanillaFixLedger() {
    }

    public static MinecraftVersionRange getAffectedRange(FeatureId featureId) {
        if (featureId == null) {
            return null;
        }
        return AFFECTED_RANGES.get(featureId);
    }

    public static boolean isFixedInVanilla(FeatureId featureId, MinecraftVersion currentVersion) {
        MinecraftVersionRange range = getAffectedRange(featureId);
        if (range == null) {
            return false;
        }
        return range.isFixedInVanilla(currentVersion);
    }

    public static boolean shouldApplyForVersion(FeatureId featureId, MinecraftVersion currentVersion) {
        MinecraftVersionRange range = getAffectedRange(featureId);
        if (range == null) {
            return true;
        }
        return range.isAffected(currentVersion);
    }
}
