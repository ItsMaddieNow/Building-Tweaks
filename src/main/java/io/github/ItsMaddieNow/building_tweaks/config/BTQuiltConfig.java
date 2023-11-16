package io.github.ItsMaddieNow.building_tweaks.config;

import io.github.ItsMaddieNow.building_tweaks.BuildingTweaks;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.loader.api.config.v2.QuiltConfig;

public class BTQuiltConfig extends AbstractConfig {
	public static final BTReflectiveConfig CONFIG = QuiltConfig.create(BuildingTweaks.ID, "config", BTReflectiveConfig.class);
	public BTQuiltConfig(){
		update();
		CONFIG.registerCallback(config -> this.update());
	}

	public void update(){
		flowersEnabled = CONFIG.flowerConfig.enabled.value();
		bonemealEnabled = CONFIG.flowerConfig.enable_bonemeal.value();
		potsEnabled = CONFIG.enable_pots.value();
	}

	public static class BTReflectiveConfig extends ReflectiveConfig{
		@Comment("Settings Related To Flowers")
		public final FlowerConfig flowerConfig = new FlowerConfig();
		public final TrackedValue<Boolean> enable_pots = this.value(true);

		public static class FlowerConfig extends Section{
			public final TrackedValue<Boolean> enabled = this.value(true);
			public final TrackedValue<Boolean> enable_bonemeal = this.value(true);
		}

	}
}
