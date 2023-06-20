package io.github.ItsMaddieNow.building_tweaks;

import io.github.ItsMaddieNow.building_tweaks.flowers.FlowerTweaks;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuildingTweaks implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String IDHuman = "Maddies Building Tweaks" ;
	public static final String ID = "maddies_building_tweaks" ;
	public static final Logger LOGGER = LoggerFactory.getLogger(IDHuman);

	@Override
	public void onInitialize(ModContainer mod) {
		FlowerTweaks.FlowerInit();
	}
}
