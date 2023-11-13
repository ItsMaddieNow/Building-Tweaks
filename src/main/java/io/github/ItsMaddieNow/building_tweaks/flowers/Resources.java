package io.github.ItsMaddieNow.building_tweaks.flowers;

import com.mojang.blaze3d.texture.NativeImage;
import io.github.ItsMaddieNow.building_tweaks.BuildingTweaks;
import net.minecraft.SharedConstants;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.pack.ResourcePackProfile;
import net.minecraft.resource.pack.ResourcePackSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.resource.loader.api.*;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Consumer;

public class Resources {

	static LinkedList<Identifier> Flowers = new LinkedList<>();
	public static void init() {
		BuildingTweaks.LOGGER.info("Registering In-Memory Resourcepacks");
		ResourceLoader.get(ResourceType.CLIENT_RESOURCES)
			.registerResourcePackProfileProvider(profileAdder -> Resources.ProvidePacks(profileAdder, ResourceType.CLIENT_RESOURCES));
		ResourceLoader.get(ResourceType.SERVER_DATA)
			.registerResourcePackProfileProvider(profileAdder -> Resources.ProvidePacks(profileAdder, ResourceType.SERVER_DATA));
	}

	public static void markFlower(Identifier id){
		Flowers.addLast(id);
	}

	public static void ProvidePacks(Consumer<ResourcePackProfile> profileAdder, ResourceType type) {

		Instant instant = Instant.now();
		try (
			InMemoryResourcePack baseResources = new InMemoryResourcePack.Named("building_tweaks_base") {
				@Override
				public @NotNull ResourcePackActivationType getActivationType() {
					return ResourcePackActivationType.ALWAYS_ENABLED;
				}
			};
			InMemoryResourcePack classicResources = new InMemoryResourcePack.Named("building_tweaks_classic")
			)
		{
			baseResources.putTextAsync("pack.mcmeta", (identifier) -> String.format("""
				{"pack":{"pack_format":%d,"description":"Maddie's Building Tweaks."}}
				""", SharedConstants.getGameVersion().getResourceVersion(type)
			));
			addIconToPack(baseResources, "/assets/maddies_building_tweaks/base-pack.png");
			addIconToPack(classicResources, "/assets/maddies_building_tweaks/classic-pack.png");
			for (Identifier flower : Flowers) {
				baseResources.putTextAsync(ResourceType.CLIENT_RESOURCES, new Identifier(flower.getNamespace(), String.format("blockstates/%1$s.json", flower.getPath())), (identifier) -> createFlowerBlockstate(flower));
				baseResources.putTextAsync(ResourceType.CLIENT_RESOURCES, new Identifier(flower.getNamespace(), String.format("models/block/%1$sx%2$d.json", flower.getPath(), 1)), (identifier) -> createFlowerQuantityModel(flower, 1));
				baseResources.putTextAsync(ResourceType.CLIENT_RESOURCES, new Identifier(flower.getNamespace(), String.format("models/block/%1$sx%2$d.json", flower.getPath(), 2)), (identifier) -> createFlowerQuantityModel(flower, 2));
				baseResources.putTextAsync(ResourceType.CLIENT_RESOURCES, new Identifier(flower.getNamespace(), String.format("models/block/%1$sx%2$d.json", flower.getPath(), 3)), (identifier) -> createFlowerQuantityModel(flower, 3));
				baseResources.putTextAsync(ResourceType.CLIENT_RESOURCES, new Identifier(flower.getNamespace(), String.format("models/block/%1$sx%2$d.json", flower.getPath(), 4)), (identifier) -> createFlowerQuantityModel(flower, 4));
				baseResources.putTextAsync(ResourceType.CLIENT_RESOURCES, new Identifier(flower.getNamespace(), String.format("models/block/%1$s_base.json", flower.getPath())), (identifier) -> createFlowerBaseModel(flower));
				baseResources.putTextAsync(ResourceType.SERVER_DATA, new Identifier(flower.getNamespace(), String.format("loot_tables/blocks/%1$s.json", flower.getPath())), (identifier) -> createFlowerLootTable(flower));
			}
			profileAdder.accept(ResourcePackProfile.of("building_tweaks_base", Text.literal("Maddies Building Tweaks"), true, name -> baseResources, type, ResourcePackProfile.InsertionPosition.TOP, ResourcePackSource.PACK_SOURCE_BUILTIN));
			classicResources.putTextAsync("pack.mcmeta", (identifier) -> String.format("""
				{"pack":{"pack_format":%d,"description":"Maddie's Building Tweaks Classic Models"}}
				""", SharedConstants.getGameVersion().getResourceVersion(type)
			));
			profileAdder.accept(ResourcePackProfile.of("building_tweaks_classic", Text.literal("Classic Models"),  false, name -> classicResources, type, ResourcePackProfile.InsertionPosition.TOP, ResourcePackSource.PACK_SOURCE_BUILTIN));
		}
		long RegistrationTime = Duration.between(instant, Instant.now()).toMillis();
		BuildingTweaks.LOGGER.info("Completed Constructing Resource Packs in %dms".formatted(RegistrationTime));
	}

	static void addIconToPack(MutableResourcePack pack, String resourcePath){
		try {

			pack.putImageAsync("pack.png", (identifier) -> {
				try {
					return NativeImage.read(Objects.requireNonNull(Resources.class.getResourceAsStream(resourcePath)));
				} catch (IOException e) {
					BuildingTweaks.LOGGER.error("Error Assigning Icon To In Memory Resource Pack");
					throw new RuntimeException(e);
				}
			});
		} catch (NullPointerException exception){
			BuildingTweaks.LOGGER.error("Error Assigning Icon To In Memory Resource Pack");
		}
	}

	static String createFlowerBlockstate(Identifier flower){
		return String.format("""
			{
				"multipart":
				[
					{
						"apply": {
							"model": "%1$s:block/%2$s_base",
							"y": 0
						},
						"when": {
							"flowers": "1",
							"facing": "north"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$s_base",
							"y": 90
						},
						"when": {
							"flowers": "1",
							"facing": "east"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$s_base",
							"y": 180
						},
						"when": {
							"flowers": "1",
							"facing": "south"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$s_base",
							"y": 270
						},
						"when": {
							"flowers": "1",
							"facing": "west"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$sx1",
							"y": 0
						},
						"when": {
							"flowers": "2|3|4",
							"facing": "north"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$sx1",
							"y": 90
						},
						"when": {
							"flowers": "2|3|4",
							"facing": "east"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$sx1",
							"y": 180
						},
						"when": {
							"flowers": "2|3|4",
							"facing": "south"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$sx1",
							"y": 270
						},
						"when": {
							"flowers": "2|3|4",
							"facing": "west"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$sx2",
							"y": 0
						},
						"when": {
							"flowers": "2|3|4",
							"facing": "north"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$sx2",
							"y": 90
						},
						"when": {
							"flowers": "2|3|4",
							"facing": "east"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$sx2",
							"y": 180
						},
						"when": {
							"flowers": "2|3|4",
							"facing": "south"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$sx2",
							"y": 270
						},
						"when": {
							"flowers": "2|3|4",
							"facing": "west"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$sx3",
							"y": 0
						},
						"when": {
							"flowers": "3|4",
							"facing": "north"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$sx3",
							"y": 90
						},
						"when": {
							"flowers": "3|4",
							"facing": "east"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$sx3",
							"y": 180
						},
						"when": {
							"flowers": "3|4",
							"facing": "south"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$sx3",
							"y": 270
						},
						"when": {
							"flowers": "3|4",
							"facing": "west"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$sx4",
							"y": 0
						},
						"when": {
							"flowers": "4",
							"facing": "north"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$sx4",
							"y": 90
						},
						"when": {
							"flowers": "4",
							"facing": "east"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$sx4",
							"y": 180
						},
						"when": {
							"flowers": "4",
							"facing": "south"
						}
					},
					{
						"apply": {
							"model": "%1$s:block/%2$sx4",
							"y": 270
						},
						"when": {
							"flowers": "4",
							"facing": "west"
						}
					}
				]
			}
			""", flower.getNamespace(), flower.getPath());
	}

	static String createFlowerQuantityModel(Identifier flower, int quantity) {
		return String.format("""
		{
		  	"parent": "minecraft:block/crossx%3$d",
			"textures": {
				"cross": "%1$s:block/%2$s"
		   	}
		}
		""", flower.getNamespace(), flower.getPath(), quantity);
	}

	static String createFlowerBaseModel(Identifier flower) {
		return String.format("""
		{
			"parent": "minecraft:block/cross",
			"textures": {
				"cross": "%1$s:block/%2$s"
			}
		}
		""", flower.getNamespace(), flower.getPath());
	}

	static String createFlowerLootTable(Identifier flower) {
		return String.format("""
			{
			  "type": "minecraft:block",
			  "pools": [
			    {
			      "entries": [
			        {
			          "type": "minecraft:item",
			          "name": "%1$s:%2$s",
			          "functions": [
			            {
			              "function": "minecraft:set_count",
			              "count": 2.0,
			              "add": false,
			              "conditions": [
			                {
			                  "condition": "minecraft:block_state_property",
			                  "block": "%1$s:%2$s",
			                  "properties": {
			                    "flowers": "2"
			                  }
			                }
			              ]
			            },
			            {
			              "function": "minecraft:set_count",
			              "count": 3.0,
			              "add": false,
			              "conditions": [
			                {
			                  "condition": "minecraft:block_state_property",
			                  "block": "%1$s:%2$s",
			                  "properties": {
			                    "flowers": "3"
			                  }
			                }
			              ]
			            },
			            {
			              "function": "minecraft:set_count",
			              "count": 4.0,
			              "add": false,
			              "conditions": [
			                {
			                  "condition": "minecraft:block_state_property",
			                  "block": "%1$s:%2$s",
			                  "properties": {
			                    "flowers": "4"
			                  }
			                }
			              ]
			            }
			          ]
			        }
			      ],
			      "rolls": 1,
			      "bonus_rolls": 0
			    }
			  ]
			}
			""", flower.getNamespace(), flower.getPath());
	}
}
