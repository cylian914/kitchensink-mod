package blackdim.mixin;

import blackdim.BlackDim;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

@Mixin(MinecraftServer.class)
public class serverAllowMixin {
    @Inject(at = @At("TAIL"), method = "loadWorld")
    private void loadConfig(CallbackInfo ci) {
        File configFile = new File("config/blacklist_Dim.txt");
        try {
            if (configFile.createNewFile()) {
                return;
            }
            Scanner reader = new Scanner(configFile);
            while (reader.hasNextLine()) {
                BlackDim.blackList.add(RegistryKey.of(RegistryKeys.WORLD, Identifier.of(reader.nextLine())));
            }
            reader.close();
        } catch (Exception e) {};
    }

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
