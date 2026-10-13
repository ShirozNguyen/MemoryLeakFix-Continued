package dev.memoryleakfix.continuation.audit;

import dev.memoryleakfix.continuation.config.FeatureId;

public final class LeakFeatureDescriptor {

    public enum Side {
        CLIENT,
        SERVER,
        BOTH
    }

    private final FeatureId featureId;
    private final String srsId;
    private final String mojiraTicket;
    private final String targetClassName;
    private final Side side;
    private final LeakFeatureStatus status;
    private final String notes;

    public LeakFeatureDescriptor(FeatureId featureId, String srsId, String mojiraTicket, String targetClassName, Side side, LeakFeatureStatus status, String notes) {
        this.featureId = featureId;
        this.srsId = srsId;
        this.mojiraTicket = mojiraTicket;
        this.targetClassName = targetClassName;
        this.side = side;
        this.status = status;
        this.notes = notes;
    }

    public FeatureId getFeatureId() {
        return featureId;
    }

    public String getSrsId() {
        return srsId;
    }

    public String getMojiraTicket() {
        return mojiraTicket;
    }

    public String getTargetClassName() {
        return targetClassName;
    }

    public Side getSide() {
        return side;
    }

    public LeakFeatureStatus getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return "LeakFeatureDescriptor{" +
                "srsId='" + srsId + '\'' +
                ", featureId=" + featureId +
                ", status=" + status +
                ", target='" + targetClassName + '\'' +
                '}';
    }
}
