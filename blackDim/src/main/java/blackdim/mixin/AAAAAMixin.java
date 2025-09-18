package blackdim.mixin;

import blackdim.BlackDim;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PacketByteBuf.class)
public class AAAAAMixin {


    @Shadow @Final private ByteBuf parent;

    @Inject(at = @At(value = "INVOKE", target = "Lio/netty/buffer/ByteBuf;readBytes([B)Lio/netty/buffer/ByteBuf;"), method = "readByteArray(Lio/netty/buffer/ByteBuf;I)[B")
    private static void a(ByteBuf buf, int maxSize, CallbackInfoReturnable<byte[]> cir) {
        BlackDim.LOGGER.warn("C");
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/network/encoding/VarInts;read(Lio/netty/buffer/ByteBuf;)I"), method = "readByteArray(Lio/netty/buffer/ByteBuf;I)[B")
    private static void b(ByteBuf buf, int maxSize, CallbackInfoReturnable<byte[]> cir) {
        BlackDim.LOGGER.warn("B");
    }

    @Inject(at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/network/encoding/VarInts;read(Lio/netty/buffer/ByteBuf;)I"), method = "readByteArray(Lio/netty/buffer/ByteBuf;I)[B", locals = LocalCapture.CAPTURE_FAILHARD)
    private static void c(ByteBuf buf, int maxSize, CallbackInfoReturnable<byte[]> cir, int size) {
        BlackDim.LOGGER.warn("{} / {}", size, maxSize);
    }
}
