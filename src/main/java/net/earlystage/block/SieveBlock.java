package net.earlystage.block;

import net.earlystage.block.entity.SieveBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

public class SieveBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public SieveBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SieveBlockEntity(pos, state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return (BlockState) state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
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
                    if (!world.isClientSide()) {
                        inventory.setItem(0, new ItemStack(itemStack.getItem(), 1));
                        if (!player.isCreative())
                            itemStack.shrink(1);
                    }
                    return ItemInteractionResult.sidedSuccess(world.isClientSide());
                }
                return ItemInteractionResult.FAIL;
            } else {
                ((SieveBlockEntity) blockEntity).sieve();
                return ItemInteractionResult.sidedSuccess(world.isClientSide());
            }
        }
        return ItemInteractionResult.FAIL;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.is(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SieveBlockEntity) {
            Containers.dropContents(world, pos, (Container) ((SieveBlockEntity) blockEntity));
        }
        super.onRemove(state, world, pos, newState, moved);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClientSide() && !world.getBlockState(pos.above()).isAir() && world.getBlockEntity(pos) != null) {
            Containers.dropContents(world, pos, (Container) ((SieveBlockEntity) world.getBlockEntity(pos)));
            ((SieveBlockEntity) world.getBlockEntity(pos)).clearContent();
        }
        super.neighborChanged(state, world, pos, sourceBlock, sourcePos, notify);
    }
}
