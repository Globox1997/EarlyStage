package net.earlystage.block.inventory;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class CraftingRockInventory extends CraftingInventory {

    private final DefaultedList<ItemStack> stacks;

    public CraftingRockInventory(Inventory inventory, int variant) {
        super(null, 3, 3);
        stacks = DefaultedList.ofSize(9, ItemStack.EMPTY);

        for (int i = 0; i < inventory.size(); i++) {
            this.stacks.set(getSlot(variant, i), inventory.getStack(i));
        }
    }

    @Override
    public int size() {
        return this.stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.stacks) {
            if (itemStack.isEmpty())
                continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        if (slot >= this.size()) {
            return ItemStack.EMPTY;
        }
        return this.stacks.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.stacks, slot, amount);
        return itemStack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.stacks, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.stacks.set(slot, stack);
    }

    @Override
    public void clear() {
        this.stacks.clear();
    }

    private int getSlot(int variant, int i) {
        if (variant == 0)
            return i;
        else if (variant == 1)
            return Math.abs(i - 8);
        else if (variant == 2) {
            switch (i) {
            case 0:
                return 6;
            case 1:
                return 3;
            case 2:
                return 0;
            case 3:
                return 7;
            case 4:
                return 4;
            case 5:
                return 1;
            case 6:
                return 8;
            case 7:
                return 5;
            case 8:
                return 2;
            default:
                return 0;
            }
        } else if (variant == 3) {
            switch (i) {
            case 0:
                return 2;
            case 1:
                return 5;
            case 2:
                return 8;
            case 3:
                return 1;
            case 4:
                return 4;
            case 5:
                return 7;
            case 6:
                return 0;
            case 7:
                return 3;
            case 8:
                return 6;
            default:
                return 0;
            }
        }
        return 0;
    }

}
