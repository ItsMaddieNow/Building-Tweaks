package com.example.example_mod;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.state.property.IntProperty;

import java.util.ArrayList;
import java.util.List;

public class Shared {

	public static final IntProperty FLOWERS = IntProperty.of("flowers",1,4);
	public static List<FlowerBlock> FlowersBlocks = new ArrayList<>();
	public static boolean FlowerAllowed = false;
}
