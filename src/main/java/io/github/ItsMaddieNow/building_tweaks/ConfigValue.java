package io.github.ItsMaddieNow.building_tweaks;

import org.quiltmc.config.api.values.TrackedValue;

import java.util.List;

@SuppressWarnings("unchecked")
public class ConfigValue {
	public static final TrackedValue<Boolean> FLOWERS_ENABLED = (TrackedValue<Boolean>) BuildingTweaks.CONFIG.getValue(List.of("enableFlowers"));
	public static final TrackedValue<Boolean> FLOWERPOTS_ENABLED = (TrackedValue<Boolean>) BuildingTweaks.CONFIG.getValue(List.of("enableFlowerPots"));
	public static final TrackedValue<Boolean> CRACKING_ENABLED = (TrackedValue<Boolean>) BuildingTweaks.CONFIG.getValue(List.of("enableCracking"));
}
