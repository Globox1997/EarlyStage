package net.earlystage.item.material;

import net.earlystage.EarlyStageMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class EarlyStageToolMaterials {

    public static final TagKey<Item> FLINT_TOOL_MATERIALS = TagKey.create(Registries.ITEM, EarlyStageMain.identifierOf("flint_tool_materials"));
    public static final TagKey<Item> STEEL_TOOL_MATERIALS = TagKey.create(Registries.ITEM, EarlyStageMain.identifierOf("steel_tool_materials"));
    public static final TagKey<Item> DAMASCUS_STEEL_TOOL_MATERIALS = TagKey.create(Registries.ITEM, EarlyStageMain.identifierOf("damascus_steel_tool_materials"));

    public static final ToolMaterial FLINT = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 29, 1.5f, 0.0f, 1, FLINT_TOOL_MATERIALS);

    public static final ToolMaterial STEEL = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 500, 7.0f, 2.0f, 14, STEEL_TOOL_MATERIALS);

    public static final ToolMaterial DAMASCUS_STEEL = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 720, 7.0f, 2.5f, 16, DAMASCUS_STEEL_TOOL_MATERIALS);

}
