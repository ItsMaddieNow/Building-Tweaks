package io.github.ItsMaddieNow.building_tweaks;

import io.github.ItsMaddieNow.building_tweaks.flowers.ResourcePack;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.registry.api.event.RegistryMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtuoel.statement.api.StateRefresher;

public class BuildingTweaks implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String IDHuman = "Maddies Building Tweaks" ;
	public static final String ID = "maddies_building_tweaks" ;
	public static final Logger LOGGER = LoggerFactory.getLogger(IDHuman);
	public static TagKey<Block> NO_BONEMEAL = TagKey.of(RegistryKeys.BLOCK, new Identifier("maddies_building_tweaks", "no_bonemeal"));

	@Override
	public void onInitialize(ModContainer mod) {
		ResourcePack.init();

	}
}
