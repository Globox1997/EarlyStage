package net.earlystage.block.render;

import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
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

                        // Rotation WIP
                        // matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(blockEntity.getCachedState().get(CraftingRockBlock.FACING).asRotation()));
                        // matrices.translate(ConfigInit.CONFIG.test1, 0D, ConfigInit.CONFIG.test2);

                        matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(90.0f));
                        // matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(ConfigInit.CONFIG.test5));
                        // matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(90));

                        // matrices.translate(i == 0 ? ConfigInit.CONFIG.test1 : i == 2 ? ConfigInit.CONFIG.test1xx : ConfigInit.CONFIG.test1x,
                        // u == 0 ? ConfigInit.CONFIG.test2 : u == 2 ? ConfigInit.CONFIG.test2xx : ConfigInit.CONFIG.test2x, ConfigInit.CONFIG.test3);
                        // if (blockEntity.getStack(slot).getItem() instanceof BlockItem)
                        // matrices.translate(i == 0 ? 0.25D : i == 2 ? 1.75D : 1.0D, u == 0 ? -0.5D : u == 2 ? -1.9D : -1.2D, ConfigInit.CONFIG.test3);
                        // else
                        // matrices.translate(i == 0 ? ConfigInit.CONFIG.test1 : i == 2 ? ConfigInit.CONFIG.test1xx : ConfigInit.CONFIG.test1x,
                        // u == 0 ? ConfigInit.CONFIG.test2 : u == 2 ? ConfigInit.CONFIG.test2xx : ConfigInit.CONFIG.test2x, ConfigInit.CONFIG.test3);

                        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
                            if (isBlockItem) {
                                matrices.translate(i == 0 ? 0.23D : i == 2 ? 1.02D : 0.63D, u == 0 ? -0.42D : u == 2 ? -1.2D : -0.81D, 0.75D);
                            } else {
                                matrices.translate(i == 0 ? 0.36D : i == 2 ? 1.63D : 1.0D, u == 0 ? -0.5D : u == 2 ? -1.75D : -1.12D, 1.02D);
                            }
                        } else {
                            if (isBlockItem) {
                                matrices.translate(i == 0 ? -0.42D : i == 2 ? -1.2D : -0.81D, u == 0 ? 0.23D : u == 2 ? 1.02D : 0.63D, 0.75D);
                                matrices.translate(1.43D, -1.43D, 0.0D);
                            } else {
                                matrices.translate(i == 0 ? -0.5D : i == 2 ? -1.75D : -1.12D, u == 0 ? 0.36D : u == 2 ? 1.63D : 1.0D, 1.02D);
                                matrices.translate(2.1D, -2.1D, 0.0D);
                            }

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
