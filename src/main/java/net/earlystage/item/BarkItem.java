package net.earlystage.item;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import net.earlystage.mixin.access.AxeItemAccessor;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class BarkItem extends Item {

    public static final Map<Block, Item> BARK_ITEMS = new HashMap<Block, Item>();
    public static final Map<Block, Block> STRIPPED_LOG_COMPAT = new HashMap<Block, Block>();
    @Nullable
    private Block logBlock;
    @Nullable
    private Block woodBlock;

    public BarkItem(Settings settings, @Nullable Block logBlock, @Nullable Block woodBlock, int cookTime) {
        super(settings);
        FuelRegistry.INSTANCE.add(this, cookTime);
        this.setLogBlock(logBlock);
        this.setWoodBlock(woodBlock);
    }

    public void setLogBlock(@Nullable Block logBlock) {
        this.logBlock = logBlock;
        if (logBlock != null) {
            BarkItem.BARK_ITEMS.put(logBlock, this);
        }
    }

    public void setWoodBlock(@Nullable Block woodBlock) {
        this.woodBlock = woodBlock;
        if (woodBlock != null) {
            BarkItem.BARK_ITEMS.put(woodBlock, this);
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos blockPos = context.getBlockPos();
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isIn(BlockTags.LOGS)) {
            Block unstrippedBlock = this.getUnstrippedBlock(blockState.getBlock());
            if (unstrippedBlock != null) {
                PlayerEntity playerEntity = context.getPlayer();
                ItemStack itemStack = context.getStack();
                if (playerEntity instanceof ServerPlayerEntity serverPlayerEntity) {
                    Criteria.ITEM_USED_ON_BLOCK.trigger(serverPlayerEntity, blockPos, itemStack);
                }
                world.playSound(null, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f);

                if (!world.isClient()) {
                    BlockState blockState2 = unstrippedBlock.getDefaultState();
                    if (blockState.contains(PillarBlock.AXIS) && blockState2.contains(PillarBlock.AXIS)) {
                        blockState2 = blockState2.with(PillarBlock.AXIS, blockState.get(PillarBlock.AXIS));
                    }
                    world.setBlockState(blockPos, blockState2, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
                    world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, blockState2));
                    if (playerEntity != null && !playerEntity.isCreative()) {
                        itemStack.decrement(1);
                    }
                }
                return ActionResult.success(world.isClient());
            }

        }
        return super.useOnBlock(context);
    }

    @Nullable
    private Block getUnstrippedBlock(Block strippedBlock) {
        if (this.logBlock != null && isStrippedVersion(this.logBlock, strippedBlock)) {
            return this.logBlock;
        }
        if (this.woodBlock != null && isStrippedVersion(this.woodBlock, strippedBlock)) {
            return this.woodBlock;
        }
        return null;
    }

    private static boolean isStrippedVersion(Block block, Block strippedBlock) {
        return AxeItemAccessor.getStrippedBlocks().get(block) == strippedBlock || STRIPPED_LOG_COMPAT.get(block) == strippedBlock;
    }

}
