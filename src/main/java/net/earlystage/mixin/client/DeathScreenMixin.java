package net.earlystage.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.earlystage.init.ConfigInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(DeathScreen.class)
public abstract class DeathScreenMixin extends Screen {

    @Shadow
    @Mutable
    @Final
    private boolean isHardcore;

    public DeathScreenMixin(Text title) {
        super(title);
    }

    @WrapOperation(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Text;translatable(Ljava/lang/String;)Lnet/minecraft/text/MutableText;", ordinal = 1))
    private MutableText initMixin(String key, Operation<MutableText> original) {
        if (!this.isHardcore && this.client != null && this.client.player != null && ConfigInit.CONFIG.beginnerDeathCount > 0 && this.client.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS)) < ConfigInit.CONFIG.beginnerDeathCount) {
            return Text.translatable("screen.earlystage.respawn_info", Text.translatable(key), ConfigInit.CONFIG.beginnerDeathCount - this.client.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS)));
        }
        return original.call(key);
    }
}
