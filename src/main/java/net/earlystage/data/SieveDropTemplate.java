package net.earlystage.data;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;

public class SieveDropTemplate {

    private final Item blockItem;
    private final List<Item> blockDrops;
    private final List<Float> dropChances;
    private final List<Integer> rollCount;

    public SieveDropTemplate(Item blockItem, List<Item> blockDrops, List<Float> dropChances, List<Integer> rollCount) {
        this.blockItem = blockItem;
        this.blockDrops = new ArrayList<Item>();
        this.blockDrops.addAll(blockDrops);
        this.dropChances = new ArrayList<Float>();
        this.dropChances.addAll(dropChances);
        this.rollCount = new ArrayList<Integer>();
        this.rollCount.addAll(rollCount);
    }

    public Item getBlockItem() {
        return blockItem;
    }

    public List<Item> getBlockDrops() {
        return this.blockDrops;
    }

    public List<Integer> getRollCount() {
        return this.rollCount;
    }

    public List<Float> getDropChances() {
        return this.dropChances;
    }

}
