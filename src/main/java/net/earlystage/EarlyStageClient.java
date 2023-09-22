package net.earlystage;

import net.earlystage.init.ModelInit;
import net.earlystage.init.RenderInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class EarlyStageClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RenderInit.init();
        ModelInit.init();
    }

}
