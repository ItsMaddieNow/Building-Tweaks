package com.example.example_mod;

import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

public class Config {
	public static boolean isAllowed(Block block){
		ExampleMod.LOGGER.info(block.toString());
		return !(block.toString().contains("byg"));
	}
}
