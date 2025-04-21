package blackdim.mixin;

import blackdim.BlackDim;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.FileWriter;

@Mixin(MinecraftDedicatedServer.class)
public class dedicatedServerAllowMixin {
	@Inject(at = @At("HEAD"), method = "isWorldAllowed", cancellable = true)
	private void allowedDim(World world, CallbackInfoReturnable<Boolean> cir) {
		if (BlackDim.blackList.contains(world.getRegistryKey())) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}
	@Inject(at = @At("HEAD"), method = "exit")
	public void saveDim(CallbackInfo ci) {
		File configFile = new File("config/blacklist_Dim.txt");
		try {
			FileWriter myWriter = new FileWriter(configFile);
			BlackDim.blackList.forEach((dim) -> {
				try {
					myWriter.write(dim.getValue().toString());
				} catch (Exception ignored) {}
			});
			myWriter.close();
		} catch (Exception ignored) {}
	}
}