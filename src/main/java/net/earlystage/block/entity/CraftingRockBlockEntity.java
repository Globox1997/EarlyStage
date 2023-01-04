package net.earlystage.block.entity;

import net.earlystage.init.BlockInit;
import net.earlystage.init.ConfigInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class CraftingRockBlockEntity extends BlockEntity implements Inventory {

    private DefaultedList<ItemStack> inventory;
    private int craftHits = 0;
    private int totalHits = 0;

    public CraftingRockBlockEntity(BlockPos pos, BlockState state) {
        super(BlockInit.CRAFTING_ROCK_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        inventory.clear();
        Inventories.readNbt(nbt, inventory);
        this.craftHits = nbt.getInt("CraftHits");
        this.totalHits = nbt.getInt("TotalHits");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("CraftHits", this.craftHits);
        nbt.putInt("TotalHits", this.totalHits);
    }

    @Override
    public void markDirty() {
        super.markDirty();
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
        if (!this.world.isClient && ConfigInit.CONFIG.craftRockMaxCraftHits <= this.totalHits) {
            this.world.breakBlock(pos, false, entity);
        }
    }

    private void sendUpdate() {
        if (this.world != null) {
            BlockState state = this.world.getBlockState(this.pos);
            (this.world).updateListeners(this.pos, state, state, 3);
        }
    }

    @Override
    public void clear() {
        this.inventory.clear();
        this.markDirty();
    }

    @Override
    public int size() {
        return 9;
    }

    @Override
    public boolean isEmpty() {
        return this.inventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(this.inventory, slot, 1);
        this.markDirty();
        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        this.markDirty();
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        this.markDirty();
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        // if (this.isEmpty() && this.world.getBlockState(pos.up()).isAir())
        // for (int i = 0; i < EarlyStageMain.SIEVE_DROP_TEMPLATES.size(); i++)
        // if (stack.getItem().equals(EarlyStageMain.SIEVE_DROP_TEMPLATES.get(i).getBlockItem()))
        // return true;
        return true;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

}
