package io.github.ItsMaddieNow.building_tweaks.flowers;

import io.github.ItsMaddieNow.building_tweaks.BuildingTweaks;
import io.github.ItsMaddieNow.building_tweaks.ConfigValue;
import io.github.ItsMaddieNow.building_tweaks.mixin.PotBlockAccessor;
import net.minecraft.block.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.registry.api.event.RegistryMonitor;
import virtuoel.statement.api.StateRefresher;

import java.util.Map;

public class FlowerTweaks {
	public static final Integer MAX_FLOWERS = 4;
	public static IntProperty FLOWERS = IntProperty.of("flowers",1,MAX_FLOWERS);
	public static final Integer MAX_POT_FLOWERS = 5;
	public static IntProperty POT_FLOWERS = IntProperty.of("flowers",1,MAX_POT_FLOWERS);
	public static final TagKey<Block> ALLOWED_POTS = TagKey.of(RegistryKeys.BLOCK, new Identifier(BuildingTweaks.ID, "allowed_pots"));
	public static TagKey<Block> NO_BONEMEAL = TagKey.of(RegistryKeys.BLOCK, new Identifier(BuildingTweaks.ID, "no_bonemeal"));
	public static void FlowerInit(){
		if (ConfigValue.FLOWERPOTS_ENABLED.value()) {
			StateRefresher.INSTANCE.addBlockProperty(Blocks.POTTED_ALLIUM, POT_FLOWERS, 1);
			StateRefresher.INSTANCE.addBlockProperty(Blocks.POTTED_AZURE_BLUET, POT_FLOWERS, 1);
			StateRefresher.INSTANCE.addBlockProperty(Blocks.POTTED_BLUE_ORCHID, POT_FLOWERS, 1);
			StateRefresher.INSTANCE.addBlockProperty(Blocks.POTTED_CORNFLOWER, POT_FLOWERS, 1);
			StateRefresher.INSTANCE.addBlockProperty(Blocks.POTTED_DANDELION, POT_FLOWERS, 1);
			StateRefresher.INSTANCE.addBlockProperty(Blocks.POTTED_LILY_OF_THE_VALLEY, POT_FLOWERS, 1);
			StateRefresher.INSTANCE.addBlockProperty(Blocks.POTTED_ORANGE_TULIP, POT_FLOWERS, 1);
			StateRefresher.INSTANCE.addBlockProperty(Blocks.POTTED_OXEYE_DAISY, POT_FLOWERS, 1);
			StateRefresher.INSTANCE.addBlockProperty(Blocks.POTTED_PINK_TULIP, POT_FLOWERS, 1);
			StateRefresher.INSTANCE.addBlockProperty(Blocks.POTTED_POPPY, POT_FLOWERS, 1);
			StateRefresher.INSTANCE.addBlockProperty(Blocks.POTTED_RED_TULIP, POT_FLOWERS, 1);
			StateRefresher.INSTANCE.addBlockProperty(Blocks.POTTED_WHITE_TULIP, POT_FLOWERS, 1);
			StateRefresher.INSTANCE.addBlockProperty(Blocks.POTTED_WITHER_ROSE, POT_FLOWERS, 1);
		}

		if (ConfigValue.FLOWERS_ENABLED.value()) {
			ResourcePack.init();
			BuildingTweaks.LOGGER.info("Setting up Registry Monitor");
			var monitor = RegistryMonitor.create(Registries.BLOCK).filter(context -> allowedFlower(context.value()));
			monitor.forAll(context -> {
				Identifier id = context.id();
				StateRefresher.INSTANCE.addBlockProperty(context.value(), FLOWERS, 1);
				StateRefresher.INSTANCE.reorderBlockStates();

				ResourcePack.resourceGen(id);
			});
		}
	}
	public static boolean allowedFlower(Block block){
		return block instanceof FlowerBlock;
	}
	public static boolean allowedPot(BlockState state){return state.isIn(ALLOWED_POTS);}
}
