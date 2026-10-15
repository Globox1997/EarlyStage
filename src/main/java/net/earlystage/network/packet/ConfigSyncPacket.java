package net.earlystage.network.packet;

import net.earlystage.EarlyStageMain;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record ConfigSyncPacket(boolean blastFurnaceExtraSlot) implements CustomPayload {

    public static final Id<ConfigSyncPacket> PACKET_ID = new Id<>(EarlyStageMain.identifierOf("config_sync_packet"));

    public static final PacketCodec<RegistryByteBuf, ConfigSyncPacket> PACKET_CODEC = PacketCodec.of((value, buf) -> {
        buf.writeBoolean(value.blastFurnaceExtraSlot);
    }, buf -> new ConfigSyncPacket(buf.readBoolean()));

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

}
