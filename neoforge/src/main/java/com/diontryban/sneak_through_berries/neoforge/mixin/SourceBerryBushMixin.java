package com.diontryban.sneak_through_berries.neoforge.mixin;

import com.hollingsworth.arsnouveau.common.block.SourceBerryBush;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SourceBerryBush.class)
public class SourceBerryBushMixin {
    @Inject(method = "entityInside", cancellable = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private void injectBeforeHurtInEntityInside(
        BlockState state,
        Level level,
        BlockPos pos,
        Entity entity,
        CallbackInfo ci
    ) {
        if (entity instanceof Player player) {
            if (player.isShiftKeyDown()) {
                ci.cancel();
            }
        }
    }
}
