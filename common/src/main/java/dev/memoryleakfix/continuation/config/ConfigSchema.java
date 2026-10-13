package dev.memoryleakfix.continuation.config;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public final class ConfigSchema {

    private static final Map<FeatureId, Boolean> DEFAULTS;

    static {
        Map<FeatureId, Boolean> map = new EnumMap<>(FeatureId.class);
        for (FeatureId id : FeatureId.values()) {
            map.put(id, id.isDefaultEnabled());
        }
        DEFAULTS = Collections.unmodifiableMap(map);
    }

    private ConfigSchema() {
    }

    public static Map<FeatureId, Boolean> getDefaultFeatureStates() {
        return DEFAULTS;
    }

    public static String generateDefaultToml() {
        StringBuilder sb = new StringBuilder();
        sb.append("[general]\n");
        sb.append("debug_logging = false\n\n");
        sb.append("[features]\n");

        for (FeatureId id : FeatureId.values()) {
            sb.append(id.getKey()).append(" = ").append(id.isDefaultEnabled()).append("\n");
        }

        return sb.toString();
    }
}
