
package net.earlystage.compat;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.earlystage.EarlyStageMain;
import net.earlystage.data.SieveDropTemplate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class SieveEmiRecipe implements EmiRecipe {

    private final List<EmiIngredient> inputs;
    private final List<EmiStack> outputs;
    private final Identifier id;

    public SieveEmiRecipe(SieveDropTemplate template) {
        this.id = EarlyStageMain.identifierOf("/sieve/" + template.getBlockItem().toString().replace(':', '_'));
        this.inputs = List.of(EmiIngredient.of(Ingredient.ofItems(template.getBlockItem())));

        this.outputs = new ArrayList<>();
        for (int i = 0; i < template.getBlockDrops().size(); i++) {
            EmiStack stack = EmiStack.of(template.getBlockDrops().get(i));
            stack.setChance(template.getDropChances().get(i));
            this.outputs.add(stack);
        }
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EarlyStageEmiPlugin.SIEVE_CATEGORY;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return outputs;
    }

    @Override
    public int getDisplayWidth() {
        return 120;
    }

    @Override
    public int getDisplayHeight() {
        int rows = (outputs.size() + 3) / 4;
        return 4 + rows * 18 + 4;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        int extraY = ((outputs.size() + 3) / 4 - 1) * 8;
        widgets.addSlot(inputs.get(0), 3, 6 + extraY);
        widgets.addFillingArrow(23, 6 + extraY, -1);
        for (int i = 0; i < outputs.size(); i++) {
            int x = 48 + (i % 4) * 18;
            int y = 4 + (i / 4) * 18;
            widgets.addSlot(outputs.get(i), x, y).recipeContext(this);
        }
    }
}