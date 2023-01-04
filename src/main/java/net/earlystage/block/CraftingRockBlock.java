package net.earlystage.block;

import java.util.Optional;

import net.earlystage.block.entity.CraftingRockBlockEntity;
import net.earlystage.block.inventory.CraftingRockInventory;
import net.earlystage.init.BlockInit;
import net.earlystage.init.ConfigInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class CraftingRockBlock extends Block implements BlockEntityProvider {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    private static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    public CraftingRockBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CraftingRockBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BOTTOM_SHAPE;
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
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

            if (hit.getPos().getY() % 1 < 0.505D && hit.getPos().getY() % 1 > 0.495D) {
                if (itemStack.isOf(BlockInit.ROCK.asItem())) {
                    if (!inventory.isEmpty()) {
                        if (!world.isClient) {
                            if (((CraftingRockBlockEntity) blockEntity).getCraftHits() - 1 == 0) {
                                tryCraftItem(world, player, (CraftingRockBlockEntity) blockEntity);
                                ((CraftingRockBlockEntity) blockEntity).setCraftHits(ConfigInit.CONFIG.craftRockCraftHits + world.getRandom().nextInt(ConfigInit.CONFIG.craftRockCraftHits / 2));
                            } else
                                ((CraftingRockBlockEntity) blockEntity).decreaseCraftHits(player);
                        }
                        world.playSound(player, pos, SoundEvents.BLOCK_STONE_HIT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        return ActionResult.success(world.isClient);
                    }
                    return ActionResult.FAIL;
                }

                int slot = getSlot(Math.abs(hit.getPos().getX() % 1), Math.abs(hit.getPos().getZ() % 1));
                if (inventory.getStack(slot).isEmpty() && !itemStack.isEmpty()) {
                    if (!world.isClient) {
                        inventory.setStack(slot, new ItemStack(itemStack.getItem(), 1));
                        if (!player.isCreative())
                            itemStack.decrement(1);
                        ((CraftingRockBlockEntity) blockEntity).setCraftHits(ConfigInit.CONFIG.craftRockCraftHits + world.getRandom().nextInt(ConfigInit.CONFIG.craftRockCraftHits / 2));
                    }
                    return ActionResult.success(world.isClient);
                } else if (!inventory.getStack(slot).isEmpty()) {
                    if (!world.isClient) {
                        if (!player.isCreative())
                            player.getInventory().offerOrDrop(inventory.getStack(slot));
                        inventory.setStack(slot, new ItemStack(Items.AIR));
                        ((CraftingRockBlockEntity) blockEntity).setCraftHits(ConfigInit.CONFIG.craftRockCraftHits + world.getRandom().nextInt(ConfigInit.CONFIG.craftRockCraftHits / 2));
                    }
                    return ActionResult.success(world.isClient);
                }
            }
        }
        return ActionResult.FAIL;
    }

    private int getSlot(double x, double z) {
        int slot = 0;
        for (int i = 2; i >= 0; i--) {
            for (int u = 2; u >= 0; u--) {
                if (x > i * 0.33D && z > u * 0.33D) {
                    return slot;
                }
                slot++;
            }
        }
        return 0;
    }

    private void tryCraftItem(World world, PlayerEntity player, CraftingRockBlockEntity blockEntity) {
        if (!world.isClient) {
            CraftingRockInventory craftingInventory = null;
            Optional<CraftingRecipe> optional = null;
            for (int i = 0; i < 4; i++) {
                craftingInventory = new CraftingRockInventory(blockEntity, i);
                optional = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);
                if (optional.isPresent()) {
                    break;
                }
            }
            if (optional != null && optional.isPresent() && (optional.get().isIgnoredInRecipeBook() || !world.getGameRules().getBoolean(GameRules.DO_LIMITED_CRAFTING)
                    || ((ServerPlayerEntity) player).getRecipeBook().contains(optional.get()))) {
                blockEntity.clear();
                blockEntity.setStack(4, optional.get().craft(craftingInventory));
            }
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CraftingRockBlockEntity) {
            ItemScatterer.spawn(world, pos, (Inventory) ((CraftingRockBlockEntity) blockEntity));
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

}
