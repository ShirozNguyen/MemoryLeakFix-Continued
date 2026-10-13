package dev.memoryleakfix.continuation.compat;

import dev.memoryleakfix.continuation.config.ConfigSnapshot;
import dev.memoryleakfix.continuation.config.FeatureId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public final class CompatibilityDecision {

    private final List<CompatibilityRule> registeredRules;

    public CompatibilityDecision() {
        List<CompatibilityRule> rules = new ArrayList<>();
        this.registeredRules = Collections.unmodifiableList(rules);
    }

    public CompatibilityDecision(List<CompatibilityRule> customRules) {
        this.registeredRules = Collections.unmodifiableList(new ArrayList<>(customRules));
    }

    public boolean shouldApply(FeatureId featureId, ConfigSnapshot config, Predicate<String> isModLoadedFn) {
        if (featureId == null || config == null) {
            return false;
        }

        if (!config.isEnabled(featureId)) {
            return false;
        }

        if (isModLoadedFn != null) {
            for (CompatibilityRule rule : registeredRules) {
                if (rule.getTargetFeature() == featureId && rule.getAction() == CompatibilityRule.Action.DISABLE_FEATURE) {
                    if (isModLoadedFn.test(rule.getModId())) {
                        System.out.println("[MemoryLeakFix:Continued] Disabled feature [" + featureId.getKey()
                                + "] due to active mod: " + rule.getModId() + " (" + rule.getReason() + ")");
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public List<CompatibilityRule> getRegisteredRules() {
        return registeredRules;
    }
}
