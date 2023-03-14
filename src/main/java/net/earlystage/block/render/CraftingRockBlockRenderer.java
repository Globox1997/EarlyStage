package net.earlystage.block.render;

import net.fabricmc.api.Environment;
import net.earlystage.block.CraftingRockBlock;
import net.earlystage.block.entity.CraftingRockBlockEntity;
import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class CraftingRockBlockRenderer implements BlockEntityRenderer<CraftingRockBlockEntity> {

    public CraftingRockBlockRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(CraftingRockBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (blockEntity != null && !blockEntity.isEmpty()) {
            int slot = 0;
            for (int i = 0; i < 3; i++) {
                for (int u = 0; u < 3; u++) {

                    if (!blockEntity.getStack(slot).isEmpty()) {
                        matrices.push();

                        boolean isBlockItem = blockEntity.getStack(slot).getItem() instanceof BlockItem;

                        if (isBlockItem) {
                            matrices.scale(0.8f, 0.8f, 0.8f);
                        } else {
                            matrices.scale(0.5f, 0.5f, 0.5f);
                        }

                        matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(90.0f));

                        Direction direction = blockEntity.getCachedState().get(CraftingRockBlock.FACING);
                        matrices.multiply(Vec3f.NEGATIVE_Z.getDegreesQuaternion(direction.asRotation() - 180.0f));

                        if (direction.equals(Direction.NORTH)) {
                            if (isBlockItem) {
                                // matrices.translate(i == 0 ? 0.23D : i == 1 ? 0.63D : 1.02D, u == 0 ? -1.2D : u == 1 ? -0.81D : -0.42D, 0.75D);
                                matrices.translate(i == 0 ? 0.23D : i == 1 ? 0.63D : 1.02D, u == 0 ? -0.42D : u == 1 ? -0.81D : -1.2D, 0.75D);
                            } else {
                                // matrices.translate(i == 0 ? 0.36D : i == 1 ? 1.0D : 1.63D, u == 0 ? -1.75D : u == 1 ? -1.12D : -0.5D, 1.02D);
                                matrices.translate(i == 0 ? 0.36D : i == 1 ? 1.0D : 1.63D, u == 0 ? -0.5D : u == 1 ? -1.12D : -1.75D, 1.02D);
                            }
                        } else if (direction.equals(Direction.EAST)) {
                            if (isBlockItem) {
                                // matrices.translate(u == 0 ? 1.02D : u == 1 ? 0.63D : 0.23D, i == 0 ? -1.95D : i == 1 ? -1.56D : -1.17D, 0.75D);
                                matrices.translate(u == 0 ? 0.23D : u == 1 ? 0.63D : 1.02D, i == 0 ? -1.95D : i == 1 ? -1.56D : -1.17D, 0.75D);
                            } else {
                                // matrices.translate(u == 0 ? 1.63D : u == 1 ? 1.0D : 0.36D, i == 0 ? -1.75D : i == 1 ? -1.12D : -0.5D, 1.02D);
                                matrices.translate(u == 0 ? 0.36D : u == 1 ? 1.0D : 1.63D, i == 0 ? -1.75D : i == 1 ? -1.12D : -0.5D, 1.02D);
                            }
                            matrices.translate(0.0D, 2.0D, 0.0D);
                        } else if (direction.equals(Direction.SOUTH)) {
                            if (isBlockItem) {
                                // matrices.translate(i == 0 ? 1.77D : i == 1 ? 1.38 : 0.98D, u == 0 ? -1.17D : u == 1 ? -1.56D : -1.95D, 0.75D);
                                matrices.translate(i == 0 ? 1.77D : i == 1 ? 1.38 : 0.98D, u == 0 ? -1.95D : u == 1 ? -1.56D : -1.17D, 0.75D);
                            } else {
                                // matrices.translate(i == 0 ? 1.63D : i == 1 ? 1.0D : 0.36D, u == 0 ? -0.5D : u == 1 ? -1.12D : -1.75D, 1.02D);
                                matrices.translate(i == 0 ? 1.63D : i == 1 ? 1.0D : 0.36D, u == 0 ? -1.75D : u == 1 ? -1.12D : -0.5D, 1.02D);
                            }
                            matrices.translate(-2.0D, 2.0D, 0.0D);
                        } else if (direction.equals(Direction.WEST)) {
                            if (isBlockItem) {
                                // matrices.translate(u == 0 ? 0.98D : u == 1 ? 1.38D : 1.77D, i == 0 ? -0.42D : i == 1 ? -0.81D : -1.2D, 0.75D);
                                matrices.translate(u == 0 ? 1.77D : u == 1 ? 1.38D : 0.98D, i == 0 ? -0.42D : i == 1 ? -0.81D : -1.2D, 0.75D);
                            } else {
                                // matrices.translate(u == 0 ? 0.36D : u == 1 ? 1.0D : 1.63D, i == 0 ? -0.5D : i == 1 ? -1.12D : -1.75D, 1.02D);
                                matrices.translate(u == 0 ? 1.63D : u == 1 ? 1.0D : 0.36D, i == 0 ? -0.5D : i == 1 ? -1.12D : -1.75D, 1.02D);
                            }
                            matrices.translate(-2.0D, 0.0D, 0.0D);
                        }

                        MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getStack(slot), ModelTransformation.Mode.GROUND,
                                WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up()), overlay, matrices, vertexConsumers, (int) blockEntity.getPos().asLong());
                        matrices.pop();
                    }
                    slot++;
                }
            }
        }
    }
}
