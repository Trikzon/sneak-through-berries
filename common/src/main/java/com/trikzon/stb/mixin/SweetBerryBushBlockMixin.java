package com.trikzon.stb.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SweetBerryBushBlock.class)
public abstract class SweetBerryBushBlockMixin extends BushBlock implements BonemealableBlock {
    public SweetBerryBushBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "entityInside", cancellable = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private void sneak_through_berries_entityInside(
            BlockState blockState,
            Level level,
            BlockPos blockPos,
            Entity entity,
            CallbackInfo ci
    ) {
        if (entity instanceof Player player)  {
            if (player.isShiftKeyDown()) {
                ci.cancel();
            }
        }
    }
}
