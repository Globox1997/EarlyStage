package net.earlystage.init;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.earlystage.config.EarlyStageConfig;

public class ConfigInit {

    public static EarlyStageConfig CONFIG = new EarlyStageConfig();

    public static void init() {
        AutoConfig.register(EarlyStageConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(EarlyStageConfig.class).getConfig();
    }
}
