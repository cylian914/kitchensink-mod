package blackdim;

import net.fabricmc.api.DedicatedServerModInitializer;

import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class BlackDim implements DedicatedServerModInitializer {
	public static final String MOD_ID = "blackdim";

	public static ArrayList<RegistryKey<World>> blackList = new ArrayList<>();

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeServer() {
		//read/create config

	}
}