package io.github.ItsMaddieNow.building_tweaks.mixin;

import io.github.ItsMaddieNow.building_tweaks.flowers.FlowerTweaks;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FlowerBlock.class)
public class FlowerBlockMixin extends Block implements Fertilizable {
	public FlowerBlockMixin(AbstractBlock.Settings settings){
		super(settings);
	}
	@Unique
	protected int getFlowers(BlockState state) {
		return state.get(FlowerTweaks.FLOWERS);
	}
	@Unique
	public void applyFlowers(World world, BlockPos pos, BlockState state){
		int i = this.getFlowers(state)+1;
		int j = FlowerTweaks.MAX_FLOWERS;
		if (i > j){
			i = j;
		}
		world.setBlockState(pos,state.with(FlowerTweaks.FLOWERS,i),2);
	}
	public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient){
		return (getFlowers(state) < FlowerTweaks.MAX_FLOWERS && FlowerTweaks.allowedFlower(state.getBlock()) && !state.isIn(FlowerTweaks.NO_BONEMEAL));
	}
	public boolean canGrow(World world, RandomGenerator random, BlockPos pos, BlockState state) { return true; }
	public void grow(ServerWorld world, RandomGenerator random, BlockPos pos, BlockState state) {
		this.applyFlowers(world,pos,state);
	}
	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context){
		return !context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && (Integer) state.get(FlowerTweaks.FLOWERS) < FlowerTweaks.MAX_FLOWERS ? true : super.canReplace(state, context);
	}
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx){
		BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
		return blockState.isOf(this)? (BlockState) blockState.with(FlowerTweaks.FLOWERS, Math.min(FlowerTweaks.MAX_FLOWERS, (Integer)blockState.get(FlowerTweaks.FLOWERS)+1)):(BlockState)this.getDefaultState().with(FlowerTweaks.FACING, ctx.getPlayerFacing().getOpposite());
	}
}
