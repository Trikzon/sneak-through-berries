/*
 * This file is part of Sneak Through Berries.
 * A copy of this program can be found at https://github.com/Trikzon/sneak-through-berries.
 * Copyright (C) 2023 Dion Tryban
 *
 * Sneak Through Berries is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * Sneak Through Berries is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Sneak Through Berries. If not, see <https://www.gnu.org/licenses/>.
 */

package com.diontryban.mods.sneak_through_berries.mixin;

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
    protected SweetBerryBushBlockMixin(Properties properties) {
        super(properties);
    }

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
