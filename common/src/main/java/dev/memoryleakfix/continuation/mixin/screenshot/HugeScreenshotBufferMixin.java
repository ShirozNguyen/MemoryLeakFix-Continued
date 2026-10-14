package dev.memoryleakfix.continuation.mixin.screenshot;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import org.lwjgl.system.MemoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.ByteBuffer;

@Mixin(Minecraft.class)
public abstract class HugeScreenshotBufferMixin {

    @ModifyExpressionValue(
            method = "grabHugeScreenshot",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/platform/GlUtil;allocateMemory(I)Ljava/nio/ByteBuffer;"
            )
    )
    private ByteBuffer memoryLeakFix$trackByteBuffer(
            ByteBuffer byteBuf,
            @Share("memoryLeakFix$capturedBuffer") LocalRef<ByteBuffer> bufRef
    ) {
        bufRef.set(byteBuf);
        return byteBuf;
    }

    @Inject(
            method = "grabHugeScreenshot",
            at = @At(
                    value = "CONSTANT",
                    args = "stringValue=screenshot.failure"
            )
    )
    private void memoryLeakFix$releaseByteBufferOnFailure(
            CallbackInfoReturnable<Component> cir,
            @Share("memoryLeakFix$capturedBuffer") LocalRef<ByteBuffer> bufRef
    ) {
        ByteBuffer buf = bufRef.get();
        if (buf != null) {
            MemoryUtil.memFree(buf);
            bufRef.set(null);
        }
    }
}
