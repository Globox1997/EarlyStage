package net.earlystage.mixin;

import com.mojang.authlib.GameProfile;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.earlystage.init.ConfigInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    @Shadow
    @Mutable
    @Final
    private ServerStatHandler statHandler;

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile, PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }

    @Inject(method = "copyFrom", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerPlayerEntity;enchantmentTableSeed:I", ordinal = 0))
    private void copyFromMixin(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo info) {
        if (ConfigInit.CONFIG.beginnerDeathCount != 0 && !this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY) && !oldPlayer.isSpectator()
                && statHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS)) <= ConfigInit.CONFIG.beginnerDeathCount) {
            this.getInventory().clone(oldPlayer.getInventory());
            this.experienceLevel = oldPlayer.experienceLevel;
            this.totalExperience = oldPlayer.totalExperience;
            this.experienceProgress = oldPlayer.experienceProgress;
            this.setScore(oldPlayer.getScore());
        }
    }

}
