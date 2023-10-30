package net.earlystage.item;

import net.minecraft.item.HoeItem;
import net.minecraft.item.ToolMaterial;

public class EarlyStageHoeItem extends HoeItem {

    // Needed cause HoeItem constructor is protected
    public EarlyStageHoeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

}
