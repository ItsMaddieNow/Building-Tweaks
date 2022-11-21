package com.example.example_mod.mixin;

import com.example.example_mod.Config;
import com.example.example_mod.ExampleMod;
import com.example.example_mod.Shared;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.ArrayList;
import java.util.List;

@Mixin(FlowerBlock.class)
public class FlowerBlockMixin extends Block implements Fertilizable {

	public FlowerBlockMixin(Settings settings){
		super(settings);
	}
	public boolean Allowed;
	@Inject(method = "<init>", at = @At("RETURN"))
	public void ConstructorInject(StatusEffect suspiciousStewEffect, int effectDuration, AbstractBlock.Settings settings, CallbackInfo info){
		//ExampleMod.LOGGER.info(((FlowerBlock)(Object)this).toString()+"added");
		//ExampleMod.LOGGER.info(String.valueOf(Shared.FlowersBlocks.size()));
		//Shared.FlowersBlocks.add((FlowerBlock)(Object)this);
		this.setDefaultState(getStateManager().getDefaultState().with(Shared.FLOWERS, 1));
	}
	public BlockState withFlowers(int flowers) {
		return (BlockState)this.getDefaultState().with(this.getGrowthProperty(), flowers);
	}
	public IntProperty getGrowthProperty() {
		return Shared.FLOWERS;
	}
	protected int getGrowth(BlockState state) {
		return (Integer)state.get(getGrowthProperty());
	}
	public void applyGrowth(World world, BlockPos pos, BlockState state){
		int i = this.getGrowth(state)+1;
		int j = Config.flowers;
		if (i > j){
			i = j;
		}
		world.setBlockState(pos,withFlowers(i),2);
	}

	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient){
		return getGrowth(state) < Config.flowers;
	}
	public boolean canGrow(World world, RandomGenerator random, BlockPos pos, BlockState state) { return true; }
	public void grow(ServerWorld world, RandomGenerator random, BlockPos pos, BlockState state) {
		this.applyGrowth(world,pos,state);
	}
}
