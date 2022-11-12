package com.example.example_mod.mixin;

import com.example.example_mod.Config;
import com.example.example_mod.ExampleMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {

	@Inject(method = "appendProperties", at = @At("HEAD"))
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo info){
		if ((Object) this instanceof FlowerBlock) {
			builder.add(ExampleMod.FLOWERS);
		}
	}
}
