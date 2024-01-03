package fr.cylian91.unbreakable.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Blocks.class)
public class ExampleMixin {
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/block/AbstractBlock$Settings;strength(F)Lnet/minecraft/block/AbstractBlock$Settings;"), method = "<clinit>",
	slice = @Slice(
			from = @At(value = "INVOKE",target = "Lnet/minecraft/block/SoulFireBlock;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V"),
			to = @At(value = "INVOKE",target = "Lnet/minecraft/block/StairsBlock;<init>(Lnet/minecraft/block/BlockState;Lnet/minecraft/block/AbstractBlock$Settings;)V", ordinal = 0)
	)
	)
	private static AbstractBlock.Settings init(AbstractBlock.Settings instance, float strength) {
		// This code is injected into the start of MinecraftServer.loadWorld()V
		instance.strength(-1.0F, 3600000.0F);
		return instance;
	}
}