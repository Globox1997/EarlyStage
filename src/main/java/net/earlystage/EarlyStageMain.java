package net.earlystage;

import java.util.ArrayList;
import java.util.List;

import net.earlystage.data.SieveDropTemplate;
import net.earlystage.init.*;
import net.earlystage.network.EarlyServerPacket;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class EarlyStageMain implements ModInitializer {

    public static final List<SieveDropTemplate> SIEVE_DROP_TEMPLATES = new ArrayList<SieveDropTemplate>();

    @Override
    public void onInitialize() {
        ConfigInit.init();
        BlockInit.init();
        ItemInit.init();
        CompatInit.init();
        TagInit.init();
        EventInit.init();
        JsonReaderInit.init();
        WorldInit.init();
        RecipeInit.init();
        EarlyServerPacket.init();
    }

    public static Identifier identifierOf(String name) {
        return Identifier.of("earlystage", name);
    }

}
