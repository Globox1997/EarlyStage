package net.earlystage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.earlystage.init.ConfigInit;
import net.earlystage.init.ItemInit;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "dropEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"), cancellable = true)
    protected void dropInventoryMixin(CallbackInfo info) {
        if (isBeginnerDeath()) {
            info.cancel();
        }
    }

    @Inject(method = "getBaseExperienceReward", at = @At("HEAD"), cancellable = true)
    protected void getXpToDropMixin(CallbackInfoReturnable<Integer> info) {
        if (isBeginnerDeath()) {
            info.setReturnValue(0);
        }
    }

    @Unique
    private boolean isBeginnerDeath() {
        return !this.level().isClientSide() && !((Player) (Object) this).isCreative() && ConfigInit.CONFIG.beginnerDeathCount > 0
                && ((ServerPlayer) (Object) this).getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS)) < ConfigInit.CONFIG.beginnerDeathCount;
    }

    @Inject(method = "hurtCurrentlyUsedShield", at = @At("HEAD"), cancellable = true)
    protected void damageShieldMixin(float amount, CallbackInfo info) {
        if (this.useItem.is(ItemInit.WOODEN_SHIELD)) {
            if (!this.level().isClientSide()) {
                this.awardStat(Stats.ITEM_USED.get(this.useItem.getItem()));
            }
            if (amount >= 3.0f) {
                int i = 1 + Mth.floor(amount);
                InteractionHand hand = this.getUsedItemHand();
                this.useItem.hurtAndBreak(i, this, LivingEntity.getSlotForHand(hand));
                if (this.useItem.isEmpty()) {
                    if (hand == InteractionHand.MAIN_HAND) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    } else {
                        this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }
                    this.useItem = ItemStack.EMPTY;
                    this.playSound(SoundEvents.SHIELD_BREAK, 0.8f, 0.8f + this.level().getRandom().nextFloat() * 0.4f);
                }
            }
            info.cancel();
        }
    }

    @Inject(method = "disableShield", at = @At("TAIL"))
    private void disableShieldMixin(CallbackInfo info) {
        this.getCooldowns().addCooldown(ItemInit.WOODEN_SHIELD, 100);
    }

    @Shadow
    public void awardStat(Stat<?> stat) {
    }

    @Shadow
    public ItemCooldowns getCooldowns() {
        return null;
    }

}
