package net.earlystage.block.entity;

import net.earlystage.EarlyStageMain;
import net.earlystage.data.SieveDropTemplate;
import net.earlystage.init.BlockInit;
import net.earlystage.init.ConfigInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class SieveBlockEntity extends BlockEntity implements Container {

    private int tick;
    private final NonNullList<ItemStack> inventory;
    private int sieveCount;

    public SieveBlockEntity(BlockPos pos, BlockState state) {
        super(BlockInit.SIEVE_ENTITY, pos, state);
        this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        inventory.clear();
        ContainerHelper.loadAllItems(input, inventory);
        sieveCount = input.getIntOr("SieveCount", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, inventory);
        output.putInt("SieveCount", sieveCount);
    }

    public static void clientTick(Level world, BlockPos pos, BlockState state, SieveBlockEntity blockEntity) {
        blockEntity.update();
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, SieveBlockEntity blockEntity) {
        blockEntity.update();
    }

    private void update() {
        if (!this.isEmpty() && level.hasNeighborSignal(worldPosition)) {
            this.tick++;
            if (this.tick >= ConfigInit.CONFIG.redstoneSieveTicks) {
                this.sieve();
                this.tick = 0;
            }
        } else if (this.tick != 0)
            this.tick = 0;
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
            if (!level.isClientSide()) {
                for (int i = 0; i < EarlyStageMain.SIEVE_DROP_TEMPLATES.size(); i++) {
                    if (EarlyStageMain.SIEVE_DROP_TEMPLATES.get(i).getBlockItem().equals(this.getItem(0).getItem())) {
                        SieveDropTemplate sieveDropTemplate = EarlyStageMain.SIEVE_DROP_TEMPLATES.get(i);

                        for (int u = 0; u < sieveDropTemplate.getBlockDrops().size(); u++) {
                            for (int k = 0; k < sieveDropTemplate.getRollCount().get(u); k++) {
                                if (this.level.getRandom().nextFloat() <= sieveDropTemplate.getDropChances().get(u))
                                    dropItem(sieveDropTemplate.getBlockDrops().get(u));
                            }
                        }
                        break;
                    }
                }
                this.clearContent();
            }
            level.playSound(null, worldPosition, SoundEvents.COMPOSTER_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
        } else {
            level.playSound(null, worldPosition, SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            if (!level.isClientSide()) {
                this.setChanged();
            }
        }
    }

    private void dropItem(Item item) {
        double d = (double) (level.getRandom().nextFloat() * 0.7f) + (double) 0.15f;
        double e = (double) (level.getRandom().nextFloat() * 0.7f) + 0.06000000238418579 + 0.6;
        double g = (double) (level.getRandom().nextFloat() * 0.7f) + (double) 0.15f;

        ItemEntity itemEntity = new ItemEntity(level, (double) worldPosition.getX() + d, (double) worldPosition.getY() + e, (double) worldPosition.getZ() + g, new ItemStack(item));
        itemEntity.setDefaultPickUpDelay();
        level.addFreshEntity(itemEntity);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        sendUpdate();
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
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.getItem(0).isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.inventory.get(0);
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
        this.inventory.set(0, stack);
        this.refreshSieveCount();
        this.setChanged();
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (this.isEmpty() && this.level.getBlockState(worldPosition.above()).isAir()) {
            for (int i = 0; i < EarlyStageMain.SIEVE_DROP_TEMPLATES.size(); i++) {
                if (stack.getItem().equals(EarlyStageMain.SIEVE_DROP_TEMPLATES.get(i).getBlockItem())) {
                    return true;
                }
            }
        }
        return false;
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
