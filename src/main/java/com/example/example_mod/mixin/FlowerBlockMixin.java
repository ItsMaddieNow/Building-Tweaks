package com.example.example_mod.mixin;

import com.example.example_mod.Config;
import com.example.example_mod.ExampleMod;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FlowerBlock.class)
public class FlowerBlockMixin extends Block implements Fertilizable {

	public FlowerBlockMixin(Settings settings){
		super(settings);
	}
	public boolean Allowed;
	@Inject(method = "<init>", at = @At("RETURN"))
	public void ConstructorInject(StatusEffect suspiciousStewEffect, int effectDuration, AbstractBlock.Settings settings, CallbackInfo info){
		this.setDefaultState(getStateManager().getDefaultState().with(ExampleMod.FLOWERS, 1));
	}
	public int getMaxFlowers(){ return 4; }
	public BlockState withFlowers(int flowers) {
		return (BlockState)this.getDefaultState().with(this.getGrowthProperty(), flowers);
	}
	public IntProperty getGrowthProperty() {
		return ExampleMod.FLOWERS;
	}
	protected int getGrowth(BlockState state) {
		return (Integer)state.get(this.getGrowthProperty());
	}
	public void applyGrowth(World world, BlockPos pos, BlockState state){
		int i = this.getGrowth(state)+1;
		int j = getMaxFlowers();
		if (i > j){
			i = j;
		}
		world.setBlockState(pos,withFlowers(i),2);
	}

	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient){
		return getGrowth(state) < getMaxFlowers();
	}
	public boolean canGrow(World world, RandomGenerator random, BlockPos pos, BlockState state) { return true; }
	public void grow(ServerWorld world, RandomGenerator random, BlockPos pos, BlockState state) {
		this.applyGrowth(world,pos,state);
	}
	/*@Override
	public boolean canRe*/
}
