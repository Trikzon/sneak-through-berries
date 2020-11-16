/*
 * Copyright 2020 Trikzon
 *
 * Sneak Through Berries is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * https://www.gnu.org/licenses/
 */
package com.trikzon.sneak_through_berries.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.trikzon.sneak_through_berries.SneakThroughBerries.CONFIG;

@Mixin(SweetBerryBushBlock.class)
public abstract class SweetBerryBushBlockMixin extends PlantBlock {
    public SweetBerryBushBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), cancellable = true)
    private void sneakThroughBerries_onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (!(entity instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) entity;

        if (CONFIG.sneakToStopDamage && player.isSneaking()) {
            ci.cancel();
        }

        if (CONFIG.requiredToWalk.boots && player.getEquippedStack(EquipmentSlot.FEET).isEmpty()) return;
        if (CONFIG.requiredToWalk.leggings && player.getEquippedStack(EquipmentSlot.LEGS).isEmpty()) return;
        if (CONFIG.requiredToWalk.chestPlate && player.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) return;
        if (CONFIG.requiredToWalk.helmet && player.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) return;

        ci.cancel();
    }
}
