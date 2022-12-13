package net.earlystage.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "earlystage")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class EarlyStageConfig implements ConfigData {

    // public double test1 = 0.0D;
    // public double test2 = 0.0D;
    // public double test3 = 0.0D;

    @Comment("Generate rocks in the overworld")
    @ConfigEntry.Gui.RequiresRestart
    public boolean generateRocks = true;

}