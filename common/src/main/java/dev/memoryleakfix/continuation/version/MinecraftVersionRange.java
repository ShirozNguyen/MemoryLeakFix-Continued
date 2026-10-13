package dev.memoryleakfix.continuation.version;

public final class MinecraftVersionRange {

    private final MinecraftVersion minVersion;
    private final MinecraftVersion maxAffectedVersion;

    public MinecraftVersionRange(MinecraftVersion minVersion, MinecraftVersion maxAffectedVersion) {
        this.minVersion = minVersion != null ? minVersion : MinecraftVersion.UNKNOWN;
        this.maxAffectedVersion = maxAffectedVersion;
    }

    public static MinecraftVersionRange between(String min, String maxAffected) {
        return new MinecraftVersionRange(MinecraftVersion.parse(min), MinecraftVersion.parse(maxAffected));
    }

    public static MinecraftVersionRange from(String min) {
        return new MinecraftVersionRange(MinecraftVersion.parse(min), null);
    }

    public boolean isAffected(MinecraftVersion currentVersion) {
        if (currentVersion == null || currentVersion.equals(MinecraftVersion.UNKNOWN)) {
            return true;
        }

        if (!minVersion.equals(MinecraftVersion.UNKNOWN) && currentVersion.isOlderThan(minVersion)) {
            return false;
        }

        if (maxAffectedVersion != null && currentVersion.isNewerThan(maxAffectedVersion)) {
            return false;
        }

        return true;
    }

    public boolean isFixedInVanilla(MinecraftVersion currentVersion) {
        if (currentVersion == null || currentVersion.equals(MinecraftVersion.UNKNOWN)) {
            return false;
        }

        if (maxAffectedVersion != null) {
            return currentVersion.isNewerThan(maxAffectedVersion);
        }

        return false;
    }

    public MinecraftVersion getMinVersion() {
        return minVersion;
    }

    public MinecraftVersion getMaxAffectedVersion() {
        return maxAffectedVersion;
    }

    @Override
    public String toString() {
        return "[" + minVersion + " -> " + (maxAffectedVersion != null ? maxAffectedVersion : "latest") + "]";
    }
}
