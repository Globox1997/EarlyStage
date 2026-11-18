package net.earlystage.network.packet;

import net.earlystage.EarlyStageMain;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ConfigSyncPacket(boolean blastFurnaceExtraSlot) implements CustomPacketPayload {

    public static final Type<ConfigSyncPacket> PACKET_ID = new Type<>(EarlyStageMain.identifierOf("config_sync_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ConfigSyncPacket> PACKET_CODEC = StreamCodec.ofMember((value, buf) -> {
        buf.writeBoolean(value.blastFurnaceExtraSlot);
    }, buf -> new ConfigSyncPacket(buf.readBoolean()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }

}
