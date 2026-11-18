package net.earlystage.network;

import net.earlystage.EarlyStageMain;
import net.earlystage.init.ConfigInit;
import net.earlystage.network.packet.BeginnerDeathPacket;
import net.earlystage.network.packet.ConfigSyncPacket;
import net.earlystage.network.packet.CraftingRockItemsPacket;
import net.earlystage.network.packet.ExtraBlastingRecipesPacket;
import net.earlystage.network.packet.SieveDropPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.stats.Stats;
import java.util.ArrayList;
import java.util.List;

public class EarlyClientPacket {

    public static void handleBeginnerDeath(BeginnerDeathPacket payload) {
        Minecraft client = Minecraft.getInstance();
        if (client.level != null && client.level.getEntity(payload.entityId()) instanceof LocalPlayer clientPlayerEntity) {
            clientPlayerEntity.getStats().setValue(clientPlayerEntity, Stats.CUSTOM.get(Stats.DEATHS), payload.deathCount());
        }
    }

    public static void handleSieveDrops(SieveDropPacket payload) {
        if (!Minecraft.getInstance().hasSingleplayerServer()) {
            EarlyStageMain.SIEVE_DROP_TEMPLATES.clear();
            EarlyStageMain.SIEVE_DROP_TEMPLATES.addAll(payload.templates());
        }
    }

    public static void handleCraftingRockItems(CraftingRockItemsPacket payload) {
        if (!Minecraft.getInstance().hasSingleplayerServer()) {
            EarlyStageMain.CRAFTING_ROCK_RECIPE_ITEMS.clear();
            payload.recipeItems().forEach((output, inputs) -> EarlyStageMain.CRAFTING_ROCK_RECIPE_ITEMS.put(output, new ArrayList<>(inputs)));
            EarlyStageMain.CRAFTING_ROCK_RECIPES = List.copyOf(payload.recipes());
        }
    }

    public static void handleExtraBlastingRecipes(ExtraBlastingRecipesPacket payload) {
        if (!Minecraft.getInstance().hasSingleplayerServer()) {
            EarlyStageMain.EXTRA_BLASTING_RECIPES = List.copyOf(payload.recipes());
        }
    }

    public static void handleConfigSync(ConfigSyncPacket payload) {
        if (!Minecraft.getInstance().hasSingleplayerServer()) {
            ConfigInit.SYNCED_BLAST_FURNACE_EXTRA_SLOT = payload.blastFurnaceExtraSlot();
        }
    }

    public static void onDisconnect() {
        ConfigInit.SYNCED_BLAST_FURNACE_EXTRA_SLOT = null;
    }

}
