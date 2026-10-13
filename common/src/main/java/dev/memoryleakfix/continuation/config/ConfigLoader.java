package dev.memoryleakfix.continuation.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.EnumMap;
import java.util.Map;

public final class ConfigLoader {

    private static final String DEFAULT_FILE_PATH = "config/memoryleakfix.toml";
    private static volatile ConfigSnapshot cachedSnapshot = null;

    private ConfigLoader() {
    }

    public static ConfigSnapshot getOrLoad() {
        if (cachedSnapshot == null) {
            synchronized (ConfigLoader.class) {
                if (cachedSnapshot == null) {
                    cachedSnapshot = loadFromFile(new File(DEFAULT_FILE_PATH));
                }
            }
        }
        return cachedSnapshot;
    }

    public static ConfigSnapshot loadFromFile(File file) {
        if (!file.exists()) {
            ensureDirectoryAndCreateDefault(file);
            return ConfigSnapshot.createDefault();
        }

        Map<FeatureId, Boolean> states = new EnumMap<>(FeatureId.class);
        boolean debugLogging = false;

        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                if (line.contains("=")) {
                    String[] parts = line.split("=", 2);
                    String key = parts[0].trim();
                    String val = parts[1].trim().toLowerCase();
                    boolean boolVal = "true".equals(val) || "1".equals(val);

                    if ("debug_logging".equalsIgnoreCase(key)) {
                        debugLogging = boolVal;
                        continue;
                    }

                    FeatureId featureId = FeatureId.fromKey(key);
                    if (featureId != null) {
                        states.put(featureId, boolVal);
                    }
                }
            }
        } catch (Exception e) {
            return ConfigSnapshot.createDefault();
        }

        return new ConfigSnapshot(states, debugLogging);
    }

    private static void ensureDirectoryAndCreateDefault(File file) {
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
                writer.write(ConfigSchema.generateDefaultToml());
            }
        } catch (IOException ignored) {
        }
    }

    public static synchronized void invalidateCache() {
        cachedSnapshot = null;
    }
}
