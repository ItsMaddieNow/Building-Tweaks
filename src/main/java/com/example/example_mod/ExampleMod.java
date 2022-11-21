package com.example.example_mod;

import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtuoel.statement.api.StateRefresher;

public class ExampleMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Example Mod");
	public static IntProperty FLOWERS = IntProperty.of("flowers",1,4);
	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		for (final Identifier id : Registry.BLOCK.getIds()){
			final Block entry = Registry.BLOCK.get(id);
			if (entry.getClass() == FlowerBlock.class){
				StateRefresher.INSTANCE.addBlockProperty(entry, FLOWERS, 1);
				StateRefresher.INSTANCE.reorderBlockStates();
			}
		}
	}
}
