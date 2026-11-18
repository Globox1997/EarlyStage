package net.earlystage.block.inventory;

import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;

public class CraftingRockInventory extends TransientCraftingContainer {

    private final NonNullList<ItemStack> stacks;

    public CraftingRockInventory(Container inventory, int variant) {
        super(null, 3, 3);
        stacks = NonNullList.withSize(9, ItemStack.EMPTY);

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            this.stacks.set(getSlot(variant, i), inventory.getItem(i));
        }
    }

    @Override
    public int getContainerSize() {
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
    public ItemStack getItem(int slot) {
        if (slot >= this.getContainerSize()) {
            return ItemStack.EMPTY;
        }
        return this.stacks.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack itemStack = ContainerHelper.removeItem(this.stacks, slot, amount);
        return itemStack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.stacks, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.stacks.set(slot, stack);
    }

    @Override
    public void clearContent() {
        this.stacks.clear();
    }

    @Override
    public List<ItemStack> getItems() {
        return List.copyOf(this.stacks);
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
