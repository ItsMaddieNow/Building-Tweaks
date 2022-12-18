package io.github.ItsMaddieNow.flourishing_flowers;

import com.google.gson.JsonObject;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.loot.*;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.registry.api.event.RegistryMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import virtuoel.statement.api.StateRefresher;

import java.util.Objects;

import static net.devtech.arrp.json.loot.JLootTable.*;

public class FlourishingFlowers implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String IDHuman = "Flourishing Flowers" ;
	public static final String ID = "flourishing-flowers" ;
	public static final Integer MAX_FLOWERS = 4;
	public static final Logger LOGGER = LoggerFactory.getLogger(IDHuman);
	public static IntProperty FLOWERS = IntProperty.of("flowers",1,MAX_FLOWERS);

	public static RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(ID+":multi-flowers");
	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		var monitor = RegistryMonitor.create(Registry.BLOCK).filter(context -> allowedFlower(context.id(),context.value()));
		RRPCallback.AFTER_VANILLA.register(a -> a.add(RESOURCE_PACK));
		monitor.forAll(context -> {
			Identifier id = context.id();
			StateRefresher.INSTANCE.addBlockProperty(context.value(), FLOWERS, 1);
			StateRefresher.INSTANCE.reorderBlockStates();
			JEntry Functions = entry().type("minecraft:item").name(id.toString());
			for (int i = 2; i <= MAX_FLOWERS; i ++){
				JsonObject flowers = new JsonObject();
				flowers.addProperty("flowers", Integer.toString(i));
				Functions = Functions.function(function("minecraft:set_count")
						.parameter("count",(float) i)
						.parameter("add", false)
						.condition(predicate("minecraft:block_state_property")
								.parameter("block" , id.toString())
								.parameter("properties",flowers))
				);
			}
			RESOURCE_PACK.addLootTable(new Identifier(id.getNamespace()+":blocks/"+id.getPath()), JLootTable.loot("minecraft:block")
					.pool(new JPool()
							.bonus(0)
							.rolls(1)
							.entry(Functions)
					)
			);



		});
	}
	public static boolean allowedFlower(Identifier id, Block block){
		return block instanceof FlowerBlock && !(Objects.  equals(id.getNamespace(), "botania"));
	}
	public static boolean allowedFlower(Block block){
		return allowedFlower(Registry.BLOCK.getId(block),block);
	}
}