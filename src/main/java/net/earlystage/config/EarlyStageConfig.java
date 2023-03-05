package net.earlystage.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "earlystage")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class EarlyStageConfig implements ConfigData {

    @Comment("Generate rocks in the overworld")
    @ConfigEntry.Gui.RequiresRestart
    public boolean generateRocks = true;
    @Comment("Generate flint stones in the overworld")
    @ConfigEntry.Gui.RequiresRestart
    public boolean generateFlint = true;
    @Comment("Time in ticks")
    public int redstoneSieveTicks = 30;
    @Comment("Min hits to craft item + random 50% of value")
    public int craftRockCraftHits = 5;
    @Comment("Max hits before breaking rock ")
    public int craftRockMaxCraftHits = 70;
    public boolean info_tooltips = true;
    public float extraStickDropChance = 0.05f;

}