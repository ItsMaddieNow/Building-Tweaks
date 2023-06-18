package io.github.ItsMaddieNow.building_tweaks.mixin;

import io.github.ItsMaddieNow.building_tweaks.BuildingTweaks;
import io.github.ItsMaddieNow.building_tweaks.ConfigValue;
import io.github.ItsMaddieNow.building_tweaks.flowers.FlowerTweaks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("DuplicatedCode")
@Mixin(BlockItem.class)
public class BlockItemMixin {
	public void AddFlowers(World world, BlockPos pos, PlayerEntity player, ItemStack stack, BlockState state, CallbackInfoReturnable<ActionResult> cir){
		Block block =  state.getBlock();
		if (ConfigValue.FLOWERS_ENABLED.value() && stack.isOf(block.asItem()) && FlowerTweaks.allowedFlower(block)) {
			int flowers = state.get(FlowerTweaks.FLOWERS);
			if (flowers < FlowerTweaks.MAX_FLOWERS){
				world.setBlockState(pos,state.with(FlowerTweaks.FLOWERS,flowers+1));
				BlockSoundGroup blockSoundGroup = state.getSoundGroup();
				world.playSound(player, pos, blockSoundGroup.getPlaceSound(), SoundCategory.BLOCKS,(blockSoundGroup.getVolume()+1.0F)/2.0F,blockSoundGroup.getPitch() * 0.8F);
				BuildingTweaks.LOGGER.info(state.get(FlowerTweaks.FLOWERS).toString());
				world.emitGameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.create(player,state));

				if (player == null || !player.getAbilities().creativeMode) {
					stack.decrement(1);
				}
				cir.setReturnValue(ActionResult.success(world.isClient));
			}
		}
	}

	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
	public void MoreFlowersOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir){
		if (!context.shouldCancelInteraction()){
			World world = context.getWorld();
			BlockPos pos = context.getBlockPos();
			PlayerEntity player = context.getPlayer();
			assert player != null;
			ItemStack stack = player.getStackInHand(context.getHand());
			BlockState state = world.getBlockState(pos);
			AddFlowers(world,pos,player,stack,state,cir);
		}
	}
	@Inject(method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;", at = @At("HEAD"), cancellable = true)
	public void MoreFlowersOnPlace(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> cir){
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		PlayerEntity player = context.getPlayer();
		assert player != null;
		ItemStack stack = player.getStackInHand(context.getHand());
		BlockState state = world.getBlockState(pos);
		AddFlowers(world,pos,player,stack,state,cir);
	}
}
