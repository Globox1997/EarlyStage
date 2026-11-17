package net.earlystage.init;

import net.earlystage.data.DataLoader;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class LoaderInit {

    public static void init() {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new DataLoader());
    }

}
