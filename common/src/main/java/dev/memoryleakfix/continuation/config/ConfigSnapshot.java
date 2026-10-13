package dev.memoryleakfix.continuation.config;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public final class ConfigSnapshot {

    private final Map<FeatureId, Boolean> featureStates;
    private final boolean debugLogging;

    public ConfigSnapshot(Map<FeatureId, Boolean> states, boolean debugLogging) {
        Map<FeatureId, Boolean> copy = new EnumMap<>(FeatureId.class);
        if (states != null) {
            copy.putAll(states);
        }
        for (FeatureId id : FeatureId.values()) {
            copy.putIfAbsent(id, id.isDefaultEnabled());
        }
        this.featureStates = Collections.unmodifiableMap(copy);
        this.debugLogging = debugLogging;
    }

    public static ConfigSnapshot createDefault() {
        return new ConfigSnapshot(Collections.emptyMap(), false);
    }

    public boolean isEnabled(FeatureId featureId) {
        if (featureId == null) {
            return false;
        }
        return featureStates.getOrDefault(featureId, featureId.isDefaultEnabled());
    }

    public boolean isDebugLogging() {
        return debugLogging;
    }

    public Map<FeatureId, Boolean> getFeatureStates() {
        return featureStates;
    }
}
