package net.earlystage.network;

import net.earlystage.network.packet.BeginnerDeathPacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class EarlyServerPacket {

    public static void init() {
        PayloadTypeRegistry.playS2C().register(BeginnerDeathPacket.PACKET_ID, BeginnerDeathPacket.PACKET_CODEC);
    }

}
