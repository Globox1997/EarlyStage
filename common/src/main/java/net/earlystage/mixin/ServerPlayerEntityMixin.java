package net.earlystage.mixin;

import com.mojang.authlib.GameProfile;
import net.earlystage.init.ConfigInit;
import net.earlystage.network.packet.BeginnerDeathPacket;
import net.earlystage.platform.Services;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerEntityMixin extends Player {

    @Shadow
    @Mutable
    @Final
    private ServerStatsCounter stats;

    public ServerPlayerEntityMixin(Level world, GameProfile gameProfile) {
        super(world, gameProfile);
    }

    @Inject(method = "restoreFrom", at = @At(value = "FIELD", target = "Lnet/minecraft/server/level/ServerPlayer;enchantmentSeed:I", ordinal = 0))
    private void copyFromMixin(ServerPlayer oldPlayer, boolean alive, CallbackInfo info) {
        if (ConfigInit.CONFIG.beginnerDeathCount > 0 && !((ServerLevel) this.level()).getGameRules().get(GameRules.KEEP_INVENTORY) && !oldPlayer.isSpectator()
                && stats.getValue(Stats.CUSTOM.get(Stats.DEATHS)) <= ConfigInit.CONFIG.beginnerDeathCount) {
            this.getInventory().replaceWith(oldPlayer.getInventory());
            this.experienceLevel = oldPlayer.experienceLevel;
            this.totalExperience = oldPlayer.totalExperience;
            this.experienceProgress = oldPlayer.experienceProgress;
            this.setScore(oldPlayer.getScore());
        }
    }

    @Inject(method = "die", at = @At("HEAD"))
    private void onDeathMixin(DamageSource damageSource, CallbackInfo info) {
        if (ConfigInit.CONFIG.beginnerDeathCount > 0 && !((ServerLevel) this.level()).getGameRules().get(GameRules.KEEP_INVENTORY)) {
            Services.network().sendToPlayer((ServerPlayer) (Object) this, new BeginnerDeathPacket(this.getId(), stats.getValue(Stats.CUSTOM.get(Stats.DEATHS))));
        }
    }

}
