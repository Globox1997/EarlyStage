package net.earlystage.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.earlystage.init.ConfigInit;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.stats.Stats;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DeathScreen.class)
public abstract class DeathScreenMixin extends Screen {

    @Shadow
    @Mutable
    @Final
    private boolean hardcore;

    public DeathScreenMixin(Component title) {
        super(title);
    }

    @WrapOperation(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;", ordinal = 1))
    private MutableComponent initMixin(String key, Operation<MutableComponent> original) {
        if (!this.hardcore && this.minecraft != null && this.minecraft.player != null && ConfigInit.CONFIG.beginnerDeathCount > 0 && this.minecraft.player.getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS)) < ConfigInit.CONFIG.beginnerDeathCount) {
            return Component.translatable("screen.earlystage.respawn_info", Component.translatable(key), ConfigInit.CONFIG.beginnerDeathCount - this.minecraft.player.getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS)));
        }
        return original.call(key);
    }
}
