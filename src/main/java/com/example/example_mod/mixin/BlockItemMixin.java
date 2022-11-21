package com.example.example_mod.mixin;

import com.example.example_mod.Config;
import com.example.example_mod.ExampleMod;
import com.example.example_mod.Shared;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {
	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
	public void MoreFlowers(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir){
		if (!context.shouldCancelInteraction()){
			World world = context.getWorld();
			BlockPos pos = context.getBlockPos();
			PlayerEntity player = context.getPlayer();
			ItemStack stack = player.getStackInHand(context.getHand());
			BlockState state = world.getBlockState(pos);
			if (stack.isOf(state.getBlock().asItem())& state.getBlock() instanceof FlowerBlock) {
				int flowers = state.get(Shared.FLOWERS);
				if (flowers < Config.flowers){
					world.setBlockState(pos,state.with(Shared.FLOWERS,flowers+1));
					BlockSoundGroup blockSoundGroup = state.getSoundGroup();
					world.playSound(player, pos, blockSoundGroup.getPlaceSound(), SoundCategory.BLOCKS,(blockSoundGroup.getVolume()+1.0F)/2.0F,blockSoundGroup.getPitch() * 0.8F);
					ExampleMod.LOGGER.info(state.get(Shared.FLOWERS).toString());
					world.emitGameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.create(player,state));

					if (player == null || !player.getAbilities().creativeMode) {
						stack.decrement(1);
					}
					cir.setReturnValue(ActionResult.success(world.isClient));
				}
			}
		}
	}
}
