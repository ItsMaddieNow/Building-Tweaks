package com.example.example_mod;

import net.minecraft.block.Block;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.TagKey;
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
	public static final String IDHuman = "Flourishing Flowers" ;
	public static final String ID = "flourishing-flowers" ;
	public static final Integer MAX_FLOWERS = 4;
	public static final Logger LOGGER = LoggerFactory.getLogger(IDHuman);
	public static IntProperty FLOWERS = IntProperty.of("flowers",1,MAX_FLOWERS);
	public static final TagKey<Block> MULTI_FLOWER = TagKey.of(Registry.BLOCK_KEY, new Identifier(ID,"multi-flower"));
	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		for (final Identifier id : Registry.BLOCK.getIds()){
			final Block entry = Registry.BLOCK.get(id);
			if (FlowerIDs.binarySearch(id)){
				StateRefresher.INSTANCE.addBlockProperty(entry, FLOWERS, 1);
				StateRefresher.INSTANCE.reorderBlockStates();
			}
		}
	}
}
