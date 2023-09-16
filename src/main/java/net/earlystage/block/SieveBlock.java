package net.earlystage.block;

import net.earlystage.block.entity.SieveBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class SieveBlock extends Block implements BlockEntityProvider {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public SieveBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SieveBlockEntity(pos, state);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState) state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            Inventory inventory = (Inventory) world.getBlockEntity(pos);
            ItemStack blockStack = inventory.getStack(0);
            if (blockStack.isEmpty()) {
                if (inventory.isValid(0, itemStack)) {
                    if (!world.isClient()) {
                        inventory.setStack(0, new ItemStack(itemStack.getItem(), 1));
                        if (!player.isCreative())
                            itemStack.decrement(1);
                    }
                    return ActionResult.success(world.isClient());
                }
                return ActionResult.FAIL;
            } else {
                ((SieveBlockEntity) blockEntity).sieve();
                return ActionResult.success(world.isClient());
            }
        }
        return ActionResult.FAIL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SieveBlockEntity) {
            ItemScatterer.spawn(world, pos, (Inventory) ((SieveBlockEntity) blockEntity));
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient() && !world.getBlockState(pos.up()).isAir() && world.getBlockEntity(pos) != null) {
            ItemScatterer.spawn(world, pos, (Inventory) ((SieveBlockEntity) world.getBlockEntity(pos)));
            ((SieveBlockEntity) world.getBlockEntity(pos)).clear();
        }
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }
}
