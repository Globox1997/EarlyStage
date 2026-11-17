package net.earlystage.block.render;

import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.earlystage.block.CraftingRockBlock;
import net.earlystage.block.entity.CraftingRockBlockEntity;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class CraftingRockBlockRenderer implements BlockEntityRenderer<CraftingRockBlockEntity> {

    public CraftingRockBlockRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(CraftingRockBlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (blockEntity != null && !blockEntity.isEmpty()) {
            int slot = 0;
            for (int i = 0; i < 3; i++) {
                for (int u = 0; u < 3; u++) {

                    if (!blockEntity.getItem(slot).isEmpty()) {
                        matrices.pushPose();

                        boolean isBlockItem = blockEntity.getItem(slot).getItem() instanceof BlockItem;

                        if (isBlockItem) {
                            matrices.scale(0.8f, 0.8f, 0.8f);
                        } else {
                            matrices.scale(0.5f, 0.5f, 0.5f);
                        }

                        matrices.mulPose(Axis.XN.rotationDegrees(90.0f));

                        Direction direction = blockEntity.getBlockState().getValue(CraftingRockBlock.FACING);
                        matrices.mulPose(Axis.ZN.rotationDegrees(direction.toYRot() - 180.0f));

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

                        Minecraft.getInstance().getItemRenderer().renderStatic(blockEntity.getItem(slot), ItemDisplayContext.GROUND,
                                LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above()), overlay, matrices, vertexConsumers, blockEntity.getLevel(),
                                (int) blockEntity.getBlockPos().asLong());
                        matrices.popPose();
                    }
                    slot++;
                }
            }
        }
    }
}
