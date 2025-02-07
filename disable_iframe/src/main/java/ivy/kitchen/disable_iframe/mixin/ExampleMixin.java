package ivy.kitchen.disable_iframe.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ExampleMixin {
	@Shadow private int joinInvulnerabilityTicks;

	@Inject(at = @At("TAIL"), method = "<init>")
	private void init(CallbackInfo info) {
		joinInvulnerabilityTicks = 0;
	}
}