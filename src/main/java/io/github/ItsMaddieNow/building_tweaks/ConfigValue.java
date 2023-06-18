package io.github.ItsMaddieNow.building_tweaks;

import org.quiltmc.config.api.values.TrackedValue;

import java.util.List;

@SuppressWarnings("unchecked")
public class ConfigValue {
	public static final TrackedValue<Boolean> FLOWERSENABLED = (TrackedValue<Boolean>) BuildingTweaks.CONFIG.getValue(List.of("enableFlowers"));
	public static final TrackedValue<Boolean> CRACKINGENABLED = (TrackedValue<Boolean>) BuildingTweaks.CONFIG.getValue(List.of("enableCracking"));
}
