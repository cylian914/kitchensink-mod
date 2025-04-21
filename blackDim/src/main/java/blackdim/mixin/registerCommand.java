package blackdim.mixin;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import blackdim.blackDimCmd;

@Mixin(CommandManager.class)
public abstract class registerCommand {

    @Shadow public abstract CommandDispatcher<ServerCommandSource> getDispatcher();

    @Inject(at = @At("TAIL"), method = "<init>")
    private void registerCommands(CommandManager.RegistrationEnvironment environment, CommandRegistryAccess commandRegistryAccess, CallbackInfo ci) {
        blackDimCmd.register(getDispatcher());
    }
}
