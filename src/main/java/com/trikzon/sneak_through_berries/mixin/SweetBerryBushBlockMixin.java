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
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
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

        ItemStack feet = player.getEquippedStack(EquipmentSlot.FEET);
        ItemStack legs = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack head = player.getEquippedStack(EquipmentSlot.HEAD);

        if (CONFIG.requiredToWalk.boots && feet.isEmpty()) return;
        if (CONFIG.requiredToWalk.leggings && legs.isEmpty()) return;
        if (CONFIG.requiredToWalk.chestPlate && chest.isEmpty()) return;
        if (CONFIG.requiredToWalk.helmet && head.isEmpty()) return;

        ci.cancel();

        if (world.isClient) return;
        if (!CONFIG.requiredArmorLosesDurabilityWhenWalking) return;
        if (world.random.nextInt(20) != 0) return;

        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        if (CONFIG.requiredToWalk.boots) feet.damage(1, world.random, serverPlayer);
        if (CONFIG.requiredToWalk.leggings) legs.damage(1, world.random, serverPlayer);
        if (CONFIG.requiredToWalk.chestPlate) chest.damage(1, world.random, serverPlayer);
        if (CONFIG.requiredToWalk.helmet) head.damage(1, world.random, serverPlayer);
    }
}
