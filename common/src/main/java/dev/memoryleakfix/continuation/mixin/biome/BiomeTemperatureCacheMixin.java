package dev.memoryleakfix.continuation.mixin.biome;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Supplier;

@Mixin(Biome.class)
public abstract class BiomeTemperatureCacheMixin {

    private static ThreadLocal<Long2FloatLinkedOpenHashMap> memoryLeakFix$staticTempCache;

    @WrapOperation(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/ThreadLocal;withInitial(Ljava/util/function/Supplier;)Ljava/lang/ThreadLocal;"
            )
    )
    private ThreadLocal<Long2FloatLinkedOpenHashMap> memoryLeakFix$wrapStaticThreadLocal(
            Supplier<?> supplier,
            Operation<ThreadLocal<Long2FloatLinkedOpenHashMap>> original
    ) {
        if (memoryLeakFix$staticTempCache == null) {
            memoryLeakFix$staticTempCache = original.call(supplier);
        }
        return memoryLeakFix$staticTempCache;
    }
}
