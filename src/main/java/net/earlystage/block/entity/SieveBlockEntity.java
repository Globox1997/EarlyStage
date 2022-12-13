package net.earlystage.block.entity;

import net.earlystage.EarlyStageMain;
import net.earlystage.data.SieveDropTemplate;
import net.earlystage.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class SieveBlockEntity extends BlockEntity implements Inventory {
    private DefaultedList<ItemStack> inventory;
    private int sieveCount;

    public SieveBlockEntity(BlockPos pos, BlockState state) {
        super(BlockInit.SIEVE_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        inventory.clear();
        Inventories.readNbt(nbt, inventory);
        sieveCount = nbt.getInt("SieveCount");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("SieveCount", sieveCount);
    }

    public int getSieveCount() {
        return this.sieveCount;
    }

    public void refreshSieveCount() {
        this.sieveCount = 0;
    }

    public void sieve() {
        this.sieveCount++;
        if (this.sieveCount > 3) {
            if (!world.isClient) {
                for (int i = 0; i < EarlyStageMain.SIEVE_DROP_TEMPLATES.size(); i++) {
                    if (EarlyStageMain.SIEVE_DROP_TEMPLATES.get(i).getBlockItem().equals(this.getStack(0).getItem())) {
                        SieveDropTemplate sieveDropTemplate = EarlyStageMain.SIEVE_DROP_TEMPLATES.get(i);

                        for (int u = 0; u < sieveDropTemplate.getBlockDrops().size(); u++) {
                            for (int k = 0; k < sieveDropTemplate.getRollCount().get(u); k++) {
                                if (this.world.random.nextFloat() <= sieveDropTemplate.getDropChances().get(u))
                                    dropItem(sieveDropTemplate.getBlockDrops().get(u));
                            }
                        }
                        break;
                    }
                }
                this.clear();
            }
            world.playSound(null, pos, SoundEvents.BLOCK_COMPOSTER_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
        } else
            world.playSound(null, pos, SoundEvents.BLOCK_COMPOSTER_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    private void dropItem(Item item) {
        double d = (double) (world.random.nextFloat() * 0.7f) + (double) 0.15f;
        double e = (double) (world.random.nextFloat() * 0.7f) + 0.06000000238418579 + 0.6;
        double g = (double) (world.random.nextFloat() * 0.7f) + (double) 0.15f;

        ItemEntity itemEntity = new ItemEntity(world, (double) pos.getX() + d, (double) pos.getY() + e, (double) pos.getZ() + g, new ItemStack(item));
        itemEntity.setToDefaultPickupDelay();
        world.spawnEntity(itemEntity);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        sendUpdate();
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
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.getStack(0).isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(0);
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
        this.inventory.set(0, stack);
        this.markDirty();
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
