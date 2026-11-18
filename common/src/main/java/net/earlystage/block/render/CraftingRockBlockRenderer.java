package net.earlystage.block.render;

import org.jspecify.annotations.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.earlystage.block.CraftingRockBlock;
import net.earlystage.block.entity.CraftingRockBlockEntity;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class CraftingRockBlockRenderer implements BlockEntityRenderer<CraftingRockBlockEntity, CraftingRockBlockRenderer.CraftingRockRenderState> {

    private final ItemModelResolver itemModelResolver;

    public CraftingRockBlockRenderer(BlockEntityRendererProvider.Context ctx) {
        this.itemModelResolver = ctx.itemModelResolver();
    }

    @Override
    public CraftingRockRenderState createRenderState() {
        return new CraftingRockRenderState();
    }

    @Override
    public void extractRenderState(CraftingRockBlockEntity blockEntity, CraftingRockRenderState state, float partialTicks, Vec3 cameraPosition,
            ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        if (blockEntity.getLevel() != null) {
            state.lightCoords = LightCoordsUtil.getLightCoords(blockEntity.getLevel(), blockEntity.getBlockPos().above());
        }
        state.facing = blockEntity.getBlockState().getValue(CraftingRockBlock.FACING);
        int seed = (int) blockEntity.getBlockPos().asLong();
        for (int slot = 0; slot < 9; slot++) {
            ItemStack stack = blockEntity.getItem(slot);
            state.blockItems[slot] = stack.getItem() instanceof BlockItem;
            this.itemModelResolver.updateForTopItem(state.items[slot], stack, ItemDisplayContext.GROUND, blockEntity.getLevel(), null, seed);
        }
    }

    @Override
    public void submit(CraftingRockRenderState state, PoseStack matrices, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        int slot = 0;
        for (int i = 0; i < 3; i++) {
            for (int u = 0; u < 3; u++) {

                if (!state.items[slot].isEmpty()) {
                    matrices.pushPose();

                    boolean isBlockItem = state.blockItems[slot];

                    if (isBlockItem) {
                        matrices.scale(0.8f, 0.8f, 0.8f);
                    } else {
                        matrices.scale(0.5f, 0.5f, 0.5f);
                    }

                    matrices.rotateDegrees(Axis.XN, 90.0f);

                    Direction direction = state.facing;
                    matrices.rotateDegrees(Axis.ZN, direction.toYRot() - 180.0f);

                    if (direction.equals(Direction.NORTH)) {
                        if (isBlockItem) {
                            matrices.translate(i == 0 ? 1.02D : i == 1 ? 0.63D : 0.23D, u == 0 ? -1.2D : u == 1 ? -0.81D : -0.42D, 0.75D);
                        } else {
                            matrices.translate(i == 0 ? 1.63D : i == 1 ? 1.0D : 0.36D, u == 0 ? -1.75D : u == 1 ? -1.12D : -0.5D, 1.02D);
                        }
                    } else if (direction.equals(Direction.EAST)) {
                        if (isBlockItem) {
                            matrices.translate(u == 0 ? 1.02D : u == 1 ? 0.63D : 0.23D, i == 0 ? -1.17D : i == 1 ? -1.56D : -1.95D, 0.75D);
                        } else {
                            matrices.translate(u == 0 ? 1.63D : u == 1 ? 1.0D : 0.36D, i == 0 ? -0.5D : i == 1 ? -1.12D : -1.75D, 1.02D);
                        }
                        matrices.translate(0.0D, 2.0D, 0.0D);
                    } else if (direction.equals(Direction.SOUTH)) {
                        if (isBlockItem) {
                            matrices.translate(i == 0 ? 0.98D : i == 1 ? 1.38 : 1.77D, u == 0 ? -1.17D : u == 1 ? -1.56D : -1.95D, 0.75D);
                        } else {
                            matrices.translate(i == 0 ? 0.36D : i == 1 ? 1.0D : 1.63D, u == 0 ? -0.5D : u == 1 ? -1.12D : -1.75D, 1.02D);
                        }
                        matrices.translate(-2.0D, 2.0D, 0.0D);
                    } else if (direction.equals(Direction.WEST)) {
                        if (isBlockItem) {
                            matrices.translate(u == 0 ? 0.98D : u == 1 ? 1.38D : 1.77D, i == 0 ? -1.2D : i == 1 ? -0.81D : -0.42D, 0.75D);
                        } else {
                            matrices.translate(u == 0 ? 0.36D : u == 1 ? 1.0D : 1.63D, i == 0 ? -1.75D : i == 1 ? -1.12D : -0.5D, 1.02D);
                        }
                        matrices.translate(-2.0D, 0.0D, 0.0D);
                    }

                    state.items[slot].submit(matrices, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                    matrices.popPose();
                }
                slot++;
            }
        }
    }

    public static class CraftingRockRenderState extends BlockEntityRenderState {
        public final ItemStackRenderState[] items = new ItemStackRenderState[9];
        public final boolean[] blockItems = new boolean[9];
        public Direction facing = Direction.NORTH;

        public CraftingRockRenderState() {
            for (int i = 0; i < 9; i++) {
                this.items[i] = new ItemStackRenderState();
            }
        }
    }
}
