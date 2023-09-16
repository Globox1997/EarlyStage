package net.earlystage.block.render;

import net.fabricmc.api.Environment;
import net.earlystage.block.entity.SieveBlockEntity;
import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class SieveBlockRenderer implements BlockEntityRenderer<SieveBlockEntity> {

    public SieveBlockRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(SieveBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (blockEntity != null && !blockEntity.isEmpty()) {
            matrices.push();
            double height;
            switch (blockEntity.getSieveCount()) {
            case 0:
                height = 0.7D;
                break;
            case 1:
                height = 0.532D;
                break;
            case 2:
                height = 0.366D;
                break;
            case 3:
                height = 0.2D;
                break;
            default:
                height = 0.2D;
                break;
            }
            matrices.translate(0.5D, height, 0.5D);
            matrices.scale(3.0f, 3.0f, 3.0f);

            MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getStack(0), ModelTransformationMode.GROUND,
                    WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up()), overlay, matrices, vertexConsumers, blockEntity.getWorld(),
                    (int) blockEntity.getPos().asLong());
            matrices.pop();
        }
    }
}