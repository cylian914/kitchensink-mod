package fr.tess.disablechat.mixin;

import fr.tess.disablechat.ExampleMod;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(at = @At("HEAD"), method = "stopServer")
    private void stopServerEvent(CallbackInfo ci) {
        ExampleMod.saveConfig();
    }
}
