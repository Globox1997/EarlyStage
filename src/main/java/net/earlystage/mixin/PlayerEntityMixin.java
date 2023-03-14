package net.earlystage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.earlystage.init.ConfigInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "dropInventory", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"), cancellable = true)
    protected void dropInventoryMixin(CallbackInfo info) {
        if (!this.world.isClient && !((PlayerEntity) (Object) this).isCreative() && ConfigInit.CONFIG.beginnerDeathCount != 0
                && ((ServerPlayerEntity) (Object) this).getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS)) < ConfigInit.CONFIG.beginnerDeathCount) {
            info.cancel();
        }
    }

}
