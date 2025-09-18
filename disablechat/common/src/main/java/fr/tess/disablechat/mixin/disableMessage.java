package fr.tess.disablechat.mixin;

import fr.tess.disablechat.ExampleMod;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class disableMessage {
    @Shadow public ServerPlayer player;
    @Shadow protected abstract void tryHandleChat(String string, Runnable runnable);

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;tryHandleChat(Ljava/lang/String;Ljava/lang/Runnable;)V"), method = "handleChat")
    private void disableMessage(ServerGamePacketListenerImpl instance, String string, Runnable runnable) {
        if (!ExampleMod.disableMessage || ExampleMod.allowMessage.contains(player.getUUID())) {
            tryHandleChat(string, runnable);
            return;
        }
        if (ExampleMod.sendMessage)
            ((ServerGamePacketListenerImpl)(Object)this).send(new ClientboundSystemChatPacket(Component.literal(ExampleMod.disabledMessage).withStyle(ChatFormatting.RED), false));
    }
}
