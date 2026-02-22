package net.earlystage.network.packet;

import net.earlystage.EarlyStageMain;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record BeginnerDeathPacket(int entityId, int deathCount) implements CustomPayload {

    public static final Id<BeginnerDeathPacket> PACKET_ID = new Id<>(EarlyStageMain.identifierOf("beginner_death_packet"));

    public static final PacketCodec<RegistryByteBuf, BeginnerDeathPacket> PACKET_CODEC = PacketCodec.of((value, buf) -> {
        buf.writeInt(value.entityId);
        buf.writeInt(value.deathCount);
    }, buf -> new BeginnerDeathPacket(buf.readInt(), buf.readInt()));

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

}
