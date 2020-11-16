/*
 * Copyright 2020 Trikzon
 *
 * Sneak Through Berries is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * https://www.gnu.org/licenses/
 */
package com.trikzon.sneak_through_berries;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SneakThroughBerries.MOD_ID)
public class SneakThroughBerries {
    public static final String MOD_ID = "sneak-through-berries";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static Config.ConfigBean CONFIG;

    public SneakThroughBerries() {
        CONFIG = Config.init(LOGGER, MOD_ID);

        MinecraftForge.EVENT_BUS.addListener(this::onLivingAttack);
    }

    private void onLivingAttack(LivingAttackEvent event) {
        if (!event.getSource().damageType.equals("sweetBerryBush")) return;

        if (!(event.getEntity() instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) event.getEntity();

        if (CONFIG.sneakToStopDamage && player.isSneaking()) {
            event.setCanceled(true);
            LOGGER.info("Is Sneaking");
            return;
        }

        ItemStack feet = player.getItemStackFromSlot(EquipmentSlotType.FEET);
        ItemStack legs = player.getItemStackFromSlot(EquipmentSlotType.LEGS);
        ItemStack chest = player.getItemStackFromSlot(EquipmentSlotType.CHEST);
        ItemStack head = player.getItemStackFromSlot(EquipmentSlotType.HEAD);

        if (CONFIG.requiredToWalk.boots && feet.isEmpty()) return;
        if (CONFIG.requiredToWalk.leggings && legs.isEmpty()) return;
        if (CONFIG.requiredToWalk.chestPlate && chest.isEmpty()) return;
        if (CONFIG.requiredToWalk.helmet && head.isEmpty()) return;

        System.out.println("Is wearing the armor");
        event.setCanceled(true);

        World world = event.getEntity().world;
        if (world.isRemote) return;
        if (!CONFIG.requiredArmorLosesDurabilityWhenWalking) return;
        if (world.rand.nextInt(20) != 0) return;

        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        if (CONFIG.requiredToWalk.boots) feet.attemptDamageItem(1, world.rand, serverPlayer);
        if (CONFIG.requiredToWalk.leggings) legs.attemptDamageItem(1, world.rand, serverPlayer);
        if (CONFIG.requiredToWalk.chestPlate) chest.attemptDamageItem(1, world.rand, serverPlayer);
        if (CONFIG.requiredToWalk.helmet) head.attemptDamageItem(1, world.rand, serverPlayer);
    }
}
