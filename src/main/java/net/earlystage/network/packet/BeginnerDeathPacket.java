package net.earlystage.network.packet;

import net.earlystage.EarlyStageMain;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record BeginnerDeathPacket(int entityId, int deathCount) implements CustomPacketPayload {

    public static final Type<BeginnerDeathPacket> PACKET_ID = new Type<>(EarlyStageMain.identifierOf("beginner_death_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, BeginnerDeathPacket> PACKET_CODEC = StreamCodec.ofMember((value, buf) -> {
        buf.writeInt(value.entityId);
        buf.writeInt(value.deathCount);
    }, buf -> new BeginnerDeathPacket(buf.readInt(), buf.readInt()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }

}
