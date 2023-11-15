package fr.cylian91.Bedrock_horse.mixin;

import fr.cylian91.Bedrock_horse.Store;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.util.math.random.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

//yes I like naming file :)
@Debug(export = true)
@Mixin(AbstractHorseEntity.class)
abstract class ExampleMixin {

	@Unique private static final Logger LOGGER = LoggerFactory.getLogger("bedrock_horse");
	@Inject(at = @At(value = "HEAD"),method = "calculateAttributeBaseValue",cancellable = true)
	static private void init(double parentBase, double otherParentBase, double min, double max, Random random, CallbackInfoReturnable<Double> cir){
		Store.parentBase = parentBase;
		Store.otherParentBase=otherParentBase;
	}
	@ModifyVariable(method = "calculateAttributeBaseValue", at= @At("STORE"),ordinal = 0)
	static private double parent(double value){
		return Store.parentBase;
	}
	@ModifyVariable(method = "calculateAttributeBaseValue", at= @At("STORE"),ordinal = 1)
	static private double parent2(double value){
		return Store.otherParentBase;
	}
	@Inject(at = @At(value = "RETURN"),method = "calculateAttributeBaseValue",cancellable = true,locals = LocalCapture.CAPTURE_FAILHARD)
	static private void setRetValue(double parentBase, double otherParentBase, double min, double max, Random random, CallbackInfoReturnable<Double> cir, double d, double e, double f, double g, double h) {
		cir.setReturnValue(h);
		LOGGER.warn(String.valueOf(parentBase) + " + " + String.valueOf(otherParentBase) + " = " + String.valueOf(parentBase+otherParentBase));
		LOGGER.warn(String.valueOf(f));
		LOGGER.warn(String.valueOf(h));
		cir.cancel();
	}
	@Redirect(method = "setChildAttribute",at = @At(value = "INVOKE",target = "Lnet/minecraft/entity/passive/AbstractHorseEntity;getAttributeBaseValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D"))
	private double getAttributeValue(AbstractHorseEntity entity, EntityAttribute attribute){
		return getotherAttributeValue(entity,attribute);
		//LOGGER.warn(String.valueOf(attribute.getTranslationKey()) + entity.getAttributeValue(attribute)+" | " + entity.getAttributeBaseValue(attribute));
		//return entity.getAttributeValue(attribute);
	}
	@Redirect(method = "setChildAttribute",at = @At(value = "INVOKE",target = "Lnet/minecraft/entity/passive/PassiveEntity;getAttributeBaseValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D"))
	private double getotherAttributeValue(PassiveEntity entity, EntityAttribute attribute){
		LOGGER.warn(String.valueOf(attribute.getTranslationKey() + ":" + entity.getAttributeValue(attribute)+" | " + entity.getAttributeBaseValue(attribute)));
		return entity.getAttributeValue(attribute);
	}
}