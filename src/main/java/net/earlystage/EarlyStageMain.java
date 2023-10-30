package net.earlystage;

import java.util.ArrayList;
import java.util.List;

import net.earlystage.data.SieveDropTemplate;
import net.earlystage.init.*;
import net.fabricmc.api.ModInitializer;

public class EarlyStageMain implements ModInitializer {

    public static final List<SieveDropTemplate> SIEVE_DROP_TEMPLATES = new ArrayList<SieveDropTemplate>();

    @Override
    public void onInitialize() {
        ConfigInit.init();
        BlockInit.init();
        ItemInit.init();
        TagInit.init();
        EventInit.init();
        JsonReaderInit.init();
        WorldInit.init();
        RecipeInit.init();
    }

}
