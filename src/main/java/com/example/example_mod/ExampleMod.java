package com.example.example_mod;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Example Mod");
	public static final String ID = "example_mod";
	public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(ID+":flowers");
	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		for (FlowerBlock flower:Shared.FlowersBlocks) {
			LOGGER.info(Registry.BLOCK.getId((Block) flower).toString());
		}
	}
}
