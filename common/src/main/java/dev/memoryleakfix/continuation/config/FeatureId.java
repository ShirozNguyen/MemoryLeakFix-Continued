package dev.memoryleakfix.continuation.config;

public enum FeatureId {
    BIOME_TEMPERATURE("biome_temperature", "MLF-H01", true),
    CLIENT_TARGET("client_target", "MLF-H05", true),
    SCREENSHOT_IMAGE("screenshot_image", "MLF-H07", true),
    ENTITY_MEMORIES("entity_memories", "MLF-H02", false),
    TAG_KEY("tag_key", "MLF-H03", false),
    DROWNED_NAVIGATION("drowned_navigation", "MLF-H04", false),
    TEXTURE_UTIL("texture_util", "MLF-H06", false),
    PACKET_PAYLOAD("packet_payload", "MLF-H08", false),
    COMBAT_TRACKER("combat_tracker", "MC-298464", true),
    BIOME_SOURCE("biome_source", "AUD-01", false),
    BAKED_QUAD_CACHE("baked_quad_cache", "AUD-02", false),
    SOUND_ENGINE_SOURCE("sound_engine_source", "AUD-03", false),
    SCREEN_WORLD_REFERENCE("screen_world_reference", "AUD-04", true),
    COMPLETABLE_FUTURE_CHAIN("completableFuture_chain", "AUD-05", false),
    NATIVE_IMAGE_BUFFER("native_image_buffer", "AUD-06", false);

    private final String key;
    private final String srsId;
    private final boolean defaultEnabled;

    FeatureId(String key, String srsId, boolean defaultEnabled) {
        this.key = key;
        this.srsId = srsId;
        this.defaultEnabled = defaultEnabled;
    }

    public String getKey() {
        return key;
    }

    public String getSrsId() {
        return srsId;
    }

    public boolean isDefaultEnabled() {
        return defaultEnabled;
    }

    public static FeatureId fromKey(String key) {
        if (key == null) return null;
        for (FeatureId id : values()) {
            if (id.key.equalsIgnoreCase(key) || id.name().equalsIgnoreCase(key)) {
                return id;
            }
        }
        return null;
    }
}
