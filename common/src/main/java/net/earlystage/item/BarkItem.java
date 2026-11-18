package net.earlystage.item;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import net.earlystage.init.CompatInit;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class BarkItem extends Item {

    public static final Map<Block, Item> BARK_ITEMS = new HashMap<Block, Item>();
    public static final Map<Block, Block> STRIPPED_LOG_COMPAT = new HashMap<Block, Block>();
    @Nullable
    private Block logBlock;
    @Nullable
    private Block woodBlock;

    public BarkItem(Properties settings, @Nullable Block logBlock, @Nullable Block woodBlock) {
        super(settings);
        this.setLogBlock(logBlock);
        this.setWoodBlock(woodBlock);
    }

    @Nullable
    public static Item getBarkItem(Block block) {
        CompatInit.resolvePendingBlocks();
        return BARK_ITEMS.get(block);
    }

    public static void addStrippedBlock(Block block, Block strippedBlock) {
        STRIPPED_LOG_COMPAT.put(block, strippedBlock);
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
    public InteractionResult useOn(UseOnContext context) {
        BlockPos blockPos = context.getClickedPos();
        Level world = context.getLevel();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.is(BlockTags.LOGS)) {
            Block unstrippedBlock = this.getUnstrippedBlock(blockState.getBlock());
            if (unstrippedBlock != null) {
                Player playerEntity = context.getPlayer();
                ItemStack itemStack = context.getItemInHand();
                if (playerEntity instanceof ServerPlayer serverPlayerEntity) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayerEntity, blockPos, itemStack);
                }
                world.playSound(null, blockPos, SoundEvents.AXE_STRIP.value(), SoundSource.BLOCKS, 1.0f, 1.0f);

                if (!world.isClientSide()) {
                    BlockState blockState2 = unstrippedBlock.defaultBlockState();
                    if (blockState.hasProperty(RotatedPillarBlock.AXIS) && blockState2.hasProperty(RotatedPillarBlock.AXIS)) {
                        blockState2 = blockState2.setValue(RotatedPillarBlock.AXIS, blockState.getValue(RotatedPillarBlock.AXIS));
                    }
                    world.setBlock(blockPos, blockState2, Block.UPDATE_ALL | Block.UPDATE_IMMEDIATE);
                    world.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(playerEntity, blockState2));
                    if (playerEntity != null && !playerEntity.isCreative()) {
                        itemStack.shrink(1);
                    }
                }
                return InteractionResult.SUCCESS;
            }

        }
        return super.useOn(context);
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
        CompatInit.resolvePendingBlocks();
        return STRIPPED_LOG_COMPAT.get(block) == strippedBlock;
    }

}
