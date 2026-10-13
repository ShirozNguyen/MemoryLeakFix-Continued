package dev.memoryleakfix.continuation.version;

import dev.memoryleakfix.continuation.diagnostics.DiagnosticLog;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public final class MinecraftVersionDetector {

    private static MinecraftVersion cachedVersion;

    private MinecraftVersionDetector() {
    }

    public static synchronized MinecraftVersion detectVersion() {
        if (cachedVersion != null) {
            return cachedVersion;
        }

        String versionString = tryReadFromVersionJson();

        if (versionString == null) {
            versionString = tryReadFromFabricLoader();
        }

        if (versionString == null) {
            versionString = tryReadFromNeoForgeLoader();
        }

        if (versionString == null) {
            versionString = tryReadFromForgeLoader();
        }

        if (versionString == null) {
            versionString = System.getProperty("minecraft.version");
        }

        if (versionString == null || versionString.trim().isEmpty()) {
            DiagnosticLog.warn("Unable to detect Minecraft version dynamically, using fallback 1.20.4");
            versionString = "1.20.4";
        }

        cachedVersion = MinecraftVersion.parse(versionString);
        DiagnosticLog.info("Detected Minecraft runtime version: " + cachedVersion.getRawString());
        return cachedVersion;
    }

    private static String tryReadFromVersionJson() {
        try (InputStream in = MinecraftVersionDetector.class.getResourceAsStream("/version.json")) {
            if (in == null) {
                return null;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String content = sb.toString();

                int idKeyIndex = content.indexOf("\"id\"");
                if (idKeyIndex >= 0) {
                    int colonIndex = content.indexOf(':', idKeyIndex);
                    if (colonIndex >= 0) {
                        int firstQuote = content.indexOf('"', colonIndex);
                        if (firstQuote >= 0) {
                            int secondQuote = content.indexOf('"', firstQuote + 1);
                            if (secondQuote > firstQuote) {
                                return content.substring(firstQuote + 1, secondQuote).trim();
                            }
                        }
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        return null;
    }

    private static String tryReadFromFabricLoader() {
        try {
            Class<?> loaderClass = Class.forName("net.fabricmc.loader.api.FabricLoader");
            Object loaderInstance = loaderClass.getMethod("getInstance").invoke(null);
            Method getModContainerMethod = loaderClass.getMethod("getModContainer", String.class);
            Object modContainerOpt = getModContainerMethod.invoke(loaderInstance, "minecraft");

            if (modContainerOpt instanceof java.util.Optional) {
                java.util.Optional<?> opt = (java.util.Optional<?>) modContainerOpt;
                if (opt.isPresent()) {
                    Object container = opt.get();
                    Object metadata = container.getClass().getMethod("getMetadata").invoke(container);
                    Object version = metadata.getClass().getMethod("getVersion").invoke(metadata);
                    return (String) version.getClass().getMethod("getFriendlyString").invoke(version);
                }
            }
        } catch (Throwable ignored) {
        }
        return null;
    }

    private static String tryReadFromNeoForgeLoader() {
        try {
            Class<?> fmlLoaderClass = Class.forName("net.neoforged.fml.loading.FMLLoader");
            Method versionInfoMethod = fmlLoaderClass.getMethod("versionInfo");
            Object versionInfo = versionInfoMethod.invoke(null);
            return (String) versionInfo.getClass().getMethod("mcVersion").invoke(versionInfo);
        } catch (Throwable ignored) {
        }
        return null;
    }

    private static String tryReadFromForgeLoader() {
        try {
            Class<?> fmlLoaderClass = Class.forName("net.minecraftforge.fml.loading.FMLLoader");
            Method versionInfoMethod = fmlLoaderClass.getMethod("versionInfo");
            Object versionInfo = versionInfoMethod.invoke(null);
            return (String) versionInfo.getClass().getMethod("mcVersion").invoke(versionInfo);
        } catch (Throwable ignored) {
        }
        return null;
    }
}
