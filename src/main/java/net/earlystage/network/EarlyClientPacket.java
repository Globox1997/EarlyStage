package net.earlystage.network;

import net.earlystage.EarlyStageMain;
import net.earlystage.network.packet.BeginnerDeathPacket;
import net.earlystage.network.packet.SieveDropPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.stat.Stats;

@Environment(EnvType.CLIENT)
public class EarlyClientPacket {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(BeginnerDeathPacket.PACKET_ID, (payload, context) -> {
            int entityId = payload.entityId();
            int deathCount = payload.deathCount();

            if (context.client().world.getEntityById(entityId) instanceof ClientPlayerEntity clientPlayerEntity) {
                clientPlayerEntity.getStatHandler().setStat(clientPlayerEntity, Stats.CUSTOM.getOrCreateStat(Stats.DEATHS), deathCount);
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(SieveDropPacket.PACKET_ID, (payload, context) -> {
                    context.client().execute(() -> {
                        EarlyStageMain.SIEVE_DROP_TEMPLATES.clear();
                        EarlyStageMain.SIEVE_DROP_TEMPLATES.addAll(payload.templates());
                    });
                }
        );
    }

}
