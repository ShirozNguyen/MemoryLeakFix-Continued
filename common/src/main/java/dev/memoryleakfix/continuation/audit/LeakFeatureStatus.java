package dev.memoryleakfix.continuation.audit;

public enum LeakFeatureStatus {
    UPSTREAM,
    ROOT_CAUSE_VERIFIED,
    VERIFIED,
    DEPRECATED,
    HYPOTHESIS
}
