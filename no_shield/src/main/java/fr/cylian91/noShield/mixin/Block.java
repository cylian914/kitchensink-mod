package fr.cylian91.noShield.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(LivingEntity.class)
public class Block {
    @Unique float Amount;
    @Inject(method = "damage", at = @At("HEAD"))
    private void noShield$getAmount(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        Amount = amount;
    }
    @ModifyConstant(method = "damage", constant = @Constant(floatValue = 0, ordinal = 2))
    private float noShield$setToOldDamage(float useless){
        return Amount/2;
    }
    @Redirect(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;takeShieldHit(Lnet/minecraft/entity/LivingEntity;)V"))
    private void v(LivingEntity instance, LivingEntity attacker){}
}
