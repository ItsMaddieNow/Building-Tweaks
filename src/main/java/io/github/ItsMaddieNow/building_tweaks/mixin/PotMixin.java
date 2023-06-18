package io.github.ItsMaddieNow.building_tweaks.mixin;

import io.github.ItsMaddieNow.building_tweaks.ConfigValue;
import io.github.ItsMaddieNow.building_tweaks.flowers.FlowerTweaks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(FlowerPotBlock.class)
public class PotMixin {
	@Shadow
	@Final
	private Block content;

	@Inject(method = "onUse(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;", at=@At("HEAD"),cancellable = true)
	public void place(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir){
		ItemStack stack = player.getStackInHand(hand);
		Item item = stack.getItem();
		Block content = ((PotBlockAccessor)state.getBlock()).getContent();
		if (FlowerTweaks.allowedPot(state)) {
			if (content != Blocks.AIR && stack.isEmpty()){
				ItemStack dropStack = new ItemStack(content, state.get(FlowerTweaks.POT_FLOWERS));
				player.giveItemStack(dropStack);
				world.setBlockState(pos, Blocks.FLOWER_POT.getDefaultState(), 3);
			}
			if (ConfigValue.FLOWERPOTS_ENABLED.value() && item instanceof BlockItem && content==((BlockItem)item).getBlock()){
				int i = state.get(FlowerTweaks.POT_FLOWERS);
				if (i<FlowerTweaks.MAX_POT_FLOWERS){
					if (!player.getAbilities().creativeMode) {
						stack.decrement(1);
					}
					world.setBlockState(pos,state.with(FlowerTweaks.POT_FLOWERS, i+1));
				}
			}
			world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
			cir.setReturnValue(ActionResult.success(world.isClient()));
			cir.cancel();
		}


	}
}
