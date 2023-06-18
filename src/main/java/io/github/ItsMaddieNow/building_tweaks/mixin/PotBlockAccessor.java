package io.github.ItsMaddieNow.building_tweaks.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(FlowerPotBlock.class)
public interface PotBlockAccessor {
	/*@Accessor
	public static Map<Block, Block> getCONTENT_TO_POTTED() {
		throw new AssertionError();
	}*/
	@Accessor
	Block getContent();
}
