package net.earlystage.block;

import net.earlystage.EarlyStageMain;
import net.earlystage.block.entity.CraftingRockBlockEntity;
import net.earlystage.block.inventory.CraftingRockInventory;
import net.earlystage.init.ConfigInit;
import net.earlystage.init.TagInit;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

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
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
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
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CraftingRockBlockEntity craftingRockBlockEntity) {
            if (Math.abs(hit.getPos().getY() % 1) < 0.505D && Math.abs(hit.getPos().getY() % 1) > 0.495D) {
                if (itemStack.isIn(TagInit.ROCK_ITEMS)) {
                    if (!craftingRockBlockEntity.isEmpty()) {
                        if (!world.isClient()) {
                            if (craftingRockBlockEntity.getCraftHits() - 1 <= 0) {
                                tryCraftItem(world, player, craftingRockBlockEntity);
                                craftingRockBlockEntity.setCraftHits(ConfigInit.CONFIG.craftRockCraftHits + world.getRandom().nextInt(ConfigInit.CONFIG.craftRockCraftHits / 2));
                            } else {
                                craftingRockBlockEntity.decreaseCraftHits(player);
                            }
                        }
                        world.playSound(player, pos, SoundEvents.BLOCK_STONE_HIT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        return ItemActionResult.success(world.isClient());
                    }
                    return ItemActionResult.FAIL;
                }
                double xPos = hit.getPos().getX() < 0D ? 1.0D + hit.getPos().getX() % 1 : hit.getPos().getX() % 1;
                double zPos = hit.getPos().getZ() < 0D ? 1.0D + hit.getPos().getZ() % 1 : hit.getPos().getZ() % 1;
                int slot = getSlot(xPos, zPos);
                if (craftingRockBlockEntity.getStack(slot).isEmpty() && !itemStack.isEmpty() && isAllowedInputItem(itemStack.getItem())) {
                    if (!world.isClient()) {
                        craftingRockBlockEntity.setStack(slot, new ItemStack(itemStack.getItem(), 1));
                        if (!player.isCreative()) {
                            itemStack.decrement(1);
                        }
                        craftingRockBlockEntity.setCraftHits(ConfigInit.CONFIG.craftRockCraftHits + world.getRandom().nextInt(ConfigInit.CONFIG.craftRockCraftHits / 2));
                    }
                    return ItemActionResult.success(world.isClient());
                } else if (!craftingRockBlockEntity.getStack(slot).isEmpty()) {
                    if (!world.isClient()) {
                        if (!player.isCreative()) {
                            player.getInventory().offerOrDrop(craftingRockBlockEntity.getStack(slot));
                        }
                        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.2F,
                                ((world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F, world.getRandom().nextLong());
                        craftingRockBlockEntity.setStack(slot, ItemStack.EMPTY);
                        craftingRockBlockEntity.setCraftHits(ConfigInit.CONFIG.craftRockCraftHits + world.getRandom().nextInt(ConfigInit.CONFIG.craftRockCraftHits / 2));
                    }
                    return ItemActionResult.success(world.isClient());
                }
            }
        }
        return ItemActionResult.FAIL;
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
        if (!world.isClient()) {
            CraftingRockInventory craftingInventory = null;
            Optional<RecipeEntry<CraftingRecipe>> optional = Optional.empty();
            for (int i = 0; i < 4; i++) {
                craftingInventory = new CraftingRockInventory(blockEntity, i);
                optional = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory.createRecipeInput(), world);
                if (optional.isPresent()) {
                    break;
                }
            }
            if (optional.isPresent() && (optional.get().value().isIgnoredInRecipeBook() || !world.getGameRules().getBoolean(GameRules.DO_LIMITED_CRAFTING) || ((ServerPlayerEntity) player).getRecipeBook().contains(optional.get()))) {
                if (isAllowedOutputItem(optional.get().value().craft(craftingInventory.createRecipeInput(), world.getRegistryManager()).getItem())) {
                    blockEntity.clear();
                    blockEntity.setStack(4, optional.get().value().craft(craftingInventory.createRecipeInput(), world.getRegistryManager()));
                }
            }
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CraftingRockBlockEntity craftingRockBlockEntity) {
            ItemScatterer.spawn(world, pos, craftingRockBlockEntity);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType options) {
        if (ConfigInit.CONFIG.infoTooltips) {
            tooltip.add(Text.translatable("earlystage.moreinfo.tooltip"));
            if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340)) {
                tooltip.remove(Text.translatable("earlystage.moreinfo.tooltip"));
                tooltip.add(Text.translatable("block.earlystage.crafting_rock.tooltip"));
            }
        }
        super.appendTooltip(stack, context, tooltip, options);
    }

    private boolean isAllowedInputItem(Item item) {
        for (List<Item> items : EarlyStageMain.CRAFTING_ROCK_RECIPE_ITEMS.values()) {
            if (items.contains(item)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAllowedOutputItem(Item item) {
        for (Item key : EarlyStageMain.CRAFTING_ROCK_RECIPE_ITEMS.keySet()) {
            if (key.equals(item)) {
                return true;
            }
        }
        return false;
    }

}
