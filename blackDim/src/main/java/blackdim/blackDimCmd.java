package blackdim;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.DimensionArgumentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class blackDimCmd {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("blackDim").requires((source) -> {
            return source.hasPermissionLevel(2);
        }).then(CommandManager.literal("list").executes((context) -> {
            context.getSource().sendFeedback(() -> {
                return Text.literal("List of blacklisted dimension:");
            }, false);
            BlackDim.blackList.forEach((key) -> {
                context.getSource().sendFeedback(() -> {
                    return Text.literal("   " + key.getValue().toString());
                }, false);
            });
            return BlackDim.blackList.size();
        })).then(CommandManager.argument("dim", DimensionArgumentType.dimension()).executes((context -> {
            RegistryKey<World> dim = DimensionArgumentType.getDimensionArgument(context, "dim").getRegistryKey();
            if (BlackDim.blackList.contains(dim)) {
                BlackDim.blackList.remove(dim);
                context.getSource().sendFeedback(() -> {
                    return Text.literal("Removed: " + dim.getValue().toString());
                }, false);
            }
            else {
                BlackDim.blackList.add(dim);
                context.getSource().sendFeedback(() -> {
                    return Text.literal("Added: " + dim.getValue().toString());
                }, false);
            }
            return 1;
        }))));
    }
}
