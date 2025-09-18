package fr.tess.disablechat;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

public final class ExampleMod {
    public static final String MOD_ID = "disablechat";

    public static boolean disableMessage = true;
    public static boolean sendMessage = true;
    public static String disabledMessage = "Chat disable by server";
    public static Set<UUID> allowMessage = new HashSet<>();

    private static final File config = new File("config/disableChat.txt");

    public static void init() {
        loadConfig();
    }

    public static void loadConfig() {
        try {
            if (!config.createNewFile()) {
                try (Scanner myReader = new Scanner(config)) {
                    disableMessage = myReader.nextBoolean(); myReader.nextLine();
                    sendMessage = myReader.nextBoolean(); myReader.nextLine();
                    disabledMessage = myReader.nextLine();
                    while (myReader.hasNextLine()) {
                        allowMessage.add(UUID.fromString(myReader.nextLine()));
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Error loading config, config file ignored");
                }
            } else {
                saveConfig();
            }
        } catch (Exception ignore) {}
    }

    public static void saveConfig() {
        try(Writer file = new FileWriter(config)) {
            file.write(disableMessage + "\n");
            file.write(sendMessage + "\n");
            file.write(disabledMessage);
            for (UUID uuid : allowMessage) {
                file.write(uuid.toString());
            }
        } catch (Exception ignore) {}
    }

    public static void commands(Commands.CommandSelection commandSelection, CommandBuildContext commandBuildContext, CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("disablechat").requires(Commands.hasPermission(2)).then(
                Commands.literal("ignoreList").then(
                        Commands.literal("add").then(Commands.argument("target", EntityArgument.players()).executes(commandContext -> {
                            Collection<ServerPlayer> targets = EntityArgument.getPlayers(commandContext, "target");
                            targets.forEach((player) -> allowMessage.add(player.getUUID()));
                            if (targets.isEmpty())
                                return 0;
                            if (targets.size() == 1)
                                commandContext.getSource().sendSuccess(() -> ((ServerPlayer)targets.toArray()[0]).getName().copy().append(" has been added to the list"), false);
                            else
                                commandContext.getSource().sendSuccess(() -> Component.literal(targets.size() + " player has been added to the list"), false);
                            return targets.size();
                        }))).then(
                        Commands.literal("remove").then(Commands.argument("target", EntityArgument.players()).executes(commandContext -> {
                            Collection<ServerPlayer> targets = EntityArgument.getPlayers(commandContext, "target");
                            targets.forEach((player) -> allowMessage.remove(player.getUUID()));
                            if (targets.isEmpty())
                                return 0;
                            if (targets.size() == 1)
                                commandContext.getSource().sendSuccess(() -> ((ServerPlayer)targets.toArray()[0]).getName().copy().append(" has been removed to the list"), false);
                            else
                                commandContext.getSource().sendSuccess(() -> Component.literal(targets.size() + " player has been removed to the list"), false);
                            return targets.size();
                        })))).then(
                Commands.literal("on").requires(Commands.hasPermission(2)).executes(commandContext -> {
                    disableMessage = true;
                    commandContext.getSource().sendSuccess(() -> Component.literal("Chat disabled"), true);
                    return 1;
                })).then(
                Commands.literal("off").requires(Commands.hasPermission(2)).executes(commandContext -> {
                    disableMessage = false;
                    commandContext.getSource().sendSuccess(() -> Component.literal("Chat enabled"), true);
                    return 1;
                })).then(
                Commands.literal("message").then(
                    Commands.literal("on").requires(Commands.hasPermission(2)).executes(commandContext -> {
                        sendMessage = true;
                        commandContext.getSource().sendSuccess(() -> Component.literal("Feedback enable "), true);
                        return 1;
                    })).then(
                    Commands.literal("off").requires(Commands.hasPermission(2)).executes(commandContext -> {
                        sendMessage = false;
                        commandContext.getSource().sendSuccess(() -> Component.literal("Feedback disable"), true);
                        return 1;
                    })).then(
                    Commands.literal("set").requires(Commands.hasPermission(2)).then(Commands.argument("message", StringArgumentType.string()).executes( commandContext -> {
                        disabledMessage = StringArgumentType.getString(commandContext, "message");
                        commandContext.getSource().sendSuccess(() -> Component.literal("Chat message set to " + disabledMessage), true);
                        return 1;
                    }))))
        );
    }
}
