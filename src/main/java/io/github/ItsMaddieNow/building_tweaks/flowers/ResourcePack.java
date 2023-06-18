package io.github.ItsMaddieNow.building_tweaks.flowers;

import com.google.gson.JsonObject;
import io.github.ItsMaddieNow.building_tweaks.BuildingTweaks;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.loot.JEntry;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.loot.JPool;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.util.Identifier;

import static net.devtech.arrp.json.loot.JLootTable.*;

public class ResourcePack {

	public static RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(BuildingTweaks.ID+":flowers");

	public static void init(){
		BuildingTweaks.LOGGER.info("Registering Virtual Resourcepack");
		RRPCallback.AFTER_VANILLA.register(a -> a.add(RESOURCE_PACK));
	}
	public static void resourceGen(Identifier id){
		String path = id.getPath();
		String namespace = id.getNamespace();

		// Create function for loot table to drop correct amount of flowers based on blockstate
		JEntry Functions = entry().type("minecraft:item").name(id.toString());
		for (int i = 2; i <= FlowerTweaks.MAX_FLOWERS; i++){
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
		// Create loot table for each flower
		RESOURCE_PACK.addLootTable(new Identifier(id.getNamespace()+":blocks/"+id.getPath()), JLootTable.loot("minecraft:block")
				.pool(new JPool()
						.bonus(0)
						.rolls(1)
						.entry(Functions)
				)
		);
		// Quick and Nasty Compat with Botainia Motif Flowers
		if(path.endsWith("motif")) {
			path = path.substring(0,path.length()-6);
		}
		// Create models for each blockstate
		RESOURCE_PACK.addModel(JModel.model("minecraft:block/crossx2").textures(JModel.textures().var("cross", namespace + ":block/" + path)), new Identifier(namespace, "block/" + path + "x2"));
		RESOURCE_PACK.addModel(JModel.model("minecraft:block/crossx3").textures(JModel.textures().var("cross", namespace + ":block/" + path)), new Identifier(namespace, "block/" + path + "x3"));
		RESOURCE_PACK.addModel(JModel.model("minecraft:block/crossx4").textures(JModel.textures().var("cross", namespace + ":block/" + path)), new Identifier(namespace, "block/" + path + "x4"));
		// Create block state file
		RESOURCE_PACK.addBlockState(JState.state(new JVariant()
				.put("flowers=1", JState.model(namespace + ":block/" + id.getPath()))
				.put("flowers=2", JState.model(namespace + ":block/" + path + "x2"))
				.put("flowers=3", JState.model(namespace + ":block/" + path + "x3"))
				.put("flowers=4", JState.model(namespace + ":block/" + path + "x4"))
		), id);

	}
}
