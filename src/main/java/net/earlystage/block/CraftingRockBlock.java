package net.earlystage.block;

import net.earlystage.EarlyStageMain;
import net.earlystage.block.entity.CraftingRockBlockEntity;
import net.earlystage.block.inventory.CraftingRockInventory;
import net.earlystage.init.ConfigInit;
import net.earlystage.init.TagInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Prediction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.List;
import java.util.Optional;

public class CraftingRockBlock extends Block implements EntityBlock {

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape BOTTOM_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    public CraftingRockBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CraftingRockBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return BOTTOM_SHAPE;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
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
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CraftingRockBlockEntity craftingRockBlockEntity) {
            if (Math.abs(hit.getLocation().y() % 1) < 0.505D && Math.abs(hit.getLocation().y() % 1) > 0.495D) {
                if (itemStack.is(TagInit.ROCK_ITEMS)) {
                    if (!craftingRockBlockEntity.isEmpty()) {
                        if (!world.isClientSide()) {
                            if (craftingRockBlockEntity.getCraftHits() - 1 <= 0) {
                                tryCraftItem(world, player, craftingRockBlockEntity);
                                craftingRockBlockEntity.setCraftHits(getRandomCraftHits(world));
                            } else {
                                craftingRockBlockEntity.decreaseCraftHits(player);
                            }
                        }
                        world.playSound(player, pos, SoundEvents.STONE_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                        return InteractionResult.SUCCESS;
                    }
                    return InteractionResult.FAIL;
                }
                double xPos = hit.getLocation().x() < 0D ? 1.0D + hit.getLocation().x() % 1 : hit.getLocation().x() % 1;
                double zPos = hit.getLocation().z() < 0D ? 1.0D + hit.getLocation().z() % 1 : hit.getLocation().z() % 1;
                int slot = getSlot(xPos, zPos);
                if (craftingRockBlockEntity.getItem(slot).isEmpty()) {
                    if (!itemStack.isEmpty()) {
                        if (isAllowedInputItem(itemStack.getItem())) {
                            if (!world.isClientSide()) {
                                craftingRockBlockEntity.setItem(slot, new ItemStack(itemStack.getItem(), 1));
                                if (!player.isCreative()) {
                                    itemStack.shrink(1);
                                }
                                craftingRockBlockEntity.setCraftHits(getRandomCraftHits(world));
                            }
                            return InteractionResult.SUCCESS;
                        } else {
                            player.sendOverlayMessage(Component.translatable("block.earlystage.crafting_rock.unusable"));
                        }
                    }
                    return InteractionResult.FAIL;
                } else if (!craftingRockBlockEntity.getItem(slot).isEmpty()) {
                    if (!world.isClientSide()) {
                        if (!player.isCreative()) {
                            player.getInventory().placeItemBackInInventory(craftingRockBlockEntity.getItem(slot), Prediction.SERVER_ONLY);
                        }
                        world.playSeededSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.2F,
                                ((world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F, world.getRandom().nextLong());
                        craftingRockBlockEntity.setItem(slot, ItemStack.EMPTY);
                        craftingRockBlockEntity.setCraftHits(getRandomCraftHits(world));
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.FAIL;
    }

    private static int getRandomCraftHits(Level world) {
        int craftHits = Math.max(1, ConfigInit.CONFIG.craftRockCraftHits);
        return craftHits + world.getRandom().nextInt(Math.max(1, craftHits / 2));
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

    private void tryCraftItem(Level world, Player player, CraftingRockBlockEntity blockEntity) {
        if (world instanceof ServerLevel serverLevel) {
            CraftingRockInventory craftingInventory = null;
            Optional<RecipeHolder<CraftingRecipe>> optional = Optional.empty();
            for (int i = 0; i < 4; i++) {
                craftingInventory = new CraftingRockInventory(blockEntity, i);
                optional = serverLevel.recipeAccess().getRecipeFor(RecipeType.CRAFTING, craftingInventory.asCraftInput(), serverLevel);
                if (optional.isPresent()) {
                    break;
                }
            }
            if (optional.isPresent() && (optional.get().value().isSpecial() || !serverLevel.getGameRules().get(GameRules.LIMITED_CRAFTING) || ((ServerPlayer) player).getRecipeBook().contains(optional.get().id()))) {
                if (isAllowedOutputItem(optional.get().value().assemble(craftingInventory.asCraftInput()).getItem())) {
                    blockEntity.clearContent();
                    blockEntity.setItem(4, optional.get().value().assemble(craftingInventory.asCraftInput()));
                } else {
                    player.sendOverlayMessage(Component.translatable("block.earlystage.crafting_rock.uncraftable"));
                }
            } else {
                player.sendOverlayMessage(Component.translatable("block.earlystage.crafting_rock.uncraftable"));
            }
        }
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
