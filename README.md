# ⚡ Memory Leak Fix: Continued ⚡
### High-Performance Memory Retention & JVM Leak Mitigations for Minecraft

<!-- ===== BUILD & STATUS BADGES ===== -->
[![Java 21](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/built-with/java21_46h.png)](https://www.oracle.com/java/)
[![Gradle](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/built-with/gradle_46h.png)](https://gradle.org)
[![License: LGPL v3](https://img.shields.io/badge/License-LGPL_v3-blue.svg)](https://www.gnu.org/licenses/lgpl-3.0)
[![CI Build](https://img.shields.io/badge/Build-GitHub_Actions-brightgreen?logo=github-actions)](https://github.com/ShirozNguyen/MemoryLeakFix-Continued/actions)
[![Modrinth](https://img.shields.io/badge/Modrinth-memoryleakfix--continued-green?logo=modrinth)](https://modrinth.com/mod/memoryleakfix-continued)

<!-- ===== SUPPORTED PLATFORMS ===== -->
[![Fabric](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/fabric_vector.svg)](https://fabricmc.net)
[![Quilt](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/quilt_vector.svg)](https://quiltmc.org)
[![NeoForge](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/neoforge_vector.svg)](https://neoforged.net)
[![Forge](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/forge_vector.svg)](https://files.minecraftforge.net)

---

> [!IMPORTANT]
> **Community Continuation Notice:**  
> This project is an independent, community-driven continuation of the original, archived **[MemoryLeakFix](https://github.com/Carpertheim/MemoryLeakFix)** mod created by **Carpertheim** (Cartman).  
> All credits for the foundational research and original mitigation concepts belong to the original author and upstream contributors.

---

## 📖 Overview

**Memory Leak Fix: Continued** is an advanced optimization mod designed to locate, isolate, and eradicate retained heap references, lingering static caches, and unreleased off-heap/native resources across both the **Minecraft Client** and **Dedicated Server**.

Unlike traditional "catch-all" optimization patches, this project adheres to an uncompromising **Evidence-Driven Engineering Protocol**:
- Every single patch is isolated into its own independent class with a dedicated toggle.
- Upstream changes are audited against modern Minecraft source code to ensure **zero redundant injections**.
- Mixins fail-closed and cleanly cooperate with leading performance mods like **Sodium**, **FerriteCore**, and **ModernFix**.

---

## 🌟 Key Architectural Features

### 🧩 1. Strict Single-Responsibility Subsystems (Modular Architecture)
* **No Monolithic Mixins:** Every individual leak mitigation is implemented in its own isolated class pair:
  - `*LeakMixin.java`: Narrow, surgical bytecode injection hook.
  - `*CleanHandler.java`: Pure lifecycle-managed cleanup execution.
* **Granular Config Toggles:** Every mitigation can be toggled independently via configuration (`config/memoryleakfix.toml`).

### 🛡️ 2. Zero-Redundant-Patches Policy
* Fixes implemented in older versions (1.17–1.19) that have already been resolved by Mojang in newer releases (1.20.x, 1.21.x+) are excluded or disabled by default.
* Avoids bytecode collisions and eliminates unnecessary runtime overhead.

### 🔄 3. Smart Lifecycle Cleansing
* **World / Dimension Transitions:** Clears dangling level, player, and entity listeners when leaving a world or disconnecting from a server.
* **Screen & GUI References:** Drops strong references held by screens, tooltips, and rendering caches upon menu closure.
* **Native & Sound Engine Resources:** Gracefully releases unallocated sound sources and `NativeImage` bytebuffers without triggering `NullPointerException` or double-free errors.

### ⚡ 4. High-Performance Multi-Loader Support
* Built from the ground up for modern loaders: **Fabric**, **Quilt**, **NeoForge**, and **Forge Legacy**.
* Automated multi-matrix CI pipeline for rapid compilation, verification, and artifact distribution.

---

## 🎯 Targeted Memory Leak Mitigations

| Subsystem | Target Defect | Mitigation Approach | Side |
| :--- | :--- | :--- | :--- |
| **BiomeProvider** | Static references to biome arrays retained after world close | Cleans cached biome provider references on server/client stop | Both |
| **BakedQuad Cache** | Model baking quads permanently stored in unmanaged tables | WeakReference transformation & cache clearance on reload | Client |
| **Sound Engine** | OpenAL sound `Source` channels not returned to pool | Explicit source disposal on sound system stop/reload | Client |
| **Screen Reference** | Last-opened screen holding pointers to world/entity instances | Nullifies retained screen references upon title screen return | Client |
| **Texture & NativeImage** | DirectByteBuffer leaks during skin/banner/texture unloading | Ensures deterministic closure of native pixel buffers | Client |
| **CompletableFuture** | Async task chains keeping strong references to world tick | Flushes completed stage listeners across chunk/dimension unloads | Both |

---

## 🌐 Supported Versions & Platform Matrix

| Platform | Supported Minecraft Versions | Status |
| :--- | :--- | :--- |
| **Fabric** | `1.20.x` &bull; `1.21.x` &bull; `Latest Stable` | 🟢 Active |
| **Quilt** | `1.20.x` &bull; `1.21.x` &bull; `Latest Stable` | 🟢 Active |
| **NeoForge** | `1.20.4` &bull; `1.21.x` &bull; `Latest Stable` | 🟢 Active |
| **Forge** | `1.18.2` &bull; `1.19.4` &bull; `1.20.1` | 🟡 Maintenance |

---

## 🛠️ Building from Source

### Prerequisites
* **Java Development Kit (JDK) 21** or higher.
* Git.

### Build Steps
```bash
# Clone the repository
git clone https://github.com/ShirozNguyen/MemoryLeakFix-Continued.git
cd MemoryLeakFix-Continued

# Build all loader targets
./gradlew build

# Build specific loader target
./gradlew :fabric:build
./gradlew :neoforge:build
./gradlew :forge:build
```
Compiled artifacts will be located in each subproject's `build/libs/` directory.

---

## 📜 License & Credits

* **Original Author:** [Carpertheim (Cartman)](https://github.com/Carpertheim) & [FxMorin](https://github.com/FxMorin)
* **Continuation Maintainer:** Community Contributors
* **License:** Licensed under the [GNU Lesser General Public License v3.0 (LGPLv3)](https://www.gnu.org/licenses/lgpl-3.0).
