/* =============================================================================
 * Copyright 2019 Trikzon
 *
 * Sneak-Through-Berries is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * File: SneakThroughBerries.java
 * Date: 2019-12-29
 * Revision:
 * Author: Trikzon
 * ============================================================================= */
package io.github.trikzon.sneakthroughberries.forge;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SneakThroughBerries.MOD_ID)
public class SneakThroughBerries {

    public static final String MOD_ID = "sneakthroughberries";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public SneakThroughBerries() {
        MinecraftForge.EVENT_BUS.register(SneakThroughBerries.class);
        MinecraftForge.EVENT_BUS.register(Config.class);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MOD_ID + "-common.toml"));
    }

    @SubscribeEvent
    public static void onDamageEvent(LivingAttackEvent event) {
        if (!event.getSource().damageType.equals("sweetBerryBush")) return;

        boolean isPlayer = event.getEntity() instanceof PlayerEntity;
        if (Config.IS_PLAYER.get() && !(isPlayer)) return;
        if (Config.IS_NOT_PLAYER.get() && isPlayer) return;

        boolean boots = false;
        boolean leggings = false;
        boolean allArmor = false;
        if (isPlayer && (Config.WEARING_BOOTS.get() || Config.WEARING_LEGGINGS.get() || Config.WEARING_ALL_ARMOR.get())) {
            PlayerEntity player = (PlayerEntity)event.getEntity();
            boots = !player.getItemStackFromSlot(EquipmentSlotType.FEET).isEmpty();
            leggings = !player.getItemStackFromSlot(EquipmentSlotType.LEGS).isEmpty();
            boolean chest = !player.getItemStackFromSlot(EquipmentSlotType.CHEST).isEmpty();
            boolean helmet = !player.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty();
            if (boots && leggings && chest && helmet) allArmor = true;
        }

        if (Config.WEARING_BOOTS.get() && !boots && !Config.WEARING_ARMOR_ALLOWS_WALKING.get()) return;
        if (Config.WEARING_LEGGINGS.get() && !leggings && !Config.WEARING_ARMOR_ALLOWS_WALKING.get()) return;
        if (Config.WEARING_ALL_ARMOR.get() && !allArmor && !Config.WEARING_ARMOR_ALLOWS_WALKING.get()) return;
        if (Config.SNEAKING.get() && !event.getEntity().isCrouching()) {
            if (Config.WEARING_ARMOR_ALLOWS_WALKING.get()) {

                if (Config.WEARING_BOOTS.get() && !boots) return;
                if (Config.WEARING_LEGGINGS.get() && !leggings) return;
                if (Config.WEARING_ALL_ARMOR.get() && !allArmor) return;

            } else return;
        }
        event.setCanceled(true);
    }
}
