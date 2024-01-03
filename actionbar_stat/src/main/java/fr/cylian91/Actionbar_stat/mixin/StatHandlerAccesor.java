package fr.cylian91.Actionbar_stat.mixin;

import fr.cylian91.Actionbar_stat.Actionbar_stat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.network.packet.s2c.play.EntitiesDestroyS2CPacket;
import net.minecraft.network.packet.s2c.play.StatisticsS2CPacket;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatHandler;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Map;

//MinecraftClient.getInstance().getNetworkHandler().sendPacket(new ClientStatusC2SPacket(Mode.REQUEST_STATS));
@Debug(export = true)
@Mixin(ClientPlayNetworkHandler.class)
public class StatHandlerAccesor {
    @Shadow @Final private static Logger LOGGER;
    @Unique int i;
    @Inject(method = "onStatistics",locals = LocalCapture.CAPTURE_FAILHARD,at = @At(value = "INVOKE",target = "Lnet/minecraft/stat/StatHandler;setStat(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/stat/Stat;I)V"))
    private void getKilled(StatisticsS2CPacket packet, CallbackInfo ci, Iterator var2, Map.Entry entry, Stat stat, int i){
        if (stat.getName().contains("minecraft.custom:minecraft.mob_kills")) {

            this.i+=i;
        }
    }
    @Inject(method = "onStatistics",at = @At(value = "TAIL"))
    private void Show(StatisticsS2CPacket packet, CallbackInfo ci){
        if (i>0)
            MinecraftClient.getInstance().inGameHud.setOverlayMessage(Text.of("Total Kills in World; "+ i), false);
        i=0;
    }
    @Inject(method = "method_37472",at = @At(value = "HEAD"))
    private void updatestat(int entityId, CallbackInfo ci){
        Entity entity = MinecraftClient.getInstance().world.getEntityById(entityId);
        if (entity instanceof LivingEntity)
            if (((LivingEntity) entity).isDead()) {
                MinecraftClient.getInstance().getNetworkHandler().sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.REQUEST_STATS));
            }
    }
}
