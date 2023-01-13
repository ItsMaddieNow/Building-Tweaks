package io.github.ItsMaddieNow.building_tweaks;

import com.google.gson.JsonObject;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.loot.*;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.TagKey;
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

public class BuildingTweaks implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String IDHuman = "Maddies Building Tweaks" ;
	public static final String ID = "maddies_building_tweaks" ;
	public static final Integer MAX_FLOWERS = 4;
	public static final Logger LOGGER = LoggerFactory.getLogger(IDHuman);
	public static IntProperty FLOWERS = IntProperty.of("flowers",1,MAX_FLOWERS);
	public static TagKey<Block> NO_BONEMEAL = TagKey.of(Registry.BLOCK_KEY, new Identifier("maddies_building_tweaks", "no_bonemeal"));
	public static RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(ID+":multi-flowers");
	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Registering Virtual Resourcepack");
		RRPCallback.AFTER_VANILLA.register(a -> a.add(RESOURCE_PACK));
		LOGGER.info("Setting up Registry Monitor");
		var monitor = RegistryMonitor.create(Registry.BLOCK).filter(context -> allowedFlower(context.value()));
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
			String path = id.getPath();
			String unmodified_path = id.getPath();
			if(path.endsWith("motif")) {
				path = path.substring(0,path.length()-6);
			}
			String namespace = id.getNamespace();
			RESOURCE_PACK.addModel(JModel.model("minecraft:block/crossx2").textures(JModel.textures().var("cross", namespace + ":block/" + path)), new Identifier(namespace, "block/" + path + "x2"));
			RESOURCE_PACK.addModel(JModel.model("minecraft:block/crossx3").textures(JModel.textures().var("cross", namespace + ":block/" + path)), new Identifier(namespace, "block/" + path + "x3"));
			RESOURCE_PACK.addModel(JModel.model("minecraft:block/crossx4").textures(JModel.textures().var("cross", namespace + ":block/" + path)), new Identifier(namespace, "block/" + path + "x4"));

			RESOURCE_PACK.addBlockState(JState.state(new JVariant()
					.put("flowers=1", JState.model(namespace + ":block/" + unmodified_path))
					.put("flowers=2", JState.model(namespace + ":block/" + path + "x2"))
					.put("flowers=3", JState.model(namespace + ":block/" + path + "x3"))
					.put("flowers=4", JState.model(namespace + ":block/" + path + "x4"))
			), id);
		});
	}
	public static boolean allowedFlower(Block block){
		return block instanceof FlowerBlock;
	}
}
