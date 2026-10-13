package dev.memoryleakfix.continuation.compat;

import dev.memoryleakfix.continuation.config.FeatureId;

public final class CompatibilityRule {

    public enum Action {
        DISABLE_FEATURE,
        WARN_ONLY
    }

    private final String modId;
    private final FeatureId targetFeature;
    private final String minVersion;
    private final String maxVersion;
    private final Action action;
    private final String reason;

    public CompatibilityRule(String modId, FeatureId targetFeature, String minVersion, String maxVersion, Action action, String reason) {
        this.modId = modId;
        this.targetFeature = targetFeature;
        this.minVersion = minVersion;
        this.maxVersion = maxVersion;
        this.action = action;
        this.reason = reason;
    }

    public String getModId() {
        return modId;
    }

    public FeatureId getTargetFeature() {
        return targetFeature;
    }

    public String getMinVersion() {
        return minVersion;
    }

    public String getMaxVersion() {
        return maxVersion;
    }

    public Action getAction() {
        return action;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "CompatibilityRule{modId='" + modId + "', feature=" + targetFeature + ", action=" + action + ", reason='" + reason + "'}";
    }
}
