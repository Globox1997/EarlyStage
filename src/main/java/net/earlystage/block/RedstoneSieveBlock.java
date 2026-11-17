package net.earlystage.block;

import org.jetbrains.annotations.Nullable;

import net.earlystage.block.entity.SieveBlockEntity;
import net.earlystage.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class RedstoneSieveBlock extends SieveBlock {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public RedstoneSieveBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(POWERED, ctx.getLevel().hasNeighborSignal(ctx.getClickedPos())).setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BlockInit.SIEVE_ENTITY, world.isClientSide() ? SieveBlockEntity::clientTick : SieveBlockEntity::serverTick);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClientSide()) {
            boolean bl = state.getValue(POWERED);
            if (bl != world.hasNeighborSignal(pos)) {
                if (bl) {
                    world.scheduleTick(pos, this, 4);
                } else {
                    world.setBlock(pos, state.cycle(POWERED), Block.UPDATE_CLIENTS);
                }
            }
        }
        super.neighborChanged(state, world, pos, sourceBlock, sourcePos, notify);

    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (state.getValue(POWERED).booleanValue() && !world.hasNeighborSignal(pos)) {
            world.setBlock(pos, state.cycle(POWERED), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            Container inventory = (Container) world.getBlockEntity(pos);
            ItemStack blockStack = inventory.getItem(0);
            if (blockStack.isEmpty()) {
                if (inventory.canPlaceItem(0, itemStack)) {
                    ((SieveBlockEntity) blockEntity).refreshSieveCount();
                    if (!world.isClientSide()) {
                        inventory.setItem(0, new ItemStack(itemStack.getItem(), 1));
                        if (!player.isCreative())
                            itemStack.shrink(1);
                    }
                    return ItemInteractionResult.sidedSuccess(world.isClientSide());
                }
                return ItemInteractionResult.FAIL;
            } else {
                if (itemStack.isEmpty()) {
                    if (!world.isClientSide()) {
                        player.setItemInHand(hand, blockStack);
                        inventory.clearContent();
                    }
                    return ItemInteractionResult.sidedSuccess(world.isClientSide());
                }
            }
        }
        return ItemInteractionResult.FAIL;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

}
