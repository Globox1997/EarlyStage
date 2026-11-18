package net.earlystage.init;

import java.util.function.BiConsumer;

import net.earlystage.EarlyStageMain;
import net.earlystage.data.DataLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;

public class LoaderInit {

    public static void register(BiConsumer<Identifier, PreparableReloadListener> registrar) {
        registrar.accept(EarlyStageMain.identifierOf("loader"), new DataLoader());
    }

}
