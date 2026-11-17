package net.earlystage.item;

import java.util.function.Consumer;

import com.mojang.blaze3d.platform.InputConstants;

import net.earlystage.init.ConfigInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

public class CraftingRockBlockItem extends BlockItem {

    public CraftingRockBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        if (ConfigInit.CONFIG.infoTooltips) {
            if (InputConstants.isKeyDown(InputConstants.KEY_LSHIFT)) {
                builder.accept(Component.translatable("block.earlystage.crafting_rock.tooltip"));
            } else {
                builder.accept(Component.translatable("earlystage.moreinfo.tooltip"));
            }
        }
        super.appendHoverText(stack, context, display, builder, flag);
    }

}
