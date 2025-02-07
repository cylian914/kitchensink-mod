package fr.cylian91.vanillia_gem.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Items.class)
public class ExampleMixin {

	@Inject(at = @At("TAIL"), method = "<clinit>")
	private static void init(CallbackInfo info) {
		Items.register("vanillia_gem:raw_diamond", new Item(new Item.Settings()));
		Items.register("vanillia_gem:raw_emerald", new Item(new Item.Settings()));
	}
}