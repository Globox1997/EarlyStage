package net.earlystage.init;

import net.earlystage.data.DataLoader;
import net.earlystage.EarlyStageMain;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.server.packs.PackType;

public class LoaderInit {

    public static void init() {
        ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(EarlyStageMain.identifierOf("loader"), new DataLoader());
    }

}
