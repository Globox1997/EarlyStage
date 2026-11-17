package net.earlystage.block.render;

import org.jspecify.annotations.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import net.earlystage.block.entity.SieveBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class SieveBlockRenderer implements BlockEntityRenderer<SieveBlockEntity, SieveBlockRenderer.SieveRenderState> {

    private final ItemModelResolver itemModelResolver;

    public SieveBlockRenderer(BlockEntityRendererProvider.Context ctx) {
        this.itemModelResolver = ctx.itemModelResolver();
    }

    @Override
    public SieveRenderState createRenderState() {
        return new SieveRenderState();
    }

    @Override
    public void extractRenderState(SieveBlockEntity blockEntity, SieveRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        if (blockEntity.getLevel() != null) {
            state.lightCoords = LightCoordsUtil.getLightCoords(blockEntity.getLevel(), blockEntity.getBlockPos().above());
        }
        state.sieveCount = blockEntity.getSieveCount();
        this.itemModelResolver.updateForTopItem(state.item, blockEntity.getItem(0), ItemDisplayContext.GROUND, blockEntity.getLevel(), null, (int) blockEntity.getBlockPos().asLong());
    }

    @Override
    public void submit(SieveRenderState state, PoseStack matrices, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (!state.item.isEmpty()) {
            matrices.pushPose();
            double height;
            switch (state.sieveCount) {
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
            state.item.submit(matrices, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            matrices.popPose();
        }
    }

    @Environment(EnvType.CLIENT)
    public static class SieveRenderState extends BlockEntityRenderState {
        public final ItemStackRenderState item = new ItemStackRenderState();
        public int sieveCount;
    }
}
