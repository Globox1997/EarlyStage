package net.earlystage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.earlystage.init.ConfigInit;
import net.earlystage.init.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "dropInventory", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"), cancellable = true)
    protected void dropInventoryMixin(CallbackInfo info) {
        if (!this.getWorld().isClient() && !((PlayerEntity) (Object) this).isCreative() && ConfigInit.CONFIG.beginnerDeathCount != 0
                && ((ServerPlayerEntity) (Object) this).getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS)) < ConfigInit.CONFIG.beginnerDeathCount) {
            info.cancel();
        }
    }

    // RemoveOffhandItemTask and BindingCurse not done by my mixins for the shield
    @Inject(method = "damageShield", at = @At("HEAD"), cancellable = true)
    protected void damageShieldMixin(float amount, CallbackInfo info) {
        if (this.activeItemStack.isOf(ItemInit.WOODEN_SHIELD)) {
            if (!this.getWorld().isClient()) {
                this.incrementStat(Stats.USED.getOrCreateStat(this.activeItemStack.getItem()));
            }
            if (amount >= 3.0f) {
                int i = 1 + MathHelper.floor(amount);
                Hand hand = this.getActiveHand();
                this.activeItemStack.damage(i, this, player -> player.sendToolBreakStatus(hand));
                if (this.activeItemStack.isEmpty()) {
                    if (hand == Hand.MAIN_HAND) {
                        this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    } else {
                        this.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }
                    this.activeItemStack = ItemStack.EMPTY;
                    this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8f, 0.8f + this.getWorld().getRandom().nextFloat() * 0.4f);
                }
            }
            info.cancel();
        }
    }

    @Inject(method = "disableShield", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;clearActiveItem()V"))
    private void disableShieldMixin(boolean sprinting, CallbackInfo info) {
        this.getItemCooldownManager().set(ItemInit.WOODEN_SHIELD, 100);
    }

    @Shadow
    public void incrementStat(Stat<?> stat) {
    }

    @Shadow
    public ItemCooldownManager getItemCooldownManager() {
        return null;
    }

}
