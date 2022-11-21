package com.example.example_mod.mixin;

import com.example.example_mod.ExampleMod;
import com.example.example_mod.Shared;
import net.minecraft.block.FlowerBlock;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
	@Inject(method = "init", at = @At("TAIL"))
	public void exampleMod$onInit(CallbackInfo ci) {
		for (FlowerBlock flower : Shared.FlowersBlocks) {
			ExampleMod.LOGGER.info(flower.toString());
		}
		ExampleMod.LOGGER.info(String.valueOf(Shared.FlowersBlocks.size()));
		ExampleMod.LOGGER.info("This line is printed by an example mod mixin!");

	}
}
