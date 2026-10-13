package dev.memoryleakfix.continuation.version;

public final class MinecraftVersion implements Comparable<MinecraftVersion> {

    public static final MinecraftVersion UNKNOWN = new MinecraftVersion(0, 0, 0, "unknown");

    private final int major;
    private final int minor;
    private final int patch;
    private final String rawString;

    public MinecraftVersion(int major, int minor, int patch, String rawString) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.rawString = rawString != null ? rawString : major + "." + minor + "." + patch;
    }

    public static MinecraftVersion parse(String versionStr) {
        if (versionStr == null || versionStr.trim().isEmpty()) {
            return UNKNOWN;
        }

        String cleaned = versionStr.trim();
        if (cleaned.startsWith("v") || cleaned.startsWith("V")) {
            cleaned = cleaned.substring(1);
        }

        int dashIndex = cleaned.indexOf('-');
        String numericPart = dashIndex >= 0 ? cleaned.substring(0, dashIndex) : cleaned;

        String[] parts = numericPart.split("\\.");
        try {
            int major = parts.length > 0 ? Integer.parseInt(parts[0]) : 0;
            int minor = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
            int patch = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;
            return new MinecraftVersion(major, minor, patch, versionStr);
        } catch (NumberFormatException ignored) {
            return new MinecraftVersion(0, 0, 0, versionStr);
        }
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    public String getRawString() {
        return rawString;
    }

    public boolean isAtLeast(MinecraftVersion other) {
        return this.compareTo(other) >= 0;
    }

    public boolean isAtMost(MinecraftVersion other) {
        return this.compareTo(other) <= 0;
    }

    public boolean isNewerThan(MinecraftVersion other) {
        return this.compareTo(other) > 0;
    }

    public boolean isOlderThan(MinecraftVersion other) {
        return this.compareTo(other) < 0;
    }

    @Override
    public int compareTo(MinecraftVersion o) {
        if (o == null) {
            return 1;
        }
        if (this.major != o.major) {
            return Integer.compare(this.major, o.major);
        }
        if (this.minor != o.minor) {
            return Integer.compare(this.minor, o.minor);
        }
        return Integer.compare(this.patch, o.patch);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MinecraftVersion)) return false;
        MinecraftVersion that = (MinecraftVersion) obj;
        return major == that.major && minor == that.minor && patch == that.patch;
    }

    @Override
    public int hashCode() {
        int result = major;
        result = 31 * result + minor;
        result = 31 * result + patch;
        return result;
    }

    @Override
    public String toString() {
        return rawString;
    }
}
