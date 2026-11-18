package net.earlystage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.earlystage.init.ConfigInit;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "dropEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"), cancellable = true)
    protected void dropInventoryMixin(ServerLevel level, CallbackInfo info) {
        if (isBeginnerDeath()) {
            info.cancel();
        }
    }

    @Inject(method = "getBaseExperienceReward", at = @At("HEAD"), cancellable = true)
    protected void getXpToDropMixin(ServerLevel level, CallbackInfoReturnable<Integer> info) {
        if (isBeginnerDeath()) {
            info.setReturnValue(0);
        }
    }

    @Unique
    private boolean isBeginnerDeath() {
        return !this.level().isClientSide() && !((Player) (Object) this).isCreative() && ConfigInit.CONFIG.beginnerDeathCount > 0
                && ((ServerPlayer) (Object) this).getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS)) < ConfigInit.CONFIG.beginnerDeathCount;
    }

}
