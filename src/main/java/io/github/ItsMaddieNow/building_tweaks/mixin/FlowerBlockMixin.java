package io.github.ItsMaddieNow.building_tweaks.mixin;

import io.github.ItsMaddieNow.building_tweaks.BuildingTweaks;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FlowerBlock.class)
public class FlowerBlockMixin extends Block implements Fertilizable {
	public FlowerBlockMixin(AbstractBlock.Settings settings){
		super(settings);
	}
	public BlockState withFlowers(int flowers) {
		return this.getDefaultState().with(BuildingTweaks.FLOWERS, flowers);
	}
	protected int getFlowers(BlockState state) {
		return state.get(BuildingTweaks.FLOWERS);
	}
	public void applyFlowers(World world, BlockPos pos, BlockState state){
		int i = this.getFlowers(state)+1;
		int j = BuildingTweaks.MAX_FLOWERS;
		if (i > j){
			i = j;
		}
		world.setBlockState(pos,withFlowers(i),2);
	}
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient){
		return (getFlowers(state) < BuildingTweaks.MAX_FLOWERS && BuildingTweaks.allowedFlower(state.getBlock()) && !state.isIn(BuildingTweaks.NO_BONEMEAL));
	}
	public boolean canGrow(World world, RandomGenerator random, BlockPos pos, BlockState state) { return true; }
	public void grow(ServerWorld world, RandomGenerator random, BlockPos pos, BlockState state) {
		this.applyFlowers(world,pos,state);
	}
}
