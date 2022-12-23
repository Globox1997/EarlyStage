package net.earlystage.mixin.access;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.loot.condition.LootCondition;

@Mixin(BlockLootTableGenerator.class)
public interface BlockLootTableGeneratorAccessor {

    @Accessor("WITHOUT_SILK_TOUCH_NOR_SHEARS")
    static LootCondition.Builder getWithoutSilkTouchNorShears() {
        throw new AssertionError("This should not occur!");
    }

}
