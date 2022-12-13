package net.earlystage.data;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.earlystage.EarlyStageMain;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class DataLoader implements SimpleSynchronousResourceReloadListener {

    private static final Logger LOGGER = LogManager.getLogger("EarlyStage");

    @Override
    public Identifier getFabricId() {
        return new Identifier("earlystage", "loader");
    }

    @Override
    public void reload(ResourceManager manager) {
        EarlyStageMain.SIEVE_DROP_TEMPLATES.clear();

        manager.findResources("sieve_drops", id -> id.getPath().endsWith(".json")).forEach((id, resourceRef) -> {
            try {
                InputStream stream = resourceRef.getInputStream();
                JsonObject data = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();

                for (int u = 0; u < data.getAsJsonArray("drops").size(); u++) {
                    JsonObject data2 = (JsonObject) data.getAsJsonArray("drops").get(u);
                    if (Registry.BLOCK.get(new Identifier(data2.get("block_id").getAsString())) == Blocks.AIR) {
                        LOGGER.warn("Block Id: " + data2.get("block_id").getAsString() + " is not a valid block id");
                        continue;
                    }

                    Item blockItem = Registry.BLOCK.get(new Identifier(data2.get("block_id").getAsString())).asItem();

                    List<Item> blockDrops = new ArrayList<Item>();
                    List<Float> dropChances = new ArrayList<Float>();
                    List<Integer> rollCount = new ArrayList<Integer>();

                    if (JsonHelper.getBoolean(data2, "replace", false)) {
                        for (int i = 0; i < EarlyStageMain.SIEVE_DROP_TEMPLATES.size(); i++)
                            if (EarlyStageMain.SIEVE_DROP_TEMPLATES.get(i).getBlockItem() == blockItem) {
                                EarlyStageMain.SIEVE_DROP_TEMPLATES.remove(i);
                                break;
                            }
                    } else {
                        // checks if it already exist
                        for (int i = 0; i < EarlyStageMain.SIEVE_DROP_TEMPLATES.size(); i++)
                            if (EarlyStageMain.SIEVE_DROP_TEMPLATES.get(i).getBlockItem() == blockItem) {
                                blockDrops.addAll(EarlyStageMain.SIEVE_DROP_TEMPLATES.get(i).getBlockDrops());
                                dropChances.addAll(EarlyStageMain.SIEVE_DROP_TEMPLATES.get(i).getDropChances());
                                rollCount.addAll(EarlyStageMain.SIEVE_DROP_TEMPLATES.get(i).getRollCount());
                                EarlyStageMain.SIEVE_DROP_TEMPLATES.remove(i);
                                break;
                            }
                    }

                    for (int i = 0; i < data2.getAsJsonArray("block_drops").size(); i++) {
                        JsonObject data3 = (JsonObject) data2.getAsJsonArray("block_drops").get(i);
                        blockDrops.add(Registry.ITEM.get(new Identifier(data3.get("item_id").getAsString())));
                        dropChances.add(data3.get("chance").getAsFloat());
                        rollCount.add(data3.get("rolls").getAsInt());
                    }
                    EarlyStageMain.SIEVE_DROP_TEMPLATES.add(new SieveDropTemplate(blockItem, blockDrops, dropChances, rollCount));
                }
            } catch (Exception e) {
                LOGGER.error("Error occurred while loading resource {}. {}", id.toString(), e.toString());
            }
        });

    }

}
