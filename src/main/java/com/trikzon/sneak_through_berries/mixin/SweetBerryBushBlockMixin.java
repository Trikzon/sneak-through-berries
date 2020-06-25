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
public abstract class SweetBerryBushBlockMixin extends PlantBlock
{
    public SweetBerryBushBlockMixin(Settings settings)
    {
        super(settings);
    }

    @Inject(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), cancellable = true)
    private void sneakThroughBerries_onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci)
    {
        boolean isPlayer = entity instanceof PlayerEntity;
        if (!CONFIG.worksForPlayer && isPlayer) return;
        if (!CONFIG.worksForNonPlayer && !isPlayer) return;

        if (isPlayer)
        {
            boolean canWalk = CONFIG.requireNothingToWalk;
            PlayerEntity player = (PlayerEntity) entity;

            if (!canWalk)
            {
                // IF SOMEONE!!! SOMEONE!!! can figure out something better... please pr it. this is madness!
                int armorCounter = 0;
                armorCounter += !player.getEquippedStack(EquipmentSlot.FEET).isEmpty() ? 1 : 0;
                armorCounter += !player.getEquippedStack(EquipmentSlot.LEGS).isEmpty() ? 1 : 0;
                armorCounter += !player.getEquippedStack(EquipmentSlot.CHEST).isEmpty()? 1 : 0;
                armorCounter += !player.getEquippedStack(EquipmentSlot.HEAD).isEmpty() ? 1 : 0;

                int requiredArmor = 0;
                requiredArmor += CONFIG.requireBootsToWalk ? 1 : 0;
                requiredArmor += CONFIG.requireLeggingsToWalk ? 1 : 0;
                requiredArmor += CONFIG.requireChestToWalk ? 1 : 0;
                requiredArmor += CONFIG.requireHelmetToWalk ? 1 : 0;

                if (armorCounter == requiredArmor)
                {
                    canWalk = true;
                }
            }
            if (canWalk)
            {
                ci.cancel();
                return;
            }
        }
        if (entity.isSneaking())
        {
            ci.cancel();
        }
    }
}
