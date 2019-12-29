/* =============================================================================
 * Copyright 2019 Trikzon
 *
 * Sneak-Through-Berries is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * File: SweetBerryBushBlockMixin.java
 * Date: 2019-12-29
 * Revision:
 * Author: Trikzon
 * ============================================================================= */
package io.github.trikzon.sneakthroughberries.fabric.mixin;

import io.github.trikzon.sneakthroughberries.fabric.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
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

@Mixin(SweetBerryBushBlock.class)
public abstract class SweetBerryBushBlockMixin extends PlantBlock implements Fertilizable {

    public SweetBerryBushBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), cancellable = true)
    private void phantom_onDamaged(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        boolean isPlayer = entity instanceof PlayerEntity;
        if (Config.CONFIG.isPlayer && !(isPlayer)) return;
        if (Config.CONFIG.isNotPlayer && isPlayer) return;

        boolean boots = false;
        boolean leggings = false;
        boolean allArmor = false;
        if (isPlayer && (Config.CONFIG.wearingBoots || Config.CONFIG.wearingLeggings || Config.CONFIG.wearingAllArmor)) {
            PlayerEntity player = (PlayerEntity)entity;

            boots = !player.getEquippedStack(EquipmentSlot.FEET).isEmpty();
            leggings = !player.getEquippedStack(EquipmentSlot.LEGS).isEmpty();
            boolean chest = !player.getEquippedStack(EquipmentSlot.CHEST).isEmpty();
            boolean helmet = !player.getEquippedStack(EquipmentSlot.HEAD).isEmpty();

            allArmor = boots && leggings && chest && helmet;
        }

        if (Config.CONFIG.wearingBoots && !boots && !Config.CONFIG.wearingArmorAllowsWalking) return;
        if (Config.CONFIG.wearingLeggings && !leggings && !Config.CONFIG.wearingArmorAllowsWalking) return;
        if (Config.CONFIG.wearingAllArmor && !allArmor && !Config.CONFIG.wearingArmorAllowsWalking) return;

        if (Config.CONFIG.sneaking && !entity.isSneaking()) {
            if (Config.CONFIG.wearingArmorAllowsWalking) {

                if (Config.CONFIG.wearingBoots && !boots) return;
                if (Config.CONFIG.wearingLeggings && !leggings) return;
                if (Config.CONFIG.wearingAllArmor && !allArmor) return;

            } else return;
        }

        ci.cancel();
    }
}
