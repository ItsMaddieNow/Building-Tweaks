package io.github.ItsMaddieNow.building_tweaks.flowers;

import io.github.ItsMaddieNow.building_tweaks.BuildingTweaks;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.registry.api.event.RegistryMonitor;
import virtuoel.statement.api.StateRefresher;

public class FlowerTweaks {
	public static final Integer MAX_FLOWERS = 4;
	public static IntProperty FLOWERS = IntProperty.of("flowers",1,MAX_FLOWERS);
	public static TagKey<Block> NO_BONEMEAL = TagKey.of(RegistryKeys.BLOCK, new Identifier("maddies_building_tweaks", "no_bonemeal"));
	public static void FlowerInit(){
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
	public static boolean allowedFlower(Block block){
		return block instanceof FlowerBlock;
	}
}
