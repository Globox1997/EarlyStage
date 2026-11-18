package net.earlystage.block.entity;

import net.earlystage.init.BlockInit;
import net.earlystage.init.ConfigInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class CraftingRockBlockEntity extends BlockEntity implements Container {

    private final NonNullList<ItemStack> inventory;
    private int craftHits = 0;
    private int totalHits = 0;

    public CraftingRockBlockEntity(BlockPos pos, BlockState state) {
        super(BlockInit.CRAFTING_ROCK_ENTITY, pos, state);
        this.inventory = NonNullList.withSize(9, ItemStack.EMPTY);
    }

    @Override
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        inventory.clear();
        ContainerHelper.loadAllItems(input, inventory);
        this.craftHits = input.getIntOr("CraftHits", 0);
        this.totalHits = input.getIntOr("TotalHits", 0);
    }

    @Override
    public void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, inventory);
        output.putInt("CraftHits", this.craftHits);
        output.putInt("TotalHits", this.totalHits);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        sendUpdate();
    }

    public void setCraftHits(int hits) {
        this.craftHits = hits;
    }

    public int getCraftHits() {
        return this.craftHits;
    }

    public void decreaseCraftHits(Entity entity) {
        this.craftHits--;
        this.totalHits++;
        if (!this.level.isClientSide() && ConfigInit.CONFIG.craftRockMaxCraftHits <= this.totalHits) {
            this.level.destroyBlock(worldPosition, false, entity);
        }
    }

    private void sendUpdate() {
        if (this.level != null) {
            BlockState state = this.level.getBlockState(this.worldPosition);
            this.level.sendBlockUpdated(this.worldPosition, state, state, 3);
        }
    }

    @Override
    public void clearContent() {
        this.inventory.clear();
        this.setChanged();
    }

    @Override
    public int getContainerSize() {
        return 9;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.inventory) {
            if (!itemStack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack result = ContainerHelper.removeItem(this.inventory, slot, 1);
        this.setChanged();
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        this.setChanged();
        return ContainerHelper.takeItem(this.inventory, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        this.setChanged();
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return false;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(Provider registryLookup) {
        return this.saveWithoutMetadata(registryLookup);
    }

}
