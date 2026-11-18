package net.earlystage.init;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.earlystage.config.EarlyStageConfig;
import org.jetbrains.annotations.Nullable;

public class ConfigInit {

    public static EarlyStageConfig CONFIG = new EarlyStageConfig();

    @Nullable
    public static Boolean SYNCED_BLAST_FURNACE_EXTRA_SLOT = null;

    public static void init() {
        AutoConfig.register(EarlyStageConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(EarlyStageConfig.class).getConfig();
    }

    public static boolean isBlastFurnaceExtraSlotEnabled() {
        return SYNCED_BLAST_FURNACE_EXTRA_SLOT != null ? SYNCED_BLAST_FURNACE_EXTRA_SLOT : CONFIG.blastFurnaceExtraSlot;
    }
}
