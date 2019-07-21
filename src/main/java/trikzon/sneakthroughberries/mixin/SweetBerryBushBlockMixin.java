package trikzon.sneakthroughberries.mixin;

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
import trikzon.sneakthroughberries.Config;

@Mixin(SweetBerryBushBlock.class)
public abstract class SweetBerryBushBlockMixin extends PlantBlock implements Fertilizable {
    public SweetBerryBushBlockMixin(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    @Inject(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), cancellable = true)
    private void onOnEntityCollision(BlockState blockState_1, World world_1, BlockPos blockPos_1, Entity entity_1, CallbackInfo ci) {
        boolean isPlayer = entity_1 instanceof PlayerEntity;
        if (Config.CONFIG.isPlayer && !(isPlayer)) return;
        if (Config.CONFIG.isNotPlayer && isPlayer) return;

        boolean boots = false;
        boolean leggings = false;
        boolean allArmor = false;
        if (isPlayer && (Config.CONFIG.wearingBoots || Config.CONFIG.wearingLeggings || Config.CONFIG.wearingAllArmor)) {
            PlayerEntity player = (PlayerEntity)entity_1;

            boots = player.getEquippedStack(EquipmentSlot.FEET).isEmpty();
            leggings = player.getEquippedStack(EquipmentSlot.LEGS).isEmpty();
            boolean chest = player.getEquippedStack(EquipmentSlot.CHEST).isEmpty();
            boolean helmet = player.getEquippedStack(EquipmentSlot.HEAD).isEmpty();

            if (boots && leggings && chest && helmet) allArmor = true;
        }
        if (Config.CONFIG.wearingBoots && boots) return;
        if (Config.CONFIG.wearingLeggings && leggings) return;
        if (Config.CONFIG.wearingAllArmor && allArmor) return;

        if (Config.CONFIG.sneaking && !entity_1.isSneaking()) return;

        ci.cancel();
    }
}
