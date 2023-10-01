package io.github.ItsMaddieNow.building_tweaks.mixin;

import io.github.ItsMaddieNow.building_tweaks.flowers.ResourcePack;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(TitleScreen.class)
public class ResourceDumpMixin {
	@Inject(method = "init", at = @At("TAIL"))
	public void exampleMod$onInit(CallbackInfo ci) {
		ResourcePack.RESOURCE_PACK.dump();
	}
}
