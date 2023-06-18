package io.github.ItsMaddieNow.building_tweaks;

import org.quiltmc.config.api.Config;
import org.quiltmc.config.api.ConfigEnvironment;
import org.quiltmc.config.api.WrappedConfig;
import org.quiltmc.config.api.annotations.Comment;

public class TweakConfig extends WrappedConfig {
	@Comment("Enable Multi-Flowers.")
	public final boolean enableFlowers = true;
	@Comment("Enable Multi-Flowers in Pots.")
	public final boolean enableFlowerPots = true;
	@Comment("Enable Block Cracking.")
	public final boolean enableCracking = true;

}
