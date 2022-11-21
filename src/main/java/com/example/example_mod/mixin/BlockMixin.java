package com.example.example_mod.mixin;

import com.example.example_mod.Config;
import com.example.example_mod.ExampleMod;
import com.example.example_mod.Shared;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin {

	@Shadow
	public abstract String toString();

	@Inject(method = "appendProperties", at = @At("RETURN"))
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo info){
		if ((Object) this instanceof FlowerBlock) {
			ExampleMod.LOGGER.info(this.toString());
			builder.add(Shared.FLOWERS);
		}
	}
	@Inject(method = "afterBreak", at = @At("HEAD"), cancellable = true)
	protected void blockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack, CallbackInfo ci){
		if ((Object)this instanceof FlowerBlock) {
			player.incrementStat(Stats.MINED.getOrCreateStat((Block) (Object)this));
			player.addExhaustion(0.005F);
			stack.setCount(state.get(Shared.FLOWERS));
			((Block) (Object)this).dropStacks(state, world, pos, blockEntity, player, stack);
			ci.cancel();
		}
	}
}
