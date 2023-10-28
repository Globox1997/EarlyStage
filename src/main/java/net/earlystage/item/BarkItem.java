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
    private final Block logBlock;
    @Nullable
    private final Block woodBlock;

    public BarkItem(Settings settings, Block logBlock, @Nullable Block woodBlock, int cookTime) {
        super(settings);
        this.logBlock = logBlock;
        this.woodBlock = woodBlock;
        FuelRegistry.INSTANCE.add(this, cookTime);
        BarkItem.BARK_ITEMS.put(this.logBlock, this);
        if (this.woodBlock != null) {
            BarkItem.BARK_ITEMS.put(this.woodBlock, this);
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos blockPos = context.getBlockPos();
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (blockState.isIn(BlockTags.LOGS) && AxeItemAccessor.getStrippedBlocks().containsValue(block)
                && (AxeItemAccessor.getStrippedBlocks().get(logBlock) == block || (woodBlock != null && AxeItemAccessor.getStrippedBlocks().get(woodBlock) == block))) {
            PlayerEntity playerEntity = context.getPlayer();
            ItemStack itemStack = context.getStack();
            if (playerEntity instanceof ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) playerEntity, blockPos, itemStack);
            }
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f);

            if (!world.isClient()) {
                BlockState blockState2 = AxeItemAccessor.getStrippedBlocks().get(logBlock) == block ? logBlock.getDefaultState().with(PillarBlock.AXIS, blockState.get(PillarBlock.AXIS))
                        : woodBlock.getDefaultState();
                world.setBlockState(blockPos, blockState2, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, blockState2));
                if (playerEntity != null && !playerEntity.isCreative()) {
                    itemStack.decrement(1);
                }
            }
            return ActionResult.success(world.isClient);
        }
        return super.useOnBlock(context);
    }

}
