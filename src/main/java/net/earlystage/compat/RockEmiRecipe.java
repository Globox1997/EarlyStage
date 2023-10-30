package net.earlystage.compat;

import java.util.List;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.widget.WidgetHolder;
import dev.emi.emi.recipe.EmiShapedRecipe;
import net.earlystage.init.ConfigInit;
import net.earlystage.init.RenderInit;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RockEmiRecipe extends EmiShapedRecipe {

    public RockEmiRecipe(ShapedRecipe recipe) {
        super(recipe);
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addText(Text.translatable("emi.crafting_rock.info", ConfigInit.CONFIG.craftRockCraftHits), 59, 5, 0xFFFFFF, true);
        widgets.addTexture(RenderInit.GUI_ICON_TEXTURES, 65, 38, 15, 12, 34, 0);
        super.addWidgets(widgets);
        widgets.addTooltipText(List.of(Text.translatable("emi.crafting_rock.tooltip")), 65, 38, 15, 12);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EarlyStageEmiPlugin.CRAFTING_ROCK_CATEGORY;
    }

    @Override
    public Identifier getId() {
        Identifier newId = new Identifier(super.getId().getPath() + "_rock");
        return newId;
    }

}
