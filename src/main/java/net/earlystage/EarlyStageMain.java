package net.earlystage;

import java.util.ArrayList;
import java.util.List;

import net.earlystage.data.SieveDropTemplate;
import net.earlystage.init.BlockInit;
import net.earlystage.init.ConfigInit;
import net.earlystage.init.EventInit;
import net.earlystage.init.ItemInit;
import net.earlystage.init.JsonReaderInit;
import net.earlystage.init.TagInit;
import net.earlystage.init.WorldInit;
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
    }

}
