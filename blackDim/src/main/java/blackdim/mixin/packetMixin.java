package blackdim.mixin;

import blackdim.BlackDim;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.login.LoginHelloS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LoginHelloS2CPacket.class)
public class packetMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketByteBuf;readByteArray()[B"), method = "Lnet/minecraft/network/packet/s2c/login/LoginHelloS2CPacket;<init>(Lnet/minecraft/network/PacketByteBuf;)V")
    private void a(PacketByteBuf buf, CallbackInfo ci) {
        BlackDim.LOGGER.warn("AAAAAAAAAAAAAAAAAA");
    }

}
